package org.jhipster.thibaut.thelibrary.service.impl;

import org.jhipster.thibaut.thelibrary.service.LibraryService;
import org.jhipster.thibaut.thelibrary.domain.Library;
import org.jhipster.thibaut.thelibrary.repository.LibraryRepository;
import org.jhipster.thibaut.thelibrary.repository.search.LibrarySearchRepository;
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
 * Service Implementation for managing Library.
 */
@Service
@Transactional
public class LibraryServiceImpl implements LibraryService {

    private final Logger log = LoggerFactory.getLogger(LibraryServiceImpl.class);

    private final LibraryRepository libraryRepository;

    private final LibrarySearchRepository librarySearchRepository;

    public LibraryServiceImpl(LibraryRepository libraryRepository, LibrarySearchRepository librarySearchRepository) {
        this.libraryRepository = libraryRepository;
        this.librarySearchRepository = librarySearchRepository;
    }

    /**
     * Save a library.
     *
     * @param library the entity to save
     * @return the persisted entity
     */
    @Override
    public Library save(Library library) {
        log.debug("Request to save Library : {}", library);
        Library result = libraryRepository.save(library);
        librarySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the libraries.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Library> findAll() {
        log.debug("Request to get all Libraries");
        return libraryRepository.findAll();
    }



    /**
     *  get all the libraries where Coordinates is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Library> findAllWhereCoordinatesIsNull() {
        log.debug("Request to get all libraries where Coordinates is null");
        return StreamSupport
            .stream(libraryRepository.findAll().spliterator(), false)
            .filter(library -> library.getCoordinates() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one library by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Library> findOne(Long id) {
        log.debug("Request to get Library : {}", id);
        return libraryRepository.findById(id);
    }

    /**
     * Delete the library by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Library : {}", id);
        libraryRepository.deleteById(id);
        librarySearchRepository.deleteById(id);
    }

    /**
     * Search for the library corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Library> search(String query) {
        log.debug("Request to search Libraries for query {}", query);
        return StreamSupport
            .stream(librarySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
