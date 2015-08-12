package rnd.handson.weather;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://api.openweathermap.org")
public interface OpenWeatherMapClient {
    @RequestMapping(method = GET, value = "/data/{version}/find", produces = APPLICATION_JSON_VALUE)
    OpenWeatherMapResult find(@PathVariable("version") String version, //
                              @RequestParam("q") String query, //
                              @RequestParam(value = "units", required = false, defaultValue = "metric") String units //
    );

    @RequestMapping(method = GET, value = "/data/{version}/forecast", produces = APPLICATION_JSON_VALUE)
    Map<String, Object> forecast(@PathVariable("version") String version, //
                                 @RequestParam("q") String query, //
                                 @RequestParam(value = "units", required = false, defaultValue = "metric") String units //
    );

    @RequestMapping(method = GET, value = "/data/{version}/forecast/daily", produces = APPLICATION_JSON_VALUE)
    Map<String, Object> forecastDaily(@PathVariable("version") String version, //
                                      @RequestParam("q") String query, //
                                      @RequestParam(value = "units", required = false, defaultValue = "metric") String units //
    );

    @RequestMapping(method = GET, value = "/data/{version}/weather", produces = APPLICATION_JSON_VALUE)
    Map<String, Object> weather(@PathVariable("version") String version, //
                                @RequestParam("q") String query, //
                                @RequestParam(value = "units", required = false, defaultValue = "metric") String units //
    );
}
