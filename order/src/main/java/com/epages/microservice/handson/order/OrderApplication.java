package com.epages.microservice.handson.order;

import com.epages.microservice.handson.shared.json.JsonConfiguration;
import com.epages.microservice.handson.shared.web.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ WebConfiguration.class, JsonConfiguration.class })
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
