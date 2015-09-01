package com.epages.microservice.handson.order;

import org.javamoney.moneta.Money;

public class Pizza {

    private String name;

    private Money price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }
}
