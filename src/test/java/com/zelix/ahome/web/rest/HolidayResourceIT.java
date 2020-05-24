package com.zelix.ahome.web.rest;

import com.zelix.ahome.AhomeApp;
import com.zelix.ahome.domain.Holiday;
import com.zelix.ahome.repository.HolidayRepository;
import com.zelix.ahome.service.HolidayService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HolidayResource} REST controller.
 */
@SpringBootTest(classes = AhomeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HolidayResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CORRESPONDING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CORRESPONDING_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHolidayMockMvc;

    private Holiday holiday;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Holiday createEntity(EntityManager em) {
        Holiday holiday = new Holiday()
            .label(DEFAULT_LABEL)
            .correspondingDate(DEFAULT_CORRESPONDING_DATE);
        return holiday;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Holiday createUpdatedEntity(EntityManager em) {
        Holiday holiday = new Holiday()
            .label(UPDATED_LABEL)
            .correspondingDate(UPDATED_CORRESPONDING_DATE);
        return holiday;
    }

    @BeforeEach
    public void initTest() {
        holiday = createEntity(em);
    }

    @Test
    @Transactional
    public void createHoliday() throws Exception {
        int databaseSizeBeforeCreate = holidayRepository.findAll().size();
        // Create the Holiday
        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holiday)))
            .andExpect(status().isCreated());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeCreate + 1);
        Holiday testHoliday = holidayList.get(holidayList.size() - 1);
        assertThat(testHoliday.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testHoliday.getCorrespondingDate()).isEqualTo(DEFAULT_CORRESPONDING_DATE);
    }

    @Test
    @Transactional
    public void createHolidayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = holidayRepository.findAll().size();

        // Create the Holiday with an existing ID
        holiday.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holiday)))
            .andExpect(status().isBadRequest());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHolidays() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList
        restHolidayMockMvc.perform(get("/api/holidays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holiday.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].correspondingDate").value(hasItem(DEFAULT_CORRESPONDING_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getHoliday() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get the holiday
        restHolidayMockMvc.perform(get("/api/holidays/{id}", holiday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(holiday.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.correspondingDate").value(DEFAULT_CORRESPONDING_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingHoliday() throws Exception {
        // Get the holiday
        restHolidayMockMvc.perform(get("/api/holidays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHoliday() throws Exception {
        // Initialize the database
        holidayService.save(holiday);

        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();

        // Update the holiday
        Holiday updatedHoliday = holidayRepository.findById(holiday.getId()).get();
        // Disconnect from session so that the updates on updatedHoliday are not directly saved in db
        em.detach(updatedHoliday);
        updatedHoliday
            .label(UPDATED_LABEL)
            .correspondingDate(UPDATED_CORRESPONDING_DATE);

        restHolidayMockMvc.perform(put("/api/holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHoliday)))
            .andExpect(status().isOk());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
        Holiday testHoliday = holidayList.get(holidayList.size() - 1);
        assertThat(testHoliday.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testHoliday.getCorrespondingDate()).isEqualTo(UPDATED_CORRESPONDING_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingHoliday() throws Exception {
        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHolidayMockMvc.perform(put("/api/holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holiday)))
            .andExpect(status().isBadRequest());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHoliday() throws Exception {
        // Initialize the database
        holidayService.save(holiday);

        int databaseSizeBeforeDelete = holidayRepository.findAll().size();

        // Delete the holiday
        restHolidayMockMvc.perform(delete("/api/holidays/{id}", holiday.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
