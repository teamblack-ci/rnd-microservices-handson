package com.epages.microservice.handson.shared.json;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.geo.GeoModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.util.Locale.ENGLISH;

@Configuration
public class JsonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder(GeoModule geoModule) {
        return Jackson2ObjectMapperBuilder //
                .json() //
                .locale(ENGLISH) //
                .timeZone("UTC") //
                .indentOutput(true) //
                .serializationInclusion(NON_NULL) //
                .featuresToDisable(WRITE_DATES_AS_TIMESTAMPS, FAIL_ON_UNKNOWN_PROPERTIES) //
                .modulesToInstall(geoModule) //
                ;
    }
}
