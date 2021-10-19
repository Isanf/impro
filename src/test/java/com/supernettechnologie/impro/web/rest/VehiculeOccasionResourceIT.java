package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.VehiculeOccasion;
import com.supernettechnologie.impro.domain.Organisation;
import com.supernettechnologie.impro.repository.VehiculeOccasionRepository;

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
 * Integration tests for the {@link VehiculeOccasionResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VehiculeOccasionResourceIT {

    private static final String DEFAULT_CHASSIS = "AAAAAAAAAA";
    private static final String UPDATED_CHASSIS = "BBBBBBBBBB";

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CARTE_W = "AAAAAAAAAA";
    private static final String UPDATED_CARTE_W = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NUMERO_CNIB = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CNIB = "BBBBBBBBBB";

    @Autowired
    private VehiculeOccasionRepository vehiculeOccasionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehiculeOccasionMockMvc;

    private VehiculeOccasion vehiculeOccasion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehiculeOccasion createEntity(EntityManager em) {
        VehiculeOccasion vehiculeOccasion = new VehiculeOccasion()
            .chassis(DEFAULT_CHASSIS)
            .marque(DEFAULT_MARQUE)
            .model(DEFAULT_MODEL)
            .nomPrenom(DEFAULT_NOM_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .carteW(DEFAULT_CARTE_W)
            .createdAt(DEFAULT_CREATED_AT)
            .numeroCNIB(DEFAULT_NUMERO_CNIB);
        // Add required entity
        Organisation organisation;
        if (TestUtil.findAll(em, Organisation.class).isEmpty()) {
            organisation = OrganisationResourceIT.createEntity(em);
            em.persist(organisation);
            em.flush();
        } else {
            organisation = TestUtil.findAll(em, Organisation.class).get(0);
        }
        vehiculeOccasion.setOrganisation(organisation);
        return vehiculeOccasion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehiculeOccasion createUpdatedEntity(EntityManager em) {
        VehiculeOccasion vehiculeOccasion = new VehiculeOccasion()
            .chassis(UPDATED_CHASSIS)
            .marque(UPDATED_MARQUE)
            .model(UPDATED_MODEL)
            .nomPrenom(UPDATED_NOM_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .carteW(UPDATED_CARTE_W)
            .createdAt(UPDATED_CREATED_AT)
            .numeroCNIB(UPDATED_NUMERO_CNIB);
        // Add required entity
        Organisation organisation;
        if (TestUtil.findAll(em, Organisation.class).isEmpty()) {
            organisation = OrganisationResourceIT.createUpdatedEntity(em);
            em.persist(organisation);
            em.flush();
        } else {
            organisation = TestUtil.findAll(em, Organisation.class).get(0);
        }
        vehiculeOccasion.setOrganisation(organisation);
        return vehiculeOccasion;
    }

    @BeforeEach
    public void initTest() {
        vehiculeOccasion = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehiculeOccasion() throws Exception {
        int databaseSizeBeforeCreate = vehiculeOccasionRepository.findAll().size();
        // Create the VehiculeOccasion
        restVehiculeOccasionMockMvc.perform(post("/api/vehicule-occasions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeOccasion)))
            .andExpect(status().isCreated());

        // Validate the VehiculeOccasion in the database
        List<VehiculeOccasion> vehiculeOccasionList = vehiculeOccasionRepository.findAll();
        assertThat(vehiculeOccasionList).hasSize(databaseSizeBeforeCreate + 1);
        VehiculeOccasion testVehiculeOccasion = vehiculeOccasionList.get(vehiculeOccasionList.size() - 1);
        assertThat(testVehiculeOccasion.getChassis()).isEqualTo(DEFAULT_CHASSIS);
        assertThat(testVehiculeOccasion.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testVehiculeOccasion.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehiculeOccasion.getNomPrenom()).isEqualTo(DEFAULT_NOM_PRENOM);
        assertThat(testVehiculeOccasion.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testVehiculeOccasion.getCarteW()).isEqualTo(DEFAULT_CARTE_W);
        assertThat(testVehiculeOccasion.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testVehiculeOccasion.getNumeroCNIB()).isEqualTo(DEFAULT_NUMERO_CNIB);
    }

    @Test
    @Transactional
    public void createVehiculeOccasionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiculeOccasionRepository.findAll().size();

        // Create the VehiculeOccasion with an existing ID
        vehiculeOccasion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculeOccasionMockMvc.perform(post("/api/vehicule-occasions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeOccasion)))
            .andExpect(status().isBadRequest());

        // Validate the VehiculeOccasion in the database
        List<VehiculeOccasion> vehiculeOccasionList = vehiculeOccasionRepository.findAll();
        assertThat(vehiculeOccasionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVehiculeOccasions() throws Exception {
        // Initialize the database
        vehiculeOccasionRepository.saveAndFlush(vehiculeOccasion);

        // Get all the vehiculeOccasionList
        restVehiculeOccasionMockMvc.perform(get("/api/vehicule-occasions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiculeOccasion.getId().intValue())))
            .andExpect(jsonPath("$.[*].chassis").value(hasItem(DEFAULT_CHASSIS)))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].nomPrenom").value(hasItem(DEFAULT_NOM_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].carteW").value(hasItem(DEFAULT_CARTE_W)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].numeroCNIB").value(hasItem(DEFAULT_NUMERO_CNIB)));
    }
    
    @Test
    @Transactional
    public void getVehiculeOccasion() throws Exception {
        // Initialize the database
        vehiculeOccasionRepository.saveAndFlush(vehiculeOccasion);

        // Get the vehiculeOccasion
        restVehiculeOccasionMockMvc.perform(get("/api/vehicule-occasions/{id}", vehiculeOccasion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehiculeOccasion.getId().intValue()))
            .andExpect(jsonPath("$.chassis").value(DEFAULT_CHASSIS))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.nomPrenom").value(DEFAULT_NOM_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.carteW").value(DEFAULT_CARTE_W))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.numeroCNIB").value(DEFAULT_NUMERO_CNIB));
    }
    @Test
    @Transactional
    public void getNonExistingVehiculeOccasion() throws Exception {
        // Get the vehiculeOccasion
        restVehiculeOccasionMockMvc.perform(get("/api/vehicule-occasions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehiculeOccasion() throws Exception {
        // Initialize the database
        vehiculeOccasionRepository.saveAndFlush(vehiculeOccasion);

        int databaseSizeBeforeUpdate = vehiculeOccasionRepository.findAll().size();

        // Update the vehiculeOccasion
        VehiculeOccasion updatedVehiculeOccasion = vehiculeOccasionRepository.findById(vehiculeOccasion.getId()).get();
        // Disconnect from session so that the updates on updatedVehiculeOccasion are not directly saved in db
        em.detach(updatedVehiculeOccasion);
        updatedVehiculeOccasion
            .chassis(UPDATED_CHASSIS)
            .marque(UPDATED_MARQUE)
            .model(UPDATED_MODEL)
            .nomPrenom(UPDATED_NOM_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .carteW(UPDATED_CARTE_W)
            .createdAt(UPDATED_CREATED_AT)
            .numeroCNIB(UPDATED_NUMERO_CNIB);

        restVehiculeOccasionMockMvc.perform(put("/api/vehicule-occasions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehiculeOccasion)))
            .andExpect(status().isOk());

        // Validate the VehiculeOccasion in the database
        List<VehiculeOccasion> vehiculeOccasionList = vehiculeOccasionRepository.findAll();
        assertThat(vehiculeOccasionList).hasSize(databaseSizeBeforeUpdate);
        VehiculeOccasion testVehiculeOccasion = vehiculeOccasionList.get(vehiculeOccasionList.size() - 1);
        assertThat(testVehiculeOccasion.getChassis()).isEqualTo(UPDATED_CHASSIS);
        assertThat(testVehiculeOccasion.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testVehiculeOccasion.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehiculeOccasion.getNomPrenom()).isEqualTo(UPDATED_NOM_PRENOM);
        assertThat(testVehiculeOccasion.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testVehiculeOccasion.getCarteW()).isEqualTo(UPDATED_CARTE_W);
        assertThat(testVehiculeOccasion.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testVehiculeOccasion.getNumeroCNIB()).isEqualTo(UPDATED_NUMERO_CNIB);
    }

    @Test
    @Transactional
    public void updateNonExistingVehiculeOccasion() throws Exception {
        int databaseSizeBeforeUpdate = vehiculeOccasionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculeOccasionMockMvc.perform(put("/api/vehicule-occasions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeOccasion)))
            .andExpect(status().isBadRequest());

        // Validate the VehiculeOccasion in the database
        List<VehiculeOccasion> vehiculeOccasionList = vehiculeOccasionRepository.findAll();
        assertThat(vehiculeOccasionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehiculeOccasion() throws Exception {
        // Initialize the database
        vehiculeOccasionRepository.saveAndFlush(vehiculeOccasion);

        int databaseSizeBeforeDelete = vehiculeOccasionRepository.findAll().size();

        // Delete the vehiculeOccasion
        restVehiculeOccasionMockMvc.perform(delete("/api/vehicule-occasions/{id}", vehiculeOccasion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehiculeOccasion> vehiculeOccasionList = vehiculeOccasionRepository.findAll();
        assertThat(vehiculeOccasionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
