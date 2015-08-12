package rnd.handson.child;

import static rnd.shared.test.RnDAssertions.then;

import rnd.handson.HandsOnApplicationTest;
import rnd.handson.parent.Parent;
import rnd.handson.parent.ParentRepository;
import rnd.shared.test.TruncateTablesRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@HandsOnApplicationTest
public class ChildPersistenceTest {

    private static final int CHILDREN = 10;

    @Rule
    @Autowired
    public TruncateTablesRule truncateTablesRule;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    private Parent parent;

    @Before
    public void given_child() {
        parent = new Parent();
        parent.setName("parent");
        parentRepository.save(parent);

        for (int i = 0; i < CHILDREN; i++) {
            Child child = new Child();
            child.setName("child_" + i);
            child.setParent(parent);
            childRepository.save(child);
        }
    }

    @Test
    @Transactional
    public void should_find_children_by_parent() {
        // GIVEN
        final int pageSize = CHILDREN / 2;
        final Pageable pageable = new PageRequest(0, pageSize);

        // WHEN
        final Page<Child> children = childRepository.findByParent(parent, pageable);

        // THEN
        then(children).hasSize(pageSize);
        then(children.getTotalElements()).isEqualTo(CHILDREN);
        then(children.getTotalPages()).isEqualTo(2);
        then(children.getNumberOfElements()).isEqualTo(pageSize);
        then(children.getNumber()).isEqualTo(0);
        then(children.isFirst()).isTrue();
        then(children.hasNext()).isTrue();
        then(children.nextPageable().getOffset()).isEqualTo(5);
        then(children.previousPageable()).isNull();
        int i = 0;
        for (Child child : children) {
            then(child.getName()).isEqualTo("child_" + i);
            then(child.getParent()).isEqualTo(parent);
            i++;
        }
        then(i).isEqualTo(pageSize);
    }

    @Test
    @Transactional
    public void should_find_children_by_parent_name() {
        // GIVEN
        final int pageSize = CHILDREN / 2;
        final Pageable pageable = new PageRequest(0, pageSize);
        final String parentName = parent.getName();

        // WHEN
        final Page<Child> children = childRepository.findByParentName(parentName, pageable);

        // THEN
        then(children).hasSize(pageSize);
        then(children.getTotalElements()).isEqualTo(CHILDREN);
        then(children.getTotalPages()).isEqualTo(2);
        then(children.getNumberOfElements()).isEqualTo(pageSize);
        then(children.getNumber()).isEqualTo(0);
        then(children.isFirst()).isTrue();
        then(children.hasNext()).isTrue();
        then(children.nextPageable().getOffset()).isEqualTo(5);
        then(children.previousPageable()).isNull();
        int i = 0;
        for (Child child : children) {
            then(child.getName()).isEqualTo("child_" + i);
            then(child.getParent()).isEqualTo(parent);
            i++;
        }
        then(i).isEqualTo(pageSize);
    }
}
