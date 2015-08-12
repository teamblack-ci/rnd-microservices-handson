package rnd.shared.jpa;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "rnd")
@EnableJpaRepositories(basePackages = "rnd")
public class JpaAutoConfiguration {

}
