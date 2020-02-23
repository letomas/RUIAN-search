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
    @Query("Search_field:*?0*")
    Page<Address> search(String term, Pageable pageable);
    Page<Address> findByAdmCodeStartsWith(String AdmCode, Pageable pageable);
    Optional<Address> findByAdmCodeEquals(String AdmCode);
}
