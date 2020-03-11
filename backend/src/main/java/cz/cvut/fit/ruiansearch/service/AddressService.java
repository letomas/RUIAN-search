package cz.cvut.fit.ruiansearch.service;

import cz.cvut.fit.ruiansearch.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AddressService {
    Page<Address> search(String term, Pageable pageable);
    Page<Address> formSearch(
            String streetName,
            String houseNumber,
            String boroughName,
            String cityName,
            Pageable pageable);
    Page<Address> findByAdmCodeStartsWith(String admCode, Pageable pageable);
    Optional<Address> findByAdmCode(String admCode);
}
