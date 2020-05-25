package com.zelix.ahome.service.impl;

import com.zelix.ahome.service.WorkCategoryService;
import com.zelix.ahome.domain.WorkCategory;
import com.zelix.ahome.repository.WorkCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkCategory}.
 */
@Service
@Transactional
public class WorkCategoryServiceImpl implements WorkCategoryService {

    private final Logger log = LoggerFactory.getLogger(WorkCategoryServiceImpl.class);

    private final WorkCategoryRepository workCategoryRepository;

    public WorkCategoryServiceImpl(WorkCategoryRepository workCategoryRepository) {
        this.workCategoryRepository = workCategoryRepository;
    }

    /**
     * Save a workCategory.
     *
     * @param workCategory the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WorkCategory save(WorkCategory workCategory) {
        log.debug("Request to save WorkCategory : {}", workCategory);
        return workCategoryRepository.save(workCategory);
    }

    /**
     * Get all the workCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkCategory> findAll(Pageable pageable) {
        log.debug("Request to get all WorkCategories");
        return workCategoryRepository.findAll(pageable);
    }


    /**
     * Get one workCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkCategory> findOne(Long id) {
        log.debug("Request to get WorkCategory : {}", id);
        return workCategoryRepository.findById(id);
    }

    /**
     * Delete the workCategory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkCategory : {}", id);

        workCategoryRepository.deleteById(id);
    }
}
