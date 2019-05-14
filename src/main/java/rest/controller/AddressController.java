package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.exception.NotFoundException;
import rest.model.Address;
import rest.repository.AddressRepository;

@Controller
@RequestMapping(path="/api/address")
public class AddressController {
    @Autowired
    private AddressRepository adressRepository;

    @PostMapping(path="")
    public @ResponseBody
    String addNewAddress (
            @RequestParam String state,
            @RequestParam String city,
            @RequestParam String street,
            @RequestParam String buildingNumber,
            @RequestParam String zip) {

        Address n = new Address();
        n.setState(state);
        n.setCity(city);
        n.setStreet(street);
        n.setBuildingNumber(buildingNumber);
        n.setZip(zip);
        adressRepository.save(n);
        return String.format("{ \"success\": \"true\", \"id\": %s }", n.getId());
    }

    @GetMapping(path="")
    public @ResponseBody
    Iterable<Address> getAllAddresses() {
        return adressRepository.findAll();
    }

}