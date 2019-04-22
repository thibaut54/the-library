package org.jhipster.thibaut.thelibrary.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of EditorSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EditorSearchRepositoryMockConfiguration {

    @MockBean
    private EditorSearchRepository mockEditorSearchRepository;

}
