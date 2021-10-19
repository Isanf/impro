package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.InfoCommandeVehicule;
import com.supernettechnologie.impro.repository.InfoCommandeVehiculeRepository;
import com.supernettechnologie.impro.service.InfoCommandeVehiculeService;
import com.supernettechnologie.impro.service.dto.InfoCommandeVehiculeDTO;
import com.supernettechnologie.impro.service.mapper.InfoCommandeVehiculeMapper;
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
 * Integration tests for the {@link InfoCommandeVehiculeResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class InfoCommandeVehiculeResourceIT {

    private static final String DEFAULT_NUMERO_COMMANDE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_COMMANDE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_COMMANDE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_COMMANDE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_QUANTITE_COMMANDE = 1L;
    private static final Long UPDATED_QUANTITE_COMMANDE = 2L;

    @Autowired
    private InfoCommandeVehiculeRepository infoCommandeVehiculeRepository;

    @Autowired
    private InfoCommandeVehiculeMapper infoCommandeVehiculeMapper;

    @Autowired
    private InfoCommandeVehiculeService infoCommandeVehiculeService;

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

    private MockMvc restInfoCommandeVehiculeMockMvc;

    private InfoCommandeVehicule infoCommandeVehicule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InfoCommandeVehiculeResource infoCommandeVehiculeResource = new InfoCommandeVehiculeResource(infoCommandeVehiculeService);
        this.restInfoCommandeVehiculeMockMvc = MockMvcBuilders.standaloneSetup(infoCommandeVehiculeResource)
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
    public static InfoCommandeVehicule createEntity(EntityManager em) {
        InfoCommandeVehicule infoCommandeVehicule = new InfoCommandeVehicule()
            .numeroCommande(DEFAULT_NUMERO_COMMANDE)
            .dateCommande(DEFAULT_DATE_COMMANDE)
            .quantiteCommande(DEFAULT_QUANTITE_COMMANDE);
        return infoCommandeVehicule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoCommandeVehicule createUpdatedEntity(EntityManager em) {
        InfoCommandeVehicule infoCommandeVehicule = new InfoCommandeVehicule()
            .numeroCommande(UPDATED_NUMERO_COMMANDE)
            .dateCommande(UPDATED_DATE_COMMANDE)
            .quantiteCommande(UPDATED_QUANTITE_COMMANDE);
        return infoCommandeVehicule;
    }

    @BeforeEach
    public void initTest() {
        infoCommandeVehicule = createEntity(em);
    }

    @Test
    @Transactional
    public void createInfoCommandeVehicule() throws Exception {
        int databaseSizeBeforeCreate = infoCommandeVehiculeRepository.findAll().size();

        // Create the InfoCommandeVehicule
        InfoCommandeVehiculeDTO infoCommandeVehiculeDTO = infoCommandeVehiculeMapper.toDto(infoCommandeVehicule);
        restInfoCommandeVehiculeMockMvc.perform(post("/api/info-commande-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoCommandeVehiculeDTO)))
            .andExpect(status().isCreated());

        // Validate the InfoCommandeVehicule in the database
        List<InfoCommandeVehicule> infoCommandeVehiculeList = infoCommandeVehiculeRepository.findAll();
        assertThat(infoCommandeVehiculeList).hasSize(databaseSizeBeforeCreate + 1);
        InfoCommandeVehicule testInfoCommandeVehicule = infoCommandeVehiculeList.get(infoCommandeVehiculeList.size() - 1);
        assertThat(testInfoCommandeVehicule.getNumeroCommande()).isEqualTo(DEFAULT_NUMERO_COMMANDE);
        assertThat(testInfoCommandeVehicule.getDateCommande()).isEqualTo(DEFAULT_DATE_COMMANDE);
        assertThat(testInfoCommandeVehicule.getQuantiteCommande()).isEqualTo(DEFAULT_QUANTITE_COMMANDE);
    }

    @Test
    @Transactional
    public void createInfoCommandeVehiculeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infoCommandeVehiculeRepository.findAll().size();

        // Create the InfoCommandeVehicule with an existing ID
        infoCommandeVehicule.setId(1L);
        InfoCommandeVehiculeDTO infoCommandeVehiculeDTO = infoCommandeVehiculeMapper.toDto(infoCommandeVehicule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoCommandeVehiculeMockMvc.perform(post("/api/info-commande-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoCommandeVehiculeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InfoCommandeVehicule in the database
        List<InfoCommandeVehicule> infoCommandeVehiculeList = infoCommandeVehiculeRepository.findAll();
        assertThat(infoCommandeVehiculeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInfoCommandeVehicules() throws Exception {
        // Initialize the database
        infoCommandeVehiculeRepository.saveAndFlush(infoCommandeVehicule);

        // Get all the infoCommandeVehiculeList
        restInfoCommandeVehiculeMockMvc.perform(get("/api/info-commande-vehicules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoCommandeVehicule.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCommande").value(hasItem(DEFAULT_NUMERO_COMMANDE)))
            .andExpect(jsonPath("$.[*].dateCommande").value(hasItem(sameInstant(DEFAULT_DATE_COMMANDE))))
            .andExpect(jsonPath("$.[*].quantiteCommande").value(hasItem(DEFAULT_QUANTITE_COMMANDE.intValue())));
    }

    @Test
    @Transactional
    public void getInfoCommandeVehicule() throws Exception {
        // Initialize the database
        infoCommandeVehiculeRepository.saveAndFlush(infoCommandeVehicule);

        // Get the infoCommandeVehicule
        restInfoCommandeVehiculeMockMvc.perform(get("/api/info-commande-vehicules/{id}", infoCommandeVehicule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(infoCommandeVehicule.getId().intValue()))
            .andExpect(jsonPath("$.numeroCommande").value(DEFAULT_NUMERO_COMMANDE))
            .andExpect(jsonPath("$.dateCommande").value(sameInstant(DEFAULT_DATE_COMMANDE)))
            .andExpect(jsonPath("$.quantiteCommande").value(DEFAULT_QUANTITE_COMMANDE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInfoCommandeVehicule() throws Exception {
        // Get the infoCommandeVehicule
        restInfoCommandeVehiculeMockMvc.perform(get("/api/info-commande-vehicules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInfoCommandeVehicule() throws Exception {
        // Initialize the database
        infoCommandeVehiculeRepository.saveAndFlush(infoCommandeVehicule);

        int databaseSizeBeforeUpdate = infoCommandeVehiculeRepository.findAll().size();

        // Update the infoCommandeVehicule
        InfoCommandeVehicule updatedInfoCommandeVehicule = infoCommandeVehiculeRepository.findById(infoCommandeVehicule.getId()).get();
        // Disconnect from session so that the updates on updatedInfoCommandeVehicule are not directly saved in db
        em.detach(updatedInfoCommandeVehicule);
        updatedInfoCommandeVehicule
            .numeroCommande(UPDATED_NUMERO_COMMANDE)
            .dateCommande(UPDATED_DATE_COMMANDE)
            .quantiteCommande(UPDATED_QUANTITE_COMMANDE);
        InfoCommandeVehiculeDTO infoCommandeVehiculeDTO = infoCommandeVehiculeMapper.toDto(updatedInfoCommandeVehicule);

        restInfoCommandeVehiculeMockMvc.perform(put("/api/info-commande-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoCommandeVehiculeDTO)))
            .andExpect(status().isOk());

        // Validate the InfoCommandeVehicule in the database
        List<InfoCommandeVehicule> infoCommandeVehiculeList = infoCommandeVehiculeRepository.findAll();
        assertThat(infoCommandeVehiculeList).hasSize(databaseSizeBeforeUpdate);
        InfoCommandeVehicule testInfoCommandeVehicule = infoCommandeVehiculeList.get(infoCommandeVehiculeList.size() - 1);
        assertThat(testInfoCommandeVehicule.getNumeroCommande()).isEqualTo(UPDATED_NUMERO_COMMANDE);
        assertThat(testInfoCommandeVehicule.getDateCommande()).isEqualTo(UPDATED_DATE_COMMANDE);
        assertThat(testInfoCommandeVehicule.getQuantiteCommande()).isEqualTo(UPDATED_QUANTITE_COMMANDE);
    }

    @Test
    @Transactional
    public void updateNonExistingInfoCommandeVehicule() throws Exception {
        int databaseSizeBeforeUpdate = infoCommandeVehiculeRepository.findAll().size();

        // Create the InfoCommandeVehicule
        InfoCommandeVehiculeDTO infoCommandeVehiculeDTO = infoCommandeVehiculeMapper.toDto(infoCommandeVehicule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoCommandeVehiculeMockMvc.perform(put("/api/info-commande-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoCommandeVehiculeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InfoCommandeVehicule in the database
        List<InfoCommandeVehicule> infoCommandeVehiculeList = infoCommandeVehiculeRepository.findAll();
        assertThat(infoCommandeVehiculeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInfoCommandeVehicule() throws Exception {
        // Initialize the database
        infoCommandeVehiculeRepository.saveAndFlush(infoCommandeVehicule);

        int databaseSizeBeforeDelete = infoCommandeVehiculeRepository.findAll().size();

        // Delete the infoCommandeVehicule
        restInfoCommandeVehiculeMockMvc.perform(delete("/api/info-commande-vehicules/{id}", infoCommandeVehicule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InfoCommandeVehicule> infoCommandeVehiculeList = infoCommandeVehiculeRepository.findAll();
        assertThat(infoCommandeVehiculeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
