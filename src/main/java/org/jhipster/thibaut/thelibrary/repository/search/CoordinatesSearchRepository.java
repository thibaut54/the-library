package org.jhipster.thibaut.thelibrary.repository.search;

import org.jhipster.thibaut.thelibrary.domain.Coordinates;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Coordinates entity.
 */
public interface CoordinatesSearchRepository extends ElasticsearchRepository<Coordinates, Long> {
}
