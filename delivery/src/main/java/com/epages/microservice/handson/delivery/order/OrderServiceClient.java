package com.epages.microservice.handson.delivery.order;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceClient.class);

    private final RestTemplate restTemplate;

    @Autowired
    public OrderServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Order getOrder(URI orderUri) {
<<<<<<< HEAD
        Order order = restTemplate.getForObject(orderUri, Order.class);
=======
        Order order = null;
        //TODO use the restTemplate and the orderUri to retrieve an order from the order service
>>>>>>> inital version to start exercise
        LOGGER.info("Read order from URI {} - got {}", orderUri, order);
        return order;
    }
}
