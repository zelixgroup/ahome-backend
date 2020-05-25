package com.zelix.ahome.service;

import com.zelix.ahome.domain.WorkCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkCategory> findAll(Pageable pageable);


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
