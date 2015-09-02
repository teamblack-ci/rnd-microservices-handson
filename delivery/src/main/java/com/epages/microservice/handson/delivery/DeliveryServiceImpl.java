package com.epages.microservice.handson.delivery;

import com.google.common.primitives.Longs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    @Value("${delivery.timeToDeliverInMillis:15000}")
    private Long timeToDeliverInMillis;

    @Value("${delivery.timeToPrepareDeliveryInMillis:5000}")
    private Long timeToPrepareDeliveryInMillis;

    private DeliveryEventPublisher deliveryEventPublisher;
    private OrderServiceClient orderServiceClient;

    @Autowired
    public DeliveryServiceImpl(DeliveryEventPublisher deliveryEventPublisher,
                               OrderServiceClient orderServiceClient) {
        this.deliveryEventPublisher = deliveryEventPublisher;
        this.orderServiceClient = orderServiceClient;
    }
    @Override
    @Async("deliveryThreadPoolTaskExecutor")
    public void startDelivery(URI orderUri) {

        //retrieve the delivery address to be able to startDelivery
        Order order = orderServiceClient.getOrder(orderUri);
        LOGGER.info("Read order from URI {} - got {}", orderUri, order);
        order.setOrderLink(orderUri);

        deliver(order);
    }

    @Override
    public void scheduleDelivery(BakingOrderReceivedEvent event) {
        //estimate deliver completion

        Long deliveryTime = timeToPrepareDeliveryInMillis + timeToDeliverInMillis;

        DeliveryOrderReceivedEvent deliveryOrderReceivedEvent = new DeliveryOrderReceivedEvent();
        deliveryOrderReceivedEvent.setOrderLink(event.getOrderLink());
        deliveryOrderReceivedEvent.setEstimatedTimeOfDelivery(
                event.getEstimatedTimeOfCompletion().plusNanos(deliveryTime * 1_000_000));

        deliveryEventPublisher.sendDeliveryOrderReceivedEvent(deliveryOrderReceivedEvent);
    }

    public void deliver(Order order) {
        LOGGER.info("Working hard to deliver order {} to address {}", order.getOrderLink(), order.getDeliveryAddress());
        delay(timeToDeliverInMillis);

        deliveryEventPublisher.sendDeliveredEvent(order);
    }

    private void delay(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
