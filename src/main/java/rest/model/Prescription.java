package rest.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import rest.serializer.CustomLocalDateTimeSerializer;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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

    public void setId(Integer id) {
        this.id = id;
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
}
