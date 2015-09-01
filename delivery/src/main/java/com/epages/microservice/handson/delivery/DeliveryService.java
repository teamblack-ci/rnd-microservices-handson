package com.epages.microservice.handson.delivery;

import java.net.URI;

public interface DeliveryService {

    public void deliver(URI orderUri);
}
