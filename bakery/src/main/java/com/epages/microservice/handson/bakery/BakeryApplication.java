package com.epages.microservice.handson.bakery;

import com.epages.microservice.handson.shared.event.EventAutoConfiguration;
import com.epages.microservice.handson.shared.json.JsonConfiguration;
import com.epages.microservice.handson.shared.web.WebConfiguration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@Import({ WebConfiguration.class, JsonConfiguration.class, EventAutoConfiguration.class })
@EnableSpringConfigured
@EnableSpringDataWebSupport
@EnableJpaAuditing
@Configuration
@EntityScan(basePackages = "com.epages")
@EnableRabbit
@EnableAsync
public class BakeryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BakeryApplication.class, args);
    }

    @Bean
    ThreadPoolTaskExecutor bakeryThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(2);
        threadPoolTaskExecutor.setQueueCapacity(1000);

        return threadPoolTaskExecutor;
    }
}
