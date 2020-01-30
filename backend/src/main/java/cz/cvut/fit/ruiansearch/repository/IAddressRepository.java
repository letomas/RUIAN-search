package cz.cvut.fit.ruiansearch.repository;

import cz.cvut.fit.ruiansearch.model.Address;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAddressRepository extends SolrCrudRepository<Address, String> {
    @Query("Search_field:*?0*")
    List<Address> search(String term);
}
