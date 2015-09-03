package com.epages.microservice.handson.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    @Value("${delivery.timeToDeliverInMillis:15000}")
    private Long timeToDeliverInMillis;

    @Value("${delivery.timeToPrepareDeliveryInMillis:5000}")
    private Long timeToPrepareDeliveryInMillis;

    private DeliveryEventPublisher deliveryEventPublisher;
    private OrderServiceClient orderServiceClient;
    private DeliveryOrderRepository deliveryOrderRepository;

    @Autowired
    public DeliveryServiceImpl(DeliveryEventPublisher deliveryEventPublisher,
                               OrderServiceClient orderServiceClient,
                               DeliveryOrderRepository deliveryOrderRepository) {
        this.deliveryEventPublisher = deliveryEventPublisher;
        this.orderServiceClient = orderServiceClient;
        this.deliveryOrderRepository = deliveryOrderRepository;
    }
    @Override
    @Async("deliveryThreadPoolTaskExecutor")
    public void startDelivery(URI orderUri) {
        DeliveryOrder deliveryOrder = deliveryOrderRepository.getDeliveryOrderByOrderLink(orderUri);
        updateOrderState(deliveryOrder, DeliveryOrderState.IN_PROGRESS);

        //retrieve the delivery address to be able to startDelivery
        Order order = orderServiceClient.getOrder(orderUri);

        doTheDeliveryWork(order);

        updateOrderState(deliveryOrder, DeliveryOrderState.DONE);
        deliveryEventPublisher.sendDeliveredEvent(order);
    }

    @Override
    public void scheduleDelivery(BakingOrderReceivedEvent event) {
        saveDeliveryOrder(event);

        sendDeliveryOrderReceivedEvent(event);
    }

    private void updateOrderState(DeliveryOrder deliveryOrder, DeliveryOrderState state) {
        deliveryOrder.setDeliveryOrderState(state);
        deliveryOrderRepository.save(deliveryOrder);
    }


    private void saveDeliveryOrder(BakingOrderReceivedEvent event) {
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setOrderLink(event.getOrderLink());
        deliveryOrder.setDeliveryOrderState(DeliveryOrderState.QUEUED);

        deliveryOrderRepository.save(deliveryOrder);
    }

    private void sendDeliveryOrderReceivedEvent(BakingOrderReceivedEvent event) {
        Long deliveryTime = timeToPrepareDeliveryInMillis + timeToDeliverInMillis;

        DeliveryOrderReceivedEvent deliveryOrderReceivedEvent = new DeliveryOrderReceivedEvent();
        deliveryOrderReceivedEvent.setOrderLink(event.getOrderLink());
        deliveryOrderReceivedEvent.setEstimatedTimeOfDelivery(
                event.getEstimatedTimeOfCompletion().plusNanos(deliveryTime * 1_000_000));

        deliveryEventPublisher.sendDeliveryOrderReceivedEvent(deliveryOrderReceivedEvent);
    }

    private void doTheDeliveryWork(Order order) {
        LOGGER.info("Working hard to deliver order {} to address {}", order.getOrderLink(), order.getDeliveryAddress());
        try {
            Thread.sleep(timeToDeliverInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
