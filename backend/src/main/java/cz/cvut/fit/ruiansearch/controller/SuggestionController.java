package cz.cvut.fit.ruiansearch.controller;

import cz.cvut.fit.ruiansearch.model.Address;
import cz.cvut.fit.ruiansearch.service.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Set<String> getCitySuggestions(@RequestParam String city) {
        if (isEmptyOrNull(city)) {
            return Collections.emptySet();
        }

        List<Address> addresses = addressService.getCitySuggestions(wrapInQuotes(city));
        Set<String> cities = addresses.stream()
                .map(Address::getCityName)
                .collect(Collectors.toSet());

        return cities;
    }

    @GetMapping("/district")
    public Set<String> getDistrictSuggestions(
        @RequestParam(defaultValue = "*") String city,
        @RequestParam String district) {
        if (isEmptyOrNull(district)) {
            return Collections.emptySet();
        }

        Map <String, String> params = Stream.of(new String[][] {
            { "city", city },
            { "district", district }
        }).collect(Collectors.toMap(data -> data[0], data -> wrapInQuotes(data[1])));

        List<Address> addresses = addressService.getDistrictSuggestions(params.get("city"), params.get("district"));
        Set<String> districts = addresses.stream()
                .map(Address::getDistrictName)
                .collect(Collectors.toSet());

        return districts;
    }

    @GetMapping("/street")
    public Set<String> getStreetSuggestions(
            @RequestParam(defaultValue = "*") String city,
            @RequestParam(defaultValue = "*") String district,
            @RequestParam String street) {
        if (isEmptyOrNull(street)) {
            return Collections.emptySet();
        }

        Map <String, String> params = Stream.of(new String[][] {
            { "city", city },
            { "district", district },
            { "street", street }
        }).collect(Collectors.toMap(data -> data[0], data -> wrapInQuotes(data[1])));

        List<Address> addresses = addressService.getStreetSuggestions(params.get("city"), params.get("district"), params.get("street"));
        Set<String> streets = addresses.stream()
                .map(Address::getStreetName)
                .collect(Collectors.toSet());

        return streets;
    }

    @GetMapping("/houseNumber")
    public Set<String> gethouseNumberSuggestions(
            @RequestParam(defaultValue = "*") String city,
            @RequestParam(defaultValue = "*") String district,
            @RequestParam(defaultValue = "*") String street,
            @RequestParam String houseNumber) {
        if (isEmptyOrNull(houseNumber) || street.equals("*")) {
            return Collections.emptySet();
        }

        Map <String, String> params = Stream.of(new String[][] {
            { "city", city },
            { "district", district },
            { "street", street },
            { "houseNumber", houseNumber }
        }).collect(Collectors.toMap(data -> data[0], data -> wrapInQuotes(data[1])));

        List<Address> addresses = addressService.getHouseNumberSuggestions(
            params.get("city"), params.get("district"), params.get("street"), params.get("houseNumber"));
        Set<String> houseNumbers = addresses.stream()
                .map(Address::getIdentification)
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(
                    x -> Integer.parseInt(x.split("/")[0]))
                    )));

        return houseNumbers;
    }

    private boolean isEmptyOrNull(String input) {
        return (input == null || input == "");
    }

    private String wrapInQuotes(String input) {
        if(input.equals("*")) {
            return input;
        }

        StringBuilder output = new StringBuilder(input);
        output.append("\"");
        output.insert(0, "\"");

        return output.toString();
    }
}
