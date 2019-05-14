package rest.controller;

// TODO: howOften??

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.model.Medicine;
import rest.repository.MedicineRepository;


@Controller
@RequestMapping(path="/api/medicine")
public class MedicineController {
    @Autowired
    private MedicineRepository medicineRepositor;

    @PostMapping(path="")
    public @ResponseBody
    String addNewMedicine (
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String howOften) {

        Medicine n = new Medicine();
        n.setName(name);
        n.setDescription(description);
        n.setHowOften(howOften);
        medicineRepositor.save(n);
        return String.format("{ \"success\": \"true\", \"id\": %s }", n.getId());
    }

    @GetMapping(path="")
    public @ResponseBody
    Iterable<Medicine> getAllMedicine() {
        return medicineRepositor.findAll();
    }

}