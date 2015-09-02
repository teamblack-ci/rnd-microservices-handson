package com.epages.microservice.handson.catalog;

import com.epages.microservice.handson.shared.json.JsonConfiguration;
import com.epages.microservice.handson.shared.web.WebConfiguration;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@SpringBootApplication
@Import({ WebConfiguration.class, JsonConfiguration.class })
@EntityScan(basePackages = "com.epages")
public class CatalogApplication {

    private static final Logger log = LoggerFactory.getLogger(CatalogApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CatalogApplication.class, args);
    }

    @Autowired
    private PizzaInitializer pizzaInitializer;

    @PostConstruct
    private void initCatalog() {
        log.info("Initializing pizza catalog.");
        pizzaInitializer.init();
    }
}