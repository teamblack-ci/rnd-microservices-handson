package com.epages.microservice.handson.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    private static final Long DELIVERY_DELAY_IN__MILLIS = 15_000l;

    private DeliveryEventPublisher deliveryEventPublisher;
    private RestTemplate restTemplate;

    @Autowired
    public DeliveryServiceImpl(DeliveryEventPublisher deliveryEventPublisher,
                               RestTemplate restTemplate) {
        this.deliveryEventPublisher = deliveryEventPublisher;
        this.restTemplate = restTemplate;
    }
    @Override
    @Async("deliveryThreadPoolTaskExecutor")
    public void deliver(URI orderUri) {
        final Order order = restTemplate.getForObject(orderUri, Order.class);
        LOGGER.info("Read order from URI {} - got {}", orderUri, order);

        order.setOrderLink(orderUri);
        deliveryEventPublisher.sendDeliveryStartedEvent(order);

        delay(DELIVERY_DELAY_IN__MILLIS);

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
