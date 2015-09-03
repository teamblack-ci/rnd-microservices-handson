package com.epages.microservice.handson.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/deliveryorders")
@ExposesResourceFor(DeliveryOrder.class)
public class DeliveryOrderController {

    private DeliveryOrderRepository deliveryOrderRepository;

    @Autowired
    public DeliveryOrderController(DeliveryOrderRepository deliveryOrderRepository) {
        this.deliveryOrderRepository = deliveryOrderRepository;
    }

    @RequestMapping(method = GET)
    public ResponseEntity<PagedResources<Resource<DeliveryOrder>>> getAll(Pageable pageable, PagedResourcesAssembler<DeliveryOrder> pagedResourcesAssembler) {
        Page<DeliveryOrder> deliveryOrders = deliveryOrderRepository.findAll(pageable);

        return ResponseEntity.ok(pagedResourcesAssembler.toResource(deliveryOrders));
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<DeliveryOrder>> get(@PathVariable Long id) {
        DeliveryOrder deliveryOrder = deliveryOrderRepository.findOne(id);
        if (deliveryOrder != null) {
            return ResponseEntity.ok(new Resource<>(deliveryOrder));
        } else {
            return new ResponseEntity<Resource<DeliveryOrder>>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/search/findByOrder", method = RequestMethod.GET)
    public ResponseEntity<Resource<DeliveryOrder>> getByOrderLink(@RequestParam URI orderLink) {
        DeliveryOrder deliveryOrder = deliveryOrderRepository.getDeliveryOrderByOrderLink(orderLink);
        if (deliveryOrder != null) {
            return ResponseEntity.ok(new Resource<>(deliveryOrder));
        } else {
            return new ResponseEntity<Resource<DeliveryOrder>>(HttpStatus.NOT_FOUND);
        }
    }

}
