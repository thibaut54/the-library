package org.jhipster.thibaut.thelibrary.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CoordinatesSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CoordinatesSearchRepositoryMockConfiguration {

    @MockBean
    private CoordinatesSearchRepository mockCoordinatesSearchRepository;

}
