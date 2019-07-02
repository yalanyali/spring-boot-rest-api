package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.model.Disease;
import rest.repository.DiseaseRepository;
import rest.response.SuccessResponse;

import javax.validation.Valid;


@Controller
@RequestMapping(path="/api/disease")
public class DiseaseController {
    @Autowired
    private DiseaseRepository diseaseRepository;

    @PostMapping(path="")
    public @ResponseBody
    ResponseEntity<Object> addNewDisease (@Valid @RequestBody Disease disease) {

        Disease savedDisease = diseaseRepository.save(disease);
        return ResponseEntity.ok(new SuccessResponse(savedDisease));
    }

    @GetMapping(path="")
    public @ResponseBody
    Iterable<Disease> getAllDiseases() {
        return diseaseRepository.findAll();
    }

}