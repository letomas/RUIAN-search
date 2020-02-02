package cz.cvut.fit.ruiansearch.repository;

import cz.cvut.fit.ruiansearch.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAddressRepository extends SolrCrudRepository<Address, String> {
    @Query("Search_field:*?0*")
    Page<Address> search(String term, Pageable pageable);
    Page<Address> findByAdmCodeStartsWith(String AdmCode, Pageable pageable);
}
