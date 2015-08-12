package rnd.handson.weather;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/weather", produces = APPLICATION_JSON_VALUE)
public class WeatherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private OpenWeatherMapClient client;

    @Autowired
    private OpenWeatherMapSettings settings;

    @RequestMapping(method = GET, value = "/{query}/find")
    public ResponseEntity<List<WeatherReport>> find(@PathVariable("query") String query) {
        final OpenWeatherMapResult result = client.find(settings.getVersion(), query, settings.getUnits());

        if (result.hasNoWeatherData()) {
            return ResponseEntity.ok(newArrayList());
        }

        final List<WeatherReport> weatherReports = result.weatherData //
                .stream() //
                .map(WeatherReport::new) //
                .collect(toList());

        LOGGER.info("WeatherReport for '{}': {}", query, weatherReports);
        return ResponseEntity.ok(weatherReports);
    }

    @RequestMapping(method = GET, value = "/{query}/forecast")
    public ResponseEntity<Map<String, Object>> forecast(@PathVariable("query") String query) {
        final Map<String, Object> result = client.forecast(settings.getVersion(), query, settings.getUnits());
        return ResponseEntity.ok(result);
    }

    @RequestMapping(method = GET, value = "/{query}/forecast/daily")
    public ResponseEntity<Map<String, Object>> forecastDaily(@PathVariable("query") String query) {
        final Map<String, Object> result = client.forecastDaily(settings.getVersion(), query, settings.getUnits());
        return ResponseEntity.ok(result);
    }

    @RequestMapping(method = GET, value = "/{query}/weather")
    public ResponseEntity<Map<String, Object>> weather(@PathVariable("query") String query) {
        final Map<String, Object> result = client.weather(settings.getVersion(), query, settings.getUnits());
        return ResponseEntity.ok(result);
    }
}
