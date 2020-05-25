package com.zelix.ahome.service.impl;

import com.zelix.ahome.service.WorkService;
import com.zelix.ahome.domain.Work;
import com.zelix.ahome.repository.WorkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Work}.
 */
@Service
@Transactional
public class WorkServiceImpl implements WorkService {

    private final Logger log = LoggerFactory.getLogger(WorkServiceImpl.class);

    private final WorkRepository workRepository;

    public WorkServiceImpl(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    /**
     * Save a work.
     *
     * @param work the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Work save(Work work) {
        log.debug("Request to save Work : {}", work);
        return workRepository.save(work);
    }

    /**
     * Get all the works.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Work> findAll(Pageable pageable) {
        log.debug("Request to get all Works");
        return workRepository.findAll(pageable);
    }


    /**
     * Get one work by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Work> findOne(Long id) {
        log.debug("Request to get Work : {}", id);
        return workRepository.findById(id);
    }

    /**
     * Delete the work by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Work : {}", id);

        workRepository.deleteById(id);
    }
}
