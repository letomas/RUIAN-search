package cz.cvut.fit.ruiansearch.config;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
public class SolrConfig {
    @Bean
    public HttpSolrClient solrClient() {
        return new HttpSolrClient.Builder("http://localhost:8983/solr").build();
    }

    @Bean
    public SolrTemplate solrTemplate() {
        return new SolrTemplate(solrClient());
    }
}
