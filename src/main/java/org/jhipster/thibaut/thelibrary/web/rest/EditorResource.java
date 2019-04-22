package org.jhipster.thibaut.thelibrary.web.rest;
import org.jhipster.thibaut.thelibrary.domain.Editor;
import org.jhipster.thibaut.thelibrary.service.EditorService;
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
 * REST controller for managing Editor.
 */
@RestController
@RequestMapping("/api")
public class EditorResource {

    private final Logger log = LoggerFactory.getLogger(EditorResource.class);

    private static final String ENTITY_NAME = "editor";

    private final EditorService editorService;

    public EditorResource(EditorService editorService) {
        this.editorService = editorService;
    }

    /**
     * POST  /editors : Create a new editor.
     *
     * @param editor the editor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new editor, or with status 400 (Bad Request) if the editor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/editors")
    public ResponseEntity<Editor> createEditor(@RequestBody Editor editor) throws URISyntaxException {
        log.debug("REST request to save Editor : {}", editor);
        if (editor.getId() != null) {
            throw new BadRequestAlertException("A new editor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Editor result = editorService.save(editor);
        return ResponseEntity.created(new URI("/api/editors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /editors : Updates an existing editor.
     *
     * @param editor the editor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated editor,
     * or with status 400 (Bad Request) if the editor is not valid,
     * or with status 500 (Internal Server Error) if the editor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/editors")
    public ResponseEntity<Editor> updateEditor(@RequestBody Editor editor) throws URISyntaxException {
        log.debug("REST request to update Editor : {}", editor);
        if (editor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Editor result = editorService.save(editor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, editor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /editors : get all the editors.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of editors in body
     */
    @GetMapping("/editors")
    public List<Editor> getAllEditors(@RequestParam(required = false) String filter) {
        if ("coordinates-is-null".equals(filter)) {
            log.debug("REST request to get all Editors where coordinates is null");
            return editorService.findAllWhereCoordinatesIsNull();
        }
        log.debug("REST request to get all Editors");
        return editorService.findAll();
    }

    /**
     * GET  /editors/:id : get the "id" editor.
     *
     * @param id the id of the editor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the editor, or with status 404 (Not Found)
     */
    @GetMapping("/editors/{id}")
    public ResponseEntity<Editor> getEditor(@PathVariable Long id) {
        log.debug("REST request to get Editor : {}", id);
        Optional<Editor> editor = editorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(editor);
    }

    /**
     * DELETE  /editors/:id : delete the "id" editor.
     *
     * @param id the id of the editor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/editors/{id}")
    public ResponseEntity<Void> deleteEditor(@PathVariable Long id) {
        log.debug("REST request to delete Editor : {}", id);
        editorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/editors?query=:query : search for the editor corresponding
     * to the query.
     *
     * @param query the query of the editor search
     * @return the result of the search
     */
    @GetMapping("/_search/editors")
    public List<Editor> searchEditors(@RequestParam String query) {
        log.debug("REST request to search Editors for query {}", query);
        return editorService.search(query);
    }

}
