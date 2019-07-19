package rest.controller;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.exception.NotFoundException;
import rest.helper.AppointmentConflictHelper;
import rest.model.Appointment;
import rest.model.AppointmentConflict;
import rest.model.AppointmentRecord;
import rest.repository.AppointmentRepository;
import rest.response.SuccessResponseWithData;
import rest.response.SuccessResponseWithId;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@Controller
@RequestMapping(path="/api/appointment", produces = APPLICATION_JSON_UTF8_VALUE)
public class AppointmentController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping(path="")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    SuccessResponseWithData addNewAppointment (@Valid @RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return new SuccessResponseWithData(savedAppointment);
    }

    @ApiResponses(value={@ApiResponse(code = 404, message = "Appointment not found")})
    @DeleteMapping("/{id}")
    public @ResponseBody
    SuccessResponseWithId deleteAppointment (@PathVariable("id") Integer id) {
        appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Appointment %s not found", id)));
        appointmentRepository.deleteById(id);
        return new SuccessResponseWithId(id);
    }

    @ApiResponses(value={@ApiResponse(code = 404, message = "Appointment not found")})
    @PutMapping(value = "/{id}")
    public @ResponseBody
    Object updateAppointment(
            @PathVariable("id") Integer id,
            @Valid @RequestBody Appointment appointment
    ) {

        Appointment a = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Appointment %d not found", id)));

        // Check conflicts
        AppointmentConflict conflict = AppointmentConflictHelper.check(appointment, appointmentRepository);
        // Ignore the conflict if conflicting appointment is the one being updated
        if(!conflict.isConflicting() || conflict.getConflictingId().equals(id)) {
            a.setDescription(appointment.getDescription());
            a.setDateTime(appointment.getDateTime());
            appointmentRepository.save(a);
            return new SuccessResponseWithId(id);
        } else {
            return conflict.toError();
        }
    }

    @GetMapping(path="")
    public @ResponseBody
    ArrayList<Appointment> getAllAppointments() {
        return Lists.newArrayList(appointmentRepository.findAll());
    }

    // FIXME: Return id of AppointmentRecord
    @ApiResponses(value={@ApiResponse(code = 404, message = "Appointment not found")})
    @PostMapping("/{id}/notes")
    public @ResponseBody
    SuccessResponseWithId addNotes(@PathVariable("id") Integer id, @Valid @RequestBody AppointmentRecord appointmentRecord) {
        Appointment n = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Appointment %d not found", id)));
        appointmentRecord.setDateAdded(LocalDateTime.now());
        n.setAppointmentRecord(appointmentRecord);
        appointmentRepository.save(n);
        return new SuccessResponseWithId(id);
    }

}