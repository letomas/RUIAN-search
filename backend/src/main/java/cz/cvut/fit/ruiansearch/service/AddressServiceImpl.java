package cz.cvut.fit.ruiansearch.service;

import cz.cvut.fit.ruiansearch.model.Address;
import cz.cvut.fit.ruiansearch.repository.AddressRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Page<Address> search(String term, Pageable pageable) {
        return addressRepository.search(term, pageable);
    }

    @Override
    public Page<Address> formSearch(
            String streetName,
            String houseNumber,
            String boroughName,
            String cityName,
            Pageable pageable) {
        return addressRepository.formSearch(
                streetName, houseNumber, boroughName, cityName, pageable);
    }
    
    @Override
    public Page<Address> findByAdmCodeStartsWith(String admCode, Pageable pageable) {
        return addressRepository.findByAdmCodeStartsWith(admCode, pageable);
    }

    @Override
    public Optional<Address> findByAdmCode(String admCode) {
        return addressRepository.findByAdmCodeEquals(admCode);
    }
}
