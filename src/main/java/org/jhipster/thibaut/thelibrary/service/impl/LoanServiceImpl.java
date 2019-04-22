package org.jhipster.thibaut.thelibrary.service.impl;

import org.jhipster.thibaut.thelibrary.service.LoanService;
import org.jhipster.thibaut.thelibrary.domain.Loan;
import org.jhipster.thibaut.thelibrary.repository.LoanRepository;
import org.jhipster.thibaut.thelibrary.repository.search.LoanSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Loan.
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);

    private final LoanRepository loanRepository;

    private final LoanSearchRepository loanSearchRepository;

    public LoanServiceImpl(LoanRepository loanRepository, LoanSearchRepository loanSearchRepository) {
        this.loanRepository = loanRepository;
        this.loanSearchRepository = loanSearchRepository;
    }

    /**
     * Save a loan.
     *
     * @param loan the entity to save
     * @return the persisted entity
     */
    @Override
    public Loan save(Loan loan) {
        log.debug("Request to save Loan : {}", loan);
        Loan result = loanRepository.save(loan);
        loanSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the loans.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Loan> findAll() {
        log.debug("Request to get all Loans");
        return loanRepository.findAll();
    }


    /**
     * Get one loan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Loan> findOne(Long id) {
        log.debug("Request to get Loan : {}", id);
        return loanRepository.findById(id);
    }

    /**
     * Delete the loan by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Loan : {}", id);
        loanRepository.deleteById(id);
        loanSearchRepository.deleteById(id);
    }

    /**
     * Search for the loan corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Loan> search(String query) {
        log.debug("Request to search Loans for query {}", query);
        return StreamSupport
            .stream(loanSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
