package com.epages.microservice.handson.bakery;

import com.epages.microservice.handson.shared.event.AbstractEventSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OrderCreatedEventSubscriber extends AbstractEventSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreatedEventSubscriber.class);

    private static final String ORDER_CREATED_EVENT_TYPE = "OrderCreated";
    private BakeryService bakeryService;

    @Autowired
    public OrderCreatedEventSubscriber(ObjectMapper objectMapper, BakeryService bakeryService) {
        super(objectMapper, ORDER_CREATED_EVENT_TYPE);
        this.bakeryService = bakeryService;
        LOGGER.info("Created");
    }

    @Override
    protected void handleOwnType(Map<String, Object> event) {
        final Map<String, Object> payload = (Map<String, Object>) getPayload(event);
        try {
            Order order = objectMapper.readValue(objectMapper.writeValueAsString(payload), Order.class);
            bakeryService.acknowledgeOrder(order);
            bakeryService.bakeOrder(order);
        } catch (IOException e) {
            LOGGER.error("Error deserializing Order {}", event, e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Consumed {} event with payload '{}' of class '{}'", ORDER_CREATED_EVENT_TYPE, payload, payload.getClass());
    }
}
