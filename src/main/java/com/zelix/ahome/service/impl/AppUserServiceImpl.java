package com.zelix.ahome.service.impl;

import com.zelix.ahome.service.AppUserService;
import com.zelix.ahome.domain.AppUser;
import com.zelix.ahome.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AppUser}.
 */
@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);

    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    /**
     * Save a appUser.
     *
     * @param appUser the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AppUser save(AppUser appUser) {
        log.debug("Request to save AppUser : {}", appUser);
        return appUserRepository.save(appUser);
    }

    /**
     * Get all the appUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AppUser> findAll(Pageable pageable) {
        log.debug("Request to get all AppUsers");
        return appUserRepository.findAll(pageable);
    }


    /**
     * Get one appUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AppUser> findOne(Long id) {
        log.debug("Request to get AppUser : {}", id);
        return appUserRepository.findById(id);
    }

    /**
     * Delete the appUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppUser : {}", id);

        appUserRepository.deleteById(id);
    }
}
