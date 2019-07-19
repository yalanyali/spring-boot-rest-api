package rest.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.model.Address;
import rest.repository.AddressRepository;
import rest.response.SuccessResponseWithData;

import javax.validation.Valid;

import java.util.ArrayList;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Controller
@RequestMapping(path="/api/address", produces = APPLICATION_JSON_UTF8_VALUE)
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @PostMapping(path="")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponseWithData
    addNewAddress (@Valid @RequestBody Address address) {
        Address savedAddress = addressRepository.save(address);
        return new SuccessResponseWithData(savedAddress);
    }

    @GetMapping(path="")
    public @ResponseBody
    ArrayList<Address> getAllAddresses() {
        return Lists.newArrayList(addressRepository.findAll());
    }

}