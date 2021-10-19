package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.MarqueVehicule;
import com.supernettechnologie.impro.repository.MarqueVehiculeRepository;
import com.supernettechnologie.impro.service.MarqueVehiculeService;
import com.supernettechnologie.impro.service.dto.MarqueVehiculeDTO;
import com.supernettechnologie.impro.service.mapper.MarqueVehiculeMapper;
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
 * Integration tests for the {@link MarqueVehiculeResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class MarqueVehiculeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private MarqueVehiculeRepository marqueVehiculeRepository;

    @Autowired
    private MarqueVehiculeMapper marqueVehiculeMapper;

    @Autowired
    private MarqueVehiculeService marqueVehiculeService;

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

    private MockMvc restMarqueVehiculeMockMvc;

    private MarqueVehicule marqueVehicule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarqueVehiculeResource marqueVehiculeResource = new MarqueVehiculeResource(marqueVehiculeService);
        this.restMarqueVehiculeMockMvc = MockMvcBuilders.standaloneSetup(marqueVehiculeResource)
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
    public static MarqueVehicule createEntity(EntityManager em) {
        MarqueVehicule marqueVehicule = new MarqueVehicule()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return marqueVehicule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarqueVehicule createUpdatedEntity(EntityManager em) {
        MarqueVehicule marqueVehicule = new MarqueVehicule()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return marqueVehicule;
    }

    @BeforeEach
    public void initTest() {
        marqueVehicule = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarqueVehicule() throws Exception {
        int databaseSizeBeforeCreate = marqueVehiculeRepository.findAll().size();

        // Create the MarqueVehicule
        MarqueVehiculeDTO marqueVehiculeDTO = marqueVehiculeMapper.toDto(marqueVehicule);
        restMarqueVehiculeMockMvc.perform(post("/api/marque-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marqueVehiculeDTO)))
            .andExpect(status().isCreated());

        // Validate the MarqueVehicule in the database
        List<MarqueVehicule> marqueVehiculeList = marqueVehiculeRepository.findAll();
        assertThat(marqueVehiculeList).hasSize(databaseSizeBeforeCreate + 1);
        MarqueVehicule testMarqueVehicule = marqueVehiculeList.get(marqueVehiculeList.size() - 1);
        assertThat(testMarqueVehicule.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMarqueVehicule.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createMarqueVehiculeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marqueVehiculeRepository.findAll().size();

        // Create the MarqueVehicule with an existing ID
        marqueVehicule.setId(1L);
        MarqueVehiculeDTO marqueVehiculeDTO = marqueVehiculeMapper.toDto(marqueVehicule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarqueVehiculeMockMvc.perform(post("/api/marque-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marqueVehiculeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MarqueVehicule in the database
        List<MarqueVehicule> marqueVehiculeList = marqueVehiculeRepository.findAll();
        assertThat(marqueVehiculeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMarqueVehicules() throws Exception {
        // Initialize the database
        marqueVehiculeRepository.saveAndFlush(marqueVehicule);

        // Get all the marqueVehiculeList
        restMarqueVehiculeMockMvc.perform(get("/api/marque-vehicules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marqueVehicule.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getMarqueVehicule() throws Exception {
        // Initialize the database
        marqueVehiculeRepository.saveAndFlush(marqueVehicule);

        // Get the marqueVehicule
        restMarqueVehiculeMockMvc.perform(get("/api/marque-vehicules/{id}", marqueVehicule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marqueVehicule.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    public void getNonExistingMarqueVehicule() throws Exception {
        // Get the marqueVehicule
        restMarqueVehiculeMockMvc.perform(get("/api/marque-vehicules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarqueVehicule() throws Exception {
        // Initialize the database
        marqueVehiculeRepository.saveAndFlush(marqueVehicule);

        int databaseSizeBeforeUpdate = marqueVehiculeRepository.findAll().size();

        // Update the marqueVehicule
        MarqueVehicule updatedMarqueVehicule = marqueVehiculeRepository.findById(marqueVehicule.getId()).get();
        // Disconnect from session so that the updates on updatedMarqueVehicule are not directly saved in db
        em.detach(updatedMarqueVehicule);
        updatedMarqueVehicule
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        MarqueVehiculeDTO marqueVehiculeDTO = marqueVehiculeMapper.toDto(updatedMarqueVehicule);

        restMarqueVehiculeMockMvc.perform(put("/api/marque-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marqueVehiculeDTO)))
            .andExpect(status().isOk());

        // Validate the MarqueVehicule in the database
        List<MarqueVehicule> marqueVehiculeList = marqueVehiculeRepository.findAll();
        assertThat(marqueVehiculeList).hasSize(databaseSizeBeforeUpdate);
        MarqueVehicule testMarqueVehicule = marqueVehiculeList.get(marqueVehiculeList.size() - 1);
        assertThat(testMarqueVehicule.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMarqueVehicule.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingMarqueVehicule() throws Exception {
        int databaseSizeBeforeUpdate = marqueVehiculeRepository.findAll().size();

        // Create the MarqueVehicule
        MarqueVehiculeDTO marqueVehiculeDTO = marqueVehiculeMapper.toDto(marqueVehicule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarqueVehiculeMockMvc.perform(put("/api/marque-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marqueVehiculeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MarqueVehicule in the database
        List<MarqueVehicule> marqueVehiculeList = marqueVehiculeRepository.findAll();
        assertThat(marqueVehiculeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMarqueVehicule() throws Exception {
        // Initialize the database
        marqueVehiculeRepository.saveAndFlush(marqueVehicule);

        int databaseSizeBeforeDelete = marqueVehiculeRepository.findAll().size();

        // Delete the marqueVehicule
        restMarqueVehiculeMockMvc.perform(delete("/api/marque-vehicules/{id}", marqueVehicule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarqueVehicule> marqueVehiculeList = marqueVehiculeRepository.findAll();
        assertThat(marqueVehiculeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
