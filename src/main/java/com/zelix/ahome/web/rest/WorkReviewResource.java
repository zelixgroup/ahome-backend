package com.zelix.ahome.web.rest;

import com.zelix.ahome.domain.WorkReview;
import com.zelix.ahome.service.WorkReviewService;
import com.zelix.ahome.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.zelix.ahome.domain.WorkReview}.
 */
@RestController
@RequestMapping("/api")
public class WorkReviewResource {

    private final Logger log = LoggerFactory.getLogger(WorkReviewResource.class);

    private static final String ENTITY_NAME = "workReview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkReviewService workReviewService;

    public WorkReviewResource(WorkReviewService workReviewService) {
        this.workReviewService = workReviewService;
    }

    /**
     * {@code POST  /work-reviews} : Create a new workReview.
     *
     * @param workReview the workReview to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workReview, or with status {@code 400 (Bad Request)} if the workReview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-reviews")
    public ResponseEntity<WorkReview> createWorkReview(@RequestBody WorkReview workReview) throws URISyntaxException {
        log.debug("REST request to save WorkReview : {}", workReview);
        if (workReview.getId() != null) {
            throw new BadRequestAlertException("A new workReview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkReview result = workReviewService.save(workReview);
        return ResponseEntity.created(new URI("/api/work-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-reviews} : Updates an existing workReview.
     *
     * @param workReview the workReview to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workReview,
     * or with status {@code 400 (Bad Request)} if the workReview is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workReview couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-reviews")
    public ResponseEntity<WorkReview> updateWorkReview(@RequestBody WorkReview workReview) throws URISyntaxException {
        log.debug("REST request to update WorkReview : {}", workReview);
        if (workReview.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkReview result = workReviewService.save(workReview);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workReview.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-reviews} : get all the workReviews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workReviews in body.
     */
    @GetMapping("/work-reviews")
    public ResponseEntity<List<WorkReview>> getAllWorkReviews(Pageable pageable) {
        log.debug("REST request to get a page of WorkReviews");
        Page<WorkReview> page = workReviewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-reviews/:id} : get the "id" workReview.
     *
     * @param id the id of the workReview to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workReview, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-reviews/{id}")
    public ResponseEntity<WorkReview> getWorkReview(@PathVariable Long id) {
        log.debug("REST request to get WorkReview : {}", id);
        Optional<WorkReview> workReview = workReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workReview);
    }

    /**
     * {@code DELETE  /work-reviews/:id} : delete the "id" workReview.
     *
     * @param id the id of the workReview to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-reviews/{id}")
    public ResponseEntity<Void> deleteWorkReview(@PathVariable Long id) {
        log.debug("REST request to delete WorkReview : {}", id);

        workReviewService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
