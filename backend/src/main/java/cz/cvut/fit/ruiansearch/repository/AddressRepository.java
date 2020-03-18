package cz.cvut.fit.ruiansearch.repository;

import cz.cvut.fit.ruiansearch.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends SolrCrudRepository<Address, String> {
    @Query("Ulice:(?0) AND Cislo_domovni:(?1) AND Obec:(?3)")
    Page<Address> search(
            String streetName,
            String houseNumber,
            String boroughName,
            String cityName,
            Pageable pageable);
    Page<Address> findByAdmCodeStartsWith(String AdmCode, Pageable pageable);
    Optional<Address> findByAdmCodeEquals(String AdmCode);
    @Query(value="Obec:?0", fields="N_zev_obce")
    List<Address> getCitySuggestions(String city);
    @Query(value="Ulice:?1", filters={"N_zev_obce:?0"}, fields="N_zev_ulice")
    List<Address> getStreetSuggestions(String city, String street);
    @Query(value="Cislo_domovni:?2", filters={"N_zev_obce:?0 AND N_zev_ulice:?1"}, fields="__slo_domovn_")
    List<Address> getHouseNumberSuggestions(String city, String street, String houseNumber);
}
