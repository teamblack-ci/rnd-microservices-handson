package com.epages.microservice.handson.shared.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAutoConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/db").setViewName("redirect:/h2-console");
    }

    @Bean
    public DefaultErrorHandler defaultErrorHandler() {
        return new DefaultErrorHandler();
    }
}
