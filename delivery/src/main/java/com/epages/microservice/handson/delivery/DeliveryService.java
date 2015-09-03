package com.epages.microservice.handson.delivery;

import java.net.URI;

public interface DeliveryService {

    public void startDelivery(URI orderUri);

    public void scheduleDelivery(BakingOrderReceivedEvent event);
}
