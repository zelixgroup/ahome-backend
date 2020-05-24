package com.zelix.ahome.service.impl;

import com.zelix.ahome.service.WorkReviewService;
import com.zelix.ahome.domain.WorkReview;
import com.zelix.ahome.repository.WorkReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkReview}.
 */
@Service
@Transactional
public class WorkReviewServiceImpl implements WorkReviewService {

    private final Logger log = LoggerFactory.getLogger(WorkReviewServiceImpl.class);

    private final WorkReviewRepository workReviewRepository;

    public WorkReviewServiceImpl(WorkReviewRepository workReviewRepository) {
        this.workReviewRepository = workReviewRepository;
    }

    /**
     * Save a workReview.
     *
     * @param workReview the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WorkReview save(WorkReview workReview) {
        log.debug("Request to save WorkReview : {}", workReview);
        return workReviewRepository.save(workReview);
    }

    /**
     * Get all the workReviews.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WorkReview> findAll() {
        log.debug("Request to get all WorkReviews");
        return workReviewRepository.findAll();
    }


    /**
     * Get one workReview by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkReview> findOne(Long id) {
        log.debug("Request to get WorkReview : {}", id);
        return workReviewRepository.findById(id);
    }

    /**
     * Delete the workReview by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkReview : {}", id);

        workReviewRepository.deleteById(id);
    }
}
