package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.exception.NotFoundException;
import rest.model.Appointment;
import rest.repository.AppointmentRepository;

import java.time.LocalDateTime;


@Controller
@RequestMapping(path="/api/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping(path="")
    public @ResponseBody
    String addNewAppointment (
            @RequestParam LocalDateTime datetime,
            @RequestParam String description
    ) {

        Appointment n = new Appointment();
        n.setDateTime(datetime);
        n.setDescription(description);
        appointmentRepository.save(n);
        return String.format("{ \"success\": \"true\", \"id\": %s }", n.getId());
    }

    @GetMapping(path="")
    public @ResponseBody
    Iterable<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @PostMapping("/{id}/notes")
    public @ResponseBody
    void addNotes(@PathVariable("id") String id, @RequestParam String notes) {
        Appointment n = appointmentRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new NotFoundException(String.format("Appointment %d not found", id)));
        n.getAppointmentRecord().setNotes(notes);
        appointmentRepository.save(n);
    }

    @PostMapping("/{id}")
    public @ResponseBody
    void updateAppointment(
            @RequestParam LocalDateTime datetime,
            @RequestParam String description
    ) {
        Appointment n = appointmentRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new NotFoundException(String.format("Appointment %d not found", id)));
        
    }
}