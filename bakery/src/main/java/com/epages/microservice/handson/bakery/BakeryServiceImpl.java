package com.epages.microservice.handson.bakery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class BakeryServiceImpl implements BakeryService {

    private BakeryEventPublisher bakeryEventPublisher;
    private BakeryOrderRepository bakeryOrderRepository;
    private OrderServiceClient orderServiceClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(BakeryServiceImpl.class);

    @Value("${bakery.timeToBakePizzaInMillis:15000}")
    private Long timeToBakePizzaInMillis;

    @Autowired
    public BakeryServiceImpl(BakeryEventPublisher bakeryEventPublisher,
                             BakeryOrderRepository bakeryOrderRepository,
                             OrderServiceClient orderServiceClient) {
        this.bakeryEventPublisher = bakeryEventPublisher;
        this.bakeryOrderRepository = bakeryOrderRepository;
        this.orderServiceClient = orderServiceClient;
    }

    @Override
    public void acknowledgeOrder(URI orderLink) {
        saveBakeryOrder(orderLink);

        sendBakingOrderReceivedEvent(orderLink);
    }

    @Async("bakeryThreadPoolTaskExecutor")
    public void bakeOrder(URI orderLink) {
        BakeryOrder bakeryOrder = bakeryOrderRepository.getBakeryOrderByOrderLink(orderLink);
        updateOrderState(bakeryOrder, BakeryOrderState.IN_PROGRESS);

        //retrieve the order to get the pizzas to bake
        Order order = orderServiceClient.getOrder(orderLink);
        //do all the work
        bake(order);

        updateOrderState(bakeryOrder, BakeryOrderState.DONE);
        bakeryEventPublisher.sendBakingFinishedEvent(orderLink);

    }

    private void updateOrderState(BakeryOrder bakeryOrder, BakeryOrderState orderState) {
        bakeryOrder.setBakeryOrderState(orderState);
        bakeryOrderRepository.save(bakeryOrder);
    }

    private void sendBakingOrderReceivedEvent(URI orderLink) {
        BakeryOrderReceivedEvent bakeryOrderReceivedEvent = new BakeryOrderReceivedEvent();
        bakeryOrderReceivedEvent.setOrderLink(orderLink);
        bakeryOrderReceivedEvent.setEstimatedTimeOfCompletion(LocalDateTime.now().plusNanos(timeToBakePizzaInMillis * 1_000_000));
        bakeryEventPublisher.sendBakingOrderReceivedEvent(bakeryOrderReceivedEvent);
    }

    private void saveBakeryOrder(URI orderLink) {
        BakeryOrder bakeryOrder = new BakeryOrder();
        bakeryOrder.setBakeryOrderState(BakeryOrderState.QUEUED);
        bakeryOrder.setOrderLink(orderLink);
        bakeryOrderRepository.save(bakeryOrder);
    }

    private void bake(Order order) {
        LOGGER.info("Working hard to bake order {} with items {}", order.getOrderLink(), order.getOrderItems());
        try {
            Thread.sleep(timeToBakePizzaInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
