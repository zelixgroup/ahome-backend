package com.zelix.ahome.service;

import com.zelix.ahome.domain.WorkReviewComment;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link WorkReviewComment}.
 */
public interface WorkReviewCommentService {

    /**
     * Save a workReviewComment.
     *
     * @param workReviewComment the entity to save.
     * @return the persisted entity.
     */
    WorkReviewComment save(WorkReviewComment workReviewComment);

    /**
     * Get all the workReviewComments.
     *
     * @return the list of entities.
     */
    List<WorkReviewComment> findAll();


    /**
     * Get the "id" workReviewComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkReviewComment> findOne(Long id);

    /**
     * Delete the "id" workReviewComment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
