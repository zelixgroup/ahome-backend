package com.zelix.ahome.web.rest;

import com.zelix.ahome.AhomeApp;
import com.zelix.ahome.domain.WorkReviewComment;
import com.zelix.ahome.repository.WorkReviewCommentRepository;
import com.zelix.ahome.service.WorkReviewCommentService;

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
 * Integration tests for the {@link WorkReviewCommentResource} REST controller.
 */
@SpringBootTest(classes = AhomeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkReviewCommentResourceIT {

    private static final ZonedDateTime DEFAULT_COMMENT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMMENT_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private WorkReviewCommentRepository workReviewCommentRepository;

    @Autowired
    private WorkReviewCommentService workReviewCommentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkReviewCommentMockMvc;

    private WorkReviewComment workReviewComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkReviewComment createEntity(EntityManager em) {
        WorkReviewComment workReviewComment = new WorkReviewComment()
            .commentDateTime(DEFAULT_COMMENT_DATE_TIME)
            .comment(DEFAULT_COMMENT);
        return workReviewComment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkReviewComment createUpdatedEntity(EntityManager em) {
        WorkReviewComment workReviewComment = new WorkReviewComment()
            .commentDateTime(UPDATED_COMMENT_DATE_TIME)
            .comment(UPDATED_COMMENT);
        return workReviewComment;
    }

    @BeforeEach
    public void initTest() {
        workReviewComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkReviewComment() throws Exception {
        int databaseSizeBeforeCreate = workReviewCommentRepository.findAll().size();
        // Create the WorkReviewComment
        restWorkReviewCommentMockMvc.perform(post("/api/work-review-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workReviewComment)))
            .andExpect(status().isCreated());

        // Validate the WorkReviewComment in the database
        List<WorkReviewComment> workReviewCommentList = workReviewCommentRepository.findAll();
        assertThat(workReviewCommentList).hasSize(databaseSizeBeforeCreate + 1);
        WorkReviewComment testWorkReviewComment = workReviewCommentList.get(workReviewCommentList.size() - 1);
        assertThat(testWorkReviewComment.getCommentDateTime()).isEqualTo(DEFAULT_COMMENT_DATE_TIME);
        assertThat(testWorkReviewComment.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createWorkReviewCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workReviewCommentRepository.findAll().size();

        // Create the WorkReviewComment with an existing ID
        workReviewComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkReviewCommentMockMvc.perform(post("/api/work-review-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workReviewComment)))
            .andExpect(status().isBadRequest());

        // Validate the WorkReviewComment in the database
        List<WorkReviewComment> workReviewCommentList = workReviewCommentRepository.findAll();
        assertThat(workReviewCommentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkReviewComments() throws Exception {
        // Initialize the database
        workReviewCommentRepository.saveAndFlush(workReviewComment);

        // Get all the workReviewCommentList
        restWorkReviewCommentMockMvc.perform(get("/api/work-review-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workReviewComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentDateTime").value(hasItem(sameInstant(DEFAULT_COMMENT_DATE_TIME))))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }
    
    @Test
    @Transactional
    public void getWorkReviewComment() throws Exception {
        // Initialize the database
        workReviewCommentRepository.saveAndFlush(workReviewComment);

        // Get the workReviewComment
        restWorkReviewCommentMockMvc.perform(get("/api/work-review-comments/{id}", workReviewComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workReviewComment.getId().intValue()))
            .andExpect(jsonPath("$.commentDateTime").value(sameInstant(DEFAULT_COMMENT_DATE_TIME)))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }
    @Test
    @Transactional
    public void getNonExistingWorkReviewComment() throws Exception {
        // Get the workReviewComment
        restWorkReviewCommentMockMvc.perform(get("/api/work-review-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkReviewComment() throws Exception {
        // Initialize the database
        workReviewCommentService.save(workReviewComment);

        int databaseSizeBeforeUpdate = workReviewCommentRepository.findAll().size();

        // Update the workReviewComment
        WorkReviewComment updatedWorkReviewComment = workReviewCommentRepository.findById(workReviewComment.getId()).get();
        // Disconnect from session so that the updates on updatedWorkReviewComment are not directly saved in db
        em.detach(updatedWorkReviewComment);
        updatedWorkReviewComment
            .commentDateTime(UPDATED_COMMENT_DATE_TIME)
            .comment(UPDATED_COMMENT);

        restWorkReviewCommentMockMvc.perform(put("/api/work-review-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkReviewComment)))
            .andExpect(status().isOk());

        // Validate the WorkReviewComment in the database
        List<WorkReviewComment> workReviewCommentList = workReviewCommentRepository.findAll();
        assertThat(workReviewCommentList).hasSize(databaseSizeBeforeUpdate);
        WorkReviewComment testWorkReviewComment = workReviewCommentList.get(workReviewCommentList.size() - 1);
        assertThat(testWorkReviewComment.getCommentDateTime()).isEqualTo(UPDATED_COMMENT_DATE_TIME);
        assertThat(testWorkReviewComment.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkReviewComment() throws Exception {
        int databaseSizeBeforeUpdate = workReviewCommentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkReviewCommentMockMvc.perform(put("/api/work-review-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workReviewComment)))
            .andExpect(status().isBadRequest());

        // Validate the WorkReviewComment in the database
        List<WorkReviewComment> workReviewCommentList = workReviewCommentRepository.findAll();
        assertThat(workReviewCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkReviewComment() throws Exception {
        // Initialize the database
        workReviewCommentService.save(workReviewComment);

        int databaseSizeBeforeDelete = workReviewCommentRepository.findAll().size();

        // Delete the workReviewComment
        restWorkReviewCommentMockMvc.perform(delete("/api/work-review-comments/{id}", workReviewComment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkReviewComment> workReviewCommentList = workReviewCommentRepository.findAll();
        assertThat(workReviewCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
