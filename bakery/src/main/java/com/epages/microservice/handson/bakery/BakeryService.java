package com.epages.microservice.handson.bakery;

public interface BakeryService {

    public void acknowledgeOrder(Order order);
    public void bakeOrder(Order order);
}
