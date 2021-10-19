package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.PrixCertificat;
import com.supernettechnologie.impro.repository.PrixCertificatRepository;
import com.supernettechnologie.impro.service.PrixCertificatService;
import com.supernettechnologie.impro.service.dto.PrixCertificatDTO;
import com.supernettechnologie.impro.service.mapper.PrixCertificatMapper;

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

/**
 * Integration tests for the {@link PrixCertificatResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PrixCertificatResourceIT {

    private static final Long DEFAULT_PRIX = 1L;
    private static final Long UPDATED_PRIX = 2L;

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    @Autowired
    private PrixCertificatRepository prixCertificatRepository;

    @Autowired
    private PrixCertificatMapper prixCertificatMapper;

    @Autowired
    private PrixCertificatService prixCertificatService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrixCertificatMockMvc;

    private PrixCertificat prixCertificat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrixCertificat createEntity(EntityManager em) {
        PrixCertificat prixCertificat = new PrixCertificat()
            .prix(DEFAULT_PRIX)
            .activated(DEFAULT_ACTIVATED);
        return prixCertificat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrixCertificat createUpdatedEntity(EntityManager em) {
        PrixCertificat prixCertificat = new PrixCertificat()
            .prix(UPDATED_PRIX)
            .activated(UPDATED_ACTIVATED);
        return prixCertificat;
    }

    @BeforeEach
    public void initTest() {
        prixCertificat = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrixCertificat() throws Exception {
        int databaseSizeBeforeCreate = prixCertificatRepository.findAll().size();
        // Create the PrixCertificat
        PrixCertificatDTO prixCertificatDTO = prixCertificatMapper.toDto(prixCertificat);
        restPrixCertificatMockMvc.perform(post("/api/prix-certificats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prixCertificatDTO)))
            .andExpect(status().isCreated());

        // Validate the PrixCertificat in the database
        List<PrixCertificat> prixCertificatList = prixCertificatRepository.findAll();
        assertThat(prixCertificatList).hasSize(databaseSizeBeforeCreate + 1);
        PrixCertificat testPrixCertificat = prixCertificatList.get(prixCertificatList.size() - 1);
        assertThat(testPrixCertificat.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testPrixCertificat.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void createPrixCertificatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prixCertificatRepository.findAll().size();

        // Create the PrixCertificat with an existing ID
        prixCertificat.setId(1L);
        PrixCertificatDTO prixCertificatDTO = prixCertificatMapper.toDto(prixCertificat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrixCertificatMockMvc.perform(post("/api/prix-certificats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prixCertificatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrixCertificat in the database
        List<PrixCertificat> prixCertificatList = prixCertificatRepository.findAll();
        assertThat(prixCertificatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPrixIsRequired() throws Exception {
        int databaseSizeBeforeTest = prixCertificatRepository.findAll().size();
        // set the field null
        prixCertificat.setPrix(null);

        // Create the PrixCertificat, which fails.
        PrixCertificatDTO prixCertificatDTO = prixCertificatMapper.toDto(prixCertificat);


        restPrixCertificatMockMvc.perform(post("/api/prix-certificats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prixCertificatDTO)))
            .andExpect(status().isBadRequest());

        List<PrixCertificat> prixCertificatList = prixCertificatRepository.findAll();
        assertThat(prixCertificatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrixCertificats() throws Exception {
        // Initialize the database
        prixCertificatRepository.saveAndFlush(prixCertificat);

        // Get all the prixCertificatList
        restPrixCertificatMockMvc.perform(get("/api/prix-certificats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prixCertificat.getId().intValue())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPrixCertificat() throws Exception {
        // Initialize the database
        prixCertificatRepository.saveAndFlush(prixCertificat);

        // Get the prixCertificat
        restPrixCertificatMockMvc.perform(get("/api/prix-certificats/{id}", prixCertificat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prixCertificat.getId().intValue()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.intValue()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPrixCertificat() throws Exception {
        // Get the prixCertificat
        restPrixCertificatMockMvc.perform(get("/api/prix-certificats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrixCertificat() throws Exception {
        // Initialize the database
        prixCertificatRepository.saveAndFlush(prixCertificat);

        int databaseSizeBeforeUpdate = prixCertificatRepository.findAll().size();

        // Update the prixCertificat
        PrixCertificat updatedPrixCertificat = prixCertificatRepository.findById(prixCertificat.getId()).get();
        // Disconnect from session so that the updates on updatedPrixCertificat are not directly saved in db
        em.detach(updatedPrixCertificat);
        updatedPrixCertificat
            .prix(UPDATED_PRIX)
            .activated(UPDATED_ACTIVATED);
        PrixCertificatDTO prixCertificatDTO = prixCertificatMapper.toDto(updatedPrixCertificat);

        restPrixCertificatMockMvc.perform(put("/api/prix-certificats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prixCertificatDTO)))
            .andExpect(status().isOk());

        // Validate the PrixCertificat in the database
        List<PrixCertificat> prixCertificatList = prixCertificatRepository.findAll();
        assertThat(prixCertificatList).hasSize(databaseSizeBeforeUpdate);
        PrixCertificat testPrixCertificat = prixCertificatList.get(prixCertificatList.size() - 1);
        assertThat(testPrixCertificat.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testPrixCertificat.isActivated()).isEqualTo(UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPrixCertificat() throws Exception {
        int databaseSizeBeforeUpdate = prixCertificatRepository.findAll().size();

        // Create the PrixCertificat
        PrixCertificatDTO prixCertificatDTO = prixCertificatMapper.toDto(prixCertificat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrixCertificatMockMvc.perform(put("/api/prix-certificats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prixCertificatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrixCertificat in the database
        List<PrixCertificat> prixCertificatList = prixCertificatRepository.findAll();
        assertThat(prixCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrixCertificat() throws Exception {
        // Initialize the database
        prixCertificatRepository.saveAndFlush(prixCertificat);

        int databaseSizeBeforeDelete = prixCertificatRepository.findAll().size();

        // Delete the prixCertificat
        restPrixCertificatMockMvc.perform(delete("/api/prix-certificats/{id}", prixCertificat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrixCertificat> prixCertificatList = prixCertificatRepository.findAll();
        assertThat(prixCertificatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
