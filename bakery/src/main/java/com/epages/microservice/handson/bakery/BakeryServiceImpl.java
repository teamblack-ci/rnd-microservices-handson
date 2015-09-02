package com.epages.microservice.handson.bakery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

@Service
public class BakeryServiceImpl implements BakeryService {

    private BakeryEventPublisher bakeryEventPublisher;

    @Value("${bakery.timeToBakePizzaInMillis:15000}")
    private Long timeToBakePizzaInMillis;

    @Autowired
    public BakeryServiceImpl(BakeryEventPublisher bakeryEventPublisher) {
        this.bakeryEventPublisher = bakeryEventPublisher;
    }


    @Override
    public void acknowledgeOrder(Order order) {
        order.setEstimatedTimeOfCompletion(LocalDateTime.now().plusNanos(timeToBakePizzaInMillis * 1_000_000));
        bakeryEventPublisher.sendBakingOrderReceivedEvent(order);
    }

    @Async("bakeryThreadPoolTaskExecutor")
    public void bakeOrder(Order order) {
        delay(timeToBakePizzaInMillis);
        bakeryEventPublisher.sendBakingFinishedEvent(order);

    }

    private void delay(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
