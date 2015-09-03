package com.epages.microservice.handson.delivery;

import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.concurrent.Future;

public interface DeliveryService {

    public void startDelivery(URI orderUri);

    public void scheduleDelivery(BakingOrderReceivedEvent event);
}
