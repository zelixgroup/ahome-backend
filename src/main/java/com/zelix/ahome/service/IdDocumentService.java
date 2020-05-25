package com.zelix.ahome.service;

import com.zelix.ahome.domain.IdDocument;

import java.util.List;
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
     * @return the list of entities.
     */
    List<IdDocument> findAll();


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
