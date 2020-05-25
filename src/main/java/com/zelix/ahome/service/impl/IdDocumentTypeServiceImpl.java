package com.zelix.ahome.service.impl;

import com.zelix.ahome.service.IdDocumentTypeService;
import com.zelix.ahome.domain.IdDocumentType;
import com.zelix.ahome.repository.IdDocumentTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link IdDocumentType}.
 */
@Service
@Transactional
public class IdDocumentTypeServiceImpl implements IdDocumentTypeService {

    private final Logger log = LoggerFactory.getLogger(IdDocumentTypeServiceImpl.class);

    private final IdDocumentTypeRepository idDocumentTypeRepository;

    public IdDocumentTypeServiceImpl(IdDocumentTypeRepository idDocumentTypeRepository) {
        this.idDocumentTypeRepository = idDocumentTypeRepository;
    }

    /**
     * Save a idDocumentType.
     *
     * @param idDocumentType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IdDocumentType save(IdDocumentType idDocumentType) {
        log.debug("Request to save IdDocumentType : {}", idDocumentType);
        return idDocumentTypeRepository.save(idDocumentType);
    }

    /**
     * Get all the idDocumentTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<IdDocumentType> findAll() {
        log.debug("Request to get all IdDocumentTypes");
        return idDocumentTypeRepository.findAll();
    }


    /**
     * Get one idDocumentType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IdDocumentType> findOne(Long id) {
        log.debug("Request to get IdDocumentType : {}", id);
        return idDocumentTypeRepository.findById(id);
    }

    /**
     * Delete the idDocumentType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IdDocumentType : {}", id);

        idDocumentTypeRepository.deleteById(id);
    }
}
