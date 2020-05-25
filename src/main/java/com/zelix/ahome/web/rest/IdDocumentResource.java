package com.zelix.ahome.web.rest;

import com.zelix.ahome.domain.IdDocument;
import com.zelix.ahome.service.IdDocumentService;
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
 * REST controller for managing {@link com.zelix.ahome.domain.IdDocument}.
 */
@RestController
@RequestMapping("/api")
public class IdDocumentResource {

    private final Logger log = LoggerFactory.getLogger(IdDocumentResource.class);

    private static final String ENTITY_NAME = "idDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdDocumentService idDocumentService;

    public IdDocumentResource(IdDocumentService idDocumentService) {
        this.idDocumentService = idDocumentService;
    }

    /**
     * {@code POST  /id-documents} : Create a new idDocument.
     *
     * @param idDocument the idDocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new idDocument, or with status {@code 400 (Bad Request)} if the idDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/id-documents")
    public ResponseEntity<IdDocument> createIdDocument(@RequestBody IdDocument idDocument) throws URISyntaxException {
        log.debug("REST request to save IdDocument : {}", idDocument);
        if (idDocument.getId() != null) {
            throw new BadRequestAlertException("A new idDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdDocument result = idDocumentService.save(idDocument);
        return ResponseEntity.created(new URI("/api/id-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /id-documents} : Updates an existing idDocument.
     *
     * @param idDocument the idDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idDocument,
     * or with status {@code 400 (Bad Request)} if the idDocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the idDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/id-documents")
    public ResponseEntity<IdDocument> updateIdDocument(@RequestBody IdDocument idDocument) throws URISyntaxException {
        log.debug("REST request to update IdDocument : {}", idDocument);
        if (idDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IdDocument result = idDocumentService.save(idDocument);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, idDocument.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /id-documents} : get all the idDocuments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of idDocuments in body.
     */
    @GetMapping("/id-documents")
    public ResponseEntity<List<IdDocument>> getAllIdDocuments(Pageable pageable) {
        log.debug("REST request to get a page of IdDocuments");
        Page<IdDocument> page = idDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /id-documents/:id} : get the "id" idDocument.
     *
     * @param id the id of the idDocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the idDocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/id-documents/{id}")
    public ResponseEntity<IdDocument> getIdDocument(@PathVariable Long id) {
        log.debug("REST request to get IdDocument : {}", id);
        Optional<IdDocument> idDocument = idDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(idDocument);
    }

    /**
     * {@code DELETE  /id-documents/:id} : delete the "id" idDocument.
     *
     * @param id the id of the idDocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/id-documents/{id}")
    public ResponseEntity<Void> deleteIdDocument(@PathVariable Long id) {
        log.debug("REST request to delete IdDocument : {}", id);

        idDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
