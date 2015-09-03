package com.epages.microservice.handson.shared.jpa;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.epages.microservice.handson")
@EnableJpaRepositories(basePackages = "com.epages.microservice.handson")
@EnableJpaAuditing
@EnableSpringConfigured
public class JpaAutoConfiguration {

}
