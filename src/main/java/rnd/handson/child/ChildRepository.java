package rnd.handson.child;

import rnd.handson.parent.Parent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {
    Page<Child> findByParent(Parent parent, Pageable pageable);

    Page<Child> findByParentName(String parentNames, Pageable pageable);
}
