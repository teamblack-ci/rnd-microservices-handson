package com.epages.microservice.handson.bakery.order;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.MoreObjects.toStringHelper;

public class Order {

    @JsonIgnore
    private URI orderLink;

    private List<LineItem> orderItems = new ArrayList<>();

    public URI getOrderLink() {
        return orderLink;
    }

    public void setOrderLink(URI orderLink) {
        this.orderLink = orderLink;
    }

    public List<LineItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<LineItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("orderItems", orderItems).toString();
    }
}
