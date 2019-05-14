package rest.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Disease {

    @Column(unique=true, length = 30)
    private String name;
    private String description;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToMany(mappedBy = "diseases")
    private Set<Patient> patients = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
