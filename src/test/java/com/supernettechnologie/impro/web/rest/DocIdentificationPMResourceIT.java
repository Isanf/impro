package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.DocIdentificationPM;
import com.supernettechnologie.impro.repository.DocIdentificationPMRepository;
import com.supernettechnologie.impro.service.DocIdentificationPMService;
import com.supernettechnologie.impro.service.dto.DocIdentificationPMDTO;
import com.supernettechnologie.impro.service.mapper.DocIdentificationPMMapper;
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
 * Integration tests for the {@link DocIdentificationPMResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class DocIdentificationPMResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_IFU = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IFU = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_RCCM = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_RCCM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SIEGE_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_SIEGE_SOCIAL = "BBBBBBBBBB";

    @Autowired
    private DocIdentificationPMRepository docIdentificationPMRepository;

    @Autowired
    private DocIdentificationPMMapper docIdentificationPMMapper;

    @Autowired
    private DocIdentificationPMService docIdentificationPMService;

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

    private MockMvc restDocIdentificationPMMockMvc;

    private DocIdentificationPM docIdentificationPM;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocIdentificationPMResource docIdentificationPMResource = new DocIdentificationPMResource(docIdentificationPMService);
        this.restDocIdentificationPMMockMvc = MockMvcBuilders.standaloneSetup(docIdentificationPMResource)
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
    public static DocIdentificationPM createEntity(EntityManager em) {
        DocIdentificationPM docIdentificationPM = new DocIdentificationPM()
            .numero(DEFAULT_NUMERO)
            .numeroIFU(DEFAULT_NUMERO_IFU)
            .numeroRCCM(DEFAULT_NUMERO_RCCM)
            .telephone(DEFAULT_TELEPHONE)
            .siegeSocial(DEFAULT_SIEGE_SOCIAL);
        return docIdentificationPM;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocIdentificationPM createUpdatedEntity(EntityManager em) {
        DocIdentificationPM docIdentificationPM = new DocIdentificationPM()
            .numero(UPDATED_NUMERO)
            .numeroIFU(UPDATED_NUMERO_IFU)
            .numeroRCCM(UPDATED_NUMERO_RCCM)
            .telephone(UPDATED_TELEPHONE)
            .siegeSocial(UPDATED_SIEGE_SOCIAL);
        return docIdentificationPM;
    }

    @BeforeEach
    public void initTest() {
        docIdentificationPM = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocIdentificationPM() throws Exception {
        int databaseSizeBeforeCreate = docIdentificationPMRepository.findAll().size();

        // Create the DocIdentificationPM
        DocIdentificationPMDTO docIdentificationPMDTO = docIdentificationPMMapper.toDto(docIdentificationPM);
        restDocIdentificationPMMockMvc.perform(post("/api/doc-identification-pms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docIdentificationPMDTO)))
            .andExpect(status().isCreated());

        // Validate the DocIdentificationPM in the database
        List<DocIdentificationPM> docIdentificationPMList = docIdentificationPMRepository.findAll();
        assertThat(docIdentificationPMList).hasSize(databaseSizeBeforeCreate + 1);
        DocIdentificationPM testDocIdentificationPM = docIdentificationPMList.get(docIdentificationPMList.size() - 1);
        assertThat(testDocIdentificationPM.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testDocIdentificationPM.getNumeroIFU()).isEqualTo(DEFAULT_NUMERO_IFU);
        assertThat(testDocIdentificationPM.getNumeroRCCM()).isEqualTo(DEFAULT_NUMERO_RCCM);
        assertThat(testDocIdentificationPM.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testDocIdentificationPM.getSiegeSocial()).isEqualTo(DEFAULT_SIEGE_SOCIAL);
    }

    @Test
    @Transactional
    public void createDocIdentificationPMWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = docIdentificationPMRepository.findAll().size();

        // Create the DocIdentificationPM with an existing ID
        docIdentificationPM.setId(1L);
        DocIdentificationPMDTO docIdentificationPMDTO = docIdentificationPMMapper.toDto(docIdentificationPM);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocIdentificationPMMockMvc.perform(post("/api/doc-identification-pms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docIdentificationPMDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocIdentificationPM in the database
        List<DocIdentificationPM> docIdentificationPMList = docIdentificationPMRepository.findAll();
        assertThat(docIdentificationPMList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocIdentificationPMS() throws Exception {
        // Initialize the database
        docIdentificationPMRepository.saveAndFlush(docIdentificationPM);

        // Get all the docIdentificationPMList
        restDocIdentificationPMMockMvc.perform(get("/api/doc-identification-pms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docIdentificationPM.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].numeroIFU").value(hasItem(DEFAULT_NUMERO_IFU)))
            .andExpect(jsonPath("$.[*].numeroRCCM").value(hasItem(DEFAULT_NUMERO_RCCM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].siegeSocial").value(hasItem(DEFAULT_SIEGE_SOCIAL)));
    }

    @Test
    @Transactional
    public void getDocIdentificationPM() throws Exception {
        // Initialize the database
        docIdentificationPMRepository.saveAndFlush(docIdentificationPM);

        // Get the docIdentificationPM
        restDocIdentificationPMMockMvc.perform(get("/api/doc-identification-pms/{id}", docIdentificationPM.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(docIdentificationPM.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.numeroIFU").value(DEFAULT_NUMERO_IFU))
            .andExpect(jsonPath("$.numeroRCCM").value(DEFAULT_NUMERO_RCCM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.siegeSocial").value(DEFAULT_SIEGE_SOCIAL));
    }

    @Test
    @Transactional
    public void getNonExistingDocIdentificationPM() throws Exception {
        // Get the docIdentificationPM
        restDocIdentificationPMMockMvc.perform(get("/api/doc-identification-pms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocIdentificationPM() throws Exception {
        // Initialize the database
        docIdentificationPMRepository.saveAndFlush(docIdentificationPM);

        int databaseSizeBeforeUpdate = docIdentificationPMRepository.findAll().size();

        // Update the docIdentificationPM
        DocIdentificationPM updatedDocIdentificationPM = docIdentificationPMRepository.findById(docIdentificationPM.getId()).get();
        // Disconnect from session so that the updates on updatedDocIdentificationPM are not directly saved in db
        em.detach(updatedDocIdentificationPM);
        updatedDocIdentificationPM
            .numero(UPDATED_NUMERO)
            .numeroIFU(UPDATED_NUMERO_IFU)
            .numeroRCCM(UPDATED_NUMERO_RCCM)
            .telephone(UPDATED_TELEPHONE)
            .siegeSocial(UPDATED_SIEGE_SOCIAL);
        DocIdentificationPMDTO docIdentificationPMDTO = docIdentificationPMMapper.toDto(updatedDocIdentificationPM);

        restDocIdentificationPMMockMvc.perform(put("/api/doc-identification-pms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docIdentificationPMDTO)))
            .andExpect(status().isOk());

        // Validate the DocIdentificationPM in the database
        List<DocIdentificationPM> docIdentificationPMList = docIdentificationPMRepository.findAll();
        assertThat(docIdentificationPMList).hasSize(databaseSizeBeforeUpdate);
        DocIdentificationPM testDocIdentificationPM = docIdentificationPMList.get(docIdentificationPMList.size() - 1);
        assertThat(testDocIdentificationPM.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDocIdentificationPM.getNumeroIFU()).isEqualTo(UPDATED_NUMERO_IFU);
        assertThat(testDocIdentificationPM.getNumeroRCCM()).isEqualTo(UPDATED_NUMERO_RCCM);
        assertThat(testDocIdentificationPM.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDocIdentificationPM.getSiegeSocial()).isEqualTo(UPDATED_SIEGE_SOCIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingDocIdentificationPM() throws Exception {
        int databaseSizeBeforeUpdate = docIdentificationPMRepository.findAll().size();

        // Create the DocIdentificationPM
        DocIdentificationPMDTO docIdentificationPMDTO = docIdentificationPMMapper.toDto(docIdentificationPM);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocIdentificationPMMockMvc.perform(put("/api/doc-identification-pms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docIdentificationPMDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocIdentificationPM in the database
        List<DocIdentificationPM> docIdentificationPMList = docIdentificationPMRepository.findAll();
        assertThat(docIdentificationPMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocIdentificationPM() throws Exception {
        // Initialize the database
        docIdentificationPMRepository.saveAndFlush(docIdentificationPM);

        int databaseSizeBeforeDelete = docIdentificationPMRepository.findAll().size();

        // Delete the docIdentificationPM
        restDocIdentificationPMMockMvc.perform(delete("/api/doc-identification-pms/{id}", docIdentificationPM.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocIdentificationPM> docIdentificationPMList = docIdentificationPMRepository.findAll();
        assertThat(docIdentificationPMList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
