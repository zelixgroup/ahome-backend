package com.zelix.ahome.service;

import com.zelix.ahome.domain.IdDocumentType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link IdDocumentType}.
 */
public interface IdDocumentTypeService {

    /**
     * Save a idDocumentType.
     *
     * @param idDocumentType the entity to save.
     * @return the persisted entity.
     */
    IdDocumentType save(IdDocumentType idDocumentType);

    /**
     * Get all the idDocumentTypes.
     *
     * @return the list of entities.
     */
    List<IdDocumentType> findAll();


    /**
     * Get the "id" idDocumentType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IdDocumentType> findOne(Long id);

    /**
     * Delete the "id" idDocumentType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
