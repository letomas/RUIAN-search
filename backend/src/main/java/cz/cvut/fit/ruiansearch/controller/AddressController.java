package cz.cvut.fit.ruiansearch.controller;

import cz.cvut.fit.ruiansearch.model.Address;
import cz.cvut.fit.ruiansearch.service.AddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @GetMapping("")
    public Page<Address> getAdresssesByCode(
        @RequestParam String admCode,
        @PageableDefault Pageable pageable) {
        if(isEmptyOrNull(admCode)) {
            return Page.empty();
        }

        return addressService.findByAdmCodeStartsWith(admCode, pageable);
    }

    @GetMapping("/search")
    public Page<Address> getAddresses(
            @RequestParam(defaultValue= "*") String city,
            @RequestParam(defaultValue= "*") String district,
            @RequestParam(defaultValue= "*") String street,
            @RequestParam(defaultValue= "*") String houseNumber,
            @PageableDefault Pageable pageable) {
        List<String> params = Arrays.asList(street, houseNumber, city, district);
        if(paramsAreDefault(params, "*")) {
            return Page.empty();
        }

        if(street.equals("*")) {
            return addressService.searchWithEmptyStreet(city, district, houseNumber, pageable);
        }

        return addressService.search(
            city, district, street, houseNumber, pageable);
    }

    @GetMapping("/nearby")
    public Page<Address> getNearbyAddresses(
        @RequestParam Double x,
        @RequestParam Double y,
        @RequestParam(defaultValue = "0.2") Double distance
    ) {
        Point center = new Point(x, y);
        return addressService.getNearbyAddresses(center, distance);
    }


    @GetMapping("/{admCode}")
    public Optional<Address> getAddressDetail(@PathVariable String admCode) {
        return addressService.findByAdmCode(admCode);
    }

    private boolean paramsAreDefault(List<String> params, String defaultValue) {
        for(String param : params) {
            if(!param.equals(defaultValue)) {
                return false;
            }
        }

        return true;
    }

    private boolean isEmptyOrNull(String input) {
        return (input == null || input == "");
    }
}
