package com.zelix.ahome.service;

import com.zelix.ahome.domain.WorkRequest;

import java.util.List;
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
     * @return the list of entities.
     */
    List<WorkRequest> findAll();


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
