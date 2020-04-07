package cz.cvut.fit.ruiansearch.service;

import cz.cvut.fit.ruiansearch.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.GroupResult;

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
    GroupResult<Address> getCitySuggestions(String city);
    GroupResult<Address> getDistrictSuggestions(String city, String district);
    GroupResult<Address> getStreetSuggestions(String city, String district, String street);
    GroupResult<Address> getHouseNumberSuggestions(String city, String district, String street, String houseNumber);
}
