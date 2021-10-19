package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.CategorieOrganisation;
import com.supernettechnologie.impro.repository.CategorieOrganisationRepository;
import com.supernettechnologie.impro.service.CategorieOrganisationService;
import com.supernettechnologie.impro.service.dto.CategorieOrganisationDTO;
import com.supernettechnologie.impro.service.mapper.CategorieOrganisationMapper;
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

import com.supernettechnologie.impro.domain.enumeration.TypeCategorieOrganisation;
/**
 * Integration tests for the {@link CategorieOrganisationResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class CategorieOrganisationResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final TypeCategorieOrganisation DEFAULT_TYPE_CATEGORIE_ORGANISATION = TypeCategorieOrganisation.SUPERNET;
    private static final TypeCategorieOrganisation UPDATED_TYPE_CATEGORIE_ORGANISATION = TypeCategorieOrganisation.CONCESSIONNAIRE;

    @Autowired
    private CategorieOrganisationRepository categorieOrganisationRepository;

    @Autowired
    private CategorieOrganisationMapper categorieOrganisationMapper;

    @Autowired
    private CategorieOrganisationService categorieOrganisationService;

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

    private MockMvc restCategorieOrganisationMockMvc;

    private CategorieOrganisation categorieOrganisation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategorieOrganisationResource categorieOrganisationResource = new CategorieOrganisationResource(categorieOrganisationService);
        this.restCategorieOrganisationMockMvc = MockMvcBuilders.standaloneSetup(categorieOrganisationResource)
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
    public static CategorieOrganisation createEntity(EntityManager em) {
        CategorieOrganisation categorieOrganisation = new CategorieOrganisation()
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION)
            .typeCategorieOrganisation(DEFAULT_TYPE_CATEGORIE_ORGANISATION);
        return categorieOrganisation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieOrganisation createUpdatedEntity(EntityManager em) {
        CategorieOrganisation categorieOrganisation = new CategorieOrganisation()
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .typeCategorieOrganisation(UPDATED_TYPE_CATEGORIE_ORGANISATION);
        return categorieOrganisation;
    }

    @BeforeEach
    public void initTest() {
        categorieOrganisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorieOrganisation() throws Exception {
        int databaseSizeBeforeCreate = categorieOrganisationRepository.findAll().size();

        // Create the CategorieOrganisation
        CategorieOrganisationDTO categorieOrganisationDTO = categorieOrganisationMapper.toDto(categorieOrganisation);
        restCategorieOrganisationMockMvc.perform(post("/api/categorie-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieOrganisationDTO)))
            .andExpect(status().isCreated());

        // Validate the CategorieOrganisation in the database
        List<CategorieOrganisation> categorieOrganisationList = categorieOrganisationRepository.findAll();
        assertThat(categorieOrganisationList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieOrganisation testCategorieOrganisation = categorieOrganisationList.get(categorieOrganisationList.size() - 1);
        assertThat(testCategorieOrganisation.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCategorieOrganisation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCategorieOrganisation.getTypeCategorieOrganisation()).isEqualTo(DEFAULT_TYPE_CATEGORIE_ORGANISATION);
    }

    @Test
    @Transactional
    public void createCategorieOrganisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categorieOrganisationRepository.findAll().size();

        // Create the CategorieOrganisation with an existing ID
        categorieOrganisation.setId(1L);
        CategorieOrganisationDTO categorieOrganisationDTO = categorieOrganisationMapper.toDto(categorieOrganisation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieOrganisationMockMvc.perform(post("/api/categorie-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieOrganisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieOrganisation in the database
        List<CategorieOrganisation> categorieOrganisationList = categorieOrganisationRepository.findAll();
        assertThat(categorieOrganisationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieOrganisationRepository.findAll().size();
        // set the field null
        categorieOrganisation.setLibelle(null);

        // Create the CategorieOrganisation, which fails.
        CategorieOrganisationDTO categorieOrganisationDTO = categorieOrganisationMapper.toDto(categorieOrganisation);

        restCategorieOrganisationMockMvc.perform(post("/api/categorie-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieOrganisationDTO)))
            .andExpect(status().isBadRequest());

        List<CategorieOrganisation> categorieOrganisationList = categorieOrganisationRepository.findAll();
        assertThat(categorieOrganisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategorieOrganisations() throws Exception {
        // Initialize the database
        categorieOrganisationRepository.saveAndFlush(categorieOrganisation);

        // Get all the categorieOrganisationList
        restCategorieOrganisationMockMvc.perform(get("/api/categorie-organisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieOrganisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].typeCategorieOrganisation").value(hasItem(DEFAULT_TYPE_CATEGORIE_ORGANISATION.toString())));
    }

    @Test
    @Transactional
    public void getCategorieOrganisation() throws Exception {
        // Initialize the database
        categorieOrganisationRepository.saveAndFlush(categorieOrganisation);

        // Get the categorieOrganisation
        restCategorieOrganisationMockMvc.perform(get("/api/categorie-organisations/{id}", categorieOrganisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorieOrganisation.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.typeCategorieOrganisation").value(DEFAULT_TYPE_CATEGORIE_ORGANISATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategorieOrganisation() throws Exception {
        // Get the categorieOrganisation
        restCategorieOrganisationMockMvc.perform(get("/api/categorie-organisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorieOrganisation() throws Exception {
        // Initialize the database
        categorieOrganisationRepository.saveAndFlush(categorieOrganisation);

        int databaseSizeBeforeUpdate = categorieOrganisationRepository.findAll().size();

        // Update the categorieOrganisation
        CategorieOrganisation updatedCategorieOrganisation = categorieOrganisationRepository.findById(categorieOrganisation.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieOrganisation are not directly saved in db
        em.detach(updatedCategorieOrganisation);
        updatedCategorieOrganisation
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .typeCategorieOrganisation(UPDATED_TYPE_CATEGORIE_ORGANISATION);
        CategorieOrganisationDTO categorieOrganisationDTO = categorieOrganisationMapper.toDto(updatedCategorieOrganisation);

        restCategorieOrganisationMockMvc.perform(put("/api/categorie-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieOrganisationDTO)))
            .andExpect(status().isOk());

        // Validate the CategorieOrganisation in the database
        List<CategorieOrganisation> categorieOrganisationList = categorieOrganisationRepository.findAll();
        assertThat(categorieOrganisationList).hasSize(databaseSizeBeforeUpdate);
        CategorieOrganisation testCategorieOrganisation = categorieOrganisationList.get(categorieOrganisationList.size() - 1);
        assertThat(testCategorieOrganisation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieOrganisation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategorieOrganisation.getTypeCategorieOrganisation()).isEqualTo(UPDATED_TYPE_CATEGORIE_ORGANISATION);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorieOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = categorieOrganisationRepository.findAll().size();

        // Create the CategorieOrganisation
        CategorieOrganisationDTO categorieOrganisationDTO = categorieOrganisationMapper.toDto(categorieOrganisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieOrganisationMockMvc.perform(put("/api/categorie-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieOrganisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieOrganisation in the database
        List<CategorieOrganisation> categorieOrganisationList = categorieOrganisationRepository.findAll();
        assertThat(categorieOrganisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategorieOrganisation() throws Exception {
        // Initialize the database
        categorieOrganisationRepository.saveAndFlush(categorieOrganisation);

        int databaseSizeBeforeDelete = categorieOrganisationRepository.findAll().size();

        // Delete the categorieOrganisation
        restCategorieOrganisationMockMvc.perform(delete("/api/categorie-organisations/{id}", categorieOrganisation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieOrganisation> categorieOrganisationList = categorieOrganisationRepository.findAll();
        assertThat(categorieOrganisationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
