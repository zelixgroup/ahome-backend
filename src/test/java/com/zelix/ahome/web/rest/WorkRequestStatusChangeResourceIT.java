package com.zelix.ahome.web.rest;

import com.zelix.ahome.AhomeApp;
import com.zelix.ahome.domain.WorkRequestStatusChange;
import com.zelix.ahome.repository.WorkRequestStatusChangeRepository;
import com.zelix.ahome.service.WorkRequestStatusChangeService;

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

import com.zelix.ahome.domain.enumeration.WorkRequestStatus;
import com.zelix.ahome.domain.enumeration.WorkRequestStatus;
/**
 * Integration tests for the {@link WorkRequestStatusChangeResource} REST controller.
 */
@SpringBootTest(classes = AhomeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkRequestStatusChangeResourceIT {

    private static final WorkRequestStatus DEFAULT_OLD_STATUS = WorkRequestStatus.SUBMITTED;
    private static final WorkRequestStatus UPDATED_OLD_STATUS = WorkRequestStatus.ACCEPTED;

    private static final WorkRequestStatus DEFAULT_NEW_STATUS = WorkRequestStatus.SUBMITTED;
    private static final WorkRequestStatus UPDATED_NEW_STATUS = WorkRequestStatus.ACCEPTED;

    private static final ZonedDateTime DEFAULT_DATE_TIME_OF_STATUS_CHANGE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIME_OF_STATUS_CHANGE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private WorkRequestStatusChangeRepository workRequestStatusChangeRepository;

    @Autowired
    private WorkRequestStatusChangeService workRequestStatusChangeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkRequestStatusChangeMockMvc;

    private WorkRequestStatusChange workRequestStatusChange;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkRequestStatusChange createEntity(EntityManager em) {
        WorkRequestStatusChange workRequestStatusChange = new WorkRequestStatusChange()
            .oldStatus(DEFAULT_OLD_STATUS)
            .newStatus(DEFAULT_NEW_STATUS)
            .dateTimeOfStatusChange(DEFAULT_DATE_TIME_OF_STATUS_CHANGE);
        return workRequestStatusChange;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkRequestStatusChange createUpdatedEntity(EntityManager em) {
        WorkRequestStatusChange workRequestStatusChange = new WorkRequestStatusChange()
            .oldStatus(UPDATED_OLD_STATUS)
            .newStatus(UPDATED_NEW_STATUS)
            .dateTimeOfStatusChange(UPDATED_DATE_TIME_OF_STATUS_CHANGE);
        return workRequestStatusChange;
    }

    @BeforeEach
    public void initTest() {
        workRequestStatusChange = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkRequestStatusChange() throws Exception {
        int databaseSizeBeforeCreate = workRequestStatusChangeRepository.findAll().size();
        // Create the WorkRequestStatusChange
        restWorkRequestStatusChangeMockMvc.perform(post("/api/work-request-status-changes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workRequestStatusChange)))
            .andExpect(status().isCreated());

        // Validate the WorkRequestStatusChange in the database
        List<WorkRequestStatusChange> workRequestStatusChangeList = workRequestStatusChangeRepository.findAll();
        assertThat(workRequestStatusChangeList).hasSize(databaseSizeBeforeCreate + 1);
        WorkRequestStatusChange testWorkRequestStatusChange = workRequestStatusChangeList.get(workRequestStatusChangeList.size() - 1);
        assertThat(testWorkRequestStatusChange.getOldStatus()).isEqualTo(DEFAULT_OLD_STATUS);
        assertThat(testWorkRequestStatusChange.getNewStatus()).isEqualTo(DEFAULT_NEW_STATUS);
        assertThat(testWorkRequestStatusChange.getDateTimeOfStatusChange()).isEqualTo(DEFAULT_DATE_TIME_OF_STATUS_CHANGE);
    }

    @Test
    @Transactional
    public void createWorkRequestStatusChangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workRequestStatusChangeRepository.findAll().size();

        // Create the WorkRequestStatusChange with an existing ID
        workRequestStatusChange.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkRequestStatusChangeMockMvc.perform(post("/api/work-request-status-changes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workRequestStatusChange)))
            .andExpect(status().isBadRequest());

        // Validate the WorkRequestStatusChange in the database
        List<WorkRequestStatusChange> workRequestStatusChangeList = workRequestStatusChangeRepository.findAll();
        assertThat(workRequestStatusChangeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkRequestStatusChanges() throws Exception {
        // Initialize the database
        workRequestStatusChangeRepository.saveAndFlush(workRequestStatusChange);

        // Get all the workRequestStatusChangeList
        restWorkRequestStatusChangeMockMvc.perform(get("/api/work-request-status-changes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workRequestStatusChange.getId().intValue())))
            .andExpect(jsonPath("$.[*].oldStatus").value(hasItem(DEFAULT_OLD_STATUS.toString())))
            .andExpect(jsonPath("$.[*].newStatus").value(hasItem(DEFAULT_NEW_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dateTimeOfStatusChange").value(hasItem(sameInstant(DEFAULT_DATE_TIME_OF_STATUS_CHANGE))));
    }
    
    @Test
    @Transactional
    public void getWorkRequestStatusChange() throws Exception {
        // Initialize the database
        workRequestStatusChangeRepository.saveAndFlush(workRequestStatusChange);

        // Get the workRequestStatusChange
        restWorkRequestStatusChangeMockMvc.perform(get("/api/work-request-status-changes/{id}", workRequestStatusChange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workRequestStatusChange.getId().intValue()))
            .andExpect(jsonPath("$.oldStatus").value(DEFAULT_OLD_STATUS.toString()))
            .andExpect(jsonPath("$.newStatus").value(DEFAULT_NEW_STATUS.toString()))
            .andExpect(jsonPath("$.dateTimeOfStatusChange").value(sameInstant(DEFAULT_DATE_TIME_OF_STATUS_CHANGE)));
    }
    @Test
    @Transactional
    public void getNonExistingWorkRequestStatusChange() throws Exception {
        // Get the workRequestStatusChange
        restWorkRequestStatusChangeMockMvc.perform(get("/api/work-request-status-changes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkRequestStatusChange() throws Exception {
        // Initialize the database
        workRequestStatusChangeService.save(workRequestStatusChange);

        int databaseSizeBeforeUpdate = workRequestStatusChangeRepository.findAll().size();

        // Update the workRequestStatusChange
        WorkRequestStatusChange updatedWorkRequestStatusChange = workRequestStatusChangeRepository.findById(workRequestStatusChange.getId()).get();
        // Disconnect from session so that the updates on updatedWorkRequestStatusChange are not directly saved in db
        em.detach(updatedWorkRequestStatusChange);
        updatedWorkRequestStatusChange
            .oldStatus(UPDATED_OLD_STATUS)
            .newStatus(UPDATED_NEW_STATUS)
            .dateTimeOfStatusChange(UPDATED_DATE_TIME_OF_STATUS_CHANGE);

        restWorkRequestStatusChangeMockMvc.perform(put("/api/work-request-status-changes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkRequestStatusChange)))
            .andExpect(status().isOk());

        // Validate the WorkRequestStatusChange in the database
        List<WorkRequestStatusChange> workRequestStatusChangeList = workRequestStatusChangeRepository.findAll();
        assertThat(workRequestStatusChangeList).hasSize(databaseSizeBeforeUpdate);
        WorkRequestStatusChange testWorkRequestStatusChange = workRequestStatusChangeList.get(workRequestStatusChangeList.size() - 1);
        assertThat(testWorkRequestStatusChange.getOldStatus()).isEqualTo(UPDATED_OLD_STATUS);
        assertThat(testWorkRequestStatusChange.getNewStatus()).isEqualTo(UPDATED_NEW_STATUS);
        assertThat(testWorkRequestStatusChange.getDateTimeOfStatusChange()).isEqualTo(UPDATED_DATE_TIME_OF_STATUS_CHANGE);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkRequestStatusChange() throws Exception {
        int databaseSizeBeforeUpdate = workRequestStatusChangeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkRequestStatusChangeMockMvc.perform(put("/api/work-request-status-changes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workRequestStatusChange)))
            .andExpect(status().isBadRequest());

        // Validate the WorkRequestStatusChange in the database
        List<WorkRequestStatusChange> workRequestStatusChangeList = workRequestStatusChangeRepository.findAll();
        assertThat(workRequestStatusChangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkRequestStatusChange() throws Exception {
        // Initialize the database
        workRequestStatusChangeService.save(workRequestStatusChange);

        int databaseSizeBeforeDelete = workRequestStatusChangeRepository.findAll().size();

        // Delete the workRequestStatusChange
        restWorkRequestStatusChangeMockMvc.perform(delete("/api/work-request-status-changes/{id}", workRequestStatusChange.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkRequestStatusChange> workRequestStatusChangeList = workRequestStatusChangeRepository.findAll();
        assertThat(workRequestStatusChangeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
