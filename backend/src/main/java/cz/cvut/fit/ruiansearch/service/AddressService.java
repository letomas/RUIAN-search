package cz.cvut.fit.ruiansearch.service;

import cz.cvut.fit.ruiansearch.repository.IAddressRepository;
import cz.cvut.fit.ruiansearch.model.Address;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AddressService implements IAddressService {
    private final IAddressRepository addressRepository;

    public AddressService(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> findAll() {
        if(addressRepository.count() == 0) {
            return Collections.emptyList();
        }
        return addressRepository.findAll();
    }

    @Override
    public List<Address> search(String term) {
        if(term == null) {
            return findAll();
        }

        return addressRepository.search(term);
    }
}
