package com.zelix.ahome.web.rest;

import com.zelix.ahome.AhomeApp;
import com.zelix.ahome.domain.WorkReview;
import com.zelix.ahome.repository.WorkReviewRepository;
import com.zelix.ahome.service.WorkReviewService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.zelix.ahome.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorkReviewResource} REST controller.
 */
@SpringBootTest(classes = AhomeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkReviewResourceIT {

    private static final ZonedDateTime DEFAULT_REVIEW_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REVIEW_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_STARS_NUMBER = 1;
    private static final Integer UPDATED_STARS_NUMBER = 2;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private WorkReviewRepository workReviewRepository;

    @Autowired
    private WorkReviewService workReviewService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkReviewMockMvc;

    private WorkReview workReview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkReview createEntity(EntityManager em) {
        WorkReview workReview = new WorkReview()
            .reviewDateTime(DEFAULT_REVIEW_DATE_TIME)
            .starsNumber(DEFAULT_STARS_NUMBER)
            .notes(DEFAULT_NOTES);
        return workReview;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkReview createUpdatedEntity(EntityManager em) {
        WorkReview workReview = new WorkReview()
            .reviewDateTime(UPDATED_REVIEW_DATE_TIME)
            .starsNumber(UPDATED_STARS_NUMBER)
            .notes(UPDATED_NOTES);
        return workReview;
    }

    @BeforeEach
    public void initTest() {
        workReview = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkReview() throws Exception {
        int databaseSizeBeforeCreate = workReviewRepository.findAll().size();
        // Create the WorkReview
        restWorkReviewMockMvc.perform(post("/api/work-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workReview)))
            .andExpect(status().isCreated());

        // Validate the WorkReview in the database
        List<WorkReview> workReviewList = workReviewRepository.findAll();
        assertThat(workReviewList).hasSize(databaseSizeBeforeCreate + 1);
        WorkReview testWorkReview = workReviewList.get(workReviewList.size() - 1);
        assertThat(testWorkReview.getReviewDateTime()).isEqualTo(DEFAULT_REVIEW_DATE_TIME);
        assertThat(testWorkReview.getStarsNumber()).isEqualTo(DEFAULT_STARS_NUMBER);
        assertThat(testWorkReview.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createWorkReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workReviewRepository.findAll().size();

        // Create the WorkReview with an existing ID
        workReview.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkReviewMockMvc.perform(post("/api/work-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workReview)))
            .andExpect(status().isBadRequest());

        // Validate the WorkReview in the database
        List<WorkReview> workReviewList = workReviewRepository.findAll();
        assertThat(workReviewList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkReviews() throws Exception {
        // Initialize the database
        workReviewRepository.saveAndFlush(workReview);

        // Get all the workReviewList
        restWorkReviewMockMvc.perform(get("/api/work-reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewDateTime").value(hasItem(sameInstant(DEFAULT_REVIEW_DATE_TIME))))
            .andExpect(jsonPath("$.[*].starsNumber").value(hasItem(DEFAULT_STARS_NUMBER)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }
    
    @Test
    @Transactional
    public void getWorkReview() throws Exception {
        // Initialize the database
        workReviewRepository.saveAndFlush(workReview);

        // Get the workReview
        restWorkReviewMockMvc.perform(get("/api/work-reviews/{id}", workReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workReview.getId().intValue()))
            .andExpect(jsonPath("$.reviewDateTime").value(sameInstant(DEFAULT_REVIEW_DATE_TIME)))
            .andExpect(jsonPath("$.starsNumber").value(DEFAULT_STARS_NUMBER))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }
    @Test
    @Transactional
    public void getNonExistingWorkReview() throws Exception {
        // Get the workReview
        restWorkReviewMockMvc.perform(get("/api/work-reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkReview() throws Exception {
        // Initialize the database
        workReviewService.save(workReview);

        int databaseSizeBeforeUpdate = workReviewRepository.findAll().size();

        // Update the workReview
        WorkReview updatedWorkReview = workReviewRepository.findById(workReview.getId()).get();
        // Disconnect from session so that the updates on updatedWorkReview are not directly saved in db
        em.detach(updatedWorkReview);
        updatedWorkReview
            .reviewDateTime(UPDATED_REVIEW_DATE_TIME)
            .starsNumber(UPDATED_STARS_NUMBER)
            .notes(UPDATED_NOTES);

        restWorkReviewMockMvc.perform(put("/api/work-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkReview)))
            .andExpect(status().isOk());

        // Validate the WorkReview in the database
        List<WorkReview> workReviewList = workReviewRepository.findAll();
        assertThat(workReviewList).hasSize(databaseSizeBeforeUpdate);
        WorkReview testWorkReview = workReviewList.get(workReviewList.size() - 1);
        assertThat(testWorkReview.getReviewDateTime()).isEqualTo(UPDATED_REVIEW_DATE_TIME);
        assertThat(testWorkReview.getStarsNumber()).isEqualTo(UPDATED_STARS_NUMBER);
        assertThat(testWorkReview.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkReview() throws Exception {
        int databaseSizeBeforeUpdate = workReviewRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkReviewMockMvc.perform(put("/api/work-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workReview)))
            .andExpect(status().isBadRequest());

        // Validate the WorkReview in the database
        List<WorkReview> workReviewList = workReviewRepository.findAll();
        assertThat(workReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkReview() throws Exception {
        // Initialize the database
        workReviewService.save(workReview);

        int databaseSizeBeforeDelete = workReviewRepository.findAll().size();

        // Delete the workReview
        restWorkReviewMockMvc.perform(delete("/api/work-reviews/{id}", workReview.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkReview> workReviewList = workReviewRepository.findAll();
        assertThat(workReviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
