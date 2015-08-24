package rnd.handson.parent;

import rnd.handson.HandsOnApplicationTest;
import rnd.shared.test.TruncateTablesRule;

import org.hibernate.LazyInitializationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@HandsOnApplicationTest
public class LazyLoadingTest {


    @Rule
    @Autowired
    public TruncateTablesRule truncateTablesRule;

    @Autowired
    private ParentRepository parentRepository;

    private Long parentId;

    @Before
    public void given_parent() {
        Parent parent = new Parent();
        parent.setName("parent");
        parentId = parentRepository.saveAndFlush(parent).getId();
        parentRepository.clear();
    }

    @Test(expected = LazyInitializationException.class)
    public void should_not_lazy_load_without_transaction() {
        // GIVEN
        Parent parent = parentRepository.findOne(parentId);

        // WHEN / THEN
        parent.getAttributes().size();
    }
}
