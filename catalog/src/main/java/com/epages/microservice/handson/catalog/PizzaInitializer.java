package com.epages.microservice.handson.catalog;

import static com.epages.microservice.handson.catalog.Topping.*;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PizzaInitializer  {

    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaInitializer(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public void init() {
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
        return pizza;
    }

    private Pizza salamiPizza() {
        Pizza pizza = new Pizza();
        pizza.setName("Pizza Salami");
        pizza.setDescription("The classic - Pizza Salami");
        pizza.setToppings(Sets.newHashSet(CHEESE, SALAMI));
        pizza.setImageUrl("http://www.sardegna-rustica.de/images/pizza.jpg");
        return pizza;
    }
}
