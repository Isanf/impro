package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.Immatriculation;
import com.supernettechnologie.impro.repository.ImmatriculationRepository;
import com.supernettechnologie.impro.service.ImmatriculationService;
import com.supernettechnologie.impro.service.dto.ImmatriculationDTO;
import com.supernettechnologie.impro.service.mapper.ImmatriculationMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.supernettechnologie.impro.web.rest.TestUtil.sameInstant;
import static com.supernettechnologie.impro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ImmatriculationResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class ImmatriculationResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_IMMATRICULATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_IMMATRICULATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ImmatriculationRepository immatriculationRepository;

    @Autowired
    private ImmatriculationMapper immatriculationMapper;

    @Autowired
    private ImmatriculationService immatriculationService;

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

    private MockMvc restImmatriculationMockMvc;

    private Immatriculation immatriculation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImmatriculationResource immatriculationResource = new ImmatriculationResource(immatriculationService);
        this.restImmatriculationMockMvc = MockMvcBuilders.standaloneSetup(immatriculationResource)
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
    public static Immatriculation createEntity(EntityManager em) {
        Immatriculation immatriculation = new Immatriculation()
            .numero(DEFAULT_NUMERO)
            .dateImmatriculation(DEFAULT_DATE_IMMATRICULATION);
        return immatriculation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Immatriculation createUpdatedEntity(EntityManager em) {
        Immatriculation immatriculation = new Immatriculation()
            .numero(UPDATED_NUMERO)
            .dateImmatriculation(UPDATED_DATE_IMMATRICULATION);
        return immatriculation;
    }

    @BeforeEach
    public void initTest() {
        immatriculation = createEntity(em);
    }

    @Test
    @Transactional
    public void createImmatriculation() throws Exception {
        int databaseSizeBeforeCreate = immatriculationRepository.findAll().size();

        // Create the Immatriculation
        ImmatriculationDTO immatriculationDTO = immatriculationMapper.toDto(immatriculation);
        restImmatriculationMockMvc.perform(post("/api/immatriculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(immatriculationDTO)))
            .andExpect(status().isCreated());

        // Validate the Immatriculation in the database
        List<Immatriculation> immatriculationList = immatriculationRepository.findAll();
        assertThat(immatriculationList).hasSize(databaseSizeBeforeCreate + 1);
        Immatriculation testImmatriculation = immatriculationList.get(immatriculationList.size() - 1);
        assertThat(testImmatriculation.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testImmatriculation.getDateImmatriculation()).isEqualTo(DEFAULT_DATE_IMMATRICULATION);
    }

    @Test
    @Transactional
    public void createImmatriculationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = immatriculationRepository.findAll().size();

        // Create the Immatriculation with an existing ID
        immatriculation.setId(1L);
        ImmatriculationDTO immatriculationDTO = immatriculationMapper.toDto(immatriculation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImmatriculationMockMvc.perform(post("/api/immatriculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(immatriculationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Immatriculation in the database
        List<Immatriculation> immatriculationList = immatriculationRepository.findAll();
        assertThat(immatriculationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImmatriculations() throws Exception {
        // Initialize the database
        immatriculationRepository.saveAndFlush(immatriculation);

        // Get all the immatriculationList
        restImmatriculationMockMvc.perform(get("/api/immatriculations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(immatriculation.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].dateImmatriculation").value(hasItem(sameInstant(DEFAULT_DATE_IMMATRICULATION))));
    }

    @Test
    @Transactional
    public void getImmatriculation() throws Exception {
        // Initialize the database
        immatriculationRepository.saveAndFlush(immatriculation);

        // Get the immatriculation
        restImmatriculationMockMvc.perform(get("/api/immatriculations/{id}", immatriculation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(immatriculation.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.dateImmatriculation").value(sameInstant(DEFAULT_DATE_IMMATRICULATION)));
    }

    @Test
    @Transactional
    public void getNonExistingImmatriculation() throws Exception {
        // Get the immatriculation
        restImmatriculationMockMvc.perform(get("/api/immatriculations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImmatriculation() throws Exception {
        // Initialize the database
        immatriculationRepository.saveAndFlush(immatriculation);

        int databaseSizeBeforeUpdate = immatriculationRepository.findAll().size();

        // Update the immatriculation
        Immatriculation updatedImmatriculation = immatriculationRepository.findById(immatriculation.getId()).get();
        // Disconnect from session so that the updates on updatedImmatriculation are not directly saved in db
        em.detach(updatedImmatriculation);
        updatedImmatriculation
            .numero(UPDATED_NUMERO)
            .dateImmatriculation(UPDATED_DATE_IMMATRICULATION);
        ImmatriculationDTO immatriculationDTO = immatriculationMapper.toDto(updatedImmatriculation);

        restImmatriculationMockMvc.perform(put("/api/immatriculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(immatriculationDTO)))
            .andExpect(status().isOk());

        // Validate the Immatriculation in the database
        List<Immatriculation> immatriculationList = immatriculationRepository.findAll();
        assertThat(immatriculationList).hasSize(databaseSizeBeforeUpdate);
        Immatriculation testImmatriculation = immatriculationList.get(immatriculationList.size() - 1);
        assertThat(testImmatriculation.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testImmatriculation.getDateImmatriculation()).isEqualTo(UPDATED_DATE_IMMATRICULATION);
    }

    @Test
    @Transactional
    public void updateNonExistingImmatriculation() throws Exception {
        int databaseSizeBeforeUpdate = immatriculationRepository.findAll().size();

        // Create the Immatriculation
        ImmatriculationDTO immatriculationDTO = immatriculationMapper.toDto(immatriculation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImmatriculationMockMvc.perform(put("/api/immatriculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(immatriculationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Immatriculation in the database
        List<Immatriculation> immatriculationList = immatriculationRepository.findAll();
        assertThat(immatriculationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImmatriculation() throws Exception {
        // Initialize the database
        immatriculationRepository.saveAndFlush(immatriculation);

        int databaseSizeBeforeDelete = immatriculationRepository.findAll().size();

        // Delete the immatriculation
        restImmatriculationMockMvc.perform(delete("/api/immatriculations/{id}", immatriculation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Immatriculation> immatriculationList = immatriculationRepository.findAll();
        assertThat(immatriculationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
