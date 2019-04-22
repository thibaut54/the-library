package org.jhipster.thibaut.thelibrary.repository.search;

import org.jhipster.thibaut.thelibrary.domain.Library;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Library entity.
 */
public interface LibrarySearchRepository extends ElasticsearchRepository<Library, Long> {
}
