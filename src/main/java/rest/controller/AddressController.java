package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rest.model.Address;
import rest.repository.AddressRepository;
import rest.response.SuccessResponse;

import javax.validation.Valid;
import java.net.URI;

@Controller
@RequestMapping(path="/api/address")
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @PostMapping(path="")
    public ResponseEntity<Object>
    addNewAddress (@Valid @RequestBody Address address) {
        System.out.println("WTF");
        Address savedAddress = addressRepository.save(address);
        return ResponseEntity.ok(new SuccessResponse(savedAddress));
    }

    @GetMapping(path="")
    public @ResponseBody
    Iterable<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

}