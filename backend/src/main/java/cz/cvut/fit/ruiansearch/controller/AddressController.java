package cz.cvut.fit.ruiansearch.controller;

import cz.cvut.fit.ruiansearch.service.AddressService;
import cz.cvut.fit.ruiansearch.model.Address;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address")
    public List<Address> getAddresses(@RequestParam(name = "search", required = false) String term) {
        return addressService.search(term);
    }
}
