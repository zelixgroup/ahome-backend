package com.zelix.ahome.web.rest;

import com.zelix.ahome.AhomeApp;
import com.zelix.ahome.domain.Worker;
import com.zelix.ahome.repository.WorkerRepository;
import com.zelix.ahome.service.WorkerService;

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
 * Integration tests for the {@link WorkerResource} REST controller.
 */
@SpringBootTest(classes = AhomeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkerResourceIT {

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_WORK_CERTIFICATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_WORK_CERTIFICATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_WORK_CERTIFICATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_WORK_CERTIFICATE_CONTENT_TYPE = "image/png";

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkerMockMvc;

    private Worker worker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Worker createEntity(EntityManager em) {
        Worker worker = new Worker()
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE)
            .workCertificate(DEFAULT_WORK_CERTIFICATE)
            .workCertificateContentType(DEFAULT_WORK_CERTIFICATE_CONTENT_TYPE);
        return worker;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Worker createUpdatedEntity(EntityManager em) {
        Worker worker = new Worker()
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .workCertificate(UPDATED_WORK_CERTIFICATE)
            .workCertificateContentType(UPDATED_WORK_CERTIFICATE_CONTENT_TYPE);
        return worker;
    }

    @BeforeEach
    public void initTest() {
        worker = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorker() throws Exception {
        int databaseSizeBeforeCreate = workerRepository.findAll().size();
        // Create the Worker
        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isCreated());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeCreate + 1);
        Worker testWorker = workerList.get(workerList.size() - 1);
        assertThat(testWorker.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testWorker.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
        assertThat(testWorker.getWorkCertificate()).isEqualTo(DEFAULT_WORK_CERTIFICATE);
        assertThat(testWorker.getWorkCertificateContentType()).isEqualTo(DEFAULT_WORK_CERTIFICATE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createWorkerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workerRepository.findAll().size();

        // Create the Worker with an existing ID
        worker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkers() throws Exception {
        // Initialize the database
        workerRepository.saveAndFlush(worker);

        // Get all the workerList
        restWorkerMockMvc.perform(get("/api/workers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(worker.getId().intValue())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].workCertificateContentType").value(hasItem(DEFAULT_WORK_CERTIFICATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].workCertificate").value(hasItem(Base64Utils.encodeToString(DEFAULT_WORK_CERTIFICATE))));
    }
    
    @Test
    @Transactional
    public void getWorker() throws Exception {
        // Initialize the database
        workerRepository.saveAndFlush(worker);

        // Get the worker
        restWorkerMockMvc.perform(get("/api/workers/{id}", worker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(worker.getId().intValue()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.workCertificateContentType").value(DEFAULT_WORK_CERTIFICATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.workCertificate").value(Base64Utils.encodeToString(DEFAULT_WORK_CERTIFICATE)));
    }
    @Test
    @Transactional
    public void getNonExistingWorker() throws Exception {
        // Get the worker
        restWorkerMockMvc.perform(get("/api/workers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorker() throws Exception {
        // Initialize the database
        workerService.save(worker);

        int databaseSizeBeforeUpdate = workerRepository.findAll().size();

        // Update the worker
        Worker updatedWorker = workerRepository.findById(worker.getId()).get();
        // Disconnect from session so that the updates on updatedWorker are not directly saved in db
        em.detach(updatedWorker);
        updatedWorker
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .workCertificate(UPDATED_WORK_CERTIFICATE)
            .workCertificateContentType(UPDATED_WORK_CERTIFICATE_CONTENT_TYPE);

        restWorkerMockMvc.perform(put("/api/workers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorker)))
            .andExpect(status().isOk());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate);
        Worker testWorker = workerList.get(workerList.size() - 1);
        assertThat(testWorker.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testWorker.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testWorker.getWorkCertificate()).isEqualTo(UPDATED_WORK_CERTIFICATE);
        assertThat(testWorker.getWorkCertificateContentType()).isEqualTo(UPDATED_WORK_CERTIFICATE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingWorker() throws Exception {
        int databaseSizeBeforeUpdate = workerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkerMockMvc.perform(put("/api/workers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorker() throws Exception {
        // Initialize the database
        workerService.save(worker);

        int databaseSizeBeforeDelete = workerRepository.findAll().size();

        // Delete the worker
        restWorkerMockMvc.perform(delete("/api/workers/{id}", worker.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
