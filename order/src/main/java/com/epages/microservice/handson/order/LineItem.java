package com.epages.microservice.handson.order;

import static com.google.common.base.MoreObjects.toStringHelper;
import static javax.persistence.GenerationType.IDENTITY;

import java.net.URI;
import java.time.LocalDateTime;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.annotation.LastModifiedDate;

import com.google.common.base.Objects;

@Entity
@Table(name = "LINE_ITEM")
public class LineItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    private Integer version;

    @LastModifiedDate
    private LocalDateTime modified;

    private URI pizza;

    private Integer amount;

    private MonetaryAmount price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public URI getPizza() {
        return pizza;
    }

    public void setPizza(URI pizza) {
        this.pizza = pizza;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public void setPrice(MonetaryAmount price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LineItem lineitem = (LineItem) o;
        return id.equals(lineitem.getId());
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("id", id)
                .add("pizza", pizza)
                .add("amount", amount)
                .toString();
    }
}
