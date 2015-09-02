package com.epages.microservice.handson.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.URI;
import java.time.LocalDateTime;


public class Order {

    @JsonIgnore
    private URI orderLink;
    private Address deliveryAddress;
    private String comment;

    public URI getOrderLink() {
        return orderLink;
    }

    public void setOrderLink(URI orderLink) {
        this.orderLink = orderLink;
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
