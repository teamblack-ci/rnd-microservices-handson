package com.epages.microservice.handson.catalog;

import static com.epages.microservice.handson.catalog.Topping.CHEESE;
import static com.epages.microservice.handson.catalog.Topping.HAM;
import static com.epages.microservice.handson.catalog.Topping.PINEAPPLE;
import static com.epages.microservice.handson.catalog.Topping.SALAMI;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

@Component
public class PizzaInitializer implements InitializingBean {

    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaInitializer(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    @Transactional
    public void afterPropertiesSet() {
        pizzaRepository.save(salamiPizza());
        pizzaRepository.save(hawaiiPizza());
    }

    private Pizza hawaiiPizza() {
        Pizza pizza;
        pizza = new Pizza();
        pizza.setName("Pizza Hawaii");
        pizza.setDescription("The exotic among the classics - Pizza Hawaii");
        pizza.setToppings(Sets.newHashSet(CHEESE, HAM, PINEAPPLE));
        pizza.setImageUrl("http://www.heimfrost.de/_images/artikel/listenbilder/08834.jpg");
        pizza.setPrice(Money.of(9.90, "EUR"));
        return pizza;
    }

    private Pizza salamiPizza() {
        Pizza pizza = new Pizza();
        pizza.setName("Pizza Salami");
        pizza.setDescription("The classic - Pizza Salami");
        pizza.setToppings(Sets.newHashSet(CHEESE, SALAMI));
        pizza.setImageUrl("http://www.sardegna-rustica.de/images/pizza.jpg");
        pizza.setPrice(Money.of(8.90, "EUR"));
        return pizza;
    }
}
