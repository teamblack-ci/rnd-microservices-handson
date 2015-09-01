package com.epages.microservice.handson.catalog;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import java.util.Set;

import javax.money.MonetaryAmount;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "PIZZA")
public class Pizza {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic
    @Column(name = "NAME", length = 75, nullable = false, unique = true)
    private String name;

    @Basic
    @Column(name = "DESCRIPTION", length = 255, nullable = true)
    private String description;

    @Basic
    @Column(name = "IMAGE_URL", length = 255, nullable = false)
    private String imageUrl;

    @Basic
    @Column(name = "PRICE")
    private MonetaryAmount price;

    @ElementCollection
    private Set<Topping> toppings;

    @Version
    private Integer version;

    @LastModifiedDate
    private LocalDateTime lastModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public void setPrice(MonetaryAmount price) {
        this.price = price;
    }

    public Set<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(Set<Topping> toppings) {
        this.toppings = toppings;
    }

}
