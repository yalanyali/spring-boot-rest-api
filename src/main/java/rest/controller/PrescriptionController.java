package rest.controller;

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

    @PostMapping("/{id}/medicine")
    public @ResponseBody
    void addMedicine(
            @PathVariable("id") String id,
            @RequestParam String medicineId
            ) {

        Prescription p = prescriptionRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new NotFoundException(String.format("Prescription %s not found", id)));

        Medicine m = medicineRepository.findById(Integer.parseInt(medicineId))
                .orElseThrow(() -> new NotFoundException(String.format("Medicine %s not found", medicineId)));

        p.getMedicine().add(m);
        prescriptionRepository.save(p);
    }

}