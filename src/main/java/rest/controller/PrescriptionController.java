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
import rest.model.Medicine;
import rest.model.Prescription;
import rest.repository.MedicineRepository;
import rest.repository.PrescriptionRepository;
import rest.response.SuccessResponseWithData;
import rest.response.SuccessResponseWithId;

import javax.validation.Valid;

import java.util.ArrayList;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@Controller
@RequestMapping(path="/api/prescription", produces = APPLICATION_JSON_UTF8_VALUE)
public class PrescriptionController {
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private MedicineRepository medicineRepository;

    @PostMapping(path="")
    public @ResponseBody
    SuccessResponseWithData addNewPrescription (@Valid @RequestBody Prescription prescription) {
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return new SuccessResponseWithData(savedPrescription);
    }

    @GetMapping(path="")
    public @ResponseBody
    ArrayList<Prescription> getAllPrescriptions() {
        return Lists.newArrayList(prescriptionRepository.findAll());
    }

    @ApiResponses(value={@ApiResponse(code = 404, message = "Prescription not found")})
    @DeleteMapping("/{id}")
    public @ResponseBody
    SuccessResponseWithId deletePrescription (@PathVariable("id") Integer id) {
        prescriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Prescription %s not found", id)));
        prescriptionRepository.deleteById(id);
        return new SuccessResponseWithId(id);
    }

    @ApiResponses(value={@ApiResponse(code = 404, message = "Prescription not found")})
    @PutMapping(path="/{id}")
    public @ResponseBody
    SuccessResponseWithData updatePrescription (
            @PathVariable("id") Integer id,
            @Valid @RequestBody Prescription prescription
        ) {

        Prescription p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Prescription %d not found", id)));

        p.setMedicine(prescription.getMedicine());

        Prescription savedPrescription = prescriptionRepository.save(p);
        return new SuccessResponseWithData(savedPrescription);
    }

    @ApiResponses(value={@ApiResponse(code = 404, message = "Prescription/Medicine not found")})
    @PostMapping("/{id}/medicine")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    SuccessResponseWithId addMedicine(
            @PathVariable("id") Integer id,
            @RequestParam Integer medicineId
            ) {

        Prescription p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Prescription %d not found", id)));

        Medicine m = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new NotFoundException(String.format("Medicine %d not found", medicineId)));

        p.getMedicine().add(m);
        prescriptionRepository.save(p);

        // FIXME: Return medicine id
        return new SuccessResponseWithId(id);
    }

}