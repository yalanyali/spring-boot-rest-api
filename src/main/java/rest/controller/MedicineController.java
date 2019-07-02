package rest.controller;

// TODO: howOften??

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.model.Medicine;
import rest.model.SearchByNameRequest;
import rest.repository.MedicineRepository;
import rest.response.SuccessResponse;

import javax.validation.Valid;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;


@Controller
@RequestMapping(path="/api/medicine")
public class MedicineController {
    @Autowired
    private MedicineRepository medicineRepository;

    @PostMapping(path="")
    public @ResponseBody
    ResponseEntity<Object> addNewMedicine (@Valid @RequestBody Medicine medicine) {
        Medicine savedMedicine = medicineRepository.save(medicine);
        return ResponseEntity.ok(new SuccessResponse(savedMedicine));
    }

    @GetMapping(path="")
    public @ResponseBody
    Iterable<Medicine> getAllMedicine() {
        return medicineRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    String deleteMedicine (@PathVariable("id") Integer id) {
        medicineRepository.deleteById(id);
        //  .orElseThrow(() -> new NotFoundException(String.format("Appointment %s not found", id)));
        return String.format("{ \"success\": \"true\", \"id\": %d }", id);
    }

    @GetMapping(path="/search/{value}")
    public @ResponseBody
    Iterable<Medicine> getByName(@PathVariable("value") String value) {
        //ExampleMatcher matcher = ExampleMatcher.matching()
        //        .withMatcher("name", startsWith().ignoreCase());
       // Medicine exampleMed = new Medicine();
       // exampleMed.setName(req.name);
        return medicineRepository.findByNameContainingIgnoreCase(value);
    }

}