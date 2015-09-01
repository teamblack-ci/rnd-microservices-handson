package com.epages.microservice.handson.order;

import com.epages.microservice.handson.shared.event.AbstractEventSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public abstract class OrderStatusEventSubscriber extends AbstractEventSubscriber {

    private OrderService orderService;
    private OrderStatus orderStatus;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusEventSubscriber.class);

    protected OrderStatusEventSubscriber(OrderService orderService,
                                         ObjectMapper objectMapper,
                                         String type, OrderStatus orderStatus) {
        super(objectMapper, type);
        this.orderService = orderService;
        this.orderStatus = orderStatus;
    }

    @Override
    protected void handleOwnType(Map<String, Object> event) {
        Map<String, Object> payload = (Map<String, Object>) getPayload(event);
        Long orderId = getOrderIdFromPayload(payload, event);
        orderService.setOrderStatus(orderId, orderStatus);
        LOGGER.info("Consumed {} event with payload '{}'", super.type, payload);

    }

    private Long getOrderIdFromPayload(Map<String, Object> payload, Map<String, Object> event) {
        String orderUriString = (String) payload.get("orderLink");
        if (Strings.isNullOrEmpty(orderUriString)) {
            LOGGER.error("Event {} does not contain an orderLink", event);
            throw new IllegalArgumentException(String.format("Event %s does not contain an orderLink", event));
        }
        URI orderUri;
        try {
            orderUri = new URI(orderUriString);
        } catch (URISyntaxException e) {
            LOGGER.error("orderLink {} is not a valid URI in event {}", orderUriString, event);
            throw new IllegalArgumentException(String.format("orderLink %s is not a valid URI in event %s", orderUriString, event));
        }

        String[] pathItems = orderUri.getPath().split("/");
        if (pathItems != null && pathItems.length > 1) {
            String idPart = pathItems[pathItems.length - 1];
            return Long.valueOf(idPart);
        } else {
            throw new IllegalArgumentException(String.format("orderLink %s in event %s does not contain an id", orderUriString, event));
        }
    }
}
