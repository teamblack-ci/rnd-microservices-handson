package com.epages.microservice.handson.order;

import org.javamoney.moneta.Money;
import org.springframework.data.annotation.LastModifiedDate;

import javax.money.MonetaryAmount;
import javax.persistence.*;
import java.net.URI;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "LINE_ITEM")
public class LineItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    private URI pizza;

    private Integer amount;

    private MonetaryAmount price;

    @Version
    private Integer version;

    @LastModifiedDate
    private LocalDateTime modified;

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
}
