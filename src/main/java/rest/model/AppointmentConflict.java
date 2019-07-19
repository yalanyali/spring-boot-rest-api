package rest.model;

import rest.exception.ErrorDetails;
import rest.model.Patient;

import java.util.Arrays;
import java.util.List;

public class AppointmentConflict {
    private boolean isConflicting = false;
    private Patient conflictingPatient;
    private Integer conflictingId;

    public ErrorDetails toError() {
        List<String> details = Arrays.asList(String.format("Appointment time conflicts with an appointment of patient: %s %s", conflictingPatient.getFirstName(), conflictingPatient.getLastName()));
        return new ErrorDetails("Appointment Conflict", details);
    }

    public boolean isConflicting() {
        return isConflicting;
    }

    public void setConflicting(boolean conflicting) {
        isConflicting = conflicting;
    }

    public void setConflictingPatient(Patient conflictingPatient) {
        this.conflictingPatient = conflictingPatient;
    }

    public Integer getConflictingId() {
        return conflictingId;
    }

    public void setConflictingId(Integer conflictingId) {
        this.conflictingId = conflictingId;
    }
}