package com.zelix.ahome.service;

import com.zelix.ahome.domain.WorkReview;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link WorkReview}.
 */
public interface WorkReviewService {

    /**
     * Save a workReview.
     *
     * @param workReview the entity to save.
     * @return the persisted entity.
     */
    WorkReview save(WorkReview workReview);

    /**
     * Get all the workReviews.
     *
     * @return the list of entities.
     */
    List<WorkReview> findAll();


    /**
     * Get the "id" workReview.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkReview> findOne(Long id);

    /**
     * Delete the "id" workReview.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
