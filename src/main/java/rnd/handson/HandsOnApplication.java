package rnd.handson;

import static org.springframework.boot.SpringApplication.run;

import rnd.shared.json.JsonConfiguration;
import rnd.shared.web.WebConfiguration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ WebConfiguration.class, JsonConfiguration.class })
public class HandsOnApplication {
    public static void main(String[] args) {
        run(HandsOnApplication.class, args);
    }

}
