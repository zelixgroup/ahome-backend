package com.zelix.ahome.service.impl;

import com.zelix.ahome.service.CityService;
import com.zelix.ahome.domain.City;
import com.zelix.ahome.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link City}.
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * Save a city.
     *
     * @param city the entity to save.
     * @return the persisted entity.
     */
    @Override
    public City save(City city) {
        log.debug("Request to save City : {}", city);
        return cityRepository.save(city);
    }

    /**
     * Get all the cities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<City> findAll(Pageable pageable) {
        log.debug("Request to get all Cities");
        return cityRepository.findAll(pageable);
    }


    /**
     * Get one city by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<City> findOne(Long id) {
        log.debug("Request to get City : {}", id);
        return cityRepository.findById(id);
    }

    /**
     * Delete the city by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete City : {}", id);

        cityRepository.deleteById(id);
    }
}
