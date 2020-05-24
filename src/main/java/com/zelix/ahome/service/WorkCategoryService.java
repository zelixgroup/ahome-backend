package com.zelix.ahome.service;

import com.zelix.ahome.domain.WorkCategory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link WorkCategory}.
 */
public interface WorkCategoryService {

    /**
     * Save a workCategory.
     *
     * @param workCategory the entity to save.
     * @return the persisted entity.
     */
    WorkCategory save(WorkCategory workCategory);

    /**
     * Get all the workCategories.
     *
     * @return the list of entities.
     */
    List<WorkCategory> findAll();


    /**
     * Get the "id" workCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkCategory> findOne(Long id);

    /**
     * Delete the "id" workCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
