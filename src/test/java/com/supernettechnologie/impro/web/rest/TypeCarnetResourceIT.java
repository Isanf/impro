package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.TypeCarnet;
import com.supernettechnologie.impro.repository.TypeCarnetRepository;
import com.supernettechnologie.impro.service.TypeCarnetService;
import com.supernettechnologie.impro.service.dto.TypeCarnetDTO;
import com.supernettechnologie.impro.service.mapper.TypeCarnetMapper;
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
 * Integration tests for the {@link TypeCarnetResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class TypeCarnetResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITE_CERTIFICAT = 1L;
    private static final Long UPDATED_QUANTITE_CERTIFICAT = 2L;

    @Autowired
    private TypeCarnetRepository typeCarnetRepository;

    @Autowired
    private TypeCarnetMapper typeCarnetMapper;

    @Autowired
    private TypeCarnetService typeCarnetService;

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

    private MockMvc restTypeCarnetMockMvc;

    private TypeCarnet typeCarnet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeCarnetResource typeCarnetResource = new TypeCarnetResource(typeCarnetService);
        this.restTypeCarnetMockMvc = MockMvcBuilders.standaloneSetup(typeCarnetResource)
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
    public static TypeCarnet createEntity(EntityManager em) {
        TypeCarnet typeCarnet = new TypeCarnet()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .quantiteCertificat(DEFAULT_QUANTITE_CERTIFICAT);
        return typeCarnet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeCarnet createUpdatedEntity(EntityManager em) {
        TypeCarnet typeCarnet = new TypeCarnet()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .quantiteCertificat(UPDATED_QUANTITE_CERTIFICAT);
        return typeCarnet;
    }

    @BeforeEach
    public void initTest() {
        typeCarnet = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeCarnet() throws Exception {
        int databaseSizeBeforeCreate = typeCarnetRepository.findAll().size();

        // Create the TypeCarnet
        TypeCarnetDTO typeCarnetDTO = typeCarnetMapper.toDto(typeCarnet);
        restTypeCarnetMockMvc.perform(post("/api/type-carnets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCarnetDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeCarnet in the database
        List<TypeCarnet> typeCarnetList = typeCarnetRepository.findAll();
        assertThat(typeCarnetList).hasSize(databaseSizeBeforeCreate + 1);
        TypeCarnet testTypeCarnet = typeCarnetList.get(typeCarnetList.size() - 1);
        assertThat(testTypeCarnet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeCarnet.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeCarnet.getQuantiteCertificat()).isEqualTo(DEFAULT_QUANTITE_CERTIFICAT);
    }

    @Test
    @Transactional
    public void createTypeCarnetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeCarnetRepository.findAll().size();

        // Create the TypeCarnet with an existing ID
        typeCarnet.setId(1L);
        TypeCarnetDTO typeCarnetDTO = typeCarnetMapper.toDto(typeCarnet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeCarnetMockMvc.perform(post("/api/type-carnets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCarnetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCarnet in the database
        List<TypeCarnet> typeCarnetList = typeCarnetRepository.findAll();
        assertThat(typeCarnetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeCarnets() throws Exception {
        // Initialize the database
        typeCarnetRepository.saveAndFlush(typeCarnet);

        // Get all the typeCarnetList
        restTypeCarnetMockMvc.perform(get("/api/type-carnets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeCarnet.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].quantiteCertificat").value(hasItem(DEFAULT_QUANTITE_CERTIFICAT.intValue())));
    }

    @Test
    @Transactional
    public void getTypeCarnet() throws Exception {
        // Initialize the database
        typeCarnetRepository.saveAndFlush(typeCarnet);

        // Get the typeCarnet
        restTypeCarnetMockMvc.perform(get("/api/type-carnets/{id}", typeCarnet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeCarnet.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.quantiteCertificat").value(DEFAULT_QUANTITE_CERTIFICAT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeCarnet() throws Exception {
        // Get the typeCarnet
        restTypeCarnetMockMvc.perform(get("/api/type-carnets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeCarnet() throws Exception {
        // Initialize the database
        typeCarnetRepository.saveAndFlush(typeCarnet);

        int databaseSizeBeforeUpdate = typeCarnetRepository.findAll().size();

        // Update the typeCarnet
        TypeCarnet updatedTypeCarnet = typeCarnetRepository.findById(typeCarnet.getId()).get();
        // Disconnect from session so that the updates on updatedTypeCarnet are not directly saved in db
        em.detach(updatedTypeCarnet);
        updatedTypeCarnet
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .quantiteCertificat(UPDATED_QUANTITE_CERTIFICAT);
        TypeCarnetDTO typeCarnetDTO = typeCarnetMapper.toDto(updatedTypeCarnet);

        restTypeCarnetMockMvc.perform(put("/api/type-carnets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCarnetDTO)))
            .andExpect(status().isOk());

        // Validate the TypeCarnet in the database
        List<TypeCarnet> typeCarnetList = typeCarnetRepository.findAll();
        assertThat(typeCarnetList).hasSize(databaseSizeBeforeUpdate);
        TypeCarnet testTypeCarnet = typeCarnetList.get(typeCarnetList.size() - 1);
        assertThat(testTypeCarnet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeCarnet.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeCarnet.getQuantiteCertificat()).isEqualTo(UPDATED_QUANTITE_CERTIFICAT);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeCarnet() throws Exception {
        int databaseSizeBeforeUpdate = typeCarnetRepository.findAll().size();

        // Create the TypeCarnet
        TypeCarnetDTO typeCarnetDTO = typeCarnetMapper.toDto(typeCarnet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeCarnetMockMvc.perform(put("/api/type-carnets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCarnetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCarnet in the database
        List<TypeCarnet> typeCarnetList = typeCarnetRepository.findAll();
        assertThat(typeCarnetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeCarnet() throws Exception {
        // Initialize the database
        typeCarnetRepository.saveAndFlush(typeCarnet);

        int databaseSizeBeforeDelete = typeCarnetRepository.findAll().size();

        // Delete the typeCarnet
        restTypeCarnetMockMvc.perform(delete("/api/type-carnets/{id}", typeCarnet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeCarnet> typeCarnetList = typeCarnetRepository.findAll();
        assertThat(typeCarnetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
