package com.zelix.ahome.web.rest;

import com.zelix.ahome.domain.IdDocumentType;
import com.zelix.ahome.service.IdDocumentTypeService;
import com.zelix.ahome.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.zelix.ahome.domain.IdDocumentType}.
 */
@RestController
@RequestMapping("/api")
public class IdDocumentTypeResource {

    private final Logger log = LoggerFactory.getLogger(IdDocumentTypeResource.class);

    private static final String ENTITY_NAME = "idDocumentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdDocumentTypeService idDocumentTypeService;

    public IdDocumentTypeResource(IdDocumentTypeService idDocumentTypeService) {
        this.idDocumentTypeService = idDocumentTypeService;
    }

    /**
     * {@code POST  /id-document-types} : Create a new idDocumentType.
     *
     * @param idDocumentType the idDocumentType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new idDocumentType, or with status {@code 400 (Bad Request)} if the idDocumentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/id-document-types")
    public ResponseEntity<IdDocumentType> createIdDocumentType(@RequestBody IdDocumentType idDocumentType) throws URISyntaxException {
        log.debug("REST request to save IdDocumentType : {}", idDocumentType);
        if (idDocumentType.getId() != null) {
            throw new BadRequestAlertException("A new idDocumentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdDocumentType result = idDocumentTypeService.save(idDocumentType);
        return ResponseEntity.created(new URI("/api/id-document-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /id-document-types} : Updates an existing idDocumentType.
     *
     * @param idDocumentType the idDocumentType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idDocumentType,
     * or with status {@code 400 (Bad Request)} if the idDocumentType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the idDocumentType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/id-document-types")
    public ResponseEntity<IdDocumentType> updateIdDocumentType(@RequestBody IdDocumentType idDocumentType) throws URISyntaxException {
        log.debug("REST request to update IdDocumentType : {}", idDocumentType);
        if (idDocumentType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IdDocumentType result = idDocumentTypeService.save(idDocumentType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, idDocumentType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /id-document-types} : get all the idDocumentTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of idDocumentTypes in body.
     */
    @GetMapping("/id-document-types")
    public ResponseEntity<List<IdDocumentType>> getAllIdDocumentTypes(Pageable pageable) {
        log.debug("REST request to get a page of IdDocumentTypes");
        Page<IdDocumentType> page = idDocumentTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /id-document-types/:id} : get the "id" idDocumentType.
     *
     * @param id the id of the idDocumentType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the idDocumentType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/id-document-types/{id}")
    public ResponseEntity<IdDocumentType> getIdDocumentType(@PathVariable Long id) {
        log.debug("REST request to get IdDocumentType : {}", id);
        Optional<IdDocumentType> idDocumentType = idDocumentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(idDocumentType);
    }

    /**
     * {@code DELETE  /id-document-types/:id} : delete the "id" idDocumentType.
     *
     * @param id the id of the idDocumentType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/id-document-types/{id}")
    public ResponseEntity<Void> deleteIdDocumentType(@PathVariable Long id) {
        log.debug("REST request to delete IdDocumentType : {}", id);

        idDocumentTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
