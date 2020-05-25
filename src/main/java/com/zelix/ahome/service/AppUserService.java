package com.zelix.ahome.service;

import com.zelix.ahome.domain.AppUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link AppUser}.
 */
public interface AppUserService {

    /**
     * Save a appUser.
     *
     * @param appUser the entity to save.
     * @return the persisted entity.
     */
    AppUser save(AppUser appUser);

    /**
     * Get all the appUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppUser> findAll(Pageable pageable);


    /**
     * Get the "id" appUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppUser> findOne(Long id);

    /**
     * Delete the "id" appUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
