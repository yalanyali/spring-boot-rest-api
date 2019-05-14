package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.model.Disease;
import rest.repository.DiseaseRepository;


@Controller
@RequestMapping(path="/api/disease")
public class DiseaseController {
    @Autowired
    private DiseaseRepository diseaseRepository;

    @PostMapping(path="")
    public @ResponseBody
    String addNewDisease (@RequestParam String name, @RequestParam String description) {

        Disease n = new Disease();
        n.setName(name);
        n.setDescription(description);
        diseaseRepository.save(n);
        return String.format("{ \"success\": \"true\", \"id\": %s }", n.getId());
    }

    @GetMapping(path="")
    public @ResponseBody
    Iterable<Disease> getAllDiseases() {
        return diseaseRepository.findAll();
    }

}