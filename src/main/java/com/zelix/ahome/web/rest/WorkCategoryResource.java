package com.zelix.ahome.web.rest;

import com.zelix.ahome.domain.WorkCategory;
import com.zelix.ahome.service.WorkCategoryService;
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
 * REST controller for managing {@link com.zelix.ahome.domain.WorkCategory}.
 */
@RestController
@RequestMapping("/api")
public class WorkCategoryResource {

    private final Logger log = LoggerFactory.getLogger(WorkCategoryResource.class);

    private static final String ENTITY_NAME = "workCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkCategoryService workCategoryService;

    public WorkCategoryResource(WorkCategoryService workCategoryService) {
        this.workCategoryService = workCategoryService;
    }

    /**
     * {@code POST  /work-categories} : Create a new workCategory.
     *
     * @param workCategory the workCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workCategory, or with status {@code 400 (Bad Request)} if the workCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-categories")
    public ResponseEntity<WorkCategory> createWorkCategory(@RequestBody WorkCategory workCategory) throws URISyntaxException {
        log.debug("REST request to save WorkCategory : {}", workCategory);
        if (workCategory.getId() != null) {
            throw new BadRequestAlertException("A new workCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkCategory result = workCategoryService.save(workCategory);
        return ResponseEntity.created(new URI("/api/work-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-categories} : Updates an existing workCategory.
     *
     * @param workCategory the workCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workCategory,
     * or with status {@code 400 (Bad Request)} if the workCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-categories")
    public ResponseEntity<WorkCategory> updateWorkCategory(@RequestBody WorkCategory workCategory) throws URISyntaxException {
        log.debug("REST request to update WorkCategory : {}", workCategory);
        if (workCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkCategory result = workCategoryService.save(workCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-categories} : get all the workCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workCategories in body.
     */
    @GetMapping("/work-categories")
    public ResponseEntity<List<WorkCategory>> getAllWorkCategories(Pageable pageable) {
        log.debug("REST request to get a page of WorkCategories");
        Page<WorkCategory> page = workCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-categories/:id} : get the "id" workCategory.
     *
     * @param id the id of the workCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-categories/{id}")
    public ResponseEntity<WorkCategory> getWorkCategory(@PathVariable Long id) {
        log.debug("REST request to get WorkCategory : {}", id);
        Optional<WorkCategory> workCategory = workCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workCategory);
    }

    /**
     * {@code DELETE  /work-categories/:id} : delete the "id" workCategory.
     *
     * @param id the id of the workCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-categories/{id}")
    public ResponseEntity<Void> deleteWorkCategory(@PathVariable Long id) {
        log.debug("REST request to delete WorkCategory : {}", id);

        workCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
