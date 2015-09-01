package com.epages.microservice.handson.order;

import com.epages.microservice.handson.shared.event.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private PizzaClientService pizzaClientService;
    private EventPublisher eventPublisher;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            PizzaClientService pizzaClientService
                            ) {
        this.orderRepository = orderRepository;
        this.pizzaClientService = pizzaClientService;
        //this.eventPublisher = eventPublisher;
    }

    @Override
    public Order create(Order order) {
        RestTemplate restTemplate = new RestTemplate();

        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("order does not have items");
        }
        getLineItemPrices(order, restTemplate);
        Order savedOrder = orderRepository.save(order);
        //TODO send event
        return savedOrder;
    }

    private void getLineItemPrices(Order order, RestTemplate restTemplate) {
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
}
