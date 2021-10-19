package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.TypeVehicule;
import com.supernettechnologie.impro.repository.TypeVehiculeRepository;
import com.supernettechnologie.impro.service.TypeVehiculeService;
import com.supernettechnologie.impro.service.dto.TypeVehiculeDTO;
import com.supernettechnologie.impro.service.mapper.TypeVehiculeMapper;
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
 * Integration tests for the {@link TypeVehiculeResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class TypeVehiculeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Long DEFAULT_NOMBRE_PLAQUE = 1L;
    private static final Long UPDATED_NOMBRE_PLAQUE = 2L;

    @Autowired
    private TypeVehiculeRepository typeVehiculeRepository;

    @Autowired
    private TypeVehiculeMapper typeVehiculeMapper;

    @Autowired
    private TypeVehiculeService typeVehiculeService;

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

    private MockMvc restTypeVehiculeMockMvc;

    private TypeVehicule typeVehicule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeVehiculeResource typeVehiculeResource = new TypeVehiculeResource(typeVehiculeService);
        this.restTypeVehiculeMockMvc = MockMvcBuilders.standaloneSetup(typeVehiculeResource)
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
    public static TypeVehicule createEntity(EntityManager em) {
        TypeVehicule typeVehicule = new TypeVehicule()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .nombrePlaque(DEFAULT_NOMBRE_PLAQUE);
        return typeVehicule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeVehicule createUpdatedEntity(EntityManager em) {
        TypeVehicule typeVehicule = new TypeVehicule()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .nombrePlaque(UPDATED_NOMBRE_PLAQUE);
        return typeVehicule;
    }

    @BeforeEach
    public void initTest() {
        typeVehicule = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeVehicule() throws Exception {
        int databaseSizeBeforeCreate = typeVehiculeRepository.findAll().size();

        // Create the TypeVehicule
        TypeVehiculeDTO typeVehiculeDTO = typeVehiculeMapper.toDto(typeVehicule);
        restTypeVehiculeMockMvc.perform(post("/api/type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVehiculeDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeVehicule in the database
        List<TypeVehicule> typeVehiculeList = typeVehiculeRepository.findAll();
        assertThat(typeVehiculeList).hasSize(databaseSizeBeforeCreate + 1);
        TypeVehicule testTypeVehicule = typeVehiculeList.get(typeVehiculeList.size() - 1);
        assertThat(testTypeVehicule.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeVehicule.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeVehicule.getNombrePlaque()).isEqualTo(DEFAULT_NOMBRE_PLAQUE);
    }

    @Test
    @Transactional
    public void createTypeVehiculeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeVehiculeRepository.findAll().size();

        // Create the TypeVehicule with an existing ID
        typeVehicule.setId(1L);
        TypeVehiculeDTO typeVehiculeDTO = typeVehiculeMapper.toDto(typeVehicule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeVehiculeMockMvc.perform(post("/api/type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVehiculeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeVehicule in the database
        List<TypeVehicule> typeVehiculeList = typeVehiculeRepository.findAll();
        assertThat(typeVehiculeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeVehicules() throws Exception {
        // Initialize the database
        typeVehiculeRepository.saveAndFlush(typeVehicule);

        // Get all the typeVehiculeList
        restTypeVehiculeMockMvc.perform(get("/api/type-vehicules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeVehicule.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].nombrePlaque").value(hasItem(DEFAULT_NOMBRE_PLAQUE.intValue())));
    }

    @Test
    @Transactional
    public void getTypeVehicule() throws Exception {
        // Initialize the database
        typeVehiculeRepository.saveAndFlush(typeVehicule);

        // Get the typeVehicule
        restTypeVehiculeMockMvc.perform(get("/api/type-vehicules/{id}", typeVehicule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeVehicule.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.nombrePlaque").value(DEFAULT_NOMBRE_PLAQUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeVehicule() throws Exception {
        // Get the typeVehicule
        restTypeVehiculeMockMvc.perform(get("/api/type-vehicules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeVehicule() throws Exception {
        // Initialize the database
        typeVehiculeRepository.saveAndFlush(typeVehicule);

        int databaseSizeBeforeUpdate = typeVehiculeRepository.findAll().size();

        // Update the typeVehicule
        TypeVehicule updatedTypeVehicule = typeVehiculeRepository.findById(typeVehicule.getId()).get();
        // Disconnect from session so that the updates on updatedTypeVehicule are not directly saved in db
        em.detach(updatedTypeVehicule);
        updatedTypeVehicule
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .nombrePlaque(UPDATED_NOMBRE_PLAQUE);
        TypeVehiculeDTO typeVehiculeDTO = typeVehiculeMapper.toDto(updatedTypeVehicule);

        restTypeVehiculeMockMvc.perform(put("/api/type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVehiculeDTO)))
            .andExpect(status().isOk());

        // Validate the TypeVehicule in the database
        List<TypeVehicule> typeVehiculeList = typeVehiculeRepository.findAll();
        assertThat(typeVehiculeList).hasSize(databaseSizeBeforeUpdate);
        TypeVehicule testTypeVehicule = typeVehiculeList.get(typeVehiculeList.size() - 1);
        assertThat(testTypeVehicule.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeVehicule.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeVehicule.getNombrePlaque()).isEqualTo(UPDATED_NOMBRE_PLAQUE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeVehicule() throws Exception {
        int databaseSizeBeforeUpdate = typeVehiculeRepository.findAll().size();

        // Create the TypeVehicule
        TypeVehiculeDTO typeVehiculeDTO = typeVehiculeMapper.toDto(typeVehicule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeVehiculeMockMvc.perform(put("/api/type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVehiculeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeVehicule in the database
        List<TypeVehicule> typeVehiculeList = typeVehiculeRepository.findAll();
        assertThat(typeVehiculeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeVehicule() throws Exception {
        // Initialize the database
        typeVehiculeRepository.saveAndFlush(typeVehicule);

        int databaseSizeBeforeDelete = typeVehiculeRepository.findAll().size();

        // Delete the typeVehicule
        restTypeVehiculeMockMvc.perform(delete("/api/type-vehicules/{id}", typeVehicule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeVehicule> typeVehiculeList = typeVehiculeRepository.findAll();
        assertThat(typeVehiculeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
