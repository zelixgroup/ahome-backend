package com.zelix.ahome.service;

import com.zelix.ahome.domain.WorkRequestStatusChange;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkRequestStatusChange> findAll(Pageable pageable);


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
