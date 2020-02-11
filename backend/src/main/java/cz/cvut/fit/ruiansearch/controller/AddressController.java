package cz.cvut.fit.ruiansearch.controller;

import cz.cvut.fit.ruiansearch.model.Address;
import cz.cvut.fit.ruiansearch.service.AddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

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
            @PageableDefault()
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "admCode", direction = Sort.Direction.ASC)
            }) Pageable pageable) {

        if(term != null) {
            return addressService.search(term, pageable);
        }

        return addressService.findByAdmCode(admCode, pageable);
    }
}
