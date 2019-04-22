package org.jhipster.thibaut.thelibrary.service;

import org.jhipster.thibaut.thelibrary.domain.Editor;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Editor.
 */
public interface EditorService {

    /**
     * Save a editor.
     *
     * @param editor the entity to save
     * @return the persisted entity
     */
    Editor save(Editor editor);

    /**
     * Get all the editors.
     *
     * @return the list of entities
     */
    List<Editor> findAll();
    /**
     * Get all the EditorDTO where Coordinates is null.
     *
     * @return the list of entities
     */
    List<Editor> findAllWhereCoordinatesIsNull();


    /**
     * Get the "id" editor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Editor> findOne(Long id);

    /**
     * Delete the "id" editor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the editor corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Editor> search(String query);
}
