package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.OrganisationLocalite;
import com.supernettechnologie.impro.repository.OrganisationLocaliteRepository;
import com.supernettechnologie.impro.service.OrganisationLocaliteService;
import com.supernettechnologie.impro.service.dto.OrganisationLocaliteDTO;
import com.supernettechnologie.impro.service.mapper.OrganisationLocaliteMapper;
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
 * Integration tests for the {@link OrganisationLocaliteResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class OrganisationLocaliteResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OrganisationLocaliteRepository organisationLocaliteRepository;

    @Autowired
    private OrganisationLocaliteMapper organisationLocaliteMapper;

    @Autowired
    private OrganisationLocaliteService organisationLocaliteService;

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

    private MockMvc restOrganisationLocaliteMockMvc;

    private OrganisationLocalite organisationLocalite;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganisationLocaliteResource organisationLocaliteResource = new OrganisationLocaliteResource(organisationLocaliteService);
        this.restOrganisationLocaliteMockMvc = MockMvcBuilders.standaloneSetup(organisationLocaliteResource)
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
    public static OrganisationLocalite createEntity(EntityManager em) {
        OrganisationLocalite organisationLocalite = new OrganisationLocalite()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION);
        return organisationLocalite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganisationLocalite createUpdatedEntity(EntityManager em) {
        OrganisationLocalite organisationLocalite = new OrganisationLocalite()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION);
        return organisationLocalite;
    }

    @BeforeEach
    public void initTest() {
        organisationLocalite = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganisationLocalite() throws Exception {
        int databaseSizeBeforeCreate = organisationLocaliteRepository.findAll().size();

        // Create the OrganisationLocalite
        OrganisationLocaliteDTO organisationLocaliteDTO = organisationLocaliteMapper.toDto(organisationLocalite);
        restOrganisationLocaliteMockMvc.perform(post("/api/organisation-localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationLocaliteDTO)))
            .andExpect(status().isCreated());

        // Validate the OrganisationLocalite in the database
        List<OrganisationLocalite> organisationLocaliteList = organisationLocaliteRepository.findAll();
        assertThat(organisationLocaliteList).hasSize(databaseSizeBeforeCreate + 1);
        OrganisationLocalite testOrganisationLocalite = organisationLocaliteList.get(organisationLocaliteList.size() - 1);
        assertThat(testOrganisationLocalite.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testOrganisationLocalite.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOrganisationLocaliteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organisationLocaliteRepository.findAll().size();

        // Create the OrganisationLocalite with an existing ID
        organisationLocalite.setId(1L);
        OrganisationLocaliteDTO organisationLocaliteDTO = organisationLocaliteMapper.toDto(organisationLocalite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisationLocaliteMockMvc.perform(post("/api/organisation-localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationLocaliteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrganisationLocalite in the database
        List<OrganisationLocalite> organisationLocaliteList = organisationLocaliteRepository.findAll();
        assertThat(organisationLocaliteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrganisationLocalites() throws Exception {
        // Initialize the database
        organisationLocaliteRepository.saveAndFlush(organisationLocalite);

        // Get all the organisationLocaliteList
        restOrganisationLocaliteMockMvc.perform(get("/api/organisation-localites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisationLocalite.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getOrganisationLocalite() throws Exception {
        // Initialize the database
        organisationLocaliteRepository.saveAndFlush(organisationLocalite);

        // Get the organisationLocalite
        restOrganisationLocaliteMockMvc.perform(get("/api/organisation-localites/{id}", organisationLocalite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organisationLocalite.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingOrganisationLocalite() throws Exception {
        // Get the organisationLocalite
        restOrganisationLocaliteMockMvc.perform(get("/api/organisation-localites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganisationLocalite() throws Exception {
        // Initialize the database
        organisationLocaliteRepository.saveAndFlush(organisationLocalite);

        int databaseSizeBeforeUpdate = organisationLocaliteRepository.findAll().size();

        // Update the organisationLocalite
        OrganisationLocalite updatedOrganisationLocalite = organisationLocaliteRepository.findById(organisationLocalite.getId()).get();
        // Disconnect from session so that the updates on updatedOrganisationLocalite are not directly saved in db
        em.detach(updatedOrganisationLocalite);
        updatedOrganisationLocalite
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION);
        OrganisationLocaliteDTO organisationLocaliteDTO = organisationLocaliteMapper.toDto(updatedOrganisationLocalite);

        restOrganisationLocaliteMockMvc.perform(put("/api/organisation-localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationLocaliteDTO)))
            .andExpect(status().isOk());

        // Validate the OrganisationLocalite in the database
        List<OrganisationLocalite> organisationLocaliteList = organisationLocaliteRepository.findAll();
        assertThat(organisationLocaliteList).hasSize(databaseSizeBeforeUpdate);
        OrganisationLocalite testOrganisationLocalite = organisationLocaliteList.get(organisationLocaliteList.size() - 1);
        assertThat(testOrganisationLocalite.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testOrganisationLocalite.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganisationLocalite() throws Exception {
        int databaseSizeBeforeUpdate = organisationLocaliteRepository.findAll().size();

        // Create the OrganisationLocalite
        OrganisationLocaliteDTO organisationLocaliteDTO = organisationLocaliteMapper.toDto(organisationLocalite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisationLocaliteMockMvc.perform(put("/api/organisation-localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationLocaliteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrganisationLocalite in the database
        List<OrganisationLocalite> organisationLocaliteList = organisationLocaliteRepository.findAll();
        assertThat(organisationLocaliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrganisationLocalite() throws Exception {
        // Initialize the database
        organisationLocaliteRepository.saveAndFlush(organisationLocalite);

        int databaseSizeBeforeDelete = organisationLocaliteRepository.findAll().size();

        // Delete the organisationLocalite
        restOrganisationLocaliteMockMvc.perform(delete("/api/organisation-localites/{id}", organisationLocalite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganisationLocalite> organisationLocaliteList = organisationLocaliteRepository.findAll();
        assertThat(organisationLocaliteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
