package com.epages.microservice.handson.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class OrderServiceClient {

    private RestTemplate restTemplate;

    @Autowired
    public OrderServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Order getOrder(URI orderUri) {
        try {
            return restTemplate.getForObject(orderUri, Order.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new IllegalArgumentException(String.format("Order with URI %s could not be found", orderUri), e);
            } else {
                throw e;
            }
        }
    }
}
