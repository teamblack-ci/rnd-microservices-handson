package rnd.handson.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParentRepository extends JpaRepository<Parent, Long>, ParentRepositoryCustom {
    @Query("SELECT p FROM Parent p LEFT JOIN FETCH p.attributes WHERE p.id = :id")
    Parent findOneWithAttributes(@Param("id") Long id);
}
