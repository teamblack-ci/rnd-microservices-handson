package com.epages.microservice.handson.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderService {

    public Order create(Order order);
    public Order update(Order order);
    public Optional<Order> getOrder(Long id);

    public Page<Order> getAll(Pageable pageable);
}
