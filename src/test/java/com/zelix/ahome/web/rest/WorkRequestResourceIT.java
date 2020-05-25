package com.zelix.ahome.web.rest;

import com.zelix.ahome.AhomeApp;
import com.zelix.ahome.domain.WorkRequest;
import com.zelix.ahome.repository.WorkRequestRepository;
import com.zelix.ahome.service.WorkRequestService;

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

import com.zelix.ahome.domain.enumeration.WorkRequestMagnitude;
import com.zelix.ahome.domain.enumeration.WorkRequestStatus;
/**
 * Integration tests for the {@link WorkRequestResource} REST controller.
 */
@SpringBootTest(classes = AhomeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkRequestResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_FOR_MYSEF = false;
    private static final Boolean UPDATED_FOR_MYSEF = true;

    private static final Boolean DEFAULT_CONSTRUCTION_SITE = false;
    private static final Boolean UPDATED_CONSTRUCTION_SITE = true;

    private static final Integer DEFAULT_MEDIATOR_PERCENTAGE = 1;
    private static final Integer UPDATED_MEDIATOR_PERCENTAGE = 2;

    private static final String DEFAULT_DETAILED_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DETAILED_DESCRIPTION = "BBBBBBBBBB";

    private static final WorkRequestMagnitude DEFAULT_MAGNITUDE = WorkRequestMagnitude.LARGE;
    private static final WorkRequestMagnitude UPDATED_MAGNITUDE = WorkRequestMagnitude.MEDIUM;

    private static final BigDecimal DEFAULT_ESTIMATED_WORK_FEES = new BigDecimal(1);
    private static final BigDecimal UPDATED_ESTIMATED_WORK_FEES = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_PLANNED_START_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PLANNED_START_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PLANNED_END_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PLANNED_END_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final WorkRequestStatus DEFAULT_STATUS = WorkRequestStatus.SUBMITTED;
    private static final WorkRequestStatus UPDATED_STATUS = WorkRequestStatus.ACCEPTED;

    @Autowired
    private WorkRequestRepository workRequestRepository;

    @Autowired
    private WorkRequestService workRequestService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkRequestMockMvc;

    private WorkRequest workRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkRequest createEntity(EntityManager em) {
        WorkRequest workRequest = new WorkRequest()
            .creationDateTime(DEFAULT_CREATION_DATE_TIME)
            .forMysef(DEFAULT_FOR_MYSEF)
            .constructionSite(DEFAULT_CONSTRUCTION_SITE)
            .mediatorPercentage(DEFAULT_MEDIATOR_PERCENTAGE)
            .detailedDescription(DEFAULT_DETAILED_DESCRIPTION)
            .magnitude(DEFAULT_MAGNITUDE)
            .estimatedWorkFees(DEFAULT_ESTIMATED_WORK_FEES)
            .plannedStartDateTime(DEFAULT_PLANNED_START_DATE_TIME)
            .plannedEndDateTime(DEFAULT_PLANNED_END_DATE_TIME)
            .status(DEFAULT_STATUS);
        return workRequest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkRequest createUpdatedEntity(EntityManager em) {
        WorkRequest workRequest = new WorkRequest()
            .creationDateTime(UPDATED_CREATION_DATE_TIME)
            .forMysef(UPDATED_FOR_MYSEF)
            .constructionSite(UPDATED_CONSTRUCTION_SITE)
            .mediatorPercentage(UPDATED_MEDIATOR_PERCENTAGE)
            .detailedDescription(UPDATED_DETAILED_DESCRIPTION)
            .magnitude(UPDATED_MAGNITUDE)
            .estimatedWorkFees(UPDATED_ESTIMATED_WORK_FEES)
            .plannedStartDateTime(UPDATED_PLANNED_START_DATE_TIME)
            .plannedEndDateTime(UPDATED_PLANNED_END_DATE_TIME)
            .status(UPDATED_STATUS);
        return workRequest;
    }

    @BeforeEach
    public void initTest() {
        workRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkRequest() throws Exception {
        int databaseSizeBeforeCreate = workRequestRepository.findAll().size();
        // Create the WorkRequest
        restWorkRequestMockMvc.perform(post("/api/work-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workRequest)))
            .andExpect(status().isCreated());

        // Validate the WorkRequest in the database
        List<WorkRequest> workRequestList = workRequestRepository.findAll();
        assertThat(workRequestList).hasSize(databaseSizeBeforeCreate + 1);
        WorkRequest testWorkRequest = workRequestList.get(workRequestList.size() - 1);
        assertThat(testWorkRequest.getCreationDateTime()).isEqualTo(DEFAULT_CREATION_DATE_TIME);
        assertThat(testWorkRequest.isForMysef()).isEqualTo(DEFAULT_FOR_MYSEF);
        assertThat(testWorkRequest.isConstructionSite()).isEqualTo(DEFAULT_CONSTRUCTION_SITE);
        assertThat(testWorkRequest.getMediatorPercentage()).isEqualTo(DEFAULT_MEDIATOR_PERCENTAGE);
        assertThat(testWorkRequest.getDetailedDescription()).isEqualTo(DEFAULT_DETAILED_DESCRIPTION);
        assertThat(testWorkRequest.getMagnitude()).isEqualTo(DEFAULT_MAGNITUDE);
        assertThat(testWorkRequest.getEstimatedWorkFees()).isEqualTo(DEFAULT_ESTIMATED_WORK_FEES);
        assertThat(testWorkRequest.getPlannedStartDateTime()).isEqualTo(DEFAULT_PLANNED_START_DATE_TIME);
        assertThat(testWorkRequest.getPlannedEndDateTime()).isEqualTo(DEFAULT_PLANNED_END_DATE_TIME);
        assertThat(testWorkRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createWorkRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workRequestRepository.findAll().size();

        // Create the WorkRequest with an existing ID
        workRequest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkRequestMockMvc.perform(post("/api/work-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workRequest)))
            .andExpect(status().isBadRequest());

        // Validate the WorkRequest in the database
        List<WorkRequest> workRequestList = workRequestRepository.findAll();
        assertThat(workRequestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkRequests() throws Exception {
        // Initialize the database
        workRequestRepository.saveAndFlush(workRequest);

        // Get all the workRequestList
        restWorkRequestMockMvc.perform(get("/api/work-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDateTime").value(hasItem(sameInstant(DEFAULT_CREATION_DATE_TIME))))
            .andExpect(jsonPath("$.[*].forMysef").value(hasItem(DEFAULT_FOR_MYSEF.booleanValue())))
            .andExpect(jsonPath("$.[*].constructionSite").value(hasItem(DEFAULT_CONSTRUCTION_SITE.booleanValue())))
            .andExpect(jsonPath("$.[*].mediatorPercentage").value(hasItem(DEFAULT_MEDIATOR_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].detailedDescription").value(hasItem(DEFAULT_DETAILED_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].magnitude").value(hasItem(DEFAULT_MAGNITUDE.toString())))
            .andExpect(jsonPath("$.[*].estimatedWorkFees").value(hasItem(DEFAULT_ESTIMATED_WORK_FEES.intValue())))
            .andExpect(jsonPath("$.[*].plannedStartDateTime").value(hasItem(sameInstant(DEFAULT_PLANNED_START_DATE_TIME))))
            .andExpect(jsonPath("$.[*].plannedEndDateTime").value(hasItem(sameInstant(DEFAULT_PLANNED_END_DATE_TIME))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getWorkRequest() throws Exception {
        // Initialize the database
        workRequestRepository.saveAndFlush(workRequest);

        // Get the workRequest
        restWorkRequestMockMvc.perform(get("/api/work-requests/{id}", workRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workRequest.getId().intValue()))
            .andExpect(jsonPath("$.creationDateTime").value(sameInstant(DEFAULT_CREATION_DATE_TIME)))
            .andExpect(jsonPath("$.forMysef").value(DEFAULT_FOR_MYSEF.booleanValue()))
            .andExpect(jsonPath("$.constructionSite").value(DEFAULT_CONSTRUCTION_SITE.booleanValue()))
            .andExpect(jsonPath("$.mediatorPercentage").value(DEFAULT_MEDIATOR_PERCENTAGE))
            .andExpect(jsonPath("$.detailedDescription").value(DEFAULT_DETAILED_DESCRIPTION))
            .andExpect(jsonPath("$.magnitude").value(DEFAULT_MAGNITUDE.toString()))
            .andExpect(jsonPath("$.estimatedWorkFees").value(DEFAULT_ESTIMATED_WORK_FEES.intValue()))
            .andExpect(jsonPath("$.plannedStartDateTime").value(sameInstant(DEFAULT_PLANNED_START_DATE_TIME)))
            .andExpect(jsonPath("$.plannedEndDateTime").value(sameInstant(DEFAULT_PLANNED_END_DATE_TIME)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingWorkRequest() throws Exception {
        // Get the workRequest
        restWorkRequestMockMvc.perform(get("/api/work-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkRequest() throws Exception {
        // Initialize the database
        workRequestService.save(workRequest);

        int databaseSizeBeforeUpdate = workRequestRepository.findAll().size();

        // Update the workRequest
        WorkRequest updatedWorkRequest = workRequestRepository.findById(workRequest.getId()).get();
        // Disconnect from session so that the updates on updatedWorkRequest are not directly saved in db
        em.detach(updatedWorkRequest);
        updatedWorkRequest
            .creationDateTime(UPDATED_CREATION_DATE_TIME)
            .forMysef(UPDATED_FOR_MYSEF)
            .constructionSite(UPDATED_CONSTRUCTION_SITE)
            .mediatorPercentage(UPDATED_MEDIATOR_PERCENTAGE)
            .detailedDescription(UPDATED_DETAILED_DESCRIPTION)
            .magnitude(UPDATED_MAGNITUDE)
            .estimatedWorkFees(UPDATED_ESTIMATED_WORK_FEES)
            .plannedStartDateTime(UPDATED_PLANNED_START_DATE_TIME)
            .plannedEndDateTime(UPDATED_PLANNED_END_DATE_TIME)
            .status(UPDATED_STATUS);

        restWorkRequestMockMvc.perform(put("/api/work-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkRequest)))
            .andExpect(status().isOk());

        // Validate the WorkRequest in the database
        List<WorkRequest> workRequestList = workRequestRepository.findAll();
        assertThat(workRequestList).hasSize(databaseSizeBeforeUpdate);
        WorkRequest testWorkRequest = workRequestList.get(workRequestList.size() - 1);
        assertThat(testWorkRequest.getCreationDateTime()).isEqualTo(UPDATED_CREATION_DATE_TIME);
        assertThat(testWorkRequest.isForMysef()).isEqualTo(UPDATED_FOR_MYSEF);
        assertThat(testWorkRequest.isConstructionSite()).isEqualTo(UPDATED_CONSTRUCTION_SITE);
        assertThat(testWorkRequest.getMediatorPercentage()).isEqualTo(UPDATED_MEDIATOR_PERCENTAGE);
        assertThat(testWorkRequest.getDetailedDescription()).isEqualTo(UPDATED_DETAILED_DESCRIPTION);
        assertThat(testWorkRequest.getMagnitude()).isEqualTo(UPDATED_MAGNITUDE);
        assertThat(testWorkRequest.getEstimatedWorkFees()).isEqualTo(UPDATED_ESTIMATED_WORK_FEES);
        assertThat(testWorkRequest.getPlannedStartDateTime()).isEqualTo(UPDATED_PLANNED_START_DATE_TIME);
        assertThat(testWorkRequest.getPlannedEndDateTime()).isEqualTo(UPDATED_PLANNED_END_DATE_TIME);
        assertThat(testWorkRequest.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkRequest() throws Exception {
        int databaseSizeBeforeUpdate = workRequestRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkRequestMockMvc.perform(put("/api/work-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workRequest)))
            .andExpect(status().isBadRequest());

        // Validate the WorkRequest in the database
        List<WorkRequest> workRequestList = workRequestRepository.findAll();
        assertThat(workRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkRequest() throws Exception {
        // Initialize the database
        workRequestService.save(workRequest);

        int databaseSizeBeforeDelete = workRequestRepository.findAll().size();

        // Delete the workRequest
        restWorkRequestMockMvc.perform(delete("/api/work-requests/{id}", workRequest.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkRequest> workRequestList = workRequestRepository.findAll();
        assertThat(workRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
