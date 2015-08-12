package rnd.handson.parent;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParentRepositoryImpl implements ParentRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void clear() {
        entityManager.clear();
    }
}
