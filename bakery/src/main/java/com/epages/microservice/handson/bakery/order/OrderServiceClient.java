package com.epages.microservice.handson.bakery.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class OrderServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceClient.class);

    private RestTemplate restTemplate;

    @Autowired
    public OrderServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Order getOrder(URI orderUri) {
        Order order = restTemplate.getForObject(orderUri, Order.class);
        order.setOrderLink(orderUri);
        LOGGER.info("Read order from URI {} - got {}", orderUri, order);
        return order;
    }
}
