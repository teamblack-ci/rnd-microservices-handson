package com.epages.microservice.handson.order.orderstatus;

import com.epages.microservice.handson.order.OrderService;
import com.epages.microservice.handson.order.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeliveryStartedEventSubscriber extends OrderStatusEventSubscriber {

    private static final String DELIVERY_STARTED_EVENT_TYPE = "DeliveryStarted";

    @Autowired
    public DeliveryStartedEventSubscriber(ObjectMapper objectMapper, OrderService orderService) {
        super(orderService, objectMapper, DELIVERY_STARTED_EVENT_TYPE, OrderStatus.IN_DELIVERY);
    }
}
