package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.TypeActeur;
import com.supernettechnologie.impro.repository.TypeActeurRepository;
import com.supernettechnologie.impro.service.TypeActeurService;
import com.supernettechnologie.impro.service.dto.TypeActeurDTO;
import com.supernettechnologie.impro.service.mapper.TypeActeurMapper;
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
 * Integration tests for the {@link TypeActeurResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class TypeActeurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TypeActeurRepository typeActeurRepository;

    @Autowired
    private TypeActeurMapper typeActeurMapper;

    @Autowired
    private TypeActeurService typeActeurService;

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

    private MockMvc restTypeActeurMockMvc;

    private TypeActeur typeActeur;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeActeurResource typeActeurResource = new TypeActeurResource(typeActeurService);
        this.restTypeActeurMockMvc = MockMvcBuilders.standaloneSetup(typeActeurResource)
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
    public static TypeActeur createEntity(EntityManager em) {
        TypeActeur typeActeur = new TypeActeur()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION);
        return typeActeur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeActeur createUpdatedEntity(EntityManager em) {
        TypeActeur typeActeur = new TypeActeur()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION);
        return typeActeur;
    }

    @BeforeEach
    public void initTest() {
        typeActeur = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeActeur() throws Exception {
        int databaseSizeBeforeCreate = typeActeurRepository.findAll().size();

        // Create the TypeActeur
        TypeActeurDTO typeActeurDTO = typeActeurMapper.toDto(typeActeur);
        restTypeActeurMockMvc.perform(post("/api/type-acteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeActeurDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeActeur in the database
        List<TypeActeur> typeActeurList = typeActeurRepository.findAll();
        assertThat(typeActeurList).hasSize(databaseSizeBeforeCreate + 1);
        TypeActeur testTypeActeur = typeActeurList.get(typeActeurList.size() - 1);
        assertThat(testTypeActeur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTypeActeur.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTypeActeurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeActeurRepository.findAll().size();

        // Create the TypeActeur with an existing ID
        typeActeur.setId(1L);
        TypeActeurDTO typeActeurDTO = typeActeurMapper.toDto(typeActeur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeActeurMockMvc.perform(post("/api/type-acteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeActeurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeActeur in the database
        List<TypeActeur> typeActeurList = typeActeurRepository.findAll();
        assertThat(typeActeurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeActeurRepository.findAll().size();
        // set the field null
        typeActeur.setNom(null);

        // Create the TypeActeur, which fails.
        TypeActeurDTO typeActeurDTO = typeActeurMapper.toDto(typeActeur);

        restTypeActeurMockMvc.perform(post("/api/type-acteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeActeurDTO)))
            .andExpect(status().isBadRequest());

        List<TypeActeur> typeActeurList = typeActeurRepository.findAll();
        assertThat(typeActeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeActeurs() throws Exception {
        // Initialize the database
        typeActeurRepository.saveAndFlush(typeActeur);

        // Get all the typeActeurList
        restTypeActeurMockMvc.perform(get("/api/type-acteurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeActeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getTypeActeur() throws Exception {
        // Initialize the database
        typeActeurRepository.saveAndFlush(typeActeur);

        // Get the typeActeur
        restTypeActeurMockMvc.perform(get("/api/type-acteurs/{id}", typeActeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeActeur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingTypeActeur() throws Exception {
        // Get the typeActeur
        restTypeActeurMockMvc.perform(get("/api/type-acteurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeActeur() throws Exception {
        // Initialize the database
        typeActeurRepository.saveAndFlush(typeActeur);

        int databaseSizeBeforeUpdate = typeActeurRepository.findAll().size();

        // Update the typeActeur
        TypeActeur updatedTypeActeur = typeActeurRepository.findById(typeActeur.getId()).get();
        // Disconnect from session so that the updates on updatedTypeActeur are not directly saved in db
        em.detach(updatedTypeActeur);
        updatedTypeActeur
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION);
        TypeActeurDTO typeActeurDTO = typeActeurMapper.toDto(updatedTypeActeur);

        restTypeActeurMockMvc.perform(put("/api/type-acteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeActeurDTO)))
            .andExpect(status().isOk());

        // Validate the TypeActeur in the database
        List<TypeActeur> typeActeurList = typeActeurRepository.findAll();
        assertThat(typeActeurList).hasSize(databaseSizeBeforeUpdate);
        TypeActeur testTypeActeur = typeActeurList.get(typeActeurList.size() - 1);
        assertThat(testTypeActeur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypeActeur.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeActeur() throws Exception {
        int databaseSizeBeforeUpdate = typeActeurRepository.findAll().size();

        // Create the TypeActeur
        TypeActeurDTO typeActeurDTO = typeActeurMapper.toDto(typeActeur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeActeurMockMvc.perform(put("/api/type-acteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeActeurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeActeur in the database
        List<TypeActeur> typeActeurList = typeActeurRepository.findAll();
        assertThat(typeActeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeActeur() throws Exception {
        // Initialize the database
        typeActeurRepository.saveAndFlush(typeActeur);

        int databaseSizeBeforeDelete = typeActeurRepository.findAll().size();

        // Delete the typeActeur
        restTypeActeurMockMvc.perform(delete("/api/type-acteurs/{id}", typeActeur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeActeur> typeActeurList = typeActeurRepository.findAll();
        assertThat(typeActeurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
