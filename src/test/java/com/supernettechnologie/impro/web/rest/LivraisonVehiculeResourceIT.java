package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.LivraisonVehicule;
import com.supernettechnologie.impro.repository.LivraisonVehiculeRepository;
import com.supernettechnologie.impro.service.LivraisonVehiculeService;
import com.supernettechnologie.impro.service.dto.LivraisonVehiculeDTO;
import com.supernettechnologie.impro.service.mapper.LivraisonVehiculeMapper;
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
 * Integration tests for the {@link LivraisonVehiculeResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class LivraisonVehiculeResourceIT {

    private static final String DEFAULT_NUMERO_LIVRAISON = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_LIVRAISON = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_LIVRAISON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_LIVRAISON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LivraisonVehiculeRepository livraisonVehiculeRepository;

    @Autowired
    private LivraisonVehiculeMapper livraisonVehiculeMapper;

    @Autowired
    private LivraisonVehiculeService livraisonVehiculeService;

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

    private MockMvc restLivraisonVehiculeMockMvc;

    private LivraisonVehicule livraisonVehicule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LivraisonVehiculeResource livraisonVehiculeResource = new LivraisonVehiculeResource(livraisonVehiculeService);
        this.restLivraisonVehiculeMockMvc = MockMvcBuilders.standaloneSetup(livraisonVehiculeResource)
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
    public static LivraisonVehicule createEntity(EntityManager em) {
        LivraisonVehicule livraisonVehicule = new LivraisonVehicule()
            .numeroLivraison(DEFAULT_NUMERO_LIVRAISON)
            .dateLivraison(DEFAULT_DATE_LIVRAISON);
        return livraisonVehicule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LivraisonVehicule createUpdatedEntity(EntityManager em) {
        LivraisonVehicule livraisonVehicule = new LivraisonVehicule()
            .numeroLivraison(UPDATED_NUMERO_LIVRAISON)
            .dateLivraison(UPDATED_DATE_LIVRAISON);
        return livraisonVehicule;
    }

    @BeforeEach
    public void initTest() {
        livraisonVehicule = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivraisonVehicule() throws Exception {
        int databaseSizeBeforeCreate = livraisonVehiculeRepository.findAll().size();

        // Create the LivraisonVehicule
        LivraisonVehiculeDTO livraisonVehiculeDTO = livraisonVehiculeMapper.toDto(livraisonVehicule);
        restLivraisonVehiculeMockMvc.perform(post("/api/livraison-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraisonVehiculeDTO)))
            .andExpect(status().isCreated());

        // Validate the LivraisonVehicule in the database
        List<LivraisonVehicule> livraisonVehiculeList = livraisonVehiculeRepository.findAll();
        assertThat(livraisonVehiculeList).hasSize(databaseSizeBeforeCreate + 1);
        LivraisonVehicule testLivraisonVehicule = livraisonVehiculeList.get(livraisonVehiculeList.size() - 1);
        assertThat(testLivraisonVehicule.getNumeroLivraison()).isEqualTo(DEFAULT_NUMERO_LIVRAISON);
        assertThat(testLivraisonVehicule.getDateLivraison()).isEqualTo(DEFAULT_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void createLivraisonVehiculeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livraisonVehiculeRepository.findAll().size();

        // Create the LivraisonVehicule with an existing ID
        livraisonVehicule.setId(1L);
        LivraisonVehiculeDTO livraisonVehiculeDTO = livraisonVehiculeMapper.toDto(livraisonVehicule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivraisonVehiculeMockMvc.perform(post("/api/livraison-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraisonVehiculeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LivraisonVehicule in the database
        List<LivraisonVehicule> livraisonVehiculeList = livraisonVehiculeRepository.findAll();
        assertThat(livraisonVehiculeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLivraisonVehicules() throws Exception {
        // Initialize the database
        livraisonVehiculeRepository.saveAndFlush(livraisonVehicule);

        // Get all the livraisonVehiculeList
        restLivraisonVehiculeMockMvc.perform(get("/api/livraison-vehicules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livraisonVehicule.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroLivraison").value(hasItem(DEFAULT_NUMERO_LIVRAISON)))
            .andExpect(jsonPath("$.[*].dateLivraison").value(hasItem(sameInstant(DEFAULT_DATE_LIVRAISON))));
    }

    @Test
    @Transactional
    public void getLivraisonVehicule() throws Exception {
        // Initialize the database
        livraisonVehiculeRepository.saveAndFlush(livraisonVehicule);

        // Get the livraisonVehicule
        restLivraisonVehiculeMockMvc.perform(get("/api/livraison-vehicules/{id}", livraisonVehicule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livraisonVehicule.getId().intValue()))
            .andExpect(jsonPath("$.numeroLivraison").value(DEFAULT_NUMERO_LIVRAISON))
            .andExpect(jsonPath("$.dateLivraison").value(sameInstant(DEFAULT_DATE_LIVRAISON)));
    }

    @Test
    @Transactional
    public void getNonExistingLivraisonVehicule() throws Exception {
        // Get the livraisonVehicule
        restLivraisonVehiculeMockMvc.perform(get("/api/livraison-vehicules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivraisonVehicule() throws Exception {
        // Initialize the database
        livraisonVehiculeRepository.saveAndFlush(livraisonVehicule);

        int databaseSizeBeforeUpdate = livraisonVehiculeRepository.findAll().size();

        // Update the livraisonVehicule
        LivraisonVehicule updatedLivraisonVehicule = livraisonVehiculeRepository.findById(livraisonVehicule.getId()).get();
        // Disconnect from session so that the updates on updatedLivraisonVehicule are not directly saved in db
        em.detach(updatedLivraisonVehicule);
        updatedLivraisonVehicule
            .numeroLivraison(UPDATED_NUMERO_LIVRAISON)
            .dateLivraison(UPDATED_DATE_LIVRAISON);
        LivraisonVehiculeDTO livraisonVehiculeDTO = livraisonVehiculeMapper.toDto(updatedLivraisonVehicule);

        restLivraisonVehiculeMockMvc.perform(put("/api/livraison-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraisonVehiculeDTO)))
            .andExpect(status().isOk());

        // Validate the LivraisonVehicule in the database
        List<LivraisonVehicule> livraisonVehiculeList = livraisonVehiculeRepository.findAll();
        assertThat(livraisonVehiculeList).hasSize(databaseSizeBeforeUpdate);
        LivraisonVehicule testLivraisonVehicule = livraisonVehiculeList.get(livraisonVehiculeList.size() - 1);
        assertThat(testLivraisonVehicule.getNumeroLivraison()).isEqualTo(UPDATED_NUMERO_LIVRAISON);
        assertThat(testLivraisonVehicule.getDateLivraison()).isEqualTo(UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void updateNonExistingLivraisonVehicule() throws Exception {
        int databaseSizeBeforeUpdate = livraisonVehiculeRepository.findAll().size();

        // Create the LivraisonVehicule
        LivraisonVehiculeDTO livraisonVehiculeDTO = livraisonVehiculeMapper.toDto(livraisonVehicule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivraisonVehiculeMockMvc.perform(put("/api/livraison-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraisonVehiculeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LivraisonVehicule in the database
        List<LivraisonVehicule> livraisonVehiculeList = livraisonVehiculeRepository.findAll();
        assertThat(livraisonVehiculeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLivraisonVehicule() throws Exception {
        // Initialize the database
        livraisonVehiculeRepository.saveAndFlush(livraisonVehicule);

        int databaseSizeBeforeDelete = livraisonVehiculeRepository.findAll().size();

        // Delete the livraisonVehicule
        restLivraisonVehiculeMockMvc.perform(delete("/api/livraison-vehicules/{id}", livraisonVehicule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LivraisonVehicule> livraisonVehiculeList = livraisonVehiculeRepository.findAll();
        assertThat(livraisonVehiculeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
