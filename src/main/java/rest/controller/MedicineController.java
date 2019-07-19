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
import rest.repository.MedicineRepository;
import rest.response.SuccessResponseWithData;
import rest.response.SuccessResponseWithId;

import javax.validation.Valid;

import java.util.ArrayList;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@Controller
@RequestMapping(path="/api/medicine", produces = APPLICATION_JSON_UTF8_VALUE)
public class MedicineController {
    @Autowired
    private MedicineRepository medicineRepository;

    @PostMapping(path="")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    SuccessResponseWithData addNewMedicine (@Valid @RequestBody Medicine medicine) {
        Medicine savedMedicine = medicineRepository.save(medicine);
        return new SuccessResponseWithData(savedMedicine);
    }

    //@ApiOperation(value = "View a list of available medicine", response = Iterable.class)
    @GetMapping(path="")
    public @ResponseBody
    ArrayList<Medicine> getAllMedicine() {
        return Lists.newArrayList(medicineRepository.findAll());
    }

    @ApiResponses(value={@ApiResponse(code = 404, message = "Medicine not found")})
    @DeleteMapping("/{id}")
    public @ResponseBody
    SuccessResponseWithId deleteMedicine (@PathVariable("id") Integer id) {
        medicineRepository.findById(id)
          .orElseThrow(() -> new NotFoundException(String.format("Medicine %s not found", id)));
        medicineRepository.deleteById(id);
        return new SuccessResponseWithId(id);
    }

    @GetMapping(path="/search/{value}")
    public @ResponseBody
    ArrayList<Medicine> getByName(@PathVariable("value") String value) {
        return Lists.newArrayList(medicineRepository.findByNameContainingIgnoreCase(value));
    }

}