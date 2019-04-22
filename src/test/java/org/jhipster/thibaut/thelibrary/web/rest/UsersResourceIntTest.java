package org.jhipster.thibaut.thelibrary.web.rest;

import org.jhipster.thibaut.thelibrary.TheLibraryApp;

import org.jhipster.thibaut.thelibrary.domain.Users;
import org.jhipster.thibaut.thelibrary.repository.UsersRepository;
import org.jhipster.thibaut.thelibrary.repository.search.UsersSearchRepository;
import org.jhipster.thibaut.thelibrary.service.UsersService;
import org.jhipster.thibaut.thelibrary.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
 * Test class for the UsersResource REST controller.
 *
 * @see UsersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheLibraryApp.class)
public class UsersResourceIntTest {

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REGISTRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UsersRepository usersRepository;

    @Mock
    private UsersRepository usersRepositoryMock;

    @Mock
    private UsersService usersServiceMock;

    @Autowired
    private UsersService usersService;

    /**
     * This repository is mocked in the org.jhipster.thibaut.thelibrary.repository.search test package.
     *
     * @see org.jhipster.thibaut.thelibrary.repository.search.UsersSearchRepositoryMockConfiguration
     */
    @Autowired
    private UsersSearchRepository mockUsersSearchRepository;

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

    private MockMvc restUsersMockMvc;

    private Users users;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsersResource usersResource = new UsersResource(usersService);
        this.restUsersMockMvc = MockMvcBuilders.standaloneSetup(usersResource)
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
    public static Users createEntity(EntityManager em) {
        Users users = new Users()
            .password(DEFAULT_PASSWORD)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .userName(DEFAULT_USER_NAME)
            .registrationDate(DEFAULT_REGISTRATION_DATE);
        return users;
    }

    @Before
    public void initTest() {
        users = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsers() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();

        // Create the Users
        restUsersMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(users)))
            .andExpect(status().isCreated());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate + 1);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUsers.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUsers.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUsers.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUsers.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);

        // Validate the Users in Elasticsearch
        verify(mockUsersSearchRepository, times(1)).save(testUsers);
    }

    @Test
    @Transactional
    public void createUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();

        // Create the Users with an existing ID
        users.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsersMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(users)))
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate);

        // Validate the Users in Elasticsearch
        verify(mockUsersSearchRepository, times(0)).save(users);
    }

    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList
        restUsersMockMvc.perform(get("/api/users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(users.getId().intValue())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUsersWithEagerRelationshipsIsEnabled() throws Exception {
        UsersResource usersResource = new UsersResource(usersServiceMock);
        when(usersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUsersMockMvc = MockMvcBuilders.standaloneSetup(usersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUsersMockMvc.perform(get("/api/users?eagerload=true"))
        .andExpect(status().isOk());

        verify(usersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        UsersResource usersResource = new UsersResource(usersServiceMock);
            when(usersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUsersMockMvc = MockMvcBuilders.standaloneSetup(usersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUsersMockMvc.perform(get("/api/users?eagerload=true"))
        .andExpect(status().isOk());

            verify(usersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get the users
        restUsersMockMvc.perform(get("/api/users/{id}", users.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(users.getId().intValue()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.registrationDate").value(DEFAULT_REGISTRATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUsers() throws Exception {
        // Get the users
        restUsersMockMvc.perform(get("/api/users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsers() throws Exception {
        // Initialize the database
        usersService.save(users);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockUsersSearchRepository);

        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Update the users
        Users updatedUsers = usersRepository.findById(users.getId()).get();
        // Disconnect from session so that the updates on updatedUsers are not directly saved in db
        em.detach(updatedUsers);
        updatedUsers
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .userName(UPDATED_USER_NAME)
            .registrationDate(UPDATED_REGISTRATION_DATE);

        restUsersMockMvc.perform(put("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsers)))
            .andExpect(status().isOk());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUsers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUsers.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUsers.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUsers.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);

        // Validate the Users in Elasticsearch
        verify(mockUsersSearchRepository, times(1)).save(testUsers);
    }

    @Test
    @Transactional
    public void updateNonExistingUsers() throws Exception {
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Create the Users

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsersMockMvc.perform(put("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(users)))
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Users in Elasticsearch
        verify(mockUsersSearchRepository, times(0)).save(users);
    }

    @Test
    @Transactional
    public void deleteUsers() throws Exception {
        // Initialize the database
        usersService.save(users);

        int databaseSizeBeforeDelete = usersRepository.findAll().size();

        // Delete the users
        restUsersMockMvc.perform(delete("/api/users/{id}", users.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Users in Elasticsearch
        verify(mockUsersSearchRepository, times(1)).deleteById(users.getId());
    }

    @Test
    @Transactional
    public void searchUsers() throws Exception {
        // Initialize the database
        usersService.save(users);
        when(mockUsersSearchRepository.search(queryStringQuery("id:" + users.getId())))
            .thenReturn(Collections.singletonList(users));
        // Search the users
        restUsersMockMvc.perform(get("/api/_search/users?query=id:" + users.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(users.getId().intValue())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Users.class);
        Users users1 = new Users();
        users1.setId(1L);
        Users users2 = new Users();
        users2.setId(users1.getId());
        assertThat(users1).isEqualTo(users2);
        users2.setId(2L);
        assertThat(users1).isNotEqualTo(users2);
        users1.setId(null);
        assertThat(users1).isNotEqualTo(users2);
    }
}
