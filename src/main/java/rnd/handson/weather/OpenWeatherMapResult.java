package rnd.handson.weather;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = ANY)
public class OpenWeatherMapResult {

    @JsonProperty("cod")
    String code;

    @JsonProperty
    int count;

    @JsonProperty("list")
    List<WeatherData> weatherData;

    boolean hasWeatherData() {
        return "200".equals(code) && (count > 0) && !weatherData.isEmpty();
    }

    boolean hasNoWeatherData() {
        return !hasWeatherData();
    }

    static class WeatherData {
        @JsonProperty
        String name;

        @JsonProperty("main")
        Air air;

        @JsonProperty
        Sys sys;
    }

    static class Air {
        @JsonProperty("temp")
        double temperature;

        @JsonProperty
        double pressure;

        @JsonProperty
        int humidity;
    }

    static class Sys {
        @JsonProperty
        String country;
    }
}
