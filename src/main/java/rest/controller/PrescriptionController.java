package rest.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.exception.NotFoundException;
import rest.model.Medicine;
import rest.model.Prescription;
import rest.repository.MedicineRepository;
import rest.repository.PrescriptionRepository;
import rest.response.SuccessResponse;

import javax.validation.Valid;
import java.time.LocalDateTime;


@Controller
@RequestMapping(path="/api/prescription")
public class PrescriptionController {
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private MedicineRepository medicineRepository;

    @PostMapping(path="")
    public @ResponseBody
    ResponseEntity<Object> addNewPrescription (@Valid @RequestBody Prescription prescription) {
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return ResponseEntity.ok(new SuccessResponse(savedPrescription));
    }

    @GetMapping(path="")
    public @ResponseBody
    Iterable<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    String deletePrescription (@PathVariable("id") Integer id) {
        prescriptionRepository.deleteById(id);
        //  .orElseThrow(() -> new NotFoundException(String.format("Patient %s not found", id)));
        return String.format("{ \"success\": \"true\", \"id\": %d }", id);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody
    ResponseEntity<Object> updatePrescription (
            @PathVariable("id") Integer id,
            @Valid @RequestBody Prescription prescription
        ) {

        Prescription p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Prescription %d not found", id)));

        p.setMedicine(prescription.getMedicine());

        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return ResponseEntity.ok(new SuccessResponse(savedPrescription));
    }

    @PostMapping("/{id}/medicine")
    public @ResponseBody
    void addMedicine(
            @PathVariable("id") Integer id,
            @RequestParam Integer medicineId
            ) {

        Prescription p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Prescription %d not found", id)));

        Medicine m = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new NotFoundException(String.format("Medicine %d not found", medicineId)));

        p.getMedicine().add(m);
        prescriptionRepository.save(p);
    }

}