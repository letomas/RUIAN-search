package cz.cvut.fit.ruiansearch.controller;

import cz.cvut.fit.ruiansearch.model.Address;
import cz.cvut.fit.ruiansearch.service.AddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/search")
    public Page<Address> getAddresses(
            @RequestParam(defaultValue= "*") String street,
            @RequestParam(defaultValue= "*") String houseNumber,
            @RequestParam(defaultValue= "*") String city,
            @RequestParam(defaultValue= "*") String borough,
            @PageableDefault() Pageable pageable) {
        List<String> params = Arrays.asList(street, houseNumber, city, borough);
        if(paramsAreDefault(params, "*")) {
            return Page.empty();
        }

        return addressService.search(
            street, houseNumber, borough, city, pageable);
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
}
