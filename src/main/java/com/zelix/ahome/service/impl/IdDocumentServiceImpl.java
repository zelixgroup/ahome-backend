package com.zelix.ahome.service.impl;

import com.zelix.ahome.service.IdDocumentService;
import com.zelix.ahome.domain.IdDocument;
import com.zelix.ahome.repository.IdDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link IdDocument}.
 */
@Service
@Transactional
public class IdDocumentServiceImpl implements IdDocumentService {

    private final Logger log = LoggerFactory.getLogger(IdDocumentServiceImpl.class);

    private final IdDocumentRepository idDocumentRepository;

    public IdDocumentServiceImpl(IdDocumentRepository idDocumentRepository) {
        this.idDocumentRepository = idDocumentRepository;
    }

    /**
     * Save a idDocument.
     *
     * @param idDocument the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IdDocument save(IdDocument idDocument) {
        log.debug("Request to save IdDocument : {}", idDocument);
        return idDocumentRepository.save(idDocument);
    }

    /**
     * Get all the idDocuments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<IdDocument> findAll() {
        log.debug("Request to get all IdDocuments");
        return idDocumentRepository.findAll();
    }


    /**
     * Get one idDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IdDocument> findOne(Long id) {
        log.debug("Request to get IdDocument : {}", id);
        return idDocumentRepository.findById(id);
    }

    /**
     * Delete the idDocument by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IdDocument : {}", id);

        idDocumentRepository.deleteById(id);
    }
}
