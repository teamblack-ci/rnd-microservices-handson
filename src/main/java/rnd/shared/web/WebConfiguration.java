package rnd.shared.web;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hal/").setViewName("forward:/hal/browser.html");
    }

    @Bean
    public ServletRegistrationBean dbServlet() {
        // http://localhost:8080/db/
        // Driver Class: org.h2.Driver
        // JDBC URL: jdbc:h2:mem:testdb
        // Username: sa
        // Password:
        final ServletRegistrationBean servletRegistration = new ServletRegistrationBean(new WebServlet(), "/db/*");
        servletRegistration.setName("dbServlet");
        return servletRegistration;
    }

    @Bean
    public DefaultErrorHandler defaultErrorHandler() {
        return new DefaultErrorHandler();
    }
}
