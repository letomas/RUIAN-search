package cz.cvut.fit.ruiansearch.service;

import cz.cvut.fit.ruiansearch.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    Page<Address> search(
            String city,
            String district,
            String street,
            String houseNumber,
            Pageable pageable);
    Page<Address> findByAdmCodeStartsWith(String admCode, Pageable pageable);
    Optional<Address> findByAdmCode(String admCode);
    List<Address> getCitySuggestions(String city);
    List<Address> getDistrictSuggestions(String city, String district);
    List<Address> getStreetSuggestions(String city, String district, String street);
    List<Address> getHouseNumberSuggestions(String city, String district, String street, String houseNumber);
}
