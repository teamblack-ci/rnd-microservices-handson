package com.epages.microservice.handson.catalog;

import static com.epages.microservice.handson.catalog.Topping.CHEESE;
import static com.epages.microservice.handson.catalog.Topping.CHICKEN;
import static com.epages.microservice.handson.catalog.Topping.HAM;
import static com.epages.microservice.handson.catalog.Topping.PEANUT_BUTTER;
import static com.epages.microservice.handson.catalog.Topping.PINEAPPLE;
import static com.epages.microservice.handson.catalog.Topping.SALAMI;
import static com.epages.microservice.handson.catalog.Topping.TUNA;

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
        pizzaRepository.save(peanutButterPizza());
        pizzaRepository.save(tonnoPizza());
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

    private Pizza peanutButterPizza() {
        Pizza pizza = new Pizza();
        pizza.setName("Chicken Pizza with Peanut Butter");
        pizza.setDescription("Take this!");
        pizza.setToppings(Sets.newHashSet(CHEESE, PEANUT_BUTTER, CHICKEN));
        pizza.setImageUrl("http://ilovepeanutbutter.com/recipes/wp-content/uploads/2012/04/Thai-Pizza-Beauty-Shot-2.jpg");
        pizza.setPrice(Money.of(10.90, "EUR"));
        return pizza;
    }

    private Pizza tonnoPizza() {
        Pizza pizza = new Pizza();
        pizza.setName("Pizza Tonno");
        pizza.setDescription("Tunu - no dolphins - promise!");
        pizza.setToppings(Sets.newHashSet(CHEESE, TUNA));
        pizza.setImageUrl("http://restaurantblume.ch/wp-content/uploads/2013/04/pizza-tonno.png");
        pizza.setPrice(Money.of(8.90, "EUR"));
        return pizza;
    }
}
