package com.epages.microservice.handson.delivery;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Import({ WebConfiguration.class, JsonConfiguration.class, EventAutoConfiguration.class })
@EnableSpringConfigured
@EnableSpringDataWebSupport
@EnableJpaAuditing
@Configuration
@EntityScan(basePackages = "com.epages")
@EnableRabbit
@EnableAsync
public class DeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }
}
