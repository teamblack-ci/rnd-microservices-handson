package com.epages.microservice.handson.order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private OrderStatus status;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime created;

    private List<OrderItem> items = emptyList();

}
