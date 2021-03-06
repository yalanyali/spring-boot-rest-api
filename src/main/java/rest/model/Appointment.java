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

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @ApiModelProperty(hidden=true)
    private Integer id;

    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "appointment_record_id")
    @ApiModelProperty(hidden=true)
    private AppointmentRecord appointmentRecord = new AppointmentRecord();

    //@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // Eager fetching is bad for performance
    @JoinColumn(name = "patient_id")
    @ApiModelProperty(hidden=true)
    private Patient patient;

    @NotNull
    private String description;

    // TODO: FOR ALL
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        return id != null && id.equals(((Appointment) o).getId());
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

    public AppointmentRecord getAppointmentRecord() {
        return appointmentRecord;
    }

    public void setAppointmentRecord(AppointmentRecord appointmentRecord) {
        this.appointmentRecord = appointmentRecord;
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
        //return String.format("%s %s", this.patient.getFirstName(), this.patient.getLastName());
    }

    @JsonIgnore
    public Patient getPatientObj() {
        return this.patient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
