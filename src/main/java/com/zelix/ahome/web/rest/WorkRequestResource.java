package com.zelix.ahome.web.rest;

import com.zelix.ahome.domain.WorkRequest;
import com.zelix.ahome.service.WorkRequestService;
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
 * REST controller for managing {@link com.zelix.ahome.domain.WorkRequest}.
 */
@RestController
@RequestMapping("/api")
public class WorkRequestResource {

    private final Logger log = LoggerFactory.getLogger(WorkRequestResource.class);

    private static final String ENTITY_NAME = "workRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkRequestService workRequestService;

    public WorkRequestResource(WorkRequestService workRequestService) {
        this.workRequestService = workRequestService;
    }

    /**
     * {@code POST  /work-requests} : Create a new workRequest.
     *
     * @param workRequest the workRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workRequest, or with status {@code 400 (Bad Request)} if the workRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-requests")
    public ResponseEntity<WorkRequest> createWorkRequest(@RequestBody WorkRequest workRequest) throws URISyntaxException {
        log.debug("REST request to save WorkRequest : {}", workRequest);
        if (workRequest.getId() != null) {
            throw new BadRequestAlertException("A new workRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkRequest result = workRequestService.save(workRequest);
        return ResponseEntity.created(new URI("/api/work-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-requests} : Updates an existing workRequest.
     *
     * @param workRequest the workRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workRequest,
     * or with status {@code 400 (Bad Request)} if the workRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-requests")
    public ResponseEntity<WorkRequest> updateWorkRequest(@RequestBody WorkRequest workRequest) throws URISyntaxException {
        log.debug("REST request to update WorkRequest : {}", workRequest);
        if (workRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkRequest result = workRequestService.save(workRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-requests} : get all the workRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workRequests in body.
     */
    @GetMapping("/work-requests")
    public ResponseEntity<List<WorkRequest>> getAllWorkRequests(Pageable pageable) {
        log.debug("REST request to get a page of WorkRequests");
        Page<WorkRequest> page = workRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-requests/:id} : get the "id" workRequest.
     *
     * @param id the id of the workRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-requests/{id}")
    public ResponseEntity<WorkRequest> getWorkRequest(@PathVariable Long id) {
        log.debug("REST request to get WorkRequest : {}", id);
        Optional<WorkRequest> workRequest = workRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workRequest);
    }

    /**
     * {@code DELETE  /work-requests/:id} : delete the "id" workRequest.
     *
     * @param id the id of the workRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-requests/{id}")
    public ResponseEntity<Void> deleteWorkRequest(@PathVariable Long id) {
        log.debug("REST request to delete WorkRequest : {}", id);

        workRequestService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
