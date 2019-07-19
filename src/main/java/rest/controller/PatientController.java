package rest.controller;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.exception.NotFoundException;
import rest.model.*;
import rest.helper.AppointmentConflictHelper;
import rest.repository.*;

import rest.response.SuccessResponseWithData;
import rest.response.SuccessResponseWithId;
import rest.response.UniqueCheckResponse;

import javax.validation.Valid;


import java.util.ArrayList;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Controller
@RequestMapping(path="/api/patient", produces = APPLICATION_JSON_UTF8_VALUE)
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private MedicineRepository medicineRepository;

    @PostMapping(path="")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    SuccessResponseWithData addNewPatient (@Valid @RequestBody Patient patient) {
        Patient savedPatient = patientRepository.save(patient);
        return new SuccessResponseWithData(savedPatient);
    }

    @ApiResponses(value={@ApiResponse(code = 404, message = "Patient not found")})
    @DeleteMapping("/{id}")
    public @ResponseBody
    SuccessResponseWithId deletePatient (@PathVariable("id") Integer id) {
        patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient %s not found", id)));
        patientRepository.deleteById(id);
        return new SuccessResponseWithId(id);
    }

    @GetMapping(path="")
    public @ResponseBody
    ArrayList<Patient> getAllPatients() {
        return Lists.newArrayList(patientRepository.findAll());
    }

    @ApiResponses(value={@ApiResponse(code = 404, message = "Patient not found")})
    @GetMapping("/{id}")
    public @ResponseBody
    Patient getPatient(@PathVariable("id") Integer id) {
        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient %d not found", id)));
        // FIXME: Duplicate patient info
        return p;
    }

    @ApiResponses(value={@ApiResponse(code = 404, message = "Patient not found")})
    @PutMapping("/{id}")
    public @ResponseBody
    SuccessResponseWithId updatePatient(@PathVariable("id") Integer id, @Valid @RequestBody Patient patient) {
        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient %d not found", id)));

        BeanUtils.copyProperties(patient, p, "id", "appointments", "prescriptions", "diseases");

        patientRepository.save(p);
        return new SuccessResponseWithId(id);
    }

    @ApiResponses(value={@ApiResponse(code = 404, message = "Patient not found")})
    @PostMapping("/{id}/prescription")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    SuccessResponseWithId addPrescription(
            @PathVariable("id") Integer id,
            @Valid @RequestBody Prescription prescription
    ) {

        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient %d not found", id)));

        p.addPrescription(prescription);
        patientRepository.save(p);
        // FIXME: Return Prescription id
        return new SuccessResponseWithId(id);
    }

    @ApiResponses(value={@ApiResponse(code = 404, message = "Patient not found")})
    @GetMapping("/{id}/prescription")
    public @ResponseBody
    ArrayList<Prescription> getAllPrescriptions(@PathVariable("id") Integer id) {
        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient %d not found", id)));
        return Lists.newArrayList(p.getPrescriptions());
    }

    @ApiResponses(value={@ApiResponse(code = 404, message = "Patient not found")})
    @PostMapping(value = "/{id}/appointment")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Object addAppointment(
            @PathVariable("id") Integer id,
            @Valid @RequestBody Appointment appointment
    ) {
        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient %d not found", id)));

        // Check conflicts
        AppointmentConflict conflict = AppointmentConflictHelper.check(appointment, appointmentRepository);
        if(!conflict.isConflicting()) {
            p.addAppointment(appointment);
            patientRepository.save(p);
            // FIXME: Return appointment id
            return new SuccessResponseWithId(id);
        } else {
            return conflict.toError();
        }

    }

    @PostMapping("/check")
    public @ResponseBody
    UniqueCheckResponse checkEmail(@RequestBody UniqueCheckRequest req) {

        boolean isUnique = false;

        switch (req.type) {
            case "email":
                isUnique = patientRepository.findByEmail(req.value).isEmpty();
                break;
            case "phoneNumber":
                isUnique = patientRepository.findByPhoneNumber(req.value).isEmpty();
                break;
            case "insuranceNumber":
                isUnique = patientRepository.findByInsuranceNumber(req.value).isEmpty();
                break;
        }

        return new UniqueCheckResponse(isUnique);

    }

}