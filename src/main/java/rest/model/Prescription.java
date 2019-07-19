package rest.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import rest.serializer.CustomLocalDateTimeSerializer;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(hidden=true)
    private Integer id;

    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    @NotNull
    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(
        name = "prescription_medicine",
        joinColumns = @JoinColumn(name = "prescription_id"),
        inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private Set<Medicine> medicine = new HashSet<>();

    //@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // Eager fetching is bad for performance
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prescription)) return false;
        return id != null && id.equals(((Prescription) o).getId());
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Set<Medicine> getMedicine() {
        return medicine;
    }

    public void setMedicine(Set<Medicine> medicine) {
        this.medicine = medicine;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public HashMap<String, String> getPatient() {
        HashMap<String, String> p = new HashMap<>();
        p.put("firstName", this.patient.getFirstName());
        p.put("lastName", this.patient.getLastName());
        p.put("id", String.valueOf(patient.getId()));
        return p;
    }
}
