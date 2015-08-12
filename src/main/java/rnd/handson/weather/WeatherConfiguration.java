package rnd.handson.weather;

import static feign.Logger.Level;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
@EnableConfigurationProperties(OpenWeatherMapSettings.class)
public class WeatherConfiguration {

    @Bean
    public Level feignLoggerLevel() {
        return Level.FULL;
    }
}
