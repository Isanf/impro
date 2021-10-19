package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.PlaqueGarage;
import com.supernettechnologie.impro.domain.CarteW;
import com.supernettechnologie.impro.repository.PlaqueGarageRepository;
import com.supernettechnologie.impro.service.PlaqueGarageService;
import com.supernettechnologie.impro.service.dto.PlaqueGarageDTO;
import com.supernettechnologie.impro.service.mapper.PlaqueGarageMapper;

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
 * Integration tests for the {@link PlaqueGarageResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlaqueGarageResourceIT {

    private static final String DEFAULT_NUMERO_ORDRE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_ORDRE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PLAQUE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PLAQUE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_QR_PLAQUE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_QR_PLAQUE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private PlaqueGarageRepository plaqueGarageRepository;

    @Autowired
    private PlaqueGarageMapper plaqueGarageMapper;

    @Autowired
    private PlaqueGarageService plaqueGarageService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaqueGarageMockMvc;

    private PlaqueGarage plaqueGarage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaqueGarage createEntity(EntityManager em) {
        PlaqueGarage plaqueGarage = new PlaqueGarage()
            .numeroOrdre(DEFAULT_NUMERO_ORDRE)
            .numeroPlaque(DEFAULT_NUMERO_PLAQUE)
            .codeQrPlaque(DEFAULT_CODE_QR_PLAQUE)
            .createdAt(DEFAULT_CREATED_AT);
        return plaqueGarage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaqueGarage createUpdatedEntity(EntityManager em) {
        PlaqueGarage plaqueGarage = new PlaqueGarage()
            .numeroOrdre(UPDATED_NUMERO_ORDRE)
            .numeroPlaque(UPDATED_NUMERO_PLAQUE)
            .codeQrPlaque(UPDATED_CODE_QR_PLAQUE)
            .createdAt(UPDATED_CREATED_AT);
        return plaqueGarage;
    }

    @BeforeEach
    public void initTest() {
        plaqueGarage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaqueGarage() throws Exception {
        int databaseSizeBeforeCreate = plaqueGarageRepository.findAll().size();
        // Create the PlaqueGarage
        PlaqueGarageDTO plaqueGarageDTO = plaqueGarageMapper.toDto(plaqueGarage);
        restPlaqueGarageMockMvc.perform(post("/api/plaque-garages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plaqueGarageDTO)))
            .andExpect(status().isCreated());

        // Validate the PlaqueGarage in the database
        List<PlaqueGarage> plaqueGarageList = plaqueGarageRepository.findAll();
        assertThat(plaqueGarageList).hasSize(databaseSizeBeforeCreate + 1);
        PlaqueGarage testPlaqueGarage = plaqueGarageList.get(plaqueGarageList.size() - 1);
        assertThat(testPlaqueGarage.getNumeroOrdre()).isEqualTo(DEFAULT_NUMERO_ORDRE);
        assertThat(testPlaqueGarage.getNumeroPlaque()).isEqualTo(DEFAULT_NUMERO_PLAQUE);
        assertThat(testPlaqueGarage.getCodeQrPlaque()).isEqualTo(DEFAULT_CODE_QR_PLAQUE);
        assertThat(testPlaqueGarage.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    public void createPlaqueGarageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plaqueGarageRepository.findAll().size();

        // Create the PlaqueGarage with an existing ID
        plaqueGarage.setId(1L);
        PlaqueGarageDTO plaqueGarageDTO = plaqueGarageMapper.toDto(plaqueGarage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaqueGarageMockMvc.perform(post("/api/plaque-garages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plaqueGarageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlaqueGarage in the database
        List<PlaqueGarage> plaqueGarageList = plaqueGarageRepository.findAll();
        assertThat(plaqueGarageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlaqueGarages() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList
        restPlaqueGarageMockMvc.perform(get("/api/plaque-garages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaqueGarage.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroOrdre").value(hasItem(DEFAULT_NUMERO_ORDRE)))
            .andExpect(jsonPath("$.[*].numeroPlaque").value(hasItem(DEFAULT_NUMERO_PLAQUE)))
            .andExpect(jsonPath("$.[*].codeQrPlaque").value(hasItem(DEFAULT_CODE_QR_PLAQUE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }

    @Test
    @Transactional
    public void getPlaqueGarage() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get the plaqueGarage
        restPlaqueGarageMockMvc.perform(get("/api/plaque-garages/{id}", plaqueGarage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plaqueGarage.getId().intValue()))
            .andExpect(jsonPath("$.numeroOrdre").value(DEFAULT_NUMERO_ORDRE))
            .andExpect(jsonPath("$.numeroPlaque").value(DEFAULT_NUMERO_PLAQUE))
            .andExpect(jsonPath("$.codeQrPlaque").value(DEFAULT_CODE_QR_PLAQUE))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }


    @Test
    @Transactional
    public void getPlaqueGaragesByIdFiltering() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        Long id = plaqueGarage.getId();

        defaultPlaqueGarageShouldBeFound("id.equals=" + id);
        defaultPlaqueGarageShouldNotBeFound("id.notEquals=" + id);

        defaultPlaqueGarageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlaqueGarageShouldNotBeFound("id.greaterThan=" + id);

        defaultPlaqueGarageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlaqueGarageShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlaqueGaragesByNumeroOrdreIsEqualToSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where numeroOrdre equals to DEFAULT_NUMERO_ORDRE
        defaultPlaqueGarageShouldBeFound("numeroOrdre.equals=" + DEFAULT_NUMERO_ORDRE);

        // Get all the plaqueGarageList where numeroOrdre equals to UPDATED_NUMERO_ORDRE
        defaultPlaqueGarageShouldNotBeFound("numeroOrdre.equals=" + UPDATED_NUMERO_ORDRE);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByNumeroOrdreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where numeroOrdre not equals to DEFAULT_NUMERO_ORDRE
        defaultPlaqueGarageShouldNotBeFound("numeroOrdre.notEquals=" + DEFAULT_NUMERO_ORDRE);

        // Get all the plaqueGarageList where numeroOrdre not equals to UPDATED_NUMERO_ORDRE
        defaultPlaqueGarageShouldBeFound("numeroOrdre.notEquals=" + UPDATED_NUMERO_ORDRE);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByNumeroOrdreIsInShouldWork() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where numeroOrdre in DEFAULT_NUMERO_ORDRE or UPDATED_NUMERO_ORDRE
        defaultPlaqueGarageShouldBeFound("numeroOrdre.in=" + DEFAULT_NUMERO_ORDRE + "," + UPDATED_NUMERO_ORDRE);

        // Get all the plaqueGarageList where numeroOrdre equals to UPDATED_NUMERO_ORDRE
        defaultPlaqueGarageShouldNotBeFound("numeroOrdre.in=" + UPDATED_NUMERO_ORDRE);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByNumeroOrdreIsNullOrNotNull() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where numeroOrdre is not null
        defaultPlaqueGarageShouldBeFound("numeroOrdre.specified=true");

        // Get all the plaqueGarageList where numeroOrdre is null
        defaultPlaqueGarageShouldNotBeFound("numeroOrdre.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlaqueGaragesByNumeroOrdreContainsSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where numeroOrdre contains DEFAULT_NUMERO_ORDRE
        defaultPlaqueGarageShouldBeFound("numeroOrdre.contains=" + DEFAULT_NUMERO_ORDRE);

        // Get all the plaqueGarageList where numeroOrdre contains UPDATED_NUMERO_ORDRE
        defaultPlaqueGarageShouldNotBeFound("numeroOrdre.contains=" + UPDATED_NUMERO_ORDRE);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByNumeroOrdreNotContainsSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where numeroOrdre does not contain DEFAULT_NUMERO_ORDRE
        defaultPlaqueGarageShouldNotBeFound("numeroOrdre.doesNotContain=" + DEFAULT_NUMERO_ORDRE);

        // Get all the plaqueGarageList where numeroOrdre does not contain UPDATED_NUMERO_ORDRE
        defaultPlaqueGarageShouldBeFound("numeroOrdre.doesNotContain=" + UPDATED_NUMERO_ORDRE);
    }


    @Test
    @Transactional
    public void getAllPlaqueGaragesByNumeroPlaqueIsEqualToSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where numeroPlaque equals to DEFAULT_NUMERO_PLAQUE
        defaultPlaqueGarageShouldBeFound("numeroPlaque.equals=" + DEFAULT_NUMERO_PLAQUE);

        // Get all the plaqueGarageList where numeroPlaque equals to UPDATED_NUMERO_PLAQUE
        defaultPlaqueGarageShouldNotBeFound("numeroPlaque.equals=" + UPDATED_NUMERO_PLAQUE);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByNumeroPlaqueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where numeroPlaque not equals to DEFAULT_NUMERO_PLAQUE
        defaultPlaqueGarageShouldNotBeFound("numeroPlaque.notEquals=" + DEFAULT_NUMERO_PLAQUE);

        // Get all the plaqueGarageList where numeroPlaque not equals to UPDATED_NUMERO_PLAQUE
        defaultPlaqueGarageShouldBeFound("numeroPlaque.notEquals=" + UPDATED_NUMERO_PLAQUE);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByNumeroPlaqueIsInShouldWork() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where numeroPlaque in DEFAULT_NUMERO_PLAQUE or UPDATED_NUMERO_PLAQUE
        defaultPlaqueGarageShouldBeFound("numeroPlaque.in=" + DEFAULT_NUMERO_PLAQUE + "," + UPDATED_NUMERO_PLAQUE);

        // Get all the plaqueGarageList where numeroPlaque equals to UPDATED_NUMERO_PLAQUE
        defaultPlaqueGarageShouldNotBeFound("numeroPlaque.in=" + UPDATED_NUMERO_PLAQUE);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByNumeroPlaqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where numeroPlaque is not null
        defaultPlaqueGarageShouldBeFound("numeroPlaque.specified=true");

        // Get all the plaqueGarageList where numeroPlaque is null
        defaultPlaqueGarageShouldNotBeFound("numeroPlaque.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlaqueGaragesByNumeroPlaqueContainsSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where numeroPlaque contains DEFAULT_NUMERO_PLAQUE
        defaultPlaqueGarageShouldBeFound("numeroPlaque.contains=" + DEFAULT_NUMERO_PLAQUE);

        // Get all the plaqueGarageList where numeroPlaque contains UPDATED_NUMERO_PLAQUE
        defaultPlaqueGarageShouldNotBeFound("numeroPlaque.contains=" + UPDATED_NUMERO_PLAQUE);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByNumeroPlaqueNotContainsSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where numeroPlaque does not contain DEFAULT_NUMERO_PLAQUE
        defaultPlaqueGarageShouldNotBeFound("numeroPlaque.doesNotContain=" + DEFAULT_NUMERO_PLAQUE);

        // Get all the plaqueGarageList where numeroPlaque does not contain UPDATED_NUMERO_PLAQUE
        defaultPlaqueGarageShouldBeFound("numeroPlaque.doesNotContain=" + UPDATED_NUMERO_PLAQUE);
    }


    @Test
    @Transactional
    public void getAllPlaqueGaragesByCodeQrPlaqueIsEqualToSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where codeQrPlaque equals to DEFAULT_CODE_QR_PLAQUE
        defaultPlaqueGarageShouldBeFound("codeQrPlaque.equals=" + DEFAULT_CODE_QR_PLAQUE);

        // Get all the plaqueGarageList where codeQrPlaque equals to UPDATED_CODE_QR_PLAQUE
        defaultPlaqueGarageShouldNotBeFound("codeQrPlaque.equals=" + UPDATED_CODE_QR_PLAQUE);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByCodeQrPlaqueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where codeQrPlaque not equals to DEFAULT_CODE_QR_PLAQUE
        defaultPlaqueGarageShouldNotBeFound("codeQrPlaque.notEquals=" + DEFAULT_CODE_QR_PLAQUE);

        // Get all the plaqueGarageList where codeQrPlaque not equals to UPDATED_CODE_QR_PLAQUE
        defaultPlaqueGarageShouldBeFound("codeQrPlaque.notEquals=" + UPDATED_CODE_QR_PLAQUE);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByCodeQrPlaqueIsInShouldWork() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where codeQrPlaque in DEFAULT_CODE_QR_PLAQUE or UPDATED_CODE_QR_PLAQUE
        defaultPlaqueGarageShouldBeFound("codeQrPlaque.in=" + DEFAULT_CODE_QR_PLAQUE + "," + UPDATED_CODE_QR_PLAQUE);

        // Get all the plaqueGarageList where codeQrPlaque equals to UPDATED_CODE_QR_PLAQUE
        defaultPlaqueGarageShouldNotBeFound("codeQrPlaque.in=" + UPDATED_CODE_QR_PLAQUE);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByCodeQrPlaqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where codeQrPlaque is not null
        defaultPlaqueGarageShouldBeFound("codeQrPlaque.specified=true");

        // Get all the plaqueGarageList where codeQrPlaque is null
        defaultPlaqueGarageShouldNotBeFound("codeQrPlaque.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlaqueGaragesByCodeQrPlaqueContainsSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where codeQrPlaque contains DEFAULT_CODE_QR_PLAQUE
        defaultPlaqueGarageShouldBeFound("codeQrPlaque.contains=" + DEFAULT_CODE_QR_PLAQUE);

        // Get all the plaqueGarageList where codeQrPlaque contains UPDATED_CODE_QR_PLAQUE
        defaultPlaqueGarageShouldNotBeFound("codeQrPlaque.contains=" + UPDATED_CODE_QR_PLAQUE);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByCodeQrPlaqueNotContainsSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where codeQrPlaque does not contain DEFAULT_CODE_QR_PLAQUE
        defaultPlaqueGarageShouldNotBeFound("codeQrPlaque.doesNotContain=" + DEFAULT_CODE_QR_PLAQUE);

        // Get all the plaqueGarageList where codeQrPlaque does not contain UPDATED_CODE_QR_PLAQUE
        defaultPlaqueGarageShouldBeFound("codeQrPlaque.doesNotContain=" + UPDATED_CODE_QR_PLAQUE);
    }


    @Test
    @Transactional
    public void getAllPlaqueGaragesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where createdAt equals to DEFAULT_CREATED_AT
        defaultPlaqueGarageShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the plaqueGarageList where createdAt equals to UPDATED_CREATED_AT
        defaultPlaqueGarageShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where createdAt not equals to DEFAULT_CREATED_AT
        defaultPlaqueGarageShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the plaqueGarageList where createdAt not equals to UPDATED_CREATED_AT
        defaultPlaqueGarageShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultPlaqueGarageShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the plaqueGarageList where createdAt equals to UPDATED_CREATED_AT
        defaultPlaqueGarageShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where createdAt is not null
        defaultPlaqueGarageShouldBeFound("createdAt.specified=true");

        // Get all the plaqueGarageList where createdAt is null
        defaultPlaqueGarageShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByCreatedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where createdAt is greater than or equal to DEFAULT_CREATED_AT
        defaultPlaqueGarageShouldBeFound("createdAt.greaterThanOrEqual=" + DEFAULT_CREATED_AT);

        // Get all the plaqueGarageList where createdAt is greater than or equal to UPDATED_CREATED_AT
        defaultPlaqueGarageShouldNotBeFound("createdAt.greaterThanOrEqual=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByCreatedAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where createdAt is less than or equal to DEFAULT_CREATED_AT
        defaultPlaqueGarageShouldBeFound("createdAt.lessThanOrEqual=" + DEFAULT_CREATED_AT);

        // Get all the plaqueGarageList where createdAt is less than or equal to SMALLER_CREATED_AT
        defaultPlaqueGarageShouldNotBeFound("createdAt.lessThanOrEqual=" + SMALLER_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByCreatedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where createdAt is less than DEFAULT_CREATED_AT
        defaultPlaqueGarageShouldNotBeFound("createdAt.lessThan=" + DEFAULT_CREATED_AT);

        // Get all the plaqueGarageList where createdAt is less than UPDATED_CREATED_AT
        defaultPlaqueGarageShouldBeFound("createdAt.lessThan=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPlaqueGaragesByCreatedAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        // Get all the plaqueGarageList where createdAt is greater than DEFAULT_CREATED_AT
        defaultPlaqueGarageShouldNotBeFound("createdAt.greaterThan=" + DEFAULT_CREATED_AT);

        // Get all the plaqueGarageList where createdAt is greater than SMALLER_CREATED_AT
        defaultPlaqueGarageShouldBeFound("createdAt.greaterThan=" + SMALLER_CREATED_AT);
    }


    @Test
    @Transactional
    public void getAllPlaqueGaragesByCarteWIsEqualToSomething() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);
        CarteW carteW = CarteWResourceIT.createEntity(em);
        em.persist(carteW);
        em.flush();
        plaqueGarage.setCarteW(carteW);
        plaqueGarageRepository.saveAndFlush(plaqueGarage);
        Long carteWId = carteW.getId();

        // Get all the plaqueGarageList where carteW equals to carteWId
        defaultPlaqueGarageShouldBeFound("carteWId.equals=" + carteWId);

        // Get all the plaqueGarageList where carteW equals to carteWId + 1
        defaultPlaqueGarageShouldNotBeFound("carteWId.equals=" + (carteWId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlaqueGarageShouldBeFound(String filter) throws Exception {
        restPlaqueGarageMockMvc.perform(get("/api/plaque-garages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaqueGarage.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroOrdre").value(hasItem(DEFAULT_NUMERO_ORDRE)))
            .andExpect(jsonPath("$.[*].numeroPlaque").value(hasItem(DEFAULT_NUMERO_PLAQUE)))
            .andExpect(jsonPath("$.[*].codeQrPlaque").value(hasItem(DEFAULT_CODE_QR_PLAQUE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));

        // Check, that the count call also returns 1
        restPlaqueGarageMockMvc.perform(get("/api/plaque-garages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlaqueGarageShouldNotBeFound(String filter) throws Exception {
        restPlaqueGarageMockMvc.perform(get("/api/plaque-garages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlaqueGarageMockMvc.perform(get("/api/plaque-garages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPlaqueGarage() throws Exception {
        // Get the plaqueGarage
        restPlaqueGarageMockMvc.perform(get("/api/plaque-garages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaqueGarage() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        int databaseSizeBeforeUpdate = plaqueGarageRepository.findAll().size();

        // Update the plaqueGarage
        PlaqueGarage updatedPlaqueGarage = plaqueGarageRepository.findById(plaqueGarage.getId()).get();
        // Disconnect from session so that the updates on updatedPlaqueGarage are not directly saved in db
        em.detach(updatedPlaqueGarage);
        updatedPlaqueGarage
            .numeroOrdre(UPDATED_NUMERO_ORDRE)
            .numeroPlaque(UPDATED_NUMERO_PLAQUE)
            .codeQrPlaque(UPDATED_CODE_QR_PLAQUE)
            .createdAt(UPDATED_CREATED_AT);
        PlaqueGarageDTO plaqueGarageDTO = plaqueGarageMapper.toDto(updatedPlaqueGarage);

        restPlaqueGarageMockMvc.perform(put("/api/plaque-garages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plaqueGarageDTO)))
            .andExpect(status().isOk());

        // Validate the PlaqueGarage in the database
        List<PlaqueGarage> plaqueGarageList = plaqueGarageRepository.findAll();
        assertThat(plaqueGarageList).hasSize(databaseSizeBeforeUpdate);
        PlaqueGarage testPlaqueGarage = plaqueGarageList.get(plaqueGarageList.size() - 1);
        assertThat(testPlaqueGarage.getNumeroOrdre()).isEqualTo(UPDATED_NUMERO_ORDRE);
        assertThat(testPlaqueGarage.getNumeroPlaque()).isEqualTo(UPDATED_NUMERO_PLAQUE);
        assertThat(testPlaqueGarage.getCodeQrPlaque()).isEqualTo(UPDATED_CODE_QR_PLAQUE);
        assertThat(testPlaqueGarage.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingPlaqueGarage() throws Exception {
        int databaseSizeBeforeUpdate = plaqueGarageRepository.findAll().size();

        // Create the PlaqueGarage
        PlaqueGarageDTO plaqueGarageDTO = plaqueGarageMapper.toDto(plaqueGarage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaqueGarageMockMvc.perform(put("/api/plaque-garages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plaqueGarageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlaqueGarage in the database
        List<PlaqueGarage> plaqueGarageList = plaqueGarageRepository.findAll();
        assertThat(plaqueGarageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlaqueGarage() throws Exception {
        // Initialize the database
        plaqueGarageRepository.saveAndFlush(plaqueGarage);

        int databaseSizeBeforeDelete = plaqueGarageRepository.findAll().size();

        // Delete the plaqueGarage
        restPlaqueGarageMockMvc.perform(delete("/api/plaque-garages/{id}", plaqueGarage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlaqueGarage> plaqueGarageList = plaqueGarageRepository.findAll();
        assertThat(plaqueGarageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
