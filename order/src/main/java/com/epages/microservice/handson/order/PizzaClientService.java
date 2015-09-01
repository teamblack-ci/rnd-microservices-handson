package com.epages.microservice.handson.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class PizzaClientService {

    private final RestTemplate restTemplate;

    @Autowired
    public PizzaClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Pizza getPizza(URI pizzaUri) {
        ResponseEntity<Pizza> pizzaResponse;
        try {
            pizzaResponse = restTemplate.getForEntity(pizzaUri, Pizza.class);
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("Unable to retrieve Pizza URI %s - error is %s",
                    pizzaUri, t.getMessage()),t);
        }

        if (!pizzaResponse.getStatusCode().is4xxClientError()
                && !pizzaResponse.getStatusCode().is5xxServerError()) {
            return pizzaResponse.getBody();
        } else {
            throw new IllegalArgumentException(String.format("Pizza URI %s is not valid - returned %s",
                    pizzaUri, pizzaResponse.getStatusCode()));
        }
    }
}
