package rnd.handson.child;

import static com.google.common.base.MoreObjects.toStringHelper;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import rnd.handson.parent.Parent;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

@Entity
@Table(name = "CHILDREN")
public class Child implements Persistable<Long> {

    private static final long serialVersionUID = 6812420642240593475L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Basic
    @Column(name = "NAME", length = 255, nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "PARENT_ID", nullable = false)
    private Parent parent;

    @ElementCollection
    @CollectionTable(name = "CHILD_ATTRIBUTES")
    @MapKeyColumn(name = "NAME", length = 50, nullable = false)
    @Column(name = "VALUE", length = 255, nullable = false)
    private Map<String, String> attributes = new HashMap<>();

    @Override
    @Transient
    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }

    @Override
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Child child = (Child) o;
        return Objects.equal(name, child.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("name", name).toString();
    }
}
