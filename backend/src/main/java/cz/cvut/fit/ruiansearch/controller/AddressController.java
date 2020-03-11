package cz.cvut.fit.ruiansearch.controller;

import cz.cvut.fit.ruiansearch.model.Address;
import cz.cvut.fit.ruiansearch.service.AddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping({"", "/"})
    public Page<Address> getAddresses(
            @RequestParam(name = "search", required = false) String term,
            @RequestParam(name = "admCode", required = false) String admCode,
            @PageableDefault() Pageable pageable) {

        if(term != null) {
            return addressService.search(term, pageable);
        }

        return addressService.findByAdmCodeStartsWith(admCode, pageable);
    }

    @GetMapping({"/form-search", "/form-search/"})
    public Page<Address> getAddressesFromForm(
            @RequestParam(name = "street", required = false) String street,
            @RequestParam(name = "houseNumber", required = false) String houseNumber,
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "borough", required = false) String borough,
            @PageableDefault() Pageable pageable) {
        return addressService.formSearch(
            street, houseNumber, borough, city, pageable);
    }


    @GetMapping({"/{admCode}", "/{admCode}/"})
    public Optional<Address> getAddressDetail(@PathVariable String admCode) {
        return addressService.findByAdmCode(admCode);
    }
}
