package com.zelix.ahome.service.impl;

import com.zelix.ahome.service.WorkRequestStatusChangeService;
import com.zelix.ahome.domain.WorkRequestStatusChange;
import com.zelix.ahome.repository.WorkRequestStatusChangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkRequestStatusChange}.
 */
@Service
@Transactional
public class WorkRequestStatusChangeServiceImpl implements WorkRequestStatusChangeService {

    private final Logger log = LoggerFactory.getLogger(WorkRequestStatusChangeServiceImpl.class);

    private final WorkRequestStatusChangeRepository workRequestStatusChangeRepository;

    public WorkRequestStatusChangeServiceImpl(WorkRequestStatusChangeRepository workRequestStatusChangeRepository) {
        this.workRequestStatusChangeRepository = workRequestStatusChangeRepository;
    }

    /**
     * Save a workRequestStatusChange.
     *
     * @param workRequestStatusChange the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WorkRequestStatusChange save(WorkRequestStatusChange workRequestStatusChange) {
        log.debug("Request to save WorkRequestStatusChange : {}", workRequestStatusChange);
        return workRequestStatusChangeRepository.save(workRequestStatusChange);
    }

    /**
     * Get all the workRequestStatusChanges.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WorkRequestStatusChange> findAll() {
        log.debug("Request to get all WorkRequestStatusChanges");
        return workRequestStatusChangeRepository.findAll();
    }


    /**
     * Get one workRequestStatusChange by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkRequestStatusChange> findOne(Long id) {
        log.debug("Request to get WorkRequestStatusChange : {}", id);
        return workRequestStatusChangeRepository.findById(id);
    }

    /**
     * Delete the workRequestStatusChange by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkRequestStatusChange : {}", id);

        workRequestStatusChangeRepository.deleteById(id);
    }
}
