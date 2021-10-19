package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.TypeOrganisation;
import com.supernettechnologie.impro.repository.TypeOrganisationRepository;
import com.supernettechnologie.impro.service.TypeOrganisationService;
import com.supernettechnologie.impro.service.dto.TypeOrganisationDTO;
import com.supernettechnologie.impro.service.mapper.TypeOrganisationMapper;
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
 * Integration tests for the {@link TypeOrganisationResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class TypeOrganisationResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NIVEAU = 1;
    private static final Integer UPDATED_NIVEAU = 2;

    @Autowired
    private TypeOrganisationRepository typeOrganisationRepository;

    @Autowired
    private TypeOrganisationMapper typeOrganisationMapper;

    @Autowired
    private TypeOrganisationService typeOrganisationService;

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

    private MockMvc restTypeOrganisationMockMvc;

    private TypeOrganisation typeOrganisation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeOrganisationResource typeOrganisationResource = new TypeOrganisationResource(typeOrganisationService);
        this.restTypeOrganisationMockMvc = MockMvcBuilders.standaloneSetup(typeOrganisationResource)
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
    public static TypeOrganisation createEntity(EntityManager em) {
        TypeOrganisation typeOrganisation = new TypeOrganisation()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .niveau(DEFAULT_NIVEAU);
        return typeOrganisation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOrganisation createUpdatedEntity(EntityManager em) {
        TypeOrganisation typeOrganisation = new TypeOrganisation()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .niveau(UPDATED_NIVEAU);
        return typeOrganisation;
    }

    @BeforeEach
    public void initTest() {
        typeOrganisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeOrganisation() throws Exception {
        int databaseSizeBeforeCreate = typeOrganisationRepository.findAll().size();

        // Create the TypeOrganisation
        TypeOrganisationDTO typeOrganisationDTO = typeOrganisationMapper.toDto(typeOrganisation);
        restTypeOrganisationMockMvc.perform(post("/api/type-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeOrganisationDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeOrganisation in the database
        List<TypeOrganisation> typeOrganisationList = typeOrganisationRepository.findAll();
        assertThat(typeOrganisationList).hasSize(databaseSizeBeforeCreate + 1);
        TypeOrganisation testTypeOrganisation = typeOrganisationList.get(typeOrganisationList.size() - 1);
        assertThat(testTypeOrganisation.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTypeOrganisation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTypeOrganisation.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
    }

    @Test
    @Transactional
    public void createTypeOrganisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeOrganisationRepository.findAll().size();

        // Create the TypeOrganisation with an existing ID
        typeOrganisation.setId(1L);
        TypeOrganisationDTO typeOrganisationDTO = typeOrganisationMapper.toDto(typeOrganisation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeOrganisationMockMvc.perform(post("/api/type-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeOrganisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeOrganisation in the database
        List<TypeOrganisation> typeOrganisationList = typeOrganisationRepository.findAll();
        assertThat(typeOrganisationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeOrganisationRepository.findAll().size();
        // set the field null
        typeOrganisation.setNom(null);

        // Create the TypeOrganisation, which fails.
        TypeOrganisationDTO typeOrganisationDTO = typeOrganisationMapper.toDto(typeOrganisation);

        restTypeOrganisationMockMvc.perform(post("/api/type-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeOrganisationDTO)))
            .andExpect(status().isBadRequest());

        List<TypeOrganisation> typeOrganisationList = typeOrganisationRepository.findAll();
        assertThat(typeOrganisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNiveauIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeOrganisationRepository.findAll().size();
        // set the field null
        typeOrganisation.setNiveau(null);

        // Create the TypeOrganisation, which fails.
        TypeOrganisationDTO typeOrganisationDTO = typeOrganisationMapper.toDto(typeOrganisation);

        restTypeOrganisationMockMvc.perform(post("/api/type-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeOrganisationDTO)))
            .andExpect(status().isBadRequest());

        List<TypeOrganisation> typeOrganisationList = typeOrganisationRepository.findAll();
        assertThat(typeOrganisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeOrganisations() throws Exception {
        // Initialize the database
        typeOrganisationRepository.saveAndFlush(typeOrganisation);

        // Get all the typeOrganisationList
        restTypeOrganisationMockMvc.perform(get("/api/type-organisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeOrganisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU)));
    }
    
    @Test
    @Transactional
    public void getTypeOrganisation() throws Exception {
        // Initialize the database
        typeOrganisationRepository.saveAndFlush(typeOrganisation);

        // Get the typeOrganisation
        restTypeOrganisationMockMvc.perform(get("/api/type-organisations/{id}", typeOrganisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeOrganisation.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU));
    }

    @Test
    @Transactional
    public void getNonExistingTypeOrganisation() throws Exception {
        // Get the typeOrganisation
        restTypeOrganisationMockMvc.perform(get("/api/type-organisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeOrganisation() throws Exception {
        // Initialize the database
        typeOrganisationRepository.saveAndFlush(typeOrganisation);

        int databaseSizeBeforeUpdate = typeOrganisationRepository.findAll().size();

        // Update the typeOrganisation
        TypeOrganisation updatedTypeOrganisation = typeOrganisationRepository.findById(typeOrganisation.getId()).get();
        // Disconnect from session so that the updates on updatedTypeOrganisation are not directly saved in db
        em.detach(updatedTypeOrganisation);
        updatedTypeOrganisation
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .niveau(UPDATED_NIVEAU);
        TypeOrganisationDTO typeOrganisationDTO = typeOrganisationMapper.toDto(updatedTypeOrganisation);

        restTypeOrganisationMockMvc.perform(put("/api/type-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeOrganisationDTO)))
            .andExpect(status().isOk());

        // Validate the TypeOrganisation in the database
        List<TypeOrganisation> typeOrganisationList = typeOrganisationRepository.findAll();
        assertThat(typeOrganisationList).hasSize(databaseSizeBeforeUpdate);
        TypeOrganisation testTypeOrganisation = typeOrganisationList.get(typeOrganisationList.size() - 1);
        assertThat(testTypeOrganisation.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypeOrganisation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTypeOrganisation.getNiveau()).isEqualTo(UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = typeOrganisationRepository.findAll().size();

        // Create the TypeOrganisation
        TypeOrganisationDTO typeOrganisationDTO = typeOrganisationMapper.toDto(typeOrganisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOrganisationMockMvc.perform(put("/api/type-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeOrganisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeOrganisation in the database
        List<TypeOrganisation> typeOrganisationList = typeOrganisationRepository.findAll();
        assertThat(typeOrganisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeOrganisation() throws Exception {
        // Initialize the database
        typeOrganisationRepository.saveAndFlush(typeOrganisation);

        int databaseSizeBeforeDelete = typeOrganisationRepository.findAll().size();

        // Delete the typeOrganisation
        restTypeOrganisationMockMvc.perform(delete("/api/type-organisations/{id}", typeOrganisation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeOrganisation> typeOrganisationList = typeOrganisationRepository.findAll();
        assertThat(typeOrganisationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
