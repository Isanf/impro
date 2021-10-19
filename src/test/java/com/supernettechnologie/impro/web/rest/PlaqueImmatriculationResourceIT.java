package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.PlaqueImmatriculation;
import com.supernettechnologie.impro.repository.PlaqueImmatriculationRepository;
import com.supernettechnologie.impro.service.PlaqueImmatriculationService;
import com.supernettechnologie.impro.service.dto.PlaqueImmatriculationDTO;
import com.supernettechnologie.impro.service.mapper.PlaqueImmatriculationMapper;
import com.supernettechnologie.impro.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.supernettechnologie.impro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlaqueImmatriculationResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class PlaqueImmatriculationResourceIT {

    private static final String DEFAULT_NUMERO_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SERIE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_IMMATRICULATION = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IMMATRICULATION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_QR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_QR = "BBBBBBBBBB";

    @Autowired
    private PlaqueImmatriculationRepository plaqueImmatriculationRepository;

    @Autowired
    private PlaqueImmatriculationMapper plaqueImmatriculationMapper;

    @Autowired
    private PlaqueImmatriculationService plaqueImmatriculationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPlaqueImmatriculationMockMvc;

    private PlaqueImmatriculation plaqueImmatriculation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlaqueImmatriculationResource plaqueImmatriculationResource = new PlaqueImmatriculationResource(plaqueImmatriculationService);
        this.restPlaqueImmatriculationMockMvc = MockMvcBuilders.standaloneSetup(plaqueImmatriculationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaqueImmatriculation createEntity(EntityManager em) {
        PlaqueImmatriculation plaqueImmatriculation = new PlaqueImmatriculation()
            .numeroSerie(DEFAULT_NUMERO_SERIE)
            .numeroImmatriculation(DEFAULT_NUMERO_IMMATRICULATION)
            .codeQR(DEFAULT_CODE_QR);
        return plaqueImmatriculation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaqueImmatriculation createUpdatedEntity(EntityManager em) {
        PlaqueImmatriculation plaqueImmatriculation = new PlaqueImmatriculation()
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .numeroImmatriculation(UPDATED_NUMERO_IMMATRICULATION)
            .codeQR(UPDATED_CODE_QR);
        return plaqueImmatriculation;
    }

    @BeforeEach
    public void initTest() {
        plaqueImmatriculation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaqueImmatriculation() throws Exception {
        int databaseSizeBeforeCreate = plaqueImmatriculationRepository.findAll().size();

        // Create the PlaqueImmatriculation
        PlaqueImmatriculationDTO plaqueImmatriculationDTO = plaqueImmatriculationMapper.toDto(plaqueImmatriculation);
        restPlaqueImmatriculationMockMvc.perform(post("/api/plaque-immatriculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plaqueImmatriculationDTO)))
            .andExpect(status().isCreated());

        // Validate the PlaqueImmatriculation in the database
        List<PlaqueImmatriculation> plaqueImmatriculationList = plaqueImmatriculationRepository.findAll();
        assertThat(plaqueImmatriculationList).hasSize(databaseSizeBeforeCreate + 1);
        PlaqueImmatriculation testPlaqueImmatriculation = plaqueImmatriculationList.get(plaqueImmatriculationList.size() - 1);
        assertThat(testPlaqueImmatriculation.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testPlaqueImmatriculation.getNumeroImmatriculation()).isEqualTo(DEFAULT_NUMERO_IMMATRICULATION);
        assertThat(testPlaqueImmatriculation.getCodeQR()).isEqualTo(DEFAULT_CODE_QR);
    }

    @Test
    @Transactional
    public void createPlaqueImmatriculationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plaqueImmatriculationRepository.findAll().size();

        // Create the PlaqueImmatriculation with an existing ID
        plaqueImmatriculation.setId(1L);
        PlaqueImmatriculationDTO plaqueImmatriculationDTO = plaqueImmatriculationMapper.toDto(plaqueImmatriculation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaqueImmatriculationMockMvc.perform(post("/api/plaque-immatriculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plaqueImmatriculationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlaqueImmatriculation in the database
        List<PlaqueImmatriculation> plaqueImmatriculationList = plaqueImmatriculationRepository.findAll();
        assertThat(plaqueImmatriculationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlaqueImmatriculations() throws Exception {
        // Initialize the database
        plaqueImmatriculationRepository.saveAndFlush(plaqueImmatriculation);

        // Get all the plaqueImmatriculationList
        restPlaqueImmatriculationMockMvc.perform(get("/api/plaque-immatriculations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaqueImmatriculation.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].numeroImmatriculation").value(hasItem(DEFAULT_NUMERO_IMMATRICULATION)))
            .andExpect(jsonPath("$.[*].codeQR").value(hasItem(DEFAULT_CODE_QR)));
    }

    @Test
    @Transactional
    public void getPlaqueImmatriculation() throws Exception {
        // Initialize the database
        plaqueImmatriculationRepository.saveAndFlush(plaqueImmatriculation);

        // Get the plaqueImmatriculation
        restPlaqueImmatriculationMockMvc.perform(get("/api/plaque-immatriculations/{id}", plaqueImmatriculation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plaqueImmatriculation.getId().intValue()))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE))
            .andExpect(jsonPath("$.numeroImmatriculation").value(DEFAULT_NUMERO_IMMATRICULATION))
            .andExpect(jsonPath("$.codeQR").value(DEFAULT_CODE_QR));
    }

    @Test
    @Transactional
    public void getNonExistingPlaqueImmatriculation() throws Exception {
        // Get the plaqueImmatriculation
        restPlaqueImmatriculationMockMvc.perform(get("/api/plaque-immatriculations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaqueImmatriculation() throws Exception {
        // Initialize the database
        plaqueImmatriculationRepository.saveAndFlush(plaqueImmatriculation);

        int databaseSizeBeforeUpdate = plaqueImmatriculationRepository.findAll().size();

        // Update the plaqueImmatriculation
        PlaqueImmatriculation updatedPlaqueImmatriculation = plaqueImmatriculationRepository.findById(plaqueImmatriculation.getId()).get();
        // Disconnect from session so that the updates on updatedPlaqueImmatriculation are not directly saved in db
        em.detach(updatedPlaqueImmatriculation);
        updatedPlaqueImmatriculation
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .numeroImmatriculation(UPDATED_NUMERO_IMMATRICULATION)
            .codeQR(UPDATED_CODE_QR);
        PlaqueImmatriculationDTO plaqueImmatriculationDTO = plaqueImmatriculationMapper.toDto(updatedPlaqueImmatriculation);

        restPlaqueImmatriculationMockMvc.perform(put("/api/plaque-immatriculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plaqueImmatriculationDTO)))
            .andExpect(status().isOk());

        // Validate the PlaqueImmatriculation in the database
        List<PlaqueImmatriculation> plaqueImmatriculationList = plaqueImmatriculationRepository.findAll();
        assertThat(plaqueImmatriculationList).hasSize(databaseSizeBeforeUpdate);
        PlaqueImmatriculation testPlaqueImmatriculation = plaqueImmatriculationList.get(plaqueImmatriculationList.size() - 1);
        assertThat(testPlaqueImmatriculation.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testPlaqueImmatriculation.getNumeroImmatriculation()).isEqualTo(UPDATED_NUMERO_IMMATRICULATION);
        assertThat(testPlaqueImmatriculation.getCodeQR()).isEqualTo(UPDATED_CODE_QR);
    }

    @Test
    @Transactional
    public void updateNonExistingPlaqueImmatriculation() throws Exception {
        int databaseSizeBeforeUpdate = plaqueImmatriculationRepository.findAll().size();

        // Create the PlaqueImmatriculation
        PlaqueImmatriculationDTO plaqueImmatriculationDTO = plaqueImmatriculationMapper.toDto(plaqueImmatriculation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaqueImmatriculationMockMvc.perform(put("/api/plaque-immatriculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plaqueImmatriculationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlaqueImmatriculation in the database
        List<PlaqueImmatriculation> plaqueImmatriculationList = plaqueImmatriculationRepository.findAll();
        assertThat(plaqueImmatriculationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlaqueImmatriculation() throws Exception {
        // Initialize the database
        plaqueImmatriculationRepository.saveAndFlush(plaqueImmatriculation);

        int databaseSizeBeforeDelete = plaqueImmatriculationRepository.findAll().size();

        // Delete the plaqueImmatriculation
        restPlaqueImmatriculationMockMvc.perform(delete("/api/plaque-immatriculations/{id}", plaqueImmatriculation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlaqueImmatriculation> plaqueImmatriculationList = plaqueImmatriculationRepository.findAll();
        assertThat(plaqueImmatriculationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
