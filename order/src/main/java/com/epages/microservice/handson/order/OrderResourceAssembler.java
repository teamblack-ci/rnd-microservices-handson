package com.epages.microservice.handson.order;

import java.util.stream.Collectors;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderResourceAssembler extends ResourceAssemblerSupport<Order, OrderResource> {

    public OrderResourceAssembler() {
        super(OrderController.class, OrderResource.class);
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
