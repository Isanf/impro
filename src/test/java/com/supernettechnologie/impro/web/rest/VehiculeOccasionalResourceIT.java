package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.VehiculeOccasional;
import com.supernettechnologie.impro.repository.VehiculeOccasionalRepository;
import com.supernettechnologie.impro.service.VehiculeOccasionalService;
import com.supernettechnologie.impro.service.dto.VehiculeOccasionalDTO;
import com.supernettechnologie.impro.service.mapper.VehiculeOccasionalMapper;

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
 * Integration tests for the {@link VehiculeOccasionalResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VehiculeOccasionalResourceIT {

    private static final String DEFAULT_CHASSIS = "AAAAAAAAAA";
    private static final String UPDATED_CHASSIS = "BBBBBBBBBB";

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private VehiculeOccasionalRepository vehiculeOccasionalRepository;

    @Autowired
    private VehiculeOccasionalMapper vehiculeOccasionalMapper;

    @Autowired
    private VehiculeOccasionalService vehiculeOccasionalService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehiculeOccasionalMockMvc;

    private VehiculeOccasional vehiculeOccasional;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehiculeOccasional createEntity(EntityManager em) {
        VehiculeOccasional vehiculeOccasional = new VehiculeOccasional()
            .chassis(DEFAULT_CHASSIS)
            .marque(DEFAULT_MARQUE)
            .model(DEFAULT_MODEL)
            .createdAt(DEFAULT_CREATED_AT);
        return vehiculeOccasional;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehiculeOccasional createUpdatedEntity(EntityManager em) {
        VehiculeOccasional vehiculeOccasional = new VehiculeOccasional()
            .chassis(UPDATED_CHASSIS)
            .marque(UPDATED_MARQUE)
            .model(UPDATED_MODEL)
            .createdAt(UPDATED_CREATED_AT);
        return vehiculeOccasional;
    }

    @BeforeEach
    public void initTest() {
        vehiculeOccasional = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehiculeOccasional() throws Exception {
        int databaseSizeBeforeCreate = vehiculeOccasionalRepository.findAll().size();
        // Create the VehiculeOccasional
        VehiculeOccasionalDTO vehiculeOccasionalDTO = vehiculeOccasionalMapper.toDto(vehiculeOccasional);
        restVehiculeOccasionalMockMvc.perform(post("/api/vehicule-occasionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeOccasionalDTO)))
            .andExpect(status().isCreated());

        // Validate the VehiculeOccasional in the database
        List<VehiculeOccasional> vehiculeOccasionalList = vehiculeOccasionalRepository.findAll();
        assertThat(vehiculeOccasionalList).hasSize(databaseSizeBeforeCreate + 1);
        VehiculeOccasional testVehiculeOccasional = vehiculeOccasionalList.get(vehiculeOccasionalList.size() - 1);
        assertThat(testVehiculeOccasional.getChassis()).isEqualTo(DEFAULT_CHASSIS);
        assertThat(testVehiculeOccasional.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testVehiculeOccasional.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehiculeOccasional.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    public void createVehiculeOccasionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiculeOccasionalRepository.findAll().size();

        // Create the VehiculeOccasional with an existing ID
        vehiculeOccasional.setId(1L);
        VehiculeOccasionalDTO vehiculeOccasionalDTO = vehiculeOccasionalMapper.toDto(vehiculeOccasional);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculeOccasionalMockMvc.perform(post("/api/vehicule-occasionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeOccasionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehiculeOccasional in the database
        List<VehiculeOccasional> vehiculeOccasionalList = vehiculeOccasionalRepository.findAll();
        assertThat(vehiculeOccasionalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVehiculeOccasionals() throws Exception {
        // Initialize the database
        vehiculeOccasionalRepository.saveAndFlush(vehiculeOccasional);

        // Get all the vehiculeOccasionalList
        restVehiculeOccasionalMockMvc.perform(get("/api/vehicule-occasionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiculeOccasional.getId().intValue())))
            .andExpect(jsonPath("$.[*].chassis").value(hasItem(DEFAULT_CHASSIS)))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }
    
    @Test
    @Transactional
    public void getVehiculeOccasional() throws Exception {
        // Initialize the database
        vehiculeOccasionalRepository.saveAndFlush(vehiculeOccasional);

        // Get the vehiculeOccasional
        restVehiculeOccasionalMockMvc.perform(get("/api/vehicule-occasionals/{id}", vehiculeOccasional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehiculeOccasional.getId().intValue()))
            .andExpect(jsonPath("$.chassis").value(DEFAULT_CHASSIS))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }
    @Test
    @Transactional
    public void getNonExistingVehiculeOccasional() throws Exception {
        // Get the vehiculeOccasional
        restVehiculeOccasionalMockMvc.perform(get("/api/vehicule-occasionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehiculeOccasional() throws Exception {
        // Initialize the database
        vehiculeOccasionalRepository.saveAndFlush(vehiculeOccasional);

        int databaseSizeBeforeUpdate = vehiculeOccasionalRepository.findAll().size();

        // Update the vehiculeOccasional
        VehiculeOccasional updatedVehiculeOccasional = vehiculeOccasionalRepository.findById(vehiculeOccasional.getId()).get();
        // Disconnect from session so that the updates on updatedVehiculeOccasional are not directly saved in db
        em.detach(updatedVehiculeOccasional);
        updatedVehiculeOccasional
            .chassis(UPDATED_CHASSIS)
            .marque(UPDATED_MARQUE)
            .model(UPDATED_MODEL)
            .createdAt(UPDATED_CREATED_AT);
        VehiculeOccasionalDTO vehiculeOccasionalDTO = vehiculeOccasionalMapper.toDto(updatedVehiculeOccasional);

        restVehiculeOccasionalMockMvc.perform(put("/api/vehicule-occasionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeOccasionalDTO)))
            .andExpect(status().isOk());

        // Validate the VehiculeOccasional in the database
        List<VehiculeOccasional> vehiculeOccasionalList = vehiculeOccasionalRepository.findAll();
        assertThat(vehiculeOccasionalList).hasSize(databaseSizeBeforeUpdate);
        VehiculeOccasional testVehiculeOccasional = vehiculeOccasionalList.get(vehiculeOccasionalList.size() - 1);
        assertThat(testVehiculeOccasional.getChassis()).isEqualTo(UPDATED_CHASSIS);
        assertThat(testVehiculeOccasional.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testVehiculeOccasional.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehiculeOccasional.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingVehiculeOccasional() throws Exception {
        int databaseSizeBeforeUpdate = vehiculeOccasionalRepository.findAll().size();

        // Create the VehiculeOccasional
        VehiculeOccasionalDTO vehiculeOccasionalDTO = vehiculeOccasionalMapper.toDto(vehiculeOccasional);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculeOccasionalMockMvc.perform(put("/api/vehicule-occasionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeOccasionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehiculeOccasional in the database
        List<VehiculeOccasional> vehiculeOccasionalList = vehiculeOccasionalRepository.findAll();
        assertThat(vehiculeOccasionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehiculeOccasional() throws Exception {
        // Initialize the database
        vehiculeOccasionalRepository.saveAndFlush(vehiculeOccasional);

        int databaseSizeBeforeDelete = vehiculeOccasionalRepository.findAll().size();

        // Delete the vehiculeOccasional
        restVehiculeOccasionalMockMvc.perform(delete("/api/vehicule-occasionals/{id}", vehiculeOccasional.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehiculeOccasional> vehiculeOccasionalList = vehiculeOccasionalRepository.findAll();
        assertThat(vehiculeOccasionalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
