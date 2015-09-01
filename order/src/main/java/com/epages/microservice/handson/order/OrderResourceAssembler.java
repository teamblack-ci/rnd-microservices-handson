package com.epages.microservice.handson.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderResourceAssembler extends ResourceAssemblerSupport<Order, OrderResource> {

    private RelProvider relProvider;

    @Autowired
    public OrderResourceAssembler(RelProvider relProvider) {
        super(OrderController.class, OrderResource.class);
        this.relProvider = relProvider;
    }

    @Override
    public OrderResource toResource(Order order) {
        OrderResource orderResource = createResourceWithId(order.getId(), order);
        orderResource.setOrderItems(order.getItems().stream()
                .map(orderItem -> {
                    LineItemResource orderItemResource = new LineItemResource(orderItem);
                    //add links
                    return orderItemResource;
                }).collect(Collectors.toList()));

        return orderResource;
    }

    @Override
    protected OrderResource instantiateResource(Order order) {
        return new OrderResource(order);
    }
}
