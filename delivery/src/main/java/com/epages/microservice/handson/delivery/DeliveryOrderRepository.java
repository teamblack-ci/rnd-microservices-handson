package com.epages.microservice.handson.delivery;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.net.URI;

public interface DeliveryOrderRepository extends PagingAndSortingRepository<DeliveryOrder, Long> {

    //TODO create a finder method to retrieve a DeliveryOrder by orderLink
	DeliveryOrder findByOrderLink(URI orderLink);
}
