package org.jhipster.thibaut.thelibrary.web.rest;
import org.jhipster.thibaut.thelibrary.domain.Coordinates;
import org.jhipster.thibaut.thelibrary.service.CoordinatesService;
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
 * REST controller for managing Coordinates.
 */
@RestController
@RequestMapping("/api")
public class CoordinatesResource {

    private final Logger log = LoggerFactory.getLogger(CoordinatesResource.class);

    private static final String ENTITY_NAME = "coordinates";

    private final CoordinatesService coordinatesService;

    public CoordinatesResource(CoordinatesService coordinatesService) {
        this.coordinatesService = coordinatesService;
    }

    /**
     * POST  /coordinates : Create a new coordinates.
     *
     * @param coordinates the coordinates to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coordinates, or with status 400 (Bad Request) if the coordinates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coordinates")
    public ResponseEntity<Coordinates> createCoordinates(@RequestBody Coordinates coordinates) throws URISyntaxException {
        log.debug("REST request to save Coordinates : {}", coordinates);
        if (coordinates.getId() != null) {
            throw new BadRequestAlertException("A new coordinates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Coordinates result = coordinatesService.save(coordinates);
        return ResponseEntity.created(new URI("/api/coordinates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coordinates : Updates an existing coordinates.
     *
     * @param coordinates the coordinates to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coordinates,
     * or with status 400 (Bad Request) if the coordinates is not valid,
     * or with status 500 (Internal Server Error) if the coordinates couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coordinates")
    public ResponseEntity<Coordinates> updateCoordinates(@RequestBody Coordinates coordinates) throws URISyntaxException {
        log.debug("REST request to update Coordinates : {}", coordinates);
        if (coordinates.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Coordinates result = coordinatesService.save(coordinates);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coordinates.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coordinates : get all the coordinates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of coordinates in body
     */
    @GetMapping("/coordinates")
    public List<Coordinates> getAllCoordinates() {
        log.debug("REST request to get all Coordinates");
        return coordinatesService.findAll();
    }

    /**
     * GET  /coordinates/:id : get the "id" coordinates.
     *
     * @param id the id of the coordinates to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coordinates, or with status 404 (Not Found)
     */
    @GetMapping("/coordinates/{id}")
    public ResponseEntity<Coordinates> getCoordinates(@PathVariable Long id) {
        log.debug("REST request to get Coordinates : {}", id);
        Optional<Coordinates> coordinates = coordinatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coordinates);
    }

    /**
     * DELETE  /coordinates/:id : delete the "id" coordinates.
     *
     * @param id the id of the coordinates to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coordinates/{id}")
    public ResponseEntity<Void> deleteCoordinates(@PathVariable Long id) {
        log.debug("REST request to delete Coordinates : {}", id);
        coordinatesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/coordinates?query=:query : search for the coordinates corresponding
     * to the query.
     *
     * @param query the query of the coordinates search
     * @return the result of the search
     */
    @GetMapping("/_search/coordinates")
    public List<Coordinates> searchCoordinates(@RequestParam String query) {
        log.debug("REST request to search Coordinates for query {}", query);
        return coordinatesService.search(query);
    }

}
