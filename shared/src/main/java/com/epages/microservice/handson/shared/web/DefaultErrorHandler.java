package com.epages.microservice.handson.shared.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DefaultErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultErrorHandler.class);

    @ExceptionHandler(Throwable.class)
    public String handle(HttpServletRequest request, Throwable throwable) {
        LOGGER.error("uncaught throwable", throwable);
        return "error";
    }
}
