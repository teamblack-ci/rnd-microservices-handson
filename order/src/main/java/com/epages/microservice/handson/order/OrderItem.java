package com.epages.microservice.handson.order;

import javax.money.Monetary;
import javax.persistence.*;

import java.net.URI;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private URI pizza;

    private Integer amount;

    private Monetary totalPrice;
}
