package com.epages.microservice.handson.bakery;

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
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/bakery-orders")
@ExposesResourceFor(BakeryOrder.class)
public class BakeryOrderController {

    private BakeryService bakeryService;

    @Autowired
    public BakeryOrderController(BakeryService bakeryService) {
        this.bakeryService = bakeryService;
    }

    @RequestMapping(method = GET)
    public ResponseEntity<PagedResources<Resource<BakeryOrder>>> getAll(Pageable pageable, PagedResourcesAssembler<BakeryOrder> pagedResourcesAssembler) {
        Page<BakeryOrder> bakeryOrders = bakeryService.getAll(pageable);

        return ResponseEntity.ok(pagedResourcesAssembler.toResource(bakeryOrders));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<BakeryOrder>> get(@PathVariable Long id) {
        Optional<BakeryOrder> bakeryOrder = bakeryService.get(id);
        return bakeryOrder
                .map(Resource<BakeryOrder>::new)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/search/findByOrder", method = RequestMethod.GET)
    public ResponseEntity<Resource<BakeryOrder>> getByOrderLink(@RequestParam URI orderLink) {
        Optional<BakeryOrder> bakeryOrder = bakeryService.getByOrderLink(orderLink);
        return bakeryOrder
                .map(Resource<BakeryOrder>::new)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
