package com.epages.microservice.handson.shared.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.javamoney.moneta.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.geo.GeoModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

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

    public @Bean
    Module moneyModule(Map<String, ObjectMapper> objectMappers) {
        MoneyModule moneyModule = new MoneyModule();

        objectMappers.forEach((beanName, objectMapper) -> {
            objectMapper.registerModule(moneyModule);
        });

        return moneyModule;
    }

    @SuppressWarnings("serial")
    static class MoneyModule extends SimpleModule {

        private static final MonetaryAmountFormat FORMAT = MonetaryFormats.getAmountFormat(Locale.ROOT);

        public MoneyModule() {
            addSerializer(Money.class, new MoneySerializer());
            addValueInstantiator(Money.class, new MoneyInstantiator());
        }

        static class MoneySerializer extends ToStringSerializer {

            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
                    JsonGenerationException {
                jgen.writeString(FORMAT.format((Money) value));
            }
        }

        static class MoneyInstantiator extends ValueInstantiator {

            @Override
            public String getValueTypeDesc() {
                return Money.class.toString();
            }

            @Override
            public boolean canCreateFromString() {
                return true;
            }

            @Override
            public Object createFromString(DeserializationContext ctxt, String value) throws IOException {
                return Money.parse(value, FORMAT);
            }
        }
    }
}
