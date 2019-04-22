package org.jhipster.thibaut.thelibrary.repository.search;

import org.jhipster.thibaut.thelibrary.domain.Users;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Users entity.
 */
public interface UsersSearchRepository extends ElasticsearchRepository<Users, Long> {
}
