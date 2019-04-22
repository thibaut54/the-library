package org.jhipster.thibaut.thelibrary.service;

import org.jhipster.thibaut.thelibrary.domain.Loan;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Loan.
 */
public interface LoanService {

    /**
     * Save a loan.
     *
     * @param loan the entity to save
     * @return the persisted entity
     */
    Loan save(Loan loan);

    /**
     * Get all the loans.
     *
     * @return the list of entities
     */
    List<Loan> findAll();


    /**
     * Get the "id" loan.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Loan> findOne(Long id);

    /**
     * Delete the "id" loan.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the loan corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Loan> search(String query);
}
