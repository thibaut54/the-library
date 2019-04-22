package org.jhipster.thibaut.thelibrary.web.rest;
import org.jhipster.thibaut.thelibrary.domain.Library;
import org.jhipster.thibaut.thelibrary.service.LibraryService;
import org.jhipster.thibaut.thelibrary.web.rest.errors.BadRequestAlertException;
import org.jhipster.thibaut.thelibrary.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Library.
 */
@RestController
@RequestMapping("/api")
public class LibraryResource {

    private final Logger log = LoggerFactory.getLogger(LibraryResource.class);

    private static final String ENTITY_NAME = "library";

    private final LibraryService libraryService;

    public LibraryResource(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     * POST  /libraries : Create a new library.
     *
     * @param library the library to create
     * @return the ResponseEntity with status 201 (Created) and with body the new library, or with status 400 (Bad Request) if the library has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/libraries")
    public ResponseEntity<Library> createLibrary(@RequestBody Library library) throws URISyntaxException {
        log.debug("REST request to save Library : {}", library);
        if (library.getId() != null) {
            throw new BadRequestAlertException("A new library cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Library result = libraryService.save(library);
        return ResponseEntity.created(new URI("/api/libraries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /libraries : Updates an existing library.
     *
     * @param library the library to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated library,
     * or with status 400 (Bad Request) if the library is not valid,
     * or with status 500 (Internal Server Error) if the library couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/libraries")
    public ResponseEntity<Library> updateLibrary(@RequestBody Library library) throws URISyntaxException {
        log.debug("REST request to update Library : {}", library);
        if (library.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Library result = libraryService.save(library);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, library.getId().toString()))
            .body(result);
    }

    /**
     * GET  /libraries : get all the libraries.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of libraries in body
     */
    @GetMapping("/libraries")
    public List<Library> getAllLibraries(@RequestParam(required = false) String filter) {
        if ("coordinates-is-null".equals(filter)) {
            log.debug("REST request to get all Librarys where coordinates is null");
            return libraryService.findAllWhereCoordinatesIsNull();
        }
        log.debug("REST request to get all Libraries");
        return libraryService.findAll();
    }

    /**
     * GET  /libraries/:id : get the "id" library.
     *
     * @param id the id of the library to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the library, or with status 404 (Not Found)
     */
    @GetMapping("/libraries/{id}")
    public ResponseEntity<Library> getLibrary(@PathVariable Long id) {
        log.debug("REST request to get Library : {}", id);
        Optional<Library> library = libraryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(library);
    }

    /**
     * DELETE  /libraries/:id : delete the "id" library.
     *
     * @param id the id of the library to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/libraries/{id}")
    public ResponseEntity<Void> deleteLibrary(@PathVariable Long id) {
        log.debug("REST request to delete Library : {}", id);
        libraryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/libraries?query=:query : search for the library corresponding
     * to the query.
     *
     * @param query the query of the library search
     * @return the result of the search
     */
    @GetMapping("/_search/libraries")
    public List<Library> searchLibraries(@RequestParam String query) {
        log.debug("REST request to search Libraries for query {}", query);
        return libraryService.search(query);
    }

}
