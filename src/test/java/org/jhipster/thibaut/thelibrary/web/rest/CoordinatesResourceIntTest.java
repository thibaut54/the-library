package org.jhipster.thibaut.thelibrary.web.rest;

import org.jhipster.thibaut.thelibrary.TheLibraryApp;

import org.jhipster.thibaut.thelibrary.domain.Coordinates;
import org.jhipster.thibaut.thelibrary.repository.CoordinatesRepository;
import org.jhipster.thibaut.thelibrary.repository.search.CoordinatesSearchRepository;
import org.jhipster.thibaut.thelibrary.service.CoordinatesService;
import org.jhipster.thibaut.thelibrary.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static org.jhipster.thibaut.thelibrary.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CoordinatesResource REST controller.
 *
 * @see CoordinatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheLibraryApp.class)
public class CoordinatesResourceIntTest {

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSTAL_CODE = 1;
    private static final Integer UPDATED_POSTAL_CODE = 2;

    private static final Integer DEFAULT_PHONE = 1;
    private static final Integer UPDATED_PHONE = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private CoordinatesRepository coordinatesRepository;

    @Autowired
    private CoordinatesService coordinatesService;

    /**
     * This repository is mocked in the org.jhipster.thibaut.thelibrary.repository.search test package.
     *
     * @see org.jhipster.thibaut.thelibrary.repository.search.CoordinatesSearchRepositoryMockConfiguration
     */
    @Autowired
    private CoordinatesSearchRepository mockCoordinatesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCoordinatesMockMvc;

    private Coordinates coordinates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoordinatesResource coordinatesResource = new CoordinatesResource(coordinatesService);
        this.restCoordinatesMockMvc = MockMvcBuilders.standaloneSetup(coordinatesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coordinates createEntity(EntityManager em) {
        Coordinates coordinates = new Coordinates()
            .street(DEFAULT_STREET)
            .streetNumber(DEFAULT_STREET_NUMBER)
            .additionalInformation(DEFAULT_ADDITIONAL_INFORMATION)
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL);
        return coordinates;
    }

    @Before
    public void initTest() {
        coordinates = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoordinates() throws Exception {
        int databaseSizeBeforeCreate = coordinatesRepository.findAll().size();

        // Create the Coordinates
        restCoordinatesMockMvc.perform(post("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinates)))
            .andExpect(status().isCreated());

        // Validate the Coordinates in the database
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeCreate + 1);
        Coordinates testCoordinates = coordinatesList.get(coordinatesList.size() - 1);
        assertThat(testCoordinates.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testCoordinates.getStreetNumber()).isEqualTo(DEFAULT_STREET_NUMBER);
        assertThat(testCoordinates.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
        assertThat(testCoordinates.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCoordinates.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCoordinates.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCoordinates.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the Coordinates in Elasticsearch
        verify(mockCoordinatesSearchRepository, times(1)).save(testCoordinates);
    }

    @Test
    @Transactional
    public void createCoordinatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coordinatesRepository.findAll().size();

        // Create the Coordinates with an existing ID
        coordinates.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoordinatesMockMvc.perform(post("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinates)))
            .andExpect(status().isBadRequest());

        // Validate the Coordinates in the database
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Coordinates in Elasticsearch
        verify(mockCoordinatesSearchRepository, times(0)).save(coordinates);
    }

    @Test
    @Transactional
    public void getAllCoordinates() throws Exception {
        // Initialize the database
        coordinatesRepository.saveAndFlush(coordinates);

        // Get all the coordinatesList
        restCoordinatesMockMvc.perform(get("/api/coordinates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordinates.getId().intValue())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getCoordinates() throws Exception {
        // Initialize the database
        coordinatesRepository.saveAndFlush(coordinates);

        // Get the coordinates
        restCoordinatesMockMvc.perform(get("/api/coordinates/{id}", coordinates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coordinates.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.streetNumber").value(DEFAULT_STREET_NUMBER.toString()))
            .andExpect(jsonPath("$.additionalInformation").value(DEFAULT_ADDITIONAL_INFORMATION.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCoordinates() throws Exception {
        // Get the coordinates
        restCoordinatesMockMvc.perform(get("/api/coordinates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoordinates() throws Exception {
        // Initialize the database
        coordinatesService.save(coordinates);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCoordinatesSearchRepository);

        int databaseSizeBeforeUpdate = coordinatesRepository.findAll().size();

        // Update the coordinates
        Coordinates updatedCoordinates = coordinatesRepository.findById(coordinates.getId()).get();
        // Disconnect from session so that the updates on updatedCoordinates are not directly saved in db
        em.detach(updatedCoordinates);
        updatedCoordinates
            .street(UPDATED_STREET)
            .streetNumber(UPDATED_STREET_NUMBER)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL);

        restCoordinatesMockMvc.perform(put("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCoordinates)))
            .andExpect(status().isOk());

        // Validate the Coordinates in the database
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeUpdate);
        Coordinates testCoordinates = coordinatesList.get(coordinatesList.size() - 1);
        assertThat(testCoordinates.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testCoordinates.getStreetNumber()).isEqualTo(UPDATED_STREET_NUMBER);
        assertThat(testCoordinates.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
        assertThat(testCoordinates.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCoordinates.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCoordinates.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCoordinates.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the Coordinates in Elasticsearch
        verify(mockCoordinatesSearchRepository, times(1)).save(testCoordinates);
    }

    @Test
    @Transactional
    public void updateNonExistingCoordinates() throws Exception {
        int databaseSizeBeforeUpdate = coordinatesRepository.findAll().size();

        // Create the Coordinates

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoordinatesMockMvc.perform(put("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinates)))
            .andExpect(status().isBadRequest());

        // Validate the Coordinates in the database
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Coordinates in Elasticsearch
        verify(mockCoordinatesSearchRepository, times(0)).save(coordinates);
    }

    @Test
    @Transactional
    public void deleteCoordinates() throws Exception {
        // Initialize the database
        coordinatesService.save(coordinates);

        int databaseSizeBeforeDelete = coordinatesRepository.findAll().size();

        // Delete the coordinates
        restCoordinatesMockMvc.perform(delete("/api/coordinates/{id}", coordinates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Coordinates in Elasticsearch
        verify(mockCoordinatesSearchRepository, times(1)).deleteById(coordinates.getId());
    }

    @Test
    @Transactional
    public void searchCoordinates() throws Exception {
        // Initialize the database
        coordinatesService.save(coordinates);
        when(mockCoordinatesSearchRepository.search(queryStringQuery("id:" + coordinates.getId())))
            .thenReturn(Collections.singletonList(coordinates));
        // Search the coordinates
        restCoordinatesMockMvc.perform(get("/api/_search/coordinates?query=id:" + coordinates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordinates.getId().intValue())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER)))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coordinates.class);
        Coordinates coordinates1 = new Coordinates();
        coordinates1.setId(1L);
        Coordinates coordinates2 = new Coordinates();
        coordinates2.setId(coordinates1.getId());
        assertThat(coordinates1).isEqualTo(coordinates2);
        coordinates2.setId(2L);
        assertThat(coordinates1).isNotEqualTo(coordinates2);
        coordinates1.setId(null);
        assertThat(coordinates1).isNotEqualTo(coordinates2);
    }
}
