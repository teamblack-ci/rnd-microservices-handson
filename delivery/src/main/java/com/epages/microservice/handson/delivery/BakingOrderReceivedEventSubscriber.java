package com.epages.microservice.handson.delivery;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epages.microservice.handson.shared.event.AbstractEventSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BakingOrderReceivedEventSubscriber extends AbstractEventSubscriber {

    private static final String BAKING_ORDER_RECEIVED_EVENT_TYPE = "BakingOrderReceived";

    private static final Logger LOGGER = LoggerFactory.getLogger(BakingOrderReceivedEventSubscriber.class);
    private final DeliveryService deliveryService;

    @Autowired
    public BakingOrderReceivedEventSubscriber(ObjectMapper objectMapper,
                                              DeliveryService deliveryService) {
        super(objectMapper, BAKING_ORDER_RECEIVED_EVENT_TYPE);
        this.deliveryService = deliveryService;
    }

    @Override
    protected void handleOwnType(Map<String, Object> event) {
        Map<String, Object> payload = getPayload(event);
        BakingOrderReceivedEvent typedEvent = null;
        try {
            typedEvent = objectMapper.readValue(objectMapper.writeValueAsString(payload), BakingOrderReceivedEvent.class);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not deserialize event %s to %s", event, BakingOrderReceivedEvent.class.getName()));
        }

        deliveryService.scheduleDelivery(typedEvent);

    }

}
