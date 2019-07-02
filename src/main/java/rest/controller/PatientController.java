package rest.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.exception.NotFoundException;
import rest.model.*;
import rest.helper.AppointmentConflictHelper;
import rest.repository.*;
import rest.response.SuccessResponse;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;


import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Controller
@RequestMapping(path="/api/patient")
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
    public @ResponseBody
    ResponseEntity<Object> addNewPatient (@Valid @RequestBody Patient patient) {
        Patient savedPatient = patientRepository.save(patient);
        return ResponseEntity.ok(new SuccessResponse(savedPatient));
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    String deletePatient (@PathVariable("id") Integer id) {
        patientRepository.deleteById(id);
              //  .orElseThrow(() -> new NotFoundException(String.format("Patient %s not found", id)));
        return String.format("{ \"success\": \"true\", \"id\": %d }", id);
    }

    @GetMapping(path="")
    public @ResponseBody
    Iterable<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Patient getPatient(@PathVariable("id") Integer id) {
        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient %d not found", id)));
        // FIXME: Duplicate patient info
        return p;
    }

    @PutMapping("/{id}")
    public @ResponseBody
    String updatePatient(@PathVariable("id") Integer id, @Valid @RequestBody Patient patient) {
        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient %d not found", id)));

        BeanUtils.copyProperties(patient, p, "id", "appointments", "prescriptions", "diseases");

        patientRepository.save(p);
        return String.format("{ \"success\": \"true\", \"id\": %d }", id);
    }

    @PostMapping("/{id}/prescription")
    public @ResponseBody
    String addPrescription(
            @PathVariable("id") Integer id,
            @Valid @RequestBody Prescription prescription
    ) {

        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient %d not found", id)));

        //Prescription savedPrescription = new Prescription();
        //savedPrescription.setPatient(p);
        //savedPrescription.setDateTime(prescription.getDateTime());

        // Add medicine by id
        //for (Medicine m: prescription.getMedicine()) {
        //    System.out.println(m.getId());
        //}
        //savedPrescription.setMedicine();
        //       prescriptionRepository.save(prescription);

        /*Set<Medicine> medicine = new HashSet<>();

        for (Integer medicineId : medicineIdList) {
            Medicine m = medicineRepository.findById(medicineId)
                    .orElseThrow(() -> new NotFoundException(String.format("Patient %s not found", medicineId)));
            medicine.add(m);
        }

        pr.setMedicine(medicine);
        pr.setDateTime(LocalDateTime.now());*/
        p.addPrescription(prescription);
        patientRepository.save(p);
        return "{ \"success\": \"true\" }";
    }

    @GetMapping("/{id}/prescription")
    public @ResponseBody
    Iterable<Prescription> getAllPrescriptions(@PathVariable("id") Integer id) {
        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient %d not found", id)));
        return p.getPrescriptions();
    }

    @PostMapping(value = "/{id}/appointment", produces = APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    String addAppointment(
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
            return "{ \"success\": \"true\" }";
        } else {
            return conflict.toString();
        }

    }

    @PostMapping("/check")
    public @ResponseBody
    String checkEmail(@RequestBody UniqueCheckRequest req) {

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

        if (isUnique) {
            return "{ \"isUnique\": true }";
        } else {
            return "{ \"isUnique\": false }";
        }

    }

}