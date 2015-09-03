package com.epages.microservice.handson.delivery;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.net.URI;

public interface DeliveryOrderRepository extends PagingAndSortingRepository<DeliveryOrder, Long> {

    public DeliveryOrder getDeliveryOrderByOrderLink(URI orderLink);
}
