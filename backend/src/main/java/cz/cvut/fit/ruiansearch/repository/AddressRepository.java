package cz.cvut.fit.ruiansearch.repository;

import cz.cvut.fit.ruiansearch.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends SolrCrudRepository<Address, String> {
    @Query("Obec:(?0) AND Cast_obce:(?1) AND -(-Ulice:(?2) AND Ulice:*)  AND (Identifikace_cs:(?3) OR Cislo_orientacni:(?3))")
    Page<Address> search(
            String city,
            String district,
            String street,
            String houseNumber,
            Pageable pageable);
    Page<Address> findByAdmCodeStartsWith(String AdmCode, Pageable pageable);
    Optional<Address> findByAdmCodeEquals(String AdmCode);
}
