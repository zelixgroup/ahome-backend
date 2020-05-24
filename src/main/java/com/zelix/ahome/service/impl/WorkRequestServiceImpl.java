package com.zelix.ahome.service.impl;

import com.zelix.ahome.service.WorkRequestService;
import com.zelix.ahome.domain.WorkRequest;
import com.zelix.ahome.repository.WorkRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkRequest}.
 */
@Service
@Transactional
public class WorkRequestServiceImpl implements WorkRequestService {

    private final Logger log = LoggerFactory.getLogger(WorkRequestServiceImpl.class);

    private final WorkRequestRepository workRequestRepository;

    public WorkRequestServiceImpl(WorkRequestRepository workRequestRepository) {
        this.workRequestRepository = workRequestRepository;
    }

    /**
     * Save a workRequest.
     *
     * @param workRequest the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WorkRequest save(WorkRequest workRequest) {
        log.debug("Request to save WorkRequest : {}", workRequest);
        return workRequestRepository.save(workRequest);
    }

    /**
     * Get all the workRequests.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WorkRequest> findAll() {
        log.debug("Request to get all WorkRequests");
        return workRequestRepository.findAll();
    }


    /**
     * Get one workRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkRequest> findOne(Long id) {
        log.debug("Request to get WorkRequest : {}", id);
        return workRequestRepository.findById(id);
    }

    /**
     * Delete the workRequest by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkRequest : {}", id);

        workRequestRepository.deleteById(id);
    }
}
