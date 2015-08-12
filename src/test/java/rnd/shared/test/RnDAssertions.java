package rnd.shared.test;

import org.assertj.core.api.BDDAssertions;
import org.springframework.data.domain.Persistable;

public class RnDAssertions extends BDDAssertions {

    private RnDAssertions() {
        // don't instance me
    }

    public static PersistableAssert then(Persistable<?> entity) {
        return PersistableAssert.assertThat(entity);
    }
}
