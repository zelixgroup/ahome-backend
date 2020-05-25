package com.zelix.ahome.web.rest;

import com.zelix.ahome.AhomeApp;
import com.zelix.ahome.domain.IdDocument;
import com.zelix.ahome.repository.IdDocumentRepository;
import com.zelix.ahome.service.IdDocumentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IdDocumentResource} REST controller.
 */
@SpringBootTest(classes = AhomeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IdDocumentResourceIT {

    private static final byte[] DEFAULT_ID_DOCUMENT_RECTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ID_DOCUMENT_RECTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ID_DOCUMENT_RECTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ID_DOCUMENT_RECTO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ID_DOCUMENT_VERSO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ID_DOCUMENT_VERSO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ID_DOCUMENT_VERSO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ID_DOCUMENT_VERSO_CONTENT_TYPE = "image/png";

    @Autowired
    private IdDocumentRepository idDocumentRepository;

    @Autowired
    private IdDocumentService idDocumentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdDocumentMockMvc;

    private IdDocument idDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdDocument createEntity(EntityManager em) {
        IdDocument idDocument = new IdDocument()
            .idDocumentRecto(DEFAULT_ID_DOCUMENT_RECTO)
            .idDocumentRectoContentType(DEFAULT_ID_DOCUMENT_RECTO_CONTENT_TYPE)
            .idDocumentVerso(DEFAULT_ID_DOCUMENT_VERSO)
            .idDocumentVersoContentType(DEFAULT_ID_DOCUMENT_VERSO_CONTENT_TYPE);
        return idDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdDocument createUpdatedEntity(EntityManager em) {
        IdDocument idDocument = new IdDocument()
            .idDocumentRecto(UPDATED_ID_DOCUMENT_RECTO)
            .idDocumentRectoContentType(UPDATED_ID_DOCUMENT_RECTO_CONTENT_TYPE)
            .idDocumentVerso(UPDATED_ID_DOCUMENT_VERSO)
            .idDocumentVersoContentType(UPDATED_ID_DOCUMENT_VERSO_CONTENT_TYPE);
        return idDocument;
    }

    @BeforeEach
    public void initTest() {
        idDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdDocument() throws Exception {
        int databaseSizeBeforeCreate = idDocumentRepository.findAll().size();
        // Create the IdDocument
        restIdDocumentMockMvc.perform(post("/api/id-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idDocument)))
            .andExpect(status().isCreated());

        // Validate the IdDocument in the database
        List<IdDocument> idDocumentList = idDocumentRepository.findAll();
        assertThat(idDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        IdDocument testIdDocument = idDocumentList.get(idDocumentList.size() - 1);
        assertThat(testIdDocument.getIdDocumentRecto()).isEqualTo(DEFAULT_ID_DOCUMENT_RECTO);
        assertThat(testIdDocument.getIdDocumentRectoContentType()).isEqualTo(DEFAULT_ID_DOCUMENT_RECTO_CONTENT_TYPE);
        assertThat(testIdDocument.getIdDocumentVerso()).isEqualTo(DEFAULT_ID_DOCUMENT_VERSO);
        assertThat(testIdDocument.getIdDocumentVersoContentType()).isEqualTo(DEFAULT_ID_DOCUMENT_VERSO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createIdDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = idDocumentRepository.findAll().size();

        // Create the IdDocument with an existing ID
        idDocument.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdDocumentMockMvc.perform(post("/api/id-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idDocument)))
            .andExpect(status().isBadRequest());

        // Validate the IdDocument in the database
        List<IdDocument> idDocumentList = idDocumentRepository.findAll();
        assertThat(idDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIdDocuments() throws Exception {
        // Initialize the database
        idDocumentRepository.saveAndFlush(idDocument);

        // Get all the idDocumentList
        restIdDocumentMockMvc.perform(get("/api/id-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDocumentRectoContentType").value(hasItem(DEFAULT_ID_DOCUMENT_RECTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].idDocumentRecto").value(hasItem(Base64Utils.encodeToString(DEFAULT_ID_DOCUMENT_RECTO))))
            .andExpect(jsonPath("$.[*].idDocumentVersoContentType").value(hasItem(DEFAULT_ID_DOCUMENT_VERSO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].idDocumentVerso").value(hasItem(Base64Utils.encodeToString(DEFAULT_ID_DOCUMENT_VERSO))));
    }
    
    @Test
    @Transactional
    public void getIdDocument() throws Exception {
        // Initialize the database
        idDocumentRepository.saveAndFlush(idDocument);

        // Get the idDocument
        restIdDocumentMockMvc.perform(get("/api/id-documents/{id}", idDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(idDocument.getId().intValue()))
            .andExpect(jsonPath("$.idDocumentRectoContentType").value(DEFAULT_ID_DOCUMENT_RECTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.idDocumentRecto").value(Base64Utils.encodeToString(DEFAULT_ID_DOCUMENT_RECTO)))
            .andExpect(jsonPath("$.idDocumentVersoContentType").value(DEFAULT_ID_DOCUMENT_VERSO_CONTENT_TYPE))
            .andExpect(jsonPath("$.idDocumentVerso").value(Base64Utils.encodeToString(DEFAULT_ID_DOCUMENT_VERSO)));
    }
    @Test
    @Transactional
    public void getNonExistingIdDocument() throws Exception {
        // Get the idDocument
        restIdDocumentMockMvc.perform(get("/api/id-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdDocument() throws Exception {
        // Initialize the database
        idDocumentService.save(idDocument);

        int databaseSizeBeforeUpdate = idDocumentRepository.findAll().size();

        // Update the idDocument
        IdDocument updatedIdDocument = idDocumentRepository.findById(idDocument.getId()).get();
        // Disconnect from session so that the updates on updatedIdDocument are not directly saved in db
        em.detach(updatedIdDocument);
        updatedIdDocument
            .idDocumentRecto(UPDATED_ID_DOCUMENT_RECTO)
            .idDocumentRectoContentType(UPDATED_ID_DOCUMENT_RECTO_CONTENT_TYPE)
            .idDocumentVerso(UPDATED_ID_DOCUMENT_VERSO)
            .idDocumentVersoContentType(UPDATED_ID_DOCUMENT_VERSO_CONTENT_TYPE);

        restIdDocumentMockMvc.perform(put("/api/id-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdDocument)))
            .andExpect(status().isOk());

        // Validate the IdDocument in the database
        List<IdDocument> idDocumentList = idDocumentRepository.findAll();
        assertThat(idDocumentList).hasSize(databaseSizeBeforeUpdate);
        IdDocument testIdDocument = idDocumentList.get(idDocumentList.size() - 1);
        assertThat(testIdDocument.getIdDocumentRecto()).isEqualTo(UPDATED_ID_DOCUMENT_RECTO);
        assertThat(testIdDocument.getIdDocumentRectoContentType()).isEqualTo(UPDATED_ID_DOCUMENT_RECTO_CONTENT_TYPE);
        assertThat(testIdDocument.getIdDocumentVerso()).isEqualTo(UPDATED_ID_DOCUMENT_VERSO);
        assertThat(testIdDocument.getIdDocumentVersoContentType()).isEqualTo(UPDATED_ID_DOCUMENT_VERSO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingIdDocument() throws Exception {
        int databaseSizeBeforeUpdate = idDocumentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdDocumentMockMvc.perform(put("/api/id-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idDocument)))
            .andExpect(status().isBadRequest());

        // Validate the IdDocument in the database
        List<IdDocument> idDocumentList = idDocumentRepository.findAll();
        assertThat(idDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdDocument() throws Exception {
        // Initialize the database
        idDocumentService.save(idDocument);

        int databaseSizeBeforeDelete = idDocumentRepository.findAll().size();

        // Delete the idDocument
        restIdDocumentMockMvc.perform(delete("/api/id-documents/{id}", idDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IdDocument> idDocumentList = idDocumentRepository.findAll();
        assertThat(idDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
