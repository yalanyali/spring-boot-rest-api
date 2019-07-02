package rest.helper;

import rest.model.Appointment;
import rest.model.AppointmentConflict;
import rest.repository.AppointmentRepository;

import java.time.LocalDateTime;

public class AppointmentConflictHelper {

    public static AppointmentConflict check(Appointment appointment, AppointmentRepository appointmentRepository) {
        final LocalDateTime early = appointment.getDateTime();
        final LocalDateTime late = appointment.getDateTime().plusMinutes(29);
        AppointmentConflict conflict = new AppointmentConflict();

        for(Appointment existing : appointmentRepository.findAll()) {
            if(!(early.isAfter(existing.getDateTime().plusMinutes(29)) || late.isBefore(existing.getDateTime()))) {
                conflict.setConflicting(true);
                conflict.setConflictingPatient(existing.getPatientObj());
                conflict.setConflictingId(existing.getId());
            }
        }
        return conflict;
    }
}

