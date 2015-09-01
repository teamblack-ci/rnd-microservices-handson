package com.epages.microservice.handson.bakery;

import com.epages.microservice.handson.shared.event.EventPublisher;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BakeryEventPublisher {

    private EventPublisher eventPublisher;

    private static final String BAKING_ORDER_RECEIVED_EVENT_TYPE = "BakingOrderReceived";
    private static final String BAKING_FINISHED_EVENT_TYPE = "BakingFinished";

    @Autowired
    public BakeryEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void sendBakingOrderReceivedEvent(Order order) {
        Map<String, Object> payloadMap = ImmutableMap.of(
                "orderLink", order.getOrderLink(),
                "estimatedTimeOfCompletion", order.getEstimatedTimeOfCompletion());
        eventPublisher.publish(BAKING_ORDER_RECEIVED_EVENT_TYPE, payloadMap);
    }

    public void sendBakingFinishedEvent(Order order) {
        Map<String, Object> payloadMap = ImmutableMap.of(
                "orderLink", order.getOrderLink());
        eventPublisher.publish(BAKING_FINISHED_EVENT_TYPE, payloadMap);
    }


}
