package rnd.shared.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;

import org.assertj.core.api.AbstractAssert;
import org.springframework.data.domain.Persistable;

public class PersistableAssert extends AbstractAssert<PersistableAssert, Persistable<?>> {

    private PersistenceUnitUtil persistenceUnitUtil;

    public PersistableAssert(Persistable<?> actual) {
        super(actual, PersistableAssert.class);
    }

    public static PersistableAssert assertThat(Persistable<?> actual) {
        return new PersistableAssert(actual);
    }

    private PersistenceUnitUtil persistenceUnitUtil(EntityManager entityManager) {
        return entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
    }

    public PersistableAssert usingEntityManager(EntityManager entityManager) {
        persistenceUnitUtil = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
        return this;
    }

    public PersistableAssert isNotLoaded() {
        isNotNull();
        if (persistenceUnitUtil.isLoaded(actual)) {
            failWithMessage("Entity <%s> was not expected to be loaded", actual);
        }
        return this;
    }

    public PersistableAssert isLoaded() {
        isNotNull();
        if (!persistenceUnitUtil.isLoaded(actual)) {
            failWithMessage("Entity <%s> was expected to be loaded", actual);
        }
        return this;
    }

    public PersistableAssert isNotLoaded(String attribute) {
        isNotNull();
        if (persistenceUnitUtil.isLoaded(actual, attribute)) {
            failWithMessage("Attribute <%s> of Entity <%s> was not expected to be loaded", attribute, actual);
        }
        return this;
    }

    public PersistableAssert isLoaded(String attribute) {
        isNotNull();
        if (!persistenceUnitUtil.isLoaded(actual, attribute)) {
            failWithMessage("Attribute <%s> of Entity <%s> was expected to be loaded", attribute, actual);
        }
        return this;
    }
}
