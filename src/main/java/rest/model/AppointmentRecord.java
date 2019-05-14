package rest.model;

import javax.persistence.*;

@Entity
public class AppointmentRecord {

    private String notes;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @OneToOne(mappedBy = "appointmentRecord")
    private Appointment appointment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
