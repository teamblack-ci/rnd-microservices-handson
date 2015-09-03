package com.epages.microservice.handson.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
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
        try {
            Order order = restTemplate.getForObject(orderUri, Order.class);
            order.setOrderLink(orderUri);
            LOGGER.info("Read order from URI {} - got {}", orderUri, order);
            return order;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new IllegalArgumentException(String.format("Order with URI %s could not be found", orderUri), e);
            } else {
                throw e;
            }
        }
    }
}
