package org.jhipster.thibaut.thelibrary.repository.search;

import org.jhipster.thibaut.thelibrary.domain.Editor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Editor entity.
 */
public interface EditorSearchRepository extends ElasticsearchRepository<Editor, Long> {
}
