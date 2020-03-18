package cz.cvut.fit.ruiansearch.service;

import cz.cvut.fit.ruiansearch.model.Address;
import cz.cvut.fit.ruiansearch.repository.AddressRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Page<Address> search(
            String city,
            String district,
            String street,
            String houseNumber,
            Pageable pageable) {
        return addressRepository.search(
                city, district, street, houseNumber, pageable);
    }

    @Override
    public Page<Address> findByAdmCodeStartsWith(String admCode, Pageable pageable) {
        return addressRepository.findByAdmCodeStartsWith(admCode, pageable);
    }

    @Override
    public Optional<Address> findByAdmCode(String admCode) {
        return addressRepository.findByAdmCodeEquals(admCode);
    }

    @Override
    public List<Address> getCitySuggestions(String city) {
        return addressRepository.getCitySuggestions(city);
    }

    @Override
    public List<Address> getDistrictSuggestions(String city, String district) {
        return addressRepository.getDistrictSuggestions(city, district);
    }        

    @Override
    public List<Address> getStreetSuggestions(String city, String district, String street) {
        return addressRepository.getStreetSuggestions(city, district, street);
    }

    @Override
    public List<Address> getHouseNumberSuggestions(String city, String district, String street, String houseNumber) {
        return addressRepository.getHouseNumberSuggestions(city, district, street, houseNumber);
    }

}
