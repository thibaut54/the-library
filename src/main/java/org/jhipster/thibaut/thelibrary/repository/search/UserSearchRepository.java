package org.jhipster.thibaut.thelibrary.repository.search;

import org.jhipster.thibaut.thelibrary.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
}
