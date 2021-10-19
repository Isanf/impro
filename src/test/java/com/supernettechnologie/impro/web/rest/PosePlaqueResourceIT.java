package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.PosePlaque;
import com.supernettechnologie.impro.repository.PosePlaqueRepository;
import com.supernettechnologie.impro.service.PosePlaqueService;
import com.supernettechnologie.impro.service.dto.PosePlaqueDTO;
import com.supernettechnologie.impro.service.mapper.PosePlaqueMapper;
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
 * Integration tests for the {@link PosePlaqueResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class PosePlaqueResourceIT {

    private static final String DEFAULT_NUMERO_POSE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_POSE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_POSE_PLAQUE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_POSE_PLAQUE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PosePlaqueRepository posePlaqueRepository;

    @Autowired
    private PosePlaqueMapper posePlaqueMapper;

    @Autowired
    private PosePlaqueService posePlaqueService;

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

    private MockMvc restPosePlaqueMockMvc;

    private PosePlaque posePlaque;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PosePlaqueResource posePlaqueResource = new PosePlaqueResource(posePlaqueService);
        this.restPosePlaqueMockMvc = MockMvcBuilders.standaloneSetup(posePlaqueResource)
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
    public static PosePlaque createEntity(EntityManager em) {
        PosePlaque posePlaque = new PosePlaque()
            .numeroPose(DEFAULT_NUMERO_POSE)
            .datePosePlaque(DEFAULT_DATE_POSE_PLAQUE);
        return posePlaque;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PosePlaque createUpdatedEntity(EntityManager em) {
        PosePlaque posePlaque = new PosePlaque()
            .numeroPose(UPDATED_NUMERO_POSE)
            .datePosePlaque(UPDATED_DATE_POSE_PLAQUE);
        return posePlaque;
    }

    @BeforeEach
    public void initTest() {
        posePlaque = createEntity(em);
    }

    @Test
    @Transactional
    public void createPosePlaque() throws Exception {
        int databaseSizeBeforeCreate = posePlaqueRepository.findAll().size();

        // Create the PosePlaque
        PosePlaqueDTO posePlaqueDTO = posePlaqueMapper.toDto(posePlaque);
        restPosePlaqueMockMvc.perform(post("/api/pose-plaques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posePlaqueDTO)))
            .andExpect(status().isCreated());

        // Validate the PosePlaque in the database
        List<PosePlaque> posePlaqueList = posePlaqueRepository.findAll();
        assertThat(posePlaqueList).hasSize(databaseSizeBeforeCreate + 1);
        PosePlaque testPosePlaque = posePlaqueList.get(posePlaqueList.size() - 1);
        assertThat(testPosePlaque.getNumeroPose()).isEqualTo(DEFAULT_NUMERO_POSE);
        assertThat(testPosePlaque.getDatePosePlaque()).isEqualTo(DEFAULT_DATE_POSE_PLAQUE);
    }

    @Test
    @Transactional
    public void createPosePlaqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = posePlaqueRepository.findAll().size();

        // Create the PosePlaque with an existing ID
        posePlaque.setId(1L);
        PosePlaqueDTO posePlaqueDTO = posePlaqueMapper.toDto(posePlaque);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPosePlaqueMockMvc.perform(post("/api/pose-plaques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posePlaqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PosePlaque in the database
        List<PosePlaque> posePlaqueList = posePlaqueRepository.findAll();
        assertThat(posePlaqueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPosePlaques() throws Exception {
        // Initialize the database
        posePlaqueRepository.saveAndFlush(posePlaque);

        // Get all the posePlaqueList
        restPosePlaqueMockMvc.perform(get("/api/pose-plaques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(posePlaque.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroPose").value(hasItem(DEFAULT_NUMERO_POSE)))
            .andExpect(jsonPath("$.[*].datePosePlaque").value(hasItem(sameInstant(DEFAULT_DATE_POSE_PLAQUE))));
    }
    
    @Test
    @Transactional
    public void getPosePlaque() throws Exception {
        // Initialize the database
        posePlaqueRepository.saveAndFlush(posePlaque);

        // Get the posePlaque
        restPosePlaqueMockMvc.perform(get("/api/pose-plaques/{id}", posePlaque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(posePlaque.getId().intValue()))
            .andExpect(jsonPath("$.numeroPose").value(DEFAULT_NUMERO_POSE))
            .andExpect(jsonPath("$.datePosePlaque").value(sameInstant(DEFAULT_DATE_POSE_PLAQUE)));
    }

    @Test
    @Transactional
    public void getNonExistingPosePlaque() throws Exception {
        // Get the posePlaque
        restPosePlaqueMockMvc.perform(get("/api/pose-plaques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePosePlaque() throws Exception {
        // Initialize the database
        posePlaqueRepository.saveAndFlush(posePlaque);

        int databaseSizeBeforeUpdate = posePlaqueRepository.findAll().size();

        // Update the posePlaque
        PosePlaque updatedPosePlaque = posePlaqueRepository.findById(posePlaque.getId()).get();
        // Disconnect from session so that the updates on updatedPosePlaque are not directly saved in db
        em.detach(updatedPosePlaque);
        updatedPosePlaque
            .numeroPose(UPDATED_NUMERO_POSE)
            .datePosePlaque(UPDATED_DATE_POSE_PLAQUE);
        PosePlaqueDTO posePlaqueDTO = posePlaqueMapper.toDto(updatedPosePlaque);

        restPosePlaqueMockMvc.perform(put("/api/pose-plaques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posePlaqueDTO)))
            .andExpect(status().isOk());

        // Validate the PosePlaque in the database
        List<PosePlaque> posePlaqueList = posePlaqueRepository.findAll();
        assertThat(posePlaqueList).hasSize(databaseSizeBeforeUpdate);
        PosePlaque testPosePlaque = posePlaqueList.get(posePlaqueList.size() - 1);
        assertThat(testPosePlaque.getNumeroPose()).isEqualTo(UPDATED_NUMERO_POSE);
        assertThat(testPosePlaque.getDatePosePlaque()).isEqualTo(UPDATED_DATE_POSE_PLAQUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPosePlaque() throws Exception {
        int databaseSizeBeforeUpdate = posePlaqueRepository.findAll().size();

        // Create the PosePlaque
        PosePlaqueDTO posePlaqueDTO = posePlaqueMapper.toDto(posePlaque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPosePlaqueMockMvc.perform(put("/api/pose-plaques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posePlaqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PosePlaque in the database
        List<PosePlaque> posePlaqueList = posePlaqueRepository.findAll();
        assertThat(posePlaqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePosePlaque() throws Exception {
        // Initialize the database
        posePlaqueRepository.saveAndFlush(posePlaque);

        int databaseSizeBeforeDelete = posePlaqueRepository.findAll().size();

        // Delete the posePlaque
        restPosePlaqueMockMvc.perform(delete("/api/pose-plaques/{id}", posePlaque.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PosePlaque> posePlaqueList = posePlaqueRepository.findAll();
        assertThat(posePlaqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
