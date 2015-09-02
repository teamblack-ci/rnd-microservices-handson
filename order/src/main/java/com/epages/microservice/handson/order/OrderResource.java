package com.epages.microservice.handson.order;

import org.springframework.hateoas.ResourceSupport;

import javax.money.MonetaryAmount;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResource extends ResourceSupport {

    private OrderStatus status;
    private LocalDateTime created;
    private LocalDateTime estimatedTimeOfDelivery;
    private MonetaryAmount totalPrice;
    private List<LineItemResource> orderItems;

    private Address deliveryAddress;
    public OrderResource(Order order) {
        this.status = order.getStatus();
        this.created = order.getCreatedAt();
        this.totalPrice = order.getTotalPrice();
        this.estimatedTimeOfDelivery = order.getEstimatedTimeOfDelivery();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getEstimatedTimeOfDelivery() {
        return estimatedTimeOfDelivery;
    }

    public void setEstimatedTimeOfDelivery(LocalDateTime estimatedTimeOfDelivery) {
        this.estimatedTimeOfDelivery = estimatedTimeOfDelivery;
    }

    public MonetaryAmount getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(MonetaryAmount totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<LineItemResource> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<LineItemResource> orderItems) {
        this.orderItems = orderItems;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
