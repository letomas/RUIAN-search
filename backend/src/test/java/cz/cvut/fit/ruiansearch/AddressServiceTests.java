package cz.cvut.fit.ruiansearch;

import cz.cvut.fit.ruiansearch.model.Address;
import cz.cvut.fit.ruiansearch.repository.AddressRepository;
import cz.cvut.fit.ruiansearch.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AddressServiceTests {
    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    @Test
    public void searchShouldReturnFromRepositoryTest() {
        String city = "city";
        String district = "district";
        String street = "street";
        String houseNumber = "666";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Address> page = getAddressPage();

        when(addressRepository.search(city, district, street, houseNumber, pageable))
                .thenReturn(page);
        Page<Address> result = addressService.search(city, district, street, houseNumber, pageable);
        assertEquals(result, page);
    }

    @Test
    public void findByAdmCodeStartsWithShouldReturnFromRepositoryTest() {
        String query = "";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Address> page = getAddressPage();

        when(addressRepository.findByAdmCodeStartsWith(query, pageable)).thenReturn(page);
        Page<Address> result = addressService.findByAdmCodeStartsWith(query, pageable);
        assertEquals(result, page);
    }

    @Test
    public void findByAdmCodeShouldReturnFromRepositoryTest() {
        String query = "";
        when(addressRepository.findByAdmCodeEquals(query)).thenReturn(Optional.empty());
        Optional<Address> result = addressService.findByAdmCode(query);
        assertEquals(result, Optional.empty());
    }

    private Page<Address> getAddressPage() {
        Address address = new Address();
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        return new PageImpl<>(addressList);
    }
}
