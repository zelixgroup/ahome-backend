package com.zelix.ahome.service;

import com.zelix.ahome.domain.WorkReviewComment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkReviewComment> findAll(Pageable pageable);


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
