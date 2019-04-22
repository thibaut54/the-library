package org.jhipster.thibaut.thelibrary.service;

import org.jhipster.thibaut.thelibrary.domain.Users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Users.
 */
public interface UsersService {

    /**
     * Save a users.
     *
     * @param users the entity to save
     * @return the persisted entity
     */
    Users save(Users users);

    /**
     * Get all the users.
     *
     * @return the list of entities
     */
    List<Users> findAll();
    /**
     * Get all the UsersDTO where Coordinates is null.
     *
     * @return the list of entities
     */
    List<Users> findAllWhereCoordinatesIsNull();

    /**
     * Get all the Users with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Users> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" users.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Users> findOne(Long id);

    /**
     * Delete the "id" users.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the users corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Users> search(String query);
}
