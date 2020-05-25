package com.zelix.ahome.service;

import com.zelix.ahome.domain.IdDocument;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link IdDocument}.
 */
public interface IdDocumentService {

    /**
     * Save a idDocument.
     *
     * @param idDocument the entity to save.
     * @return the persisted entity.
     */
    IdDocument save(IdDocument idDocument);

    /**
     * Get all the idDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IdDocument> findAll(Pageable pageable);


    /**
     * Get the "id" idDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IdDocument> findOne(Long id);

    /**
     * Delete the "id" idDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
