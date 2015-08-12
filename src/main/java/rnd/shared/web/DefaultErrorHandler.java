package rnd.shared.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultErrorHandler.class);

    @ExceptionHandler(Throwable.class)
    public String handle(HttpServletRequest request, Throwable throwable) {
        LOGGER.error("uncaught throwable", throwable);
        return "error";
    }
}
