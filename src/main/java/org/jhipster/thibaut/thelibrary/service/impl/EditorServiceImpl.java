package org.jhipster.thibaut.thelibrary.service.impl;

import org.jhipster.thibaut.thelibrary.service.EditorService;
import org.jhipster.thibaut.thelibrary.domain.Editor;
import org.jhipster.thibaut.thelibrary.repository.EditorRepository;
import org.jhipster.thibaut.thelibrary.repository.search.EditorSearchRepository;
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
 * Service Implementation for managing Editor.
 */
@Service
@Transactional
public class EditorServiceImpl implements EditorService {

    private final Logger log = LoggerFactory.getLogger(EditorServiceImpl.class);

    private final EditorRepository editorRepository;

    private final EditorSearchRepository editorSearchRepository;

    public EditorServiceImpl(EditorRepository editorRepository, EditorSearchRepository editorSearchRepository) {
        this.editorRepository = editorRepository;
        this.editorSearchRepository = editorSearchRepository;
    }

    /**
     * Save a editor.
     *
     * @param editor the entity to save
     * @return the persisted entity
     */
    @Override
    public Editor save(Editor editor) {
        log.debug("Request to save Editor : {}", editor);
        Editor result = editorRepository.save(editor);
        editorSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the editors.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Editor> findAll() {
        log.debug("Request to get all Editors");
        return editorRepository.findAll();
    }



    /**
     *  get all the editors where Coordinates is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Editor> findAllWhereCoordinatesIsNull() {
        log.debug("Request to get all editors where Coordinates is null");
        return StreamSupport
            .stream(editorRepository.findAll().spliterator(), false)
            .filter(editor -> editor.getCoordinates() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one editor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Editor> findOne(Long id) {
        log.debug("Request to get Editor : {}", id);
        return editorRepository.findById(id);
    }

    /**
     * Delete the editor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Editor : {}", id);
        editorRepository.deleteById(id);
        editorSearchRepository.deleteById(id);
    }

    /**
     * Search for the editor corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Editor> search(String query) {
        log.debug("Request to search Editors for query {}", query);
        return StreamSupport
            .stream(editorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
