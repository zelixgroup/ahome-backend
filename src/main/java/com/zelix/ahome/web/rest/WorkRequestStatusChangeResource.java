package com.zelix.ahome.web.rest;

import com.zelix.ahome.domain.WorkRequestStatusChange;
import com.zelix.ahome.service.WorkRequestStatusChangeService;
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
 * REST controller for managing {@link com.zelix.ahome.domain.WorkRequestStatusChange}.
 */
@RestController
@RequestMapping("/api")
public class WorkRequestStatusChangeResource {

    private final Logger log = LoggerFactory.getLogger(WorkRequestStatusChangeResource.class);

    private static final String ENTITY_NAME = "workRequestStatusChange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkRequestStatusChangeService workRequestStatusChangeService;

    public WorkRequestStatusChangeResource(WorkRequestStatusChangeService workRequestStatusChangeService) {
        this.workRequestStatusChangeService = workRequestStatusChangeService;
    }

    /**
     * {@code POST  /work-request-status-changes} : Create a new workRequestStatusChange.
     *
     * @param workRequestStatusChange the workRequestStatusChange to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workRequestStatusChange, or with status {@code 400 (Bad Request)} if the workRequestStatusChange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-request-status-changes")
    public ResponseEntity<WorkRequestStatusChange> createWorkRequestStatusChange(@RequestBody WorkRequestStatusChange workRequestStatusChange) throws URISyntaxException {
        log.debug("REST request to save WorkRequestStatusChange : {}", workRequestStatusChange);
        if (workRequestStatusChange.getId() != null) {
            throw new BadRequestAlertException("A new workRequestStatusChange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkRequestStatusChange result = workRequestStatusChangeService.save(workRequestStatusChange);
        return ResponseEntity.created(new URI("/api/work-request-status-changes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-request-status-changes} : Updates an existing workRequestStatusChange.
     *
     * @param workRequestStatusChange the workRequestStatusChange to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workRequestStatusChange,
     * or with status {@code 400 (Bad Request)} if the workRequestStatusChange is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workRequestStatusChange couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-request-status-changes")
    public ResponseEntity<WorkRequestStatusChange> updateWorkRequestStatusChange(@RequestBody WorkRequestStatusChange workRequestStatusChange) throws URISyntaxException {
        log.debug("REST request to update WorkRequestStatusChange : {}", workRequestStatusChange);
        if (workRequestStatusChange.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkRequestStatusChange result = workRequestStatusChangeService.save(workRequestStatusChange);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workRequestStatusChange.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-request-status-changes} : get all the workRequestStatusChanges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workRequestStatusChanges in body.
     */
    @GetMapping("/work-request-status-changes")
    public ResponseEntity<List<WorkRequestStatusChange>> getAllWorkRequestStatusChanges(Pageable pageable) {
        log.debug("REST request to get a page of WorkRequestStatusChanges");
        Page<WorkRequestStatusChange> page = workRequestStatusChangeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-request-status-changes/:id} : get the "id" workRequestStatusChange.
     *
     * @param id the id of the workRequestStatusChange to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workRequestStatusChange, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-request-status-changes/{id}")
    public ResponseEntity<WorkRequestStatusChange> getWorkRequestStatusChange(@PathVariable Long id) {
        log.debug("REST request to get WorkRequestStatusChange : {}", id);
        Optional<WorkRequestStatusChange> workRequestStatusChange = workRequestStatusChangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workRequestStatusChange);
    }

    /**
     * {@code DELETE  /work-request-status-changes/:id} : delete the "id" workRequestStatusChange.
     *
     * @param id the id of the workRequestStatusChange to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-request-status-changes/{id}")
    public ResponseEntity<Void> deleteWorkRequestStatusChange(@PathVariable Long id) {
        log.debug("REST request to delete WorkRequestStatusChange : {}", id);

        workRequestStatusChangeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
