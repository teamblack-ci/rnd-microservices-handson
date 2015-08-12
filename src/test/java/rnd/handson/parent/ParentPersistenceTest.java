package rnd.handson.parent;

import static rnd.shared.test.RnDAssertions.then;

import rnd.handson.HandsOnApplicationTest;
import rnd.handson.child.ChildRepository;
import rnd.shared.test.TruncateTablesRule;

import javax.persistence.EntityManager;

import org.hibernate.LazyInitializationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@HandsOnApplicationTest
public class ParentPersistenceTest {

    private static final int CHILDREN = 10;

    @Rule
    @Autowired
    public TruncateTablesRule truncateTablesRule;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    private Parent parent;

    @Before
    public void given_children() {
        parent = new Parent();
        parent.setName("parent");
        parent = parentRepository.save(parent);
        parentRepository.clear();
    }

    @Test(expected = LazyInitializationException.class)
    public void should_not_lazy_load_without_transaction() {
        // GIVEN
        parent = parentRepository.findOne(parent.getId());

        // WHEN / THEN
        parent.getAttributes().size();
    }

    @Test
    @Transactional
    public void should_persist_attributes() {
        // GIVEN / WHEN
        parent = parentRepository.findOne(parent.getId());

        then(parent).usingEntityManager(entityManager).isNotLoaded("attributes");
        then(parent.getAttributes()).isEmpty();
        then(parent).usingEntityManager(entityManager).isLoaded("attributes");

        // fresh inserting
        parent.getAttributes().put("key1", "value1");
        parent.getAttributes().put("key2", "true");
        parentRepository.saveAndFlush(parent);
        then(parent.getAttributes()).hasSize(2);

        // appending
        parent.getAttributes().put("key3", "value3");
        parentRepository.saveAndFlush(parent);
        then(parent.getAttributes()).hasSize(3);

        // updating
        parent.getAttributes().put("key2", "false");
        parentRepository.saveAndFlush(parent);
        then(parent.getAttributes()).hasSize(3);

        // deleting
        parent.getAttributes().remove("key2");
        parentRepository.saveAndFlush(parent);
        then(parent.getAttributes()).hasSize(2);
    }

    @Test
    @Transactional
    public void should_fetch_parent_with_attributes() {
        // GIVEN
        parent.getAttributes().put("key1", "value1");
        parent.getAttributes().put("key2", "false");
        parentRepository.saveAndFlush(parent);
        parentRepository.clear();

        // WHEN
        parent = parentRepository.findOneWithAttributes(parent.getId());

        // THEN
        then(parent).usingEntityManager(entityManager).isLoaded("attributes");
        then(parent.getAttributes()).hasSize(2);
        then(parent.getAttributes().get("key1")).isEqualTo("value1");
        then(parent.getAttributes().get("key2")).isEqualTo("false");
    }
}
