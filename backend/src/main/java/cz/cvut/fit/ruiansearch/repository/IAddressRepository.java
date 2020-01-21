package cz.cvut.fit.ruiansearch.repository;

import cz.cvut.fit.ruiansearch.model.Address;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAddressRepository extends SolrCrudRepository<Address, String> {
    @Query(":")
    List<Address> findAll();

    @Query("N_zev_obce:*?0* OR N_zev___sti_obce:*?0* OR N_zev_ulice:*?0*")
    List<Address> search(String term);
}
