package com.epages.microservice.handson.bakery;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.net.URI;

public interface BakeryOrderRepository extends PagingAndSortingRepository<BakeryOrder, Long> {

    //TODO create a finder method to retrieve a BakeryOrder by orderLink
}
