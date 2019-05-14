package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.exception.NotFoundException;
import rest.model.*;
import rest.repository.DiseaseRepository;
import rest.repository.MedicineRepository;
import rest.repository.PatientRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping(path="/api/patient")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DiseaseRepository diseaseRepository;
    @Autowired
    private MedicineRepository medicineRepository;

    @PostMapping(path="")
    public @ResponseBody
    String addNewPatient (
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String gender,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam LocalDate dateOfBirth,
            @RequestParam String insuranceNumber,
            @RequestParam String state,
            @RequestParam String city,
            @RequestParam String street,
            @RequestParam String buildingNumber,
            @RequestParam String zip,
            @RequestParam Set<Integer> diseaseIdList
            ) {
        Patient n = new Patient();
        n.setFirstName(firstName);
        n.setLastName(lastName);
        n.setGender(gender);
        n.setEmail(email);
        n.setPhoneNumber(phoneNumber);
        n.setDateOfBirth(dateOfBirth);
        n.setInsuranceNumber(insuranceNumber);

        Address a = new Address();
        a.setState(state);
        a.setCity(city);
        a.setStreet(street);
        a.setBuildingNumber(buildingNumber);
        a.setZip(zip);
        n.setAddress(a);

        Set<Disease> diseases = new HashSet<>();

        for(Integer diseaseId : diseaseIdList) {
            Disease d = diseaseRepository.findById(diseaseId)
                    .orElseThrow(() -> new NotFoundException(String.format("Disease %s not found", diseaseId)));
            diseases.add(d);
        }

        n.setDiseases(diseases);
        patientRepository.save(n);
        return String.format("{ \"success\": \"true\", \"id\": %s }", n.getId());
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

    @PostMapping("/{id}/prescription")
    public @ResponseBody
    String addPrescription(
            @PathVariable("id") Integer id,
            @RequestParam Set<Integer> medicineIdList
    ) {

        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient %d not found", id)));

        Prescription pr = new Prescription();
        Set<Medicine> medicine = new HashSet<>();

        for (Integer medicineId : medicineIdList) {
            Medicine m = medicineRepository.findById(medicineId)
                    .orElseThrow(() -> new NotFoundException(String.format("Patient %s not found", medicineId)));
            medicine.add(m);
        }

        pr.setMedicine(medicine);
        pr.setDateTime(LocalDateTime.now());
        p.addPrescription(pr);
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

    @PostMapping("/{id}/appointment")
    public @ResponseBody
    String addAppointment(
            @PathVariable("id") Integer id,
            @RequestParam LocalDateTime datetime
    ) {

        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient %d not found", id)));

        Appointment a = new Appointment();
        a.setDateTime(datetime);

        p.addAppointment(a);
        patientRepository.save(p);
        return "{ \"success\": \"true\" }";
    }

}