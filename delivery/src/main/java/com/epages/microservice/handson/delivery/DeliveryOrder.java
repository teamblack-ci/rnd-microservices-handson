package com.epages.microservice.handson.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.net.URI;

import static com.google.common.base.MoreObjects.toStringHelper;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "DELIVERY_ORDER")
public class DeliveryOrder {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    @JsonIgnore
    private Long id;

    @Column(unique = true, nullable = false)
    private URI orderLink;

    @Enumerated(EnumType.STRING)
    private DeliveryOrderState deliveryOrderState;

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

    public DeliveryOrderState getDeliveryOrderState() {
        return deliveryOrderState;
    }

    public void setDeliveryOrderState(DeliveryOrderState deliveryOrderState) {
        this.deliveryOrderState = deliveryOrderState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryOrder that = (DeliveryOrder) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("id", id)
                .add("orderLink", orderLink)
                .add("deliveryOrderState", deliveryOrderState)
                .toString();
    }
}
