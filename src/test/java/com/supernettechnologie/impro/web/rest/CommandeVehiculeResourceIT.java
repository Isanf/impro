package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.CommandeVehicule;
import com.supernettechnologie.impro.repository.CommandeVehiculeRepository;
import com.supernettechnologie.impro.service.CommandeVehiculeService;
import com.supernettechnologie.impro.service.dto.CommandeVehiculeDTO;
import com.supernettechnologie.impro.service.mapper.CommandeVehiculeMapper;
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
 * Integration tests for the {@link CommandeVehiculeResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class CommandeVehiculeResourceIT {

    private static final String DEFAULT_NUMERO_COMMANDE_VEHICULE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_COMMANDE_VEHICULE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_COMMANDE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_COMMANDE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_EST_LIVREE = false;
    private static final Boolean UPDATED_EST_LIVREE = true;

    @Autowired
    private CommandeVehiculeRepository commandeVehiculeRepository;

    @Autowired
    private CommandeVehiculeMapper commandeVehiculeMapper;

    @Autowired
    private CommandeVehiculeService commandeVehiculeService;

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

    private MockMvc restCommandeVehiculeMockMvc;

    private CommandeVehicule commandeVehicule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommandeVehiculeResource commandeVehiculeResource = new CommandeVehiculeResource(commandeVehiculeService);
        this.restCommandeVehiculeMockMvc = MockMvcBuilders.standaloneSetup(commandeVehiculeResource)
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
    public static CommandeVehicule createEntity(EntityManager em) {
        CommandeVehicule commandeVehicule = new CommandeVehicule()
            .numeroCommandeVehicule(DEFAULT_NUMERO_COMMANDE_VEHICULE)
            .dateCommande(DEFAULT_DATE_COMMANDE)
            .estLivree(DEFAULT_EST_LIVREE);
        return commandeVehicule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeVehicule createUpdatedEntity(EntityManager em) {
        CommandeVehicule commandeVehicule = new CommandeVehicule()
            .numeroCommandeVehicule(UPDATED_NUMERO_COMMANDE_VEHICULE)
            .dateCommande(UPDATED_DATE_COMMANDE)
            .estLivree(UPDATED_EST_LIVREE);
        return commandeVehicule;
    }

    @BeforeEach
    public void initTest() {
        commandeVehicule = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandeVehicule() throws Exception {
        int databaseSizeBeforeCreate = commandeVehiculeRepository.findAll().size();

        // Create the CommandeVehicule
        CommandeVehiculeDTO commandeVehiculeDTO = commandeVehiculeMapper.toDto(commandeVehicule);
        restCommandeVehiculeMockMvc.perform(post("/api/commande-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandeVehiculeDTO)))
            .andExpect(status().isCreated());

        // Validate the CommandeVehicule in the database
        List<CommandeVehicule> commandeVehiculeList = commandeVehiculeRepository.findAll();
        assertThat(commandeVehiculeList).hasSize(databaseSizeBeforeCreate + 1);
        CommandeVehicule testCommandeVehicule = commandeVehiculeList.get(commandeVehiculeList.size() - 1);
        assertThat(testCommandeVehicule.getNumeroCommandeVehicule()).isEqualTo(DEFAULT_NUMERO_COMMANDE_VEHICULE);
        assertThat(testCommandeVehicule.getDateCommande()).isEqualTo(DEFAULT_DATE_COMMANDE);
        assertThat(testCommandeVehicule.isEstLivree()).isEqualTo(DEFAULT_EST_LIVREE);
    }

    @Test
    @Transactional
    public void createCommandeVehiculeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeVehiculeRepository.findAll().size();

        // Create the CommandeVehicule with an existing ID
        commandeVehicule.setId(1L);
        CommandeVehiculeDTO commandeVehiculeDTO = commandeVehiculeMapper.toDto(commandeVehicule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeVehiculeMockMvc.perform(post("/api/commande-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandeVehiculeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeVehicule in the database
        List<CommandeVehicule> commandeVehiculeList = commandeVehiculeRepository.findAll();
        assertThat(commandeVehiculeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommandeVehicules() throws Exception {
        // Initialize the database
        commandeVehiculeRepository.saveAndFlush(commandeVehicule);

        // Get all the commandeVehiculeList
        restCommandeVehiculeMockMvc.perform(get("/api/commande-vehicules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeVehicule.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCommandeVehicule").value(hasItem(DEFAULT_NUMERO_COMMANDE_VEHICULE)))
            .andExpect(jsonPath("$.[*].dateCommande").value(hasItem(sameInstant(DEFAULT_DATE_COMMANDE))))
            .andExpect(jsonPath("$.[*].estLivree").value(hasItem(DEFAULT_EST_LIVREE.booleanValue())));
    }

    @Test
    @Transactional
    public void getCommandeVehicule() throws Exception {
        // Initialize the database
        commandeVehiculeRepository.saveAndFlush(commandeVehicule);

        // Get the commandeVehicule
        restCommandeVehiculeMockMvc.perform(get("/api/commande-vehicules/{id}", commandeVehicule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandeVehicule.getId().intValue()))
            .andExpect(jsonPath("$.numeroCommandeVehicule").value(DEFAULT_NUMERO_COMMANDE_VEHICULE))
            .andExpect(jsonPath("$.dateCommande").value(sameInstant(DEFAULT_DATE_COMMANDE)))
            .andExpect(jsonPath("$.estLivree").value(DEFAULT_EST_LIVREE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCommandeVehicule() throws Exception {
        // Get the commandeVehicule
        restCommandeVehiculeMockMvc.perform(get("/api/commande-vehicules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandeVehicule() throws Exception {
        // Initialize the database
        commandeVehiculeRepository.saveAndFlush(commandeVehicule);

        int databaseSizeBeforeUpdate = commandeVehiculeRepository.findAll().size();

        // Update the commandeVehicule
        CommandeVehicule updatedCommandeVehicule = commandeVehiculeRepository.findById(commandeVehicule.getId()).get();
        // Disconnect from session so that the updates on updatedCommandeVehicule are not directly saved in db
        em.detach(updatedCommandeVehicule);
        updatedCommandeVehicule
            .numeroCommandeVehicule(UPDATED_NUMERO_COMMANDE_VEHICULE)
            .dateCommande(UPDATED_DATE_COMMANDE)
            .estLivree(UPDATED_EST_LIVREE);
        CommandeVehiculeDTO commandeVehiculeDTO = commandeVehiculeMapper.toDto(updatedCommandeVehicule);

        restCommandeVehiculeMockMvc.perform(put("/api/commande-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandeVehiculeDTO)))
            .andExpect(status().isOk());

        // Validate the CommandeVehicule in the database
        List<CommandeVehicule> commandeVehiculeList = commandeVehiculeRepository.findAll();
        assertThat(commandeVehiculeList).hasSize(databaseSizeBeforeUpdate);
        CommandeVehicule testCommandeVehicule = commandeVehiculeList.get(commandeVehiculeList.size() - 1);
        assertThat(testCommandeVehicule.getNumeroCommandeVehicule()).isEqualTo(UPDATED_NUMERO_COMMANDE_VEHICULE);
        assertThat(testCommandeVehicule.getDateCommande()).isEqualTo(UPDATED_DATE_COMMANDE);
        assertThat(testCommandeVehicule.isEstLivree()).isEqualTo(UPDATED_EST_LIVREE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandeVehicule() throws Exception {
        int databaseSizeBeforeUpdate = commandeVehiculeRepository.findAll().size();

        // Create the CommandeVehicule
        CommandeVehiculeDTO commandeVehiculeDTO = commandeVehiculeMapper.toDto(commandeVehicule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeVehiculeMockMvc.perform(put("/api/commande-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandeVehiculeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeVehicule in the database
        List<CommandeVehicule> commandeVehiculeList = commandeVehiculeRepository.findAll();
        assertThat(commandeVehiculeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommandeVehicule() throws Exception {
        // Initialize the database
        commandeVehiculeRepository.saveAndFlush(commandeVehicule);

        int databaseSizeBeforeDelete = commandeVehiculeRepository.findAll().size();

        // Delete the commandeVehicule
        restCommandeVehiculeMockMvc.perform(delete("/api/commande-vehicules/{id}", commandeVehicule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommandeVehicule> commandeVehiculeList = commandeVehiculeRepository.findAll();
        assertThat(commandeVehiculeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
