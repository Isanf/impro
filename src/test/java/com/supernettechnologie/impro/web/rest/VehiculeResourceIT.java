package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.Vehicule;
import com.supernettechnologie.impro.repository.VehiculeRepository;
import com.supernettechnologie.impro.service.VehiculeService;
import com.supernettechnologie.impro.service.dto.VehiculeDTO;
import com.supernettechnologie.impro.service.mapper.VehiculeMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.supernettechnologie.impro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VehiculeResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class VehiculeResourceIT {

    private static final String DEFAULT_NUMERO_CHASSIS = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CHASSIS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPES = "AAAAAAAAAA";
    private static final String UPDATED_TYPES = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_ENERGIE = "AAAAAAAAAA";
    private static final String UPDATED_ENERGIE = "BBBBBBBBBB";

    private static final String DEFAULT_PUISSANCE_REEL = "AAAAAAAAAA";
    private static final String UPDATED_PUISSANCE_REEL = "BBBBBBBBBB";

    private static final String DEFAULT_PUISSANCE_ADMIN = "AAAAAAAAAA";
    private static final String UPDATED_PUISSANCE_ADMIN = "BBBBBBBBBB";

    private static final String DEFAULT_COULEUR = "AAAAAAAAAA";
    private static final String UPDATED_COULEUR = "BBBBBBBBBB";

    private static final Integer DEFAULT_POIDS_VIDE = 1;
    private static final Integer UPDATED_POIDS_VIDE = 2;

    private static final Integer DEFAULT_CHARGE_UTILE = 1;
    private static final Integer UPDATED_CHARGE_UTILE = 2;

    private static final Integer DEFAULT_PTAC = 1;
    private static final Integer UPDATED_PTAC = 2;

    private static final Integer DEFAULT_PTRA = 1;
    private static final Integer UPDATED_PTRA = 2;

    private static final Integer DEFAULT_NBR_PLACE = 1;
    private static final Integer UPDATED_NBR_PLACE = 2;

    private static final Integer DEFAULT_CAPACITE = 1;
    private static final Integer UPDATED_CAPACITE = 2;

    private static final LocalDate DEFAULT_DATE_MISE_CIRCULATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MISE_CIRCULATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REGIME = "AAAAAAAAAA";
    private static final String UPDATED_REGIME = "BBBBBBBBBB";

    private static final String DEFAULT_NO_DEDOUANEMENT = "AAAAAAAAAA";
    private static final String UPDATED_NO_DEDOUANEMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEDOUANEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEDOUANEMENT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private VehiculeMapper vehiculeMapper;

    @Autowired
    private VehiculeService vehiculeService;

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

    private MockMvc restVehiculeMockMvc;

    private Vehicule vehicule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehiculeResource vehiculeResource = new VehiculeResource(vehiculeService, vehiculeMapper);
        this.restVehiculeMockMvc = MockMvcBuilders.standaloneSetup(vehiculeResource)
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
    public static Vehicule createEntity(EntityManager em) {
        Vehicule vehicule = new Vehicule()
            .numeroChassis(DEFAULT_NUMERO_CHASSIS)
            .types(DEFAULT_TYPES)
            .model(DEFAULT_MODEL)
            .energie(DEFAULT_ENERGIE)
            .puissanceReel(DEFAULT_PUISSANCE_REEL)
            .puissanceAdmin(DEFAULT_PUISSANCE_ADMIN)
            .couleur(DEFAULT_COULEUR)
            .poidsVide(DEFAULT_POIDS_VIDE)
            .chargeUtile(DEFAULT_CHARGE_UTILE)
            .ptac(DEFAULT_PTAC)
            .ptra(DEFAULT_PTRA)
            .nbrPlace(DEFAULT_NBR_PLACE)
            .capacite(DEFAULT_CAPACITE)
            .dateMiseCirculation(DEFAULT_DATE_MISE_CIRCULATION)
            .regime(DEFAULT_REGIME)
            .noDedouanement(DEFAULT_NO_DEDOUANEMENT)
            .dateDedouanement(DEFAULT_DATE_DEDOUANEMENT);
        return vehicule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicule createUpdatedEntity(EntityManager em) {
        Vehicule vehicule = new Vehicule()
            .numeroChassis(UPDATED_NUMERO_CHASSIS)
            .types(UPDATED_TYPES)
            .model(UPDATED_MODEL)
            .energie(UPDATED_ENERGIE)
            .puissanceReel(UPDATED_PUISSANCE_REEL)
            .puissanceAdmin(UPDATED_PUISSANCE_ADMIN)
            .couleur(UPDATED_COULEUR)
            .poidsVide(UPDATED_POIDS_VIDE)
            .chargeUtile(UPDATED_CHARGE_UTILE)
            .ptac(UPDATED_PTAC)
            .ptra(UPDATED_PTRA)
            .nbrPlace(UPDATED_NBR_PLACE)
            .capacite(UPDATED_CAPACITE)
            .dateMiseCirculation(UPDATED_DATE_MISE_CIRCULATION)
            .regime(UPDATED_REGIME)
            .noDedouanement(UPDATED_NO_DEDOUANEMENT)
            .dateDedouanement(UPDATED_DATE_DEDOUANEMENT);
        return vehicule;
    }

    @BeforeEach
    public void initTest() {
        vehicule = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicule() throws Exception {
        int databaseSizeBeforeCreate = vehiculeRepository.findAll().size();

        // Create the Vehicule
        VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehicule);
        restVehiculeMockMvc.perform(post("/api/vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeDTO)))
            .andExpect(status().isCreated());

        // Validate the Vehicule in the database
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicule testVehicule = vehiculeList.get(vehiculeList.size() - 1);
        assertThat(testVehicule.getNumeroChassis()).isEqualTo(DEFAULT_NUMERO_CHASSIS);
        assertThat(testVehicule.getTypes()).isEqualTo(DEFAULT_TYPES);
        assertThat(testVehicule.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehicule.getEnergie()).isEqualTo(DEFAULT_ENERGIE);
        assertThat(testVehicule.getPuissanceReel()).isEqualTo(DEFAULT_PUISSANCE_REEL);
        assertThat(testVehicule.getPuissanceAdmin()).isEqualTo(DEFAULT_PUISSANCE_ADMIN);
        assertThat(testVehicule.getCouleur()).isEqualTo(DEFAULT_COULEUR);
        assertThat(testVehicule.getPoidsVide()).isEqualTo(DEFAULT_POIDS_VIDE);
        assertThat(testVehicule.getChargeUtile()).isEqualTo(DEFAULT_CHARGE_UTILE);
        assertThat(testVehicule.getPtac()).isEqualTo(DEFAULT_PTAC);
        assertThat(testVehicule.getPtra()).isEqualTo(DEFAULT_PTRA);
        assertThat(testVehicule.getNbrPlace()).isEqualTo(DEFAULT_NBR_PLACE);
        assertThat(testVehicule.getCapacite()).isEqualTo(DEFAULT_CAPACITE);
        assertThat(testVehicule.getDateMiseCirculation()).isEqualTo(DEFAULT_DATE_MISE_CIRCULATION);
        assertThat(testVehicule.getRegime()).isEqualTo(DEFAULT_REGIME);
        assertThat(testVehicule.getNoDedouanement()).isEqualTo(DEFAULT_NO_DEDOUANEMENT);
        assertThat(testVehicule.getDateDedouanement()).isEqualTo(DEFAULT_DATE_DEDOUANEMENT);
    }

    @Test
    @Transactional
    public void createVehiculeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiculeRepository.findAll().size();

        // Create the Vehicule with an existing ID
        vehicule.setId(1L);
        VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehicule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculeMockMvc.perform(post("/api/vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicule in the database
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVehicules() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList
        restVehiculeMockMvc.perform(get("/api/vehicules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicule.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroChassis").value(hasItem(DEFAULT_NUMERO_CHASSIS)))
            .andExpect(jsonPath("$.[*].types").value(hasItem(DEFAULT_TYPES)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].energie").value(hasItem(DEFAULT_ENERGIE)))
            .andExpect(jsonPath("$.[*].puissanceReel").value(hasItem(DEFAULT_PUISSANCE_REEL)))
            .andExpect(jsonPath("$.[*].puissanceAdmin").value(hasItem(DEFAULT_PUISSANCE_ADMIN)))
            .andExpect(jsonPath("$.[*].couleur").value(hasItem(DEFAULT_COULEUR)))
            .andExpect(jsonPath("$.[*].poidsVide").value(hasItem(DEFAULT_POIDS_VIDE)))
            .andExpect(jsonPath("$.[*].chargeUtile").value(hasItem(DEFAULT_CHARGE_UTILE)))
            .andExpect(jsonPath("$.[*].ptac").value(hasItem(DEFAULT_PTAC)))
            .andExpect(jsonPath("$.[*].ptra").value(hasItem(DEFAULT_PTRA)))
            .andExpect(jsonPath("$.[*].nbrPlace").value(hasItem(DEFAULT_NBR_PLACE)))
            .andExpect(jsonPath("$.[*].capacite").value(hasItem(DEFAULT_CAPACITE)))
            .andExpect(jsonPath("$.[*].dateMiseCirculation").value(hasItem(DEFAULT_DATE_MISE_CIRCULATION.toString())))
            .andExpect(jsonPath("$.[*].regime").value(hasItem(DEFAULT_REGIME)))
            .andExpect(jsonPath("$.[*].noDedouanement").value(hasItem(DEFAULT_NO_DEDOUANEMENT)))
            .andExpect(jsonPath("$.[*].dateDedouanement").value(hasItem(DEFAULT_DATE_DEDOUANEMENT.toString())));
    }

    @Test
    @Transactional
    public void getVehicule() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get the vehicule
        restVehiculeMockMvc.perform(get("/api/vehicules/{id}", vehicule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicule.getId().intValue()))
            .andExpect(jsonPath("$.numeroChassis").value(DEFAULT_NUMERO_CHASSIS))
            .andExpect(jsonPath("$.types").value(DEFAULT_TYPES))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.energie").value(DEFAULT_ENERGIE))
            .andExpect(jsonPath("$.puissanceReel").value(DEFAULT_PUISSANCE_REEL))
            .andExpect(jsonPath("$.puissanceAdmin").value(DEFAULT_PUISSANCE_ADMIN))
            .andExpect(jsonPath("$.couleur").value(DEFAULT_COULEUR))
            .andExpect(jsonPath("$.poidsVide").value(DEFAULT_POIDS_VIDE))
            .andExpect(jsonPath("$.chargeUtile").value(DEFAULT_CHARGE_UTILE))
            .andExpect(jsonPath("$.ptac").value(DEFAULT_PTAC))
            .andExpect(jsonPath("$.ptra").value(DEFAULT_PTRA))
            .andExpect(jsonPath("$.nbrPlace").value(DEFAULT_NBR_PLACE))
            .andExpect(jsonPath("$.capacite").value(DEFAULT_CAPACITE))
            .andExpect(jsonPath("$.dateMiseCirculation").value(DEFAULT_DATE_MISE_CIRCULATION.toString()))
            .andExpect(jsonPath("$.regime").value(DEFAULT_REGIME))
            .andExpect(jsonPath("$.noDedouanement").value(DEFAULT_NO_DEDOUANEMENT))
            .andExpect(jsonPath("$.dateDedouanement").value(DEFAULT_DATE_DEDOUANEMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicule() throws Exception {
        // Get the vehicule
        restVehiculeMockMvc.perform(get("/api/vehicules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicule() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        int databaseSizeBeforeUpdate = vehiculeRepository.findAll().size();

        // Update the vehicule
        Vehicule updatedVehicule = vehiculeRepository.findById(vehicule.getId()).get();
        // Disconnect from session so that the updates on updatedVehicule are not directly saved in db
        em.detach(updatedVehicule);
        updatedVehicule
            .numeroChassis(UPDATED_NUMERO_CHASSIS)
            .types(UPDATED_TYPES)
            .model(UPDATED_MODEL)
            .energie(UPDATED_ENERGIE)
            .puissanceReel(UPDATED_PUISSANCE_REEL)
            .puissanceAdmin(UPDATED_PUISSANCE_ADMIN)
            .couleur(UPDATED_COULEUR)
            .poidsVide(UPDATED_POIDS_VIDE)
            .chargeUtile(UPDATED_CHARGE_UTILE)
            .ptac(UPDATED_PTAC)
            .ptra(UPDATED_PTRA)
            .nbrPlace(UPDATED_NBR_PLACE)
            .capacite(UPDATED_CAPACITE)
            .dateMiseCirculation(UPDATED_DATE_MISE_CIRCULATION)
            .regime(UPDATED_REGIME)
            .noDedouanement(UPDATED_NO_DEDOUANEMENT)
            .dateDedouanement(UPDATED_DATE_DEDOUANEMENT);
        VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(updatedVehicule);

        restVehiculeMockMvc.perform(put("/api/vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeDTO)))
            .andExpect(status().isOk());

        // Validate the Vehicule in the database
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeUpdate);
        Vehicule testVehicule = vehiculeList.get(vehiculeList.size() - 1);
        assertThat(testVehicule.getNumeroChassis()).isEqualTo(UPDATED_NUMERO_CHASSIS);
        assertThat(testVehicule.getTypes()).isEqualTo(UPDATED_TYPES);
        assertThat(testVehicule.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehicule.getEnergie()).isEqualTo(UPDATED_ENERGIE);
        assertThat(testVehicule.getPuissanceReel()).isEqualTo(UPDATED_PUISSANCE_REEL);
        assertThat(testVehicule.getPuissanceAdmin()).isEqualTo(UPDATED_PUISSANCE_ADMIN);
        assertThat(testVehicule.getCouleur()).isEqualTo(UPDATED_COULEUR);
        assertThat(testVehicule.getPoidsVide()).isEqualTo(UPDATED_POIDS_VIDE);
        assertThat(testVehicule.getChargeUtile()).isEqualTo(UPDATED_CHARGE_UTILE);
        assertThat(testVehicule.getPtac()).isEqualTo(UPDATED_PTAC);
        assertThat(testVehicule.getPtra()).isEqualTo(UPDATED_PTRA);
        assertThat(testVehicule.getNbrPlace()).isEqualTo(UPDATED_NBR_PLACE);
        assertThat(testVehicule.getCapacite()).isEqualTo(UPDATED_CAPACITE);
        assertThat(testVehicule.getDateMiseCirculation()).isEqualTo(UPDATED_DATE_MISE_CIRCULATION);
        assertThat(testVehicule.getRegime()).isEqualTo(UPDATED_REGIME);
        assertThat(testVehicule.getNoDedouanement()).isEqualTo(UPDATED_NO_DEDOUANEMENT);
        assertThat(testVehicule.getDateDedouanement()).isEqualTo(UPDATED_DATE_DEDOUANEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicule() throws Exception {
        int databaseSizeBeforeUpdate = vehiculeRepository.findAll().size();

        // Create the Vehicule
        VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehicule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculeMockMvc.perform(put("/api/vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicule in the database
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicule() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        int databaseSizeBeforeDelete = vehiculeRepository.findAll().size();

        // Delete the vehicule
        restVehiculeMockMvc.perform(delete("/api/vehicules/{id}", vehicule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
