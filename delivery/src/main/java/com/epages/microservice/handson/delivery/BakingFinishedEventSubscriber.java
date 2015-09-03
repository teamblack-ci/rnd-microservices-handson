package com.epages.microservice.handson.delivery;

import com.epages.microservice.handson.shared.event.AbstractEventSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;

@Component
public class BakingFinishedEventSubscriber extends AbstractEventSubscriber {

    private static final String BAKING_FINISHED_EVENT_TYPE = "BakingFinished";

    private static final Logger LOGGER = LoggerFactory.getLogger(BakingFinishedEventSubscriber.class);
    private final DeliveryService deliveryService;

    @Autowired
    public BakingFinishedEventSubscriber(ObjectMapper objectMapper,
                                         DeliveryService deliveryService) {
        super(objectMapper, BAKING_FINISHED_EVENT_TYPE);
        this.deliveryService = deliveryService;
    }

    @Override
    protected void handleOwnType(Map<String, Object> event) {
        Map<String, Object> payload = getPayload(event);
        String orderUriString = (String) payload.get("orderLink");
        URI orderUri = URI.create(orderUriString);
        deliveryService.startDelivery(orderUri);
    }

}
