package com.zelix.ahome.service.impl;

import com.zelix.ahome.service.WorkReviewCommentService;
import com.zelix.ahome.domain.WorkReviewComment;
import com.zelix.ahome.repository.WorkReviewCommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkReviewComment}.
 */
@Service
@Transactional
public class WorkReviewCommentServiceImpl implements WorkReviewCommentService {

    private final Logger log = LoggerFactory.getLogger(WorkReviewCommentServiceImpl.class);

    private final WorkReviewCommentRepository workReviewCommentRepository;

    public WorkReviewCommentServiceImpl(WorkReviewCommentRepository workReviewCommentRepository) {
        this.workReviewCommentRepository = workReviewCommentRepository;
    }

    /**
     * Save a workReviewComment.
     *
     * @param workReviewComment the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WorkReviewComment save(WorkReviewComment workReviewComment) {
        log.debug("Request to save WorkReviewComment : {}", workReviewComment);
        return workReviewCommentRepository.save(workReviewComment);
    }

    /**
     * Get all the workReviewComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkReviewComment> findAll(Pageable pageable) {
        log.debug("Request to get all WorkReviewComments");
        return workReviewCommentRepository.findAll(pageable);
    }


    /**
     * Get one workReviewComment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkReviewComment> findOne(Long id) {
        log.debug("Request to get WorkReviewComment : {}", id);
        return workReviewCommentRepository.findById(id);
    }

    /**
     * Delete the workReviewComment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkReviewComment : {}", id);

        workReviewCommentRepository.deleteById(id);
    }
}
