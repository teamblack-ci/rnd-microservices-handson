package rnd.handson.weather;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.google.common.base.MoreObjects.toStringHelper;

import rnd.handson.weather.OpenWeatherMapResult.WeatherData;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
class WeatherReport {

    final LocalDateTime date;

    final String city;

    final String country;

    final double temperature;

    final double pressure;

    final int humidity;

    WeatherReport(WeatherData data) {
        date = LocalDateTime.now();
        city = data.name;
        country = data.sys.country;
        temperature = data.air.temperature;
        pressure = data.air.pressure;
        humidity = data.air.humidity;
    }

    @Override
    public String toString() {
        return toStringHelper(this) //
                .add("date", date) //
                .add("city", city) //
                .add("country", country) //
                .add("temperature", temperature) //
                .add("pressure", pressure) //
                .add("humidity", humidity) //
                .toString();
    }
}
