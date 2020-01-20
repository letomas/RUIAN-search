package cz.cvut.fit.ruiansearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@SpringBootApplication
@EnableSolrRepositories
public class RuianSearchApplication {
	public static void main(String[] args) {
		SpringApplication.run(RuianSearchApplication.class, args);
	}
}
