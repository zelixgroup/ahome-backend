package com.zelix.ahome.service;

import com.zelix.ahome.domain.IdDocumentType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IdDocumentType> findAll(Pageable pageable);


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
