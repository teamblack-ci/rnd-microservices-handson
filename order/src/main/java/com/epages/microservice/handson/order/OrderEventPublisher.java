package com.epages.microservice.handson.order;

import com.epages.microservice.handson.shared.event.EventPublisher;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderEventPublisher {

    private EventPublisher eventPublisher;
    private EntityLinks entityLinks;

    private static final String ORDER_CREATED_EVENT_TYPE = "OrderCreated";

    @Autowired
    public  OrderEventPublisher(EventPublisher eventPublisher, EntityLinks entityLinks) {
        this.eventPublisher = eventPublisher;
        this.entityLinks = entityLinks;
    }

    public void sendOrderCreatedEvent(final Order order) {
        Map<String, Object> payloadMap = ImmutableMap.of(
                "orderLink", entityLinks.linkForSingleResource(Order.class, order.getId()).toUri().toString());
        eventPublisher.publish(ORDER_CREATED_EVENT_TYPE, payloadMap);
    }

}
