package cz.cvut.fit.ruiansearch.service;

import cz.cvut.fit.ruiansearch.model.Address;
import cz.cvut.fit.ruiansearch.repository.IAddressRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements IAddressService {
    private final IAddressRepository addressRepository;

    public AddressService(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Page<Address> search(String term, Pageable pageable) {
        return addressRepository.search(term, pageable);
    }

    @Override
    public Page<Address> findByAdmCode(String admCode, Pageable pageable) {
        return addressRepository.findByAdmCodeStartsWith(admCode, pageable);
    }
}
