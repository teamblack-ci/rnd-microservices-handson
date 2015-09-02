package com.epages.microservice.handson.delivery;

import com.epages.microservice.handson.shared.event.AbstractEventSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Component
public class OrderStatusEventSubscriber extends AbstractEventSubscriber {

    private static final String BAKING_FINISHED_EVENT_TYPE = "BakingFinished";

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusEventSubscriber.class);
    private final DeliveryService deliveryService;

    @Autowired
    public OrderStatusEventSubscriber(ObjectMapper objectMapper,
                                         DeliveryService deliveryService) {
        super(objectMapper, BAKING_FINISHED_EVENT_TYPE);
        this.deliveryService = deliveryService;
    }

    @Override
    protected void handleOwnType(Map<String, Object> event) {
        Map<String, Object> payload = (Map<String, Object>) getPayload(event);
        String orderUriString = (String) payload.get("orderLink");
        URI orderUri = null;
        try {
            orderUri = new URI(orderUriString);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(String.format("URI %s is invalid in event %s", orderUriString, event));
        }
        deliveryService.deliver(orderUri);

    }

}
