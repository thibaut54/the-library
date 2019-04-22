package org.jhipster.thibaut.thelibrary.service;

import org.jhipster.thibaut.thelibrary.domain.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Book.
 */
public interface BookService {

    /**
     * Save a book.
     *
     * @param book the entity to save
     * @return the persisted entity
     */
    Book save(Book book);

    /**
     * Get all the books.
     *
     * @return the list of entities
     */
    List<Book> findAll();

    /**
     * Get all the Book with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Book> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" book.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Book> findOne(Long id);

    /**
     * Delete the "id" book.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the book corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Book> search(String query);
}
