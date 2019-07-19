package rest.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(hidden=true)
    private Integer id;

    @NotNull
    @Column(unique=true, length = 30)
    private String name;

    private String description;


    @ManyToMany(mappedBy = "medicine")
    private Set<Prescription> prescriptionSet = new HashSet<>();

    public Integer getId() {
        return id;
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
