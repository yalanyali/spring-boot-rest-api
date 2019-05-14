package rest.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Medicine {

    @Column(unique=true, length = 30)
    private String name;
    private String howOften;
    private String description;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToMany(mappedBy = "medicine")
    private Set<Prescription> prescriptionSet = new HashSet<>();

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

    public String getHowOften() {
        return howOften;
    }

    public void setHowOften(String howOften) {
        this.howOften = howOften;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
