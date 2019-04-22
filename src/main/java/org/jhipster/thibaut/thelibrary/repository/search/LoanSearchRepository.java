package org.jhipster.thibaut.thelibrary.repository.search;

import org.jhipster.thibaut.thelibrary.domain.Loan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Loan entity.
 */
public interface LoanSearchRepository extends ElasticsearchRepository<Loan, Long> {
}
