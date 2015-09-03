package com.epages.microservice.handson.bakery;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.net.URI;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "BAKERY_ORDER")
public class BakeryOrder {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    @JsonIgnore
    private Long id;

    private URI orderLink;

    @Enumerated(EnumType.STRING)
    private BakeryOrderState bakeryOrderState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public URI getOrderLink() {
        return orderLink;
    }

    public void setOrderLink(URI orderLink) {
        this.orderLink = orderLink;
    }

    public BakeryOrderState getBakeryOrderState() {
        return bakeryOrderState;
    }

    public void setBakeryOrderState(BakeryOrderState bakeryOrderState) {
        this.bakeryOrderState = bakeryOrderState;
    }
}
