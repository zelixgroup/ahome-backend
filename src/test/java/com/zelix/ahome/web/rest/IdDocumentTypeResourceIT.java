package com.zelix.ahome.web.rest;

import com.zelix.ahome.AhomeApp;
import com.zelix.ahome.domain.IdDocumentType;
import com.zelix.ahome.repository.IdDocumentTypeRepository;
import com.zelix.ahome.service.IdDocumentTypeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IdDocumentTypeResource} REST controller.
 */
@SpringBootTest(classes = AhomeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IdDocumentTypeResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NEED_VERSO = false;
    private static final Boolean UPDATED_NEED_VERSO = true;

    @Autowired
    private IdDocumentTypeRepository idDocumentTypeRepository;

    @Autowired
    private IdDocumentTypeService idDocumentTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdDocumentTypeMockMvc;

    private IdDocumentType idDocumentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdDocumentType createEntity(EntityManager em) {
        IdDocumentType idDocumentType = new IdDocumentType()
            .label(DEFAULT_LABEL)
            .description(DEFAULT_DESCRIPTION)
            .needVerso(DEFAULT_NEED_VERSO);
        return idDocumentType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdDocumentType createUpdatedEntity(EntityManager em) {
        IdDocumentType idDocumentType = new IdDocumentType()
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .needVerso(UPDATED_NEED_VERSO);
        return idDocumentType;
    }

    @BeforeEach
    public void initTest() {
        idDocumentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdDocumentType() throws Exception {
        int databaseSizeBeforeCreate = idDocumentTypeRepository.findAll().size();
        // Create the IdDocumentType
        restIdDocumentTypeMockMvc.perform(post("/api/id-document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idDocumentType)))
            .andExpect(status().isCreated());

        // Validate the IdDocumentType in the database
        List<IdDocumentType> idDocumentTypeList = idDocumentTypeRepository.findAll();
        assertThat(idDocumentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        IdDocumentType testIdDocumentType = idDocumentTypeList.get(idDocumentTypeList.size() - 1);
        assertThat(testIdDocumentType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testIdDocumentType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIdDocumentType.isNeedVerso()).isEqualTo(DEFAULT_NEED_VERSO);
    }

    @Test
    @Transactional
    public void createIdDocumentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = idDocumentTypeRepository.findAll().size();

        // Create the IdDocumentType with an existing ID
        idDocumentType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdDocumentTypeMockMvc.perform(post("/api/id-document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idDocumentType)))
            .andExpect(status().isBadRequest());

        // Validate the IdDocumentType in the database
        List<IdDocumentType> idDocumentTypeList = idDocumentTypeRepository.findAll();
        assertThat(idDocumentTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIdDocumentTypes() throws Exception {
        // Initialize the database
        idDocumentTypeRepository.saveAndFlush(idDocumentType);

        // Get all the idDocumentTypeList
        restIdDocumentTypeMockMvc.perform(get("/api/id-document-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idDocumentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].needVerso").value(hasItem(DEFAULT_NEED_VERSO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getIdDocumentType() throws Exception {
        // Initialize the database
        idDocumentTypeRepository.saveAndFlush(idDocumentType);

        // Get the idDocumentType
        restIdDocumentTypeMockMvc.perform(get("/api/id-document-types/{id}", idDocumentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(idDocumentType.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.needVerso").value(DEFAULT_NEED_VERSO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingIdDocumentType() throws Exception {
        // Get the idDocumentType
        restIdDocumentTypeMockMvc.perform(get("/api/id-document-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdDocumentType() throws Exception {
        // Initialize the database
        idDocumentTypeService.save(idDocumentType);

        int databaseSizeBeforeUpdate = idDocumentTypeRepository.findAll().size();

        // Update the idDocumentType
        IdDocumentType updatedIdDocumentType = idDocumentTypeRepository.findById(idDocumentType.getId()).get();
        // Disconnect from session so that the updates on updatedIdDocumentType are not directly saved in db
        em.detach(updatedIdDocumentType);
        updatedIdDocumentType
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .needVerso(UPDATED_NEED_VERSO);

        restIdDocumentTypeMockMvc.perform(put("/api/id-document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdDocumentType)))
            .andExpect(status().isOk());

        // Validate the IdDocumentType in the database
        List<IdDocumentType> idDocumentTypeList = idDocumentTypeRepository.findAll();
        assertThat(idDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
        IdDocumentType testIdDocumentType = idDocumentTypeList.get(idDocumentTypeList.size() - 1);
        assertThat(testIdDocumentType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testIdDocumentType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIdDocumentType.isNeedVerso()).isEqualTo(UPDATED_NEED_VERSO);
    }

    @Test
    @Transactional
    public void updateNonExistingIdDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = idDocumentTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdDocumentTypeMockMvc.perform(put("/api/id-document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idDocumentType)))
            .andExpect(status().isBadRequest());

        // Validate the IdDocumentType in the database
        List<IdDocumentType> idDocumentTypeList = idDocumentTypeRepository.findAll();
        assertThat(idDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdDocumentType() throws Exception {
        // Initialize the database
        idDocumentTypeService.save(idDocumentType);

        int databaseSizeBeforeDelete = idDocumentTypeRepository.findAll().size();

        // Delete the idDocumentType
        restIdDocumentTypeMockMvc.perform(delete("/api/id-document-types/{id}", idDocumentType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IdDocumentType> idDocumentTypeList = idDocumentTypeRepository.findAll();
        assertThat(idDocumentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
