package com.zelix.ahome.web.rest;

import com.zelix.ahome.AhomeApp;
import com.zelix.ahome.domain.WorkCategory;
import com.zelix.ahome.repository.WorkCategoryRepository;
import com.zelix.ahome.service.WorkCategoryService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorkCategoryResource} REST controller.
 */
@SpringBootTest(classes = AhomeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_PICTURE = "BBBBBBBBBB";

    private static final String DEFAULT_ICONE = "AAAAAAAAAA";
    private static final String UPDATED_ICONE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE_PER_HOUR = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_PER_HOUR = new BigDecimal(2);

    @Autowired
    private WorkCategoryRepository workCategoryRepository;

    @Autowired
    private WorkCategoryService workCategoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkCategoryMockMvc;

    private WorkCategory workCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkCategory createEntity(EntityManager em) {
        WorkCategory workCategory = new WorkCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .picture(DEFAULT_PICTURE)
            .icone(DEFAULT_ICONE)
            .pricePerHour(DEFAULT_PRICE_PER_HOUR);
        return workCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkCategory createUpdatedEntity(EntityManager em) {
        WorkCategory workCategory = new WorkCategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .picture(UPDATED_PICTURE)
            .icone(UPDATED_ICONE)
            .pricePerHour(UPDATED_PRICE_PER_HOUR);
        return workCategory;
    }

    @BeforeEach
    public void initTest() {
        workCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkCategory() throws Exception {
        int databaseSizeBeforeCreate = workCategoryRepository.findAll().size();
        // Create the WorkCategory
        restWorkCategoryMockMvc.perform(post("/api/work-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workCategory)))
            .andExpect(status().isCreated());

        // Validate the WorkCategory in the database
        List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
        assertThat(workCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        WorkCategory testWorkCategory = workCategoryList.get(workCategoryList.size() - 1);
        assertThat(testWorkCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWorkCategory.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testWorkCategory.getIcone()).isEqualTo(DEFAULT_ICONE);
        assertThat(testWorkCategory.getPricePerHour()).isEqualTo(DEFAULT_PRICE_PER_HOUR);
    }

    @Test
    @Transactional
    public void createWorkCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workCategoryRepository.findAll().size();

        // Create the WorkCategory with an existing ID
        workCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkCategoryMockMvc.perform(post("/api/work-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workCategory)))
            .andExpect(status().isBadRequest());

        // Validate the WorkCategory in the database
        List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
        assertThat(workCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkCategories() throws Exception {
        // Initialize the database
        workCategoryRepository.saveAndFlush(workCategory);

        // Get all the workCategoryList
        restWorkCategoryMockMvc.perform(get("/api/work-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.[*].icone").value(hasItem(DEFAULT_ICONE)))
            .andExpect(jsonPath("$.[*].pricePerHour").value(hasItem(DEFAULT_PRICE_PER_HOUR.intValue())));
    }
    
    @Test
    @Transactional
    public void getWorkCategory() throws Exception {
        // Initialize the database
        workCategoryRepository.saveAndFlush(workCategory);

        // Get the workCategory
        restWorkCategoryMockMvc.perform(get("/api/work-categories/{id}", workCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.picture").value(DEFAULT_PICTURE))
            .andExpect(jsonPath("$.icone").value(DEFAULT_ICONE))
            .andExpect(jsonPath("$.pricePerHour").value(DEFAULT_PRICE_PER_HOUR.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingWorkCategory() throws Exception {
        // Get the workCategory
        restWorkCategoryMockMvc.perform(get("/api/work-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkCategory() throws Exception {
        // Initialize the database
        workCategoryService.save(workCategory);

        int databaseSizeBeforeUpdate = workCategoryRepository.findAll().size();

        // Update the workCategory
        WorkCategory updatedWorkCategory = workCategoryRepository.findById(workCategory.getId()).get();
        // Disconnect from session so that the updates on updatedWorkCategory are not directly saved in db
        em.detach(updatedWorkCategory);
        updatedWorkCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .picture(UPDATED_PICTURE)
            .icone(UPDATED_ICONE)
            .pricePerHour(UPDATED_PRICE_PER_HOUR);

        restWorkCategoryMockMvc.perform(put("/api/work-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkCategory)))
            .andExpect(status().isOk());

        // Validate the WorkCategory in the database
        List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
        assertThat(workCategoryList).hasSize(databaseSizeBeforeUpdate);
        WorkCategory testWorkCategory = workCategoryList.get(workCategoryList.size() - 1);
        assertThat(testWorkCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkCategory.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testWorkCategory.getIcone()).isEqualTo(UPDATED_ICONE);
        assertThat(testWorkCategory.getPricePerHour()).isEqualTo(UPDATED_PRICE_PER_HOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkCategory() throws Exception {
        int databaseSizeBeforeUpdate = workCategoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkCategoryMockMvc.perform(put("/api/work-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workCategory)))
            .andExpect(status().isBadRequest());

        // Validate the WorkCategory in the database
        List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
        assertThat(workCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkCategory() throws Exception {
        // Initialize the database
        workCategoryService.save(workCategory);

        int databaseSizeBeforeDelete = workCategoryRepository.findAll().size();

        // Delete the workCategory
        restWorkCategoryMockMvc.perform(delete("/api/work-categories/{id}", workCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
        assertThat(workCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
