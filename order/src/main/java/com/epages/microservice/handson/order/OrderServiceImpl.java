package com.epages.microservice.handson.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private PizzaServiceClient pizzaClientService;
    private OrderEventPublisher orderEventPublisher;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            PizzaServiceClient pizzaClientService,
                            OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.pizzaClientService = pizzaClientService;
        this.orderEventPublisher = orderEventPublisher;
    }

    @Override
    public Order create(Order order) {
        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("order does not have items");
        }
        getLineItemPrices(order);
        Order savedOrder = orderRepository.save(order);

        orderEventPublisher.sendOrderCreatedEvent(savedOrder);
        LOGGER.info("order created {}", order);
        return savedOrder;
    }

    private void getLineItemPrices(Order order) {
        order.getItems().forEach(lineItem -> {
                    Pizza pizza = pizzaClientService.getPizza(lineItem.getPizza());
                    lineItem.setPrice(pizza.getPrice());
                });
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrder(Long id) {
        return Optional.ofNullable(orderRepository.findOne(id));
    }

    @Override
    public Page<Order> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public void setOrderStatus(Long id, OrderStatus status) {
        Order order = getOrder(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Order %s not found")));
        order.setStatus(status);
        update(order);
    }
}
