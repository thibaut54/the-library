package org.jhipster.thibaut.thelibrary.web.rest;

import org.jhipster.thibaut.thelibrary.TheLibraryApp;

import org.jhipster.thibaut.thelibrary.domain.Loan;
import org.jhipster.thibaut.thelibrary.repository.LoanRepository;
import org.jhipster.thibaut.thelibrary.repository.search.LoanSearchRepository;
import org.jhipster.thibaut.thelibrary.service.LoanService;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the LoanResource REST controller.
 *
 * @see LoanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheLibraryApp.class)
public class LoanResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_RETURNED = false;
    private static final Boolean UPDATED_RETURNED = true;

    private static final LocalDate DEFAULT_INITIAL_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INITIAL_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXTENDED_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXTENDED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanService loanService;

    /**
     * This repository is mocked in the org.jhipster.thibaut.thelibrary.repository.search test package.
     *
     * @see org.jhipster.thibaut.thelibrary.repository.search.LoanSearchRepositoryMockConfiguration
     */
    @Autowired
    private LoanSearchRepository mockLoanSearchRepository;

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

    private MockMvc restLoanMockMvc;

    private Loan loan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoanResource loanResource = new LoanResource(loanService);
        this.restLoanMockMvc = MockMvcBuilders.standaloneSetup(loanResource)
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
    public static Loan createEntity(EntityManager em) {
        Loan loan = new Loan()
            .startDate(DEFAULT_START_DATE)
            .returned(DEFAULT_RETURNED)
            .initialEndDate(DEFAULT_INITIAL_END_DATE)
            .extendedEndDate(DEFAULT_EXTENDED_END_DATE);
        return loan;
    }

    @Before
    public void initTest() {
        loan = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoan() throws Exception {
        int databaseSizeBeforeCreate = loanRepository.findAll().size();

        // Create the Loan
        restLoanMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loan)))
            .andExpect(status().isCreated());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeCreate + 1);
        Loan testLoan = loanList.get(loanList.size() - 1);
        assertThat(testLoan.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testLoan.isReturned()).isEqualTo(DEFAULT_RETURNED);
        assertThat(testLoan.getInitialEndDate()).isEqualTo(DEFAULT_INITIAL_END_DATE);
        assertThat(testLoan.getExtendedEndDate()).isEqualTo(DEFAULT_EXTENDED_END_DATE);

        // Validate the Loan in Elasticsearch
        verify(mockLoanSearchRepository, times(1)).save(testLoan);
    }

    @Test
    @Transactional
    public void createLoanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loanRepository.findAll().size();

        // Create the Loan with an existing ID
        loan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loan)))
            .andExpect(status().isBadRequest());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeCreate);

        // Validate the Loan in Elasticsearch
        verify(mockLoanSearchRepository, times(0)).save(loan);
    }

    @Test
    @Transactional
    public void getAllLoans() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList
        restLoanMockMvc.perform(get("/api/loans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loan.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].returned").value(hasItem(DEFAULT_RETURNED.booleanValue())))
            .andExpect(jsonPath("$.[*].initialEndDate").value(hasItem(DEFAULT_INITIAL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].extendedEndDate").value(hasItem(DEFAULT_EXTENDED_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getLoan() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get the loan
        restLoanMockMvc.perform(get("/api/loans/{id}", loan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loan.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.returned").value(DEFAULT_RETURNED.booleanValue()))
            .andExpect(jsonPath("$.initialEndDate").value(DEFAULT_INITIAL_END_DATE.toString()))
            .andExpect(jsonPath("$.extendedEndDate").value(DEFAULT_EXTENDED_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLoan() throws Exception {
        // Get the loan
        restLoanMockMvc.perform(get("/api/loans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoan() throws Exception {
        // Initialize the database
        loanService.save(loan);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockLoanSearchRepository);

        int databaseSizeBeforeUpdate = loanRepository.findAll().size();

        // Update the loan
        Loan updatedLoan = loanRepository.findById(loan.getId()).get();
        // Disconnect from session so that the updates on updatedLoan are not directly saved in db
        em.detach(updatedLoan);
        updatedLoan
            .startDate(UPDATED_START_DATE)
            .returned(UPDATED_RETURNED)
            .initialEndDate(UPDATED_INITIAL_END_DATE)
            .extendedEndDate(UPDATED_EXTENDED_END_DATE);

        restLoanMockMvc.perform(put("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLoan)))
            .andExpect(status().isOk());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeUpdate);
        Loan testLoan = loanList.get(loanList.size() - 1);
        assertThat(testLoan.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testLoan.isReturned()).isEqualTo(UPDATED_RETURNED);
        assertThat(testLoan.getInitialEndDate()).isEqualTo(UPDATED_INITIAL_END_DATE);
        assertThat(testLoan.getExtendedEndDate()).isEqualTo(UPDATED_EXTENDED_END_DATE);

        // Validate the Loan in Elasticsearch
        verify(mockLoanSearchRepository, times(1)).save(testLoan);
    }

    @Test
    @Transactional
    public void updateNonExistingLoan() throws Exception {
        int databaseSizeBeforeUpdate = loanRepository.findAll().size();

        // Create the Loan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanMockMvc.perform(put("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loan)))
            .andExpect(status().isBadRequest());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Loan in Elasticsearch
        verify(mockLoanSearchRepository, times(0)).save(loan);
    }

    @Test
    @Transactional
    public void deleteLoan() throws Exception {
        // Initialize the database
        loanService.save(loan);

        int databaseSizeBeforeDelete = loanRepository.findAll().size();

        // Delete the loan
        restLoanMockMvc.perform(delete("/api/loans/{id}", loan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Loan in Elasticsearch
        verify(mockLoanSearchRepository, times(1)).deleteById(loan.getId());
    }

    @Test
    @Transactional
    public void searchLoan() throws Exception {
        // Initialize the database
        loanService.save(loan);
        when(mockLoanSearchRepository.search(queryStringQuery("id:" + loan.getId())))
            .thenReturn(Collections.singletonList(loan));
        // Search the loan
        restLoanMockMvc.perform(get("/api/_search/loans?query=id:" + loan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loan.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].returned").value(hasItem(DEFAULT_RETURNED.booleanValue())))
            .andExpect(jsonPath("$.[*].initialEndDate").value(hasItem(DEFAULT_INITIAL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].extendedEndDate").value(hasItem(DEFAULT_EXTENDED_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Loan.class);
        Loan loan1 = new Loan();
        loan1.setId(1L);
        Loan loan2 = new Loan();
        loan2.setId(loan1.getId());
        assertThat(loan1).isEqualTo(loan2);
        loan2.setId(2L);
        assertThat(loan1).isNotEqualTo(loan2);
        loan1.setId(null);
        assertThat(loan1).isNotEqualTo(loan2);
    }
}
