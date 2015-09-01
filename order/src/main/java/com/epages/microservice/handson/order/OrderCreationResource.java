package com.epages.microservice.handson.order;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class OrderCreationResource extends ResourceSupport {

    private List<LineItemResource> items;
    private Address deliveryAddress;
    private String comment;

    public List<LineItemResource> getItems() {
        return items;
    }

    public void setItems(List<LineItemResource> items) {
        this.items = items;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
