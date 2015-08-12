package rnd.handson;

import rnd.shared.test.TruncateTablesRule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Profile("test")
public class HandsOnApplicationTestConfiguration {

    @Bean
    public TruncateTablesRule truncateTablesRule(JdbcTemplate jdbcTemplate) {
        return new TruncateTablesRule(jdbcTemplate);
    }
}
