package com.zelix.ahome.service;

import com.zelix.ahome.domain.WorkRequestStatusChange;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link WorkRequestStatusChange}.
 */
public interface WorkRequestStatusChangeService {

    /**
     * Save a workRequestStatusChange.
     *
     * @param workRequestStatusChange the entity to save.
     * @return the persisted entity.
     */
    WorkRequestStatusChange save(WorkRequestStatusChange workRequestStatusChange);

    /**
     * Get all the workRequestStatusChanges.
     *
     * @return the list of entities.
     */
    List<WorkRequestStatusChange> findAll();


    /**
     * Get the "id" workRequestStatusChange.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkRequestStatusChange> findOne(Long id);

    /**
     * Delete the "id" workRequestStatusChange.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
