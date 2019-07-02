package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.exception.NotFoundException;
import rest.helper.AppointmentConflictHelper;
import rest.model.Appointment;
import rest.model.AppointmentConflict;
import rest.model.AppointmentRecord;
import rest.model.Patient;
import rest.repository.AppointmentRepository;
import rest.response.SuccessResponse;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@Controller
@RequestMapping(path="/api/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping(path="")
    public @ResponseBody
    ResponseEntity<Object> addNewAppointment (@Valid @RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return ResponseEntity.ok(new SuccessResponse(savedAppointment));
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    String deleteAppointment (@PathVariable("id") Integer id) {
        appointmentRepository.deleteById(id);
        //  .orElseThrow(() -> new NotFoundException(String.format("Appointment %s not found", id)));
        return String.format("{ \"success\": \"true\", \"id\": %d }", id);
    }


    // FIXME: JSON OUTPUT FOR ALL
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    String updateAppointment(
            @PathVariable("id") Integer id,
            @Valid @RequestBody Appointment appointment
    ) {

        Appointment a = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Appointment %d not found", id)));

        // Check conflicts
        AppointmentConflict conflict = AppointmentConflictHelper.check(appointment, appointmentRepository);
        // Ignore conflict if the conflicting appointment is the one being updated
        if(!conflict.isConflicting() || conflict.getConflictingId() == id) {
            a.setDescription(appointment.getDescription());
            a.setDateTime(appointment.getDateTime());
            appointmentRepository.save(a);
            return String.format("{ \"success\": \"true\", \"id\": %d }", id);
        } else {
            return conflict.toString();
        }
    }

    @GetMapping(path="")
    public @ResponseBody
    Iterable<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @PostMapping("/{id}/notes")
    public @ResponseBody
    String addNotes(@PathVariable("id") Integer id, @Valid @RequestBody AppointmentRecord appointmentRecord) {
        Appointment n = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Appointment %d not found", id)));
        appointmentRecord.setDateAdded(LocalDateTime.now());
        n.setAppointmentRecord(appointmentRecord);
        appointmentRepository.save(n);
        return "{ \"success\": \"true\" }";
    }

    /*@PostMapping("/{id}")
    public @ResponseBody
    void updateAppointment(
            @RequestParam LocalDateTime datetime,
            @RequestParam String description
    ) {
        Appointment n = appointmentRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new NotFoundException(String.format("Appointment %d not found", id)));

    }*/
}