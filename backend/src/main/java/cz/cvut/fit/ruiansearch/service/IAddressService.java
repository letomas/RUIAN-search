package cz.cvut.fit.ruiansearch.service;

import cz.cvut.fit.ruiansearch.model.Address;

import java.util.List;

public interface IAddressService {
    List<Address> findAll();
}
