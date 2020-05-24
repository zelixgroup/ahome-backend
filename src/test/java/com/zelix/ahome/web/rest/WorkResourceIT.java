package com.zelix.ahome.web.rest;

import com.zelix.ahome.AhomeApp;
import com.zelix.ahome.domain.Work;
import com.zelix.ahome.repository.WorkRepository;
import com.zelix.ahome.service.WorkService;

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
 * Integration tests for the {@link WorkResource} REST controller.
 */
@SpringBootTest(classes = AhomeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkResourceIT {

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
    private WorkRepository workRepository;

    @Autowired
    private WorkService workService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkMockMvc;

    private Work work;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Work createEntity(EntityManager em) {
        Work work = new Work()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .picture(DEFAULT_PICTURE)
            .icone(DEFAULT_ICONE)
            .pricePerHour(DEFAULT_PRICE_PER_HOUR);
        return work;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Work createUpdatedEntity(EntityManager em) {
        Work work = new Work()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .picture(UPDATED_PICTURE)
            .icone(UPDATED_ICONE)
            .pricePerHour(UPDATED_PRICE_PER_HOUR);
        return work;
    }

    @BeforeEach
    public void initTest() {
        work = createEntity(em);
    }

    @Test
    @Transactional
    public void createWork() throws Exception {
        int databaseSizeBeforeCreate = workRepository.findAll().size();
        // Create the Work
        restWorkMockMvc.perform(post("/api/works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(work)))
            .andExpect(status().isCreated());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeCreate + 1);
        Work testWork = workList.get(workList.size() - 1);
        assertThat(testWork.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWork.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWork.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testWork.getIcone()).isEqualTo(DEFAULT_ICONE);
        assertThat(testWork.getPricePerHour()).isEqualTo(DEFAULT_PRICE_PER_HOUR);
    }

    @Test
    @Transactional
    public void createWorkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workRepository.findAll().size();

        // Create the Work with an existing ID
        work.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkMockMvc.perform(post("/api/works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(work)))
            .andExpect(status().isBadRequest());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorks() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        // Get all the workList
        restWorkMockMvc.perform(get("/api/works?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(work.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.[*].icone").value(hasItem(DEFAULT_ICONE)))
            .andExpect(jsonPath("$.[*].pricePerHour").value(hasItem(DEFAULT_PRICE_PER_HOUR.intValue())));
    }
    
    @Test
    @Transactional
    public void getWork() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        // Get the work
        restWorkMockMvc.perform(get("/api/works/{id}", work.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(work.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.picture").value(DEFAULT_PICTURE))
            .andExpect(jsonPath("$.icone").value(DEFAULT_ICONE))
            .andExpect(jsonPath("$.pricePerHour").value(DEFAULT_PRICE_PER_HOUR.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingWork() throws Exception {
        // Get the work
        restWorkMockMvc.perform(get("/api/works/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWork() throws Exception {
        // Initialize the database
        workService.save(work);

        int databaseSizeBeforeUpdate = workRepository.findAll().size();

        // Update the work
        Work updatedWork = workRepository.findById(work.getId()).get();
        // Disconnect from session so that the updates on updatedWork are not directly saved in db
        em.detach(updatedWork);
        updatedWork
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .picture(UPDATED_PICTURE)
            .icone(UPDATED_ICONE)
            .pricePerHour(UPDATED_PRICE_PER_HOUR);

        restWorkMockMvc.perform(put("/api/works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWork)))
            .andExpect(status().isOk());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeUpdate);
        Work testWork = workList.get(workList.size() - 1);
        assertThat(testWork.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWork.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWork.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testWork.getIcone()).isEqualTo(UPDATED_ICONE);
        assertThat(testWork.getPricePerHour()).isEqualTo(UPDATED_PRICE_PER_HOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingWork() throws Exception {
        int databaseSizeBeforeUpdate = workRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkMockMvc.perform(put("/api/works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(work)))
            .andExpect(status().isBadRequest());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWork() throws Exception {
        // Initialize the database
        workService.save(work);

        int databaseSizeBeforeDelete = workRepository.findAll().size();

        // Delete the work
        restWorkMockMvc.perform(delete("/api/works/{id}", work.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
