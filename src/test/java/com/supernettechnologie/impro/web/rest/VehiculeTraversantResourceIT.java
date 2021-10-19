package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.VehiculeTraversant;
import com.supernettechnologie.impro.repository.VehiculeTraversantRepository;
import com.supernettechnologie.impro.service.VehiculeTraversantService;
import com.supernettechnologie.impro.service.dto.VehiculeTraversantDTO;
import com.supernettechnologie.impro.service.mapper.VehiculeTraversantMapper;

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

import static com.supernettechnologie.impro.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VehiculeTraversantResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VehiculeTraversantResourceIT {

    private static final String DEFAULT_CHASSIS = "AAAAAAAAAA";
    private static final String UPDATED_CHASSIS = "BBBBBBBBBB";

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private VehiculeTraversantRepository vehiculeTraversantRepository;

    @Autowired
    private VehiculeTraversantMapper vehiculeTraversantMapper;

    @Autowired
    private VehiculeTraversantService vehiculeTraversantService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehiculeTraversantMockMvc;

    private VehiculeTraversant vehiculeTraversant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehiculeTraversant createEntity(EntityManager em) {
        VehiculeTraversant vehiculeTraversant = new VehiculeTraversant()
            .chassis(DEFAULT_CHASSIS)
            .marque(DEFAULT_MARQUE)
            .model(DEFAULT_MODEL)
            .createdAt(DEFAULT_CREATED_AT);
        return vehiculeTraversant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehiculeTraversant createUpdatedEntity(EntityManager em) {
        VehiculeTraversant vehiculeTraversant = new VehiculeTraversant()
            .chassis(UPDATED_CHASSIS)
            .marque(UPDATED_MARQUE)
            .model(UPDATED_MODEL)
            .createdAt(UPDATED_CREATED_AT);
        return vehiculeTraversant;
    }

    @BeforeEach
    public void initTest() {
        vehiculeTraversant = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehiculeTraversant() throws Exception {
        int databaseSizeBeforeCreate = vehiculeTraversantRepository.findAll().size();
        // Create the VehiculeTraversant
        VehiculeTraversantDTO vehiculeTraversantDTO = vehiculeTraversantMapper.toDto(vehiculeTraversant);
        restVehiculeTraversantMockMvc.perform(post("/api/vehicule-traversants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeTraversantDTO)))
            .andExpect(status().isCreated());

        // Validate the VehiculeTraversant in the database
        List<VehiculeTraversant> vehiculeTraversantList = vehiculeTraversantRepository.findAll();
        assertThat(vehiculeTraversantList).hasSize(databaseSizeBeforeCreate + 1);
        VehiculeTraversant testVehiculeTraversant = vehiculeTraversantList.get(vehiculeTraversantList.size() - 1);
        assertThat(testVehiculeTraversant.getChassis()).isEqualTo(DEFAULT_CHASSIS);
        assertThat(testVehiculeTraversant.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testVehiculeTraversant.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehiculeTraversant.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    public void createVehiculeTraversantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiculeTraversantRepository.findAll().size();

        // Create the VehiculeTraversant with an existing ID
        vehiculeTraversant.setId(1L);
        VehiculeTraversantDTO vehiculeTraversantDTO = vehiculeTraversantMapper.toDto(vehiculeTraversant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculeTraversantMockMvc.perform(post("/api/vehicule-traversants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeTraversantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehiculeTraversant in the database
        List<VehiculeTraversant> vehiculeTraversantList = vehiculeTraversantRepository.findAll();
        assertThat(vehiculeTraversantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVehiculeTraversants() throws Exception {
        // Initialize the database
        vehiculeTraversantRepository.saveAndFlush(vehiculeTraversant);

        // Get all the vehiculeTraversantList
        restVehiculeTraversantMockMvc.perform(get("/api/vehicule-traversants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiculeTraversant.getId().intValue())))
            .andExpect(jsonPath("$.[*].chassis").value(hasItem(DEFAULT_CHASSIS)))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }
    
    @Test
    @Transactional
    public void getVehiculeTraversant() throws Exception {
        // Initialize the database
        vehiculeTraversantRepository.saveAndFlush(vehiculeTraversant);

        // Get the vehiculeTraversant
        restVehiculeTraversantMockMvc.perform(get("/api/vehicule-traversants/{id}", vehiculeTraversant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehiculeTraversant.getId().intValue()))
            .andExpect(jsonPath("$.chassis").value(DEFAULT_CHASSIS))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }
    @Test
    @Transactional
    public void getNonExistingVehiculeTraversant() throws Exception {
        // Get the vehiculeTraversant
        restVehiculeTraversantMockMvc.perform(get("/api/vehicule-traversants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehiculeTraversant() throws Exception {
        // Initialize the database
        vehiculeTraversantRepository.saveAndFlush(vehiculeTraversant);

        int databaseSizeBeforeUpdate = vehiculeTraversantRepository.findAll().size();

        // Update the vehiculeTraversant
        VehiculeTraversant updatedVehiculeTraversant = vehiculeTraversantRepository.findById(vehiculeTraversant.getId()).get();
        // Disconnect from session so that the updates on updatedVehiculeTraversant are not directly saved in db
        em.detach(updatedVehiculeTraversant);
        updatedVehiculeTraversant
            .chassis(UPDATED_CHASSIS)
            .marque(UPDATED_MARQUE)
            .model(UPDATED_MODEL)
            .createdAt(UPDATED_CREATED_AT);
        VehiculeTraversantDTO vehiculeTraversantDTO = vehiculeTraversantMapper.toDto(updatedVehiculeTraversant);

        restVehiculeTraversantMockMvc.perform(put("/api/vehicule-traversants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeTraversantDTO)))
            .andExpect(status().isOk());

        // Validate the VehiculeTraversant in the database
        List<VehiculeTraversant> vehiculeTraversantList = vehiculeTraversantRepository.findAll();
        assertThat(vehiculeTraversantList).hasSize(databaseSizeBeforeUpdate);
        VehiculeTraversant testVehiculeTraversant = vehiculeTraversantList.get(vehiculeTraversantList.size() - 1);
        assertThat(testVehiculeTraversant.getChassis()).isEqualTo(UPDATED_CHASSIS);
        assertThat(testVehiculeTraversant.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testVehiculeTraversant.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehiculeTraversant.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingVehiculeTraversant() throws Exception {
        int databaseSizeBeforeUpdate = vehiculeTraversantRepository.findAll().size();

        // Create the VehiculeTraversant
        VehiculeTraversantDTO vehiculeTraversantDTO = vehiculeTraversantMapper.toDto(vehiculeTraversant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculeTraversantMockMvc.perform(put("/api/vehicule-traversants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeTraversantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehiculeTraversant in the database
        List<VehiculeTraversant> vehiculeTraversantList = vehiculeTraversantRepository.findAll();
        assertThat(vehiculeTraversantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehiculeTraversant() throws Exception {
        // Initialize the database
        vehiculeTraversantRepository.saveAndFlush(vehiculeTraversant);

        int databaseSizeBeforeDelete = vehiculeTraversantRepository.findAll().size();

        // Delete the vehiculeTraversant
        restVehiculeTraversantMockMvc.perform(delete("/api/vehicule-traversants/{id}", vehiculeTraversant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehiculeTraversant> vehiculeTraversantList = vehiculeTraversantRepository.findAll();
        assertThat(vehiculeTraversantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
