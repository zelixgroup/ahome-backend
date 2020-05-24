package com.zelix.ahome.service;

import com.zelix.ahome.domain.Work;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Work}.
 */
public interface WorkService {

    /**
     * Save a work.
     *
     * @param work the entity to save.
     * @return the persisted entity.
     */
    Work save(Work work);

    /**
     * Get all the works.
     *
     * @return the list of entities.
     */
    List<Work> findAll();


    /**
     * Get the "id" work.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Work> findOne(Long id);

    /**
     * Delete the "id" work.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
