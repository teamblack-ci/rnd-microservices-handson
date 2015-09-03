package com.epages.microservice.handson.delivery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epages.microservice.handson.shared.event.AbstractEventSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BakingFinishedEventSubscriber extends AbstractEventSubscriber {

    private static final String BAKING_FINISHED_EVENT_TYPE = "BakingFinished";

    private final DeliveryService deliveryService;

    @Autowired
    public BakingFinishedEventSubscriber(ObjectMapper objectMapper,
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
        deliveryService.startDelivery(orderUri);

    }

}
