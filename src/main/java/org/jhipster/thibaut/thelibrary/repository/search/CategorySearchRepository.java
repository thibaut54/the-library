package org.jhipster.thibaut.thelibrary.repository.search;

import org.jhipster.thibaut.thelibrary.domain.Category;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Category entity.
 */
public interface CategorySearchRepository extends ElasticsearchRepository<Category, Long> {
}
