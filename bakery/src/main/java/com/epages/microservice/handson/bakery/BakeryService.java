package com.epages.microservice.handson.bakery;

import java.net.URI;

public interface BakeryService {

    public void acknowledgeOrder(URI orderLink);
    public void bakeOrder(URI orderLink);
}
