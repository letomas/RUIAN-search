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
    @Query("Obec:(?0) AND Cast_obce:(?1) AND Ulice:(?2) AND Cislo_domovni:(?3)")
    Page<Address> search(
            String city,
            String district,
            String street,
            String houseNumber,
            Pageable pageable);
    Page<Address> findByAdmCodeStartsWith(String AdmCode, Pageable pageable);
    Optional<Address> findByAdmCodeEquals(String AdmCode);
    @Query(value="Obec:(?0)", fields="N_zev_obce")
    List<Address> getCitySuggestions(String city);
    @Query(value="Cast_obce:(?1)", filters={"N_zev_obce:(?0)"}, fields="N_zev___sti_obce")
    List<Address> getDistrictSuggestions(String city, String district);
    @Query(value="Ulice:(?2)", filters={"N_zev_obce:(?0) AND N_zev___sti_obce:(?1)"}, fields="N_zev_ulice")
    List<Address> getStreetSuggestions(String city, String district, String street);
    @Query(
        value="Cislo_domovni:(?3)",
        filters={"N_zev_obce:(?0) AND N_zev___sti_obce:(?1) AND N_zev_ulice:(?2)"},
        fields="__slo_domovn_")
    List<Address> getHouseNumberSuggestions(String city, String district, String street, String houseNumber);
}
