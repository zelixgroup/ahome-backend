package com.zelix.ahome.service;

import com.zelix.ahome.domain.WorkRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link WorkRequest}.
 */
public interface WorkRequestService {

    /**
     * Save a workRequest.
     *
     * @param workRequest the entity to save.
     * @return the persisted entity.
     */
    WorkRequest save(WorkRequest workRequest);

    /**
     * Get all the workRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkRequest> findAll(Pageable pageable);


    /**
     * Get the "id" workRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkRequest> findOne(Long id);

    /**
     * Delete the "id" workRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
