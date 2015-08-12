package rnd.shared.test;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.validation.constraints.NotNull;

import org.junit.rules.ExternalResource;
import org.springframework.jdbc.core.JdbcTemplate;

// see http://stackoverflow.com/questions/27045568/h2-how-to-truncate-all-tables
public class TruncateTablesRule extends ExternalResource {

    private final JdbcTemplate jdbcTemplate;

    public TruncateTablesRule(@NotNull JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = checkNotNull(jdbcTemplate);
    }

    @Override
    protected void after() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.query("SHOW TABLES", (rs, rowNum) -> {
            return rs.getString(1);
        }).forEach(table -> jdbcTemplate.execute("TRUNCATE TABLE " + table));
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }
}
