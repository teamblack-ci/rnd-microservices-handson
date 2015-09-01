package com.epages.microservice.handson.catalog;

import com.epages.microservice.handson.shared.json.JsonConfiguration;
import com.epages.microservice.handson.shared.web.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ WebConfiguration.class, JsonConfiguration.class })
public class CatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogApplication.class, args);
    }
}
