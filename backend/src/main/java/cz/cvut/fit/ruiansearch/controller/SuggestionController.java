package cz.cvut.fit.ruiansearch.controller;

import cz.cvut.fit.ruiansearch.model.Address;
import cz.cvut.fit.ruiansearch.service.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/suggestions")
public class SuggestionController {
    private final AddressService addressService;

    public SuggestionController(AddressService addressService) {
        this.addressService = addressService;
    }

    public List<String> getCitySuggestions() {
        return getCitySuggestions();
    }

    @GetMapping("/city")
    public Set<String> getCitySuggestions(@RequestParam() String city) {
        if (isEmptyOrNull(city)) {
            return Collections.emptySet();
        }

        List<Address> addresses = addressService.getCitySuggestions(city);
        Set<String> cities = addresses.stream()
                .map(Address::getCityName)
                .collect(Collectors.toSet());

        return cities;
    }

    @GetMapping("/street")
    public Set<String> getStreetSuggestions(
            @RequestParam(defaultValue = "*") String city,
            @RequestParam() String street) {
        if (isEmptyOrNull(street)) {
            return Collections.emptySet();
        }

        List<Address> addresses = addressService.getStreetSuggestions(city, street);
        Set<String> streets = addresses.stream()
                .map(Address::getStreetName)
                .collect(Collectors.toSet());

        return streets;
    }

    @GetMapping("/houseNumber")
    public Set<String> gethouseNumberSuggestions(
            @RequestParam(defaultValue = "*") String city,
            @RequestParam(defaultValue = "*") String street,
            @RequestParam() String houseNumber) {
        if (isEmptyOrNull(houseNumber) || street.equals("*")) {
            return Collections.emptySet();
        }

        List<Address> addresses = addressService.getHouseNumberSuggestions(city, street, houseNumber);
        Set<String> houseNumbers = addresses.stream()
                .map(Address::getHouseNumber)
                .collect(Collectors.toSet());

        return houseNumbers;
    }

    private boolean isEmptyOrNull(String input) {
        return (input == null || input == "");
    }
}
