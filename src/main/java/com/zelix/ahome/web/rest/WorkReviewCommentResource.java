package com.zelix.ahome.web.rest;

import com.zelix.ahome.domain.WorkReviewComment;
import com.zelix.ahome.service.WorkReviewCommentService;
import com.zelix.ahome.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.zelix.ahome.domain.WorkReviewComment}.
 */
@RestController
@RequestMapping("/api")
public class WorkReviewCommentResource {

    private final Logger log = LoggerFactory.getLogger(WorkReviewCommentResource.class);

    private static final String ENTITY_NAME = "workReviewComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkReviewCommentService workReviewCommentService;

    public WorkReviewCommentResource(WorkReviewCommentService workReviewCommentService) {
        this.workReviewCommentService = workReviewCommentService;
    }

    /**
     * {@code POST  /work-review-comments} : Create a new workReviewComment.
     *
     * @param workReviewComment the workReviewComment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workReviewComment, or with status {@code 400 (Bad Request)} if the workReviewComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-review-comments")
    public ResponseEntity<WorkReviewComment> createWorkReviewComment(@RequestBody WorkReviewComment workReviewComment) throws URISyntaxException {
        log.debug("REST request to save WorkReviewComment : {}", workReviewComment);
        if (workReviewComment.getId() != null) {
            throw new BadRequestAlertException("A new workReviewComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkReviewComment result = workReviewCommentService.save(workReviewComment);
        return ResponseEntity.created(new URI("/api/work-review-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-review-comments} : Updates an existing workReviewComment.
     *
     * @param workReviewComment the workReviewComment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workReviewComment,
     * or with status {@code 400 (Bad Request)} if the workReviewComment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workReviewComment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-review-comments")
    public ResponseEntity<WorkReviewComment> updateWorkReviewComment(@RequestBody WorkReviewComment workReviewComment) throws URISyntaxException {
        log.debug("REST request to update WorkReviewComment : {}", workReviewComment);
        if (workReviewComment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkReviewComment result = workReviewCommentService.save(workReviewComment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workReviewComment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-review-comments} : get all the workReviewComments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workReviewComments in body.
     */
    @GetMapping("/work-review-comments")
    public List<WorkReviewComment> getAllWorkReviewComments() {
        log.debug("REST request to get all WorkReviewComments");
        return workReviewCommentService.findAll();
    }

    /**
     * {@code GET  /work-review-comments/:id} : get the "id" workReviewComment.
     *
     * @param id the id of the workReviewComment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workReviewComment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-review-comments/{id}")
    public ResponseEntity<WorkReviewComment> getWorkReviewComment(@PathVariable Long id) {
        log.debug("REST request to get WorkReviewComment : {}", id);
        Optional<WorkReviewComment> workReviewComment = workReviewCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workReviewComment);
    }

    /**
     * {@code DELETE  /work-review-comments/:id} : delete the "id" workReviewComment.
     *
     * @param id the id of the workReviewComment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-review-comments/{id}")
    public ResponseEntity<Void> deleteWorkReviewComment(@PathVariable Long id) {
        log.debug("REST request to delete WorkReviewComment : {}", id);

        workReviewCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
