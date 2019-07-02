package rest.model;

import rest.model.Patient;

public class AppointmentConflict {
    private boolean isConflicting = false;
    private Patient conflictingPatient;
    private Integer conflictingId;

    public String toString() {
        return String.format("{ \"error\": \"CONFLICT\", \"patient\": \"%s %s\"}", conflictingPatient.getFirstName(), conflictingPatient.getLastName());
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