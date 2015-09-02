package com.epages.microservice.handson.bakery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class BakeryServiceImpl implements BakeryService {

    private BakeryEventPublisher bakeryEventPublisher;

    private static Long TIME_TO_BAKE_PIZZA_IN_MILLIS = 30_000l;
    @Autowired
    public BakeryServiceImpl(BakeryEventPublisher bakeryEventPublisher) {
        this.bakeryEventPublisher = bakeryEventPublisher;
    }


    @Override
    public void acknowledgeOrder(Order order) {
        order.setEstimatedTimeOfCompletion(LocalDateTime.now().plusMinutes(TIME_TO_BAKE_PIZZA_IN_MILLIS));
        bakeryEventPublisher.sendBakingOrderReceivedEvent(order);
    }

    @Async("bakeryThreadPoolTaskExecutor")
    public void bakeOrder(Order order) {
        delay(TIME_TO_BAKE_PIZZA_IN_MILLIS);
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
