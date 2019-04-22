package org.jhipster.thibaut.thelibrary.service.impl;

import org.jhipster.thibaut.thelibrary.service.AuthorService;
import org.jhipster.thibaut.thelibrary.domain.Author;
import org.jhipster.thibaut.thelibrary.repository.AuthorRepository;
import org.jhipster.thibaut.thelibrary.repository.search.AuthorSearchRepository;
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
 * Service Implementation for managing Author.
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;

    private final AuthorSearchRepository authorSearchRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorSearchRepository authorSearchRepository) {
        this.authorRepository = authorRepository;
        this.authorSearchRepository = authorSearchRepository;
    }

    /**
     * Save a author.
     *
     * @param author the entity to save
     * @return the persisted entity
     */
    @Override
    public Author save(Author author) {
        log.debug("Request to save Author : {}", author);
        Author result = authorRepository.save(author);
        authorSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the authors.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        log.debug("Request to get all Authors");
        return authorRepository.findAll();
    }


    /**
     * Get one author by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findOne(Long id) {
        log.debug("Request to get Author : {}", id);
        return authorRepository.findById(id);
    }

    /**
     * Delete the author by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Author : {}", id);
        authorRepository.deleteById(id);
        authorSearchRepository.deleteById(id);
    }

    /**
     * Search for the author corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Author> search(String query) {
        log.debug("Request to search Authors for query {}", query);
        return StreamSupport
            .stream(authorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
