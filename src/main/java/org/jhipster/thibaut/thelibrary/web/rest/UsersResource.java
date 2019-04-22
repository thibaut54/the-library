package org.jhipster.thibaut.thelibrary.web.rest;
import org.jhipster.thibaut.thelibrary.domain.Users;
import org.jhipster.thibaut.thelibrary.service.UsersService;
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
 * REST controller for managing Users.
 */
@RestController
@RequestMapping("/api")
public class UsersResource {

    private final Logger log = LoggerFactory.getLogger(UsersResource.class);

    private static final String ENTITY_NAME = "users";

    private final UsersService usersService;

    public UsersResource(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * POST  /users : Create a new users.
     *
     * @param users the users to create
     * @return the ResponseEntity with status 201 (Created) and with body the new users, or with status 400 (Bad Request) if the users has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    public ResponseEntity<Users> createUsers(@RequestBody Users users) throws URISyntaxException {
        log.debug("REST request to save Users : {}", users);
        if (users.getId() != null) {
            throw new BadRequestAlertException("A new users cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Users result = usersService.save(users);
        return ResponseEntity.created(new URI("/api/users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /users : Updates an existing users.
     *
     * @param users the users to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated users,
     * or with status 400 (Bad Request) if the users is not valid,
     * or with status 500 (Internal Server Error) if the users couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/users")
    public ResponseEntity<Users> updateUsers(@RequestBody Users users) throws URISyntaxException {
        log.debug("REST request to update Users : {}", users);
        if (users.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Users result = usersService.save(users);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, users.getId().toString()))
            .body(result);
    }

    /**
     * GET  /users : get all the users.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of users in body
     */
    @GetMapping("/users")
    public List<Users> getAllUsers(@RequestParam(required = false) String filter,@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("coordinates-is-null".equals(filter)) {
            log.debug("REST request to get all Userss where coordinates is null");
            return usersService.findAllWhereCoordinatesIsNull();
        }
        log.debug("REST request to get all Users");
        return usersService.findAll();
    }

    /**
     * GET  /users/:id : get the "id" users.
     *
     * @param id the id of the users to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the users, or with status 404 (Not Found)
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUsers(@PathVariable Long id) {
        log.debug("REST request to get Users : {}", id);
        Optional<Users> users = usersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(users);
    }

    /**
     * DELETE  /users/:id : delete the "id" users.
     *
     * @param id the id of the users to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable Long id) {
        log.debug("REST request to delete Users : {}", id);
        usersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/users?query=:query : search for the users corresponding
     * to the query.
     *
     * @param query the query of the users search
     * @return the result of the search
     */
    @GetMapping("/_search/users")
    public List<Users> searchUsers(@RequestParam String query) {
        log.debug("REST request to search Users for query {}", query);
        return usersService.search(query);
    }

}
