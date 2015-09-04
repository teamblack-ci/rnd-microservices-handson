package com.epages.microservice.handson.bakery;

import com.epages.microservice.handson.shared.event.EventPublisher;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
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

    public void sendBakingOrderReceivedEvent(BakeryOrderReceivedEvent event) {
        Map<String, Object> payloadMap = ImmutableMap.of(
                "orderLink", event.getOrderLink(),
                "estimatedTimeOfCompletion", event.getEstimatedTimeOfCompletion());
        eventPublisher.publish(BAKING_ORDER_RECEIVED_EVENT_TYPE, payloadMap);
    }

    public void sendBakingFinishedEvent(URI orderLink) {
        //TODO construct an event payload and use the eventPublisher to send event with type BAKING_FINISHED_EVENT_TYPE
    }


}
