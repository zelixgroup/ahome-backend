package com.zelix.ahome.web.rest;

import com.zelix.ahome.AhomeApp;
import com.zelix.ahome.domain.Address;
import com.zelix.ahome.repository.AddressRepository;
import com.zelix.ahome.service.AddressService;

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

import com.zelix.ahome.domain.enumeration.AddressType;
/**
 * Integration tests for the {@link AddressResource} REST controller.
 */
@SpringBootTest(classes = AhomeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AddressResourceIT {

    private static final AddressType DEFAULT_TYPE = AddressType.HOME;
    private static final AddressType UPDATED_TYPE = AddressType.WORK;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_GEOLOCATION = "AAAAAAAAAA";
    private static final String UPDATED_GEOLOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .type(DEFAULT_TYPE)
            .location(DEFAULT_LOCATION)
            .geolocation(DEFAULT_GEOLOCATION)
            .primaryPhoneNumber(DEFAULT_PRIMARY_PHONE_NUMBER)
            .secondaryPhoneNumber(DEFAULT_SECONDARY_PHONE_NUMBER)
            .emailAddress(DEFAULT_EMAIL_ADDRESS);
        return address;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity(EntityManager em) {
        Address address = new Address()
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .geolocation(UPDATED_GEOLOCATION)
            .primaryPhoneNumber(UPDATED_PRIMARY_PHONE_NUMBER)
            .secondaryPhoneNumber(UPDATED_SECONDARY_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS);
        return address;
    }

    @BeforeEach
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();
        // Create the Address
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(address)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAddress.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testAddress.getGeolocation()).isEqualTo(DEFAULT_GEOLOCATION);
        assertThat(testAddress.getPrimaryPhoneNumber()).isEqualTo(DEFAULT_PRIMARY_PHONE_NUMBER);
        assertThat(testAddress.getSecondaryPhoneNumber()).isEqualTo(DEFAULT_SECONDARY_PHONE_NUMBER);
        assertThat(testAddress.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void createAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address with an existing ID
        address.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(address)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].geolocation").value(hasItem(DEFAULT_GEOLOCATION)))
            .andExpect(jsonPath("$.[*].primaryPhoneNumber").value(hasItem(DEFAULT_PRIMARY_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].secondaryPhoneNumber").value(hasItem(DEFAULT_SECONDARY_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.geolocation").value(DEFAULT_GEOLOCATION))
            .andExpect(jsonPath("$.primaryPhoneNumber").value(DEFAULT_PRIMARY_PHONE_NUMBER))
            .andExpect(jsonPath("$.secondaryPhoneNumber").value(DEFAULT_SECONDARY_PHONE_NUMBER))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddress() throws Exception {
        // Initialize the database
        addressService.save(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .geolocation(UPDATED_GEOLOCATION)
            .primaryPhoneNumber(UPDATED_PRIMARY_PHONE_NUMBER)
            .secondaryPhoneNumber(UPDATED_SECONDARY_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS);

        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAddress)))
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAddress.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testAddress.getGeolocation()).isEqualTo(UPDATED_GEOLOCATION);
        assertThat(testAddress.getPrimaryPhoneNumber()).isEqualTo(UPDATED_PRIMARY_PHONE_NUMBER);
        assertThat(testAddress.getSecondaryPhoneNumber()).isEqualTo(UPDATED_SECONDARY_PHONE_NUMBER);
        assertThat(testAddress.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(address)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressService.save(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Delete the address
        restAddressMockMvc.perform(delete("/api/addresses/{id}", address.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
