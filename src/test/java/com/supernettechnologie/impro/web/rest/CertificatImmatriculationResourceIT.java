package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.CertificatImmatriculation;
import com.supernettechnologie.impro.repository.CertificatImmatriculationRepository;
import com.supernettechnologie.impro.service.CertificatImmatriculationService;
import com.supernettechnologie.impro.service.dto.CertificatImmatriculationDTO;
import com.supernettechnologie.impro.service.mapper.CertificatImmatriculationMapper;
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
 * Integration tests for the {@link CertificatImmatriculationResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class CertificatImmatriculationResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_QR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_QR = "BBBBBBBBBB";

    @Autowired
    private CertificatImmatriculationRepository certificatImmatriculationRepository;

    @Autowired
    private CertificatImmatriculationMapper certificatImmatriculationMapper;

    @Autowired
    private CertificatImmatriculationService certificatImmatriculationService;

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

    private MockMvc restCertificatImmatriculationMockMvc;

    private CertificatImmatriculation certificatImmatriculation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CertificatImmatriculationResource certificatImmatriculationResource = new CertificatImmatriculationResource(certificatImmatriculationService);
        this.restCertificatImmatriculationMockMvc = MockMvcBuilders.standaloneSetup(certificatImmatriculationResource)
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
    public static CertificatImmatriculation createEntity(EntityManager em) {
        CertificatImmatriculation certificatImmatriculation = new CertificatImmatriculation()
            .numero(DEFAULT_NUMERO)
            .codeQr(DEFAULT_CODE_QR);
        return certificatImmatriculation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CertificatImmatriculation createUpdatedEntity(EntityManager em) {
        CertificatImmatriculation certificatImmatriculation = new CertificatImmatriculation()
            .numero(UPDATED_NUMERO)
            .codeQr(UPDATED_CODE_QR);
        return certificatImmatriculation;
    }

    @BeforeEach
    public void initTest() {
        certificatImmatriculation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCertificatImmatriculation() throws Exception {
        int databaseSizeBeforeCreate = certificatImmatriculationRepository.findAll().size();

        // Create the CertificatImmatriculation
        CertificatImmatriculationDTO certificatImmatriculationDTO = certificatImmatriculationMapper.toDto(certificatImmatriculation);
        restCertificatImmatriculationMockMvc.perform(post("/api/certificat-immatriculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificatImmatriculationDTO)))
            .andExpect(status().isCreated());

        // Validate the CertificatImmatriculation in the database
        List<CertificatImmatriculation> certificatImmatriculationList = certificatImmatriculationRepository.findAll();
        assertThat(certificatImmatriculationList).hasSize(databaseSizeBeforeCreate + 1);
        CertificatImmatriculation testCertificatImmatriculation = certificatImmatriculationList.get(certificatImmatriculationList.size() - 1);
        assertThat(testCertificatImmatriculation.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCertificatImmatriculation.getCodeQr()).isEqualTo(DEFAULT_CODE_QR);
    }

    @Test
    @Transactional
    public void createCertificatImmatriculationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = certificatImmatriculationRepository.findAll().size();

        // Create the CertificatImmatriculation with an existing ID
        certificatImmatriculation.setId(1L);
        CertificatImmatriculationDTO certificatImmatriculationDTO = certificatImmatriculationMapper.toDto(certificatImmatriculation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificatImmatriculationMockMvc.perform(post("/api/certificat-immatriculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificatImmatriculationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CertificatImmatriculation in the database
        List<CertificatImmatriculation> certificatImmatriculationList = certificatImmatriculationRepository.findAll();
        assertThat(certificatImmatriculationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCertificatImmatriculations() throws Exception {
        // Initialize the database
        certificatImmatriculationRepository.saveAndFlush(certificatImmatriculation);

        // Get all the certificatImmatriculationList
        restCertificatImmatriculationMockMvc.perform(get("/api/certificat-immatriculations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificatImmatriculation.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].codeQr").value(hasItem(DEFAULT_CODE_QR)));
    }

    @Test
    @Transactional
    public void getCertificatImmatriculation() throws Exception {
        // Initialize the database
        certificatImmatriculationRepository.saveAndFlush(certificatImmatriculation);

        // Get the certificatImmatriculation
        restCertificatImmatriculationMockMvc.perform(get("/api/certificat-immatriculations/{id}", certificatImmatriculation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(certificatImmatriculation.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.codeQr").value(DEFAULT_CODE_QR));
    }

    @Test
    @Transactional
    public void getNonExistingCertificatImmatriculation() throws Exception {
        // Get the certificatImmatriculation
        restCertificatImmatriculationMockMvc.perform(get("/api/certificat-immatriculations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCertificatImmatriculation() throws Exception {
        // Initialize the database
        certificatImmatriculationRepository.saveAndFlush(certificatImmatriculation);

        int databaseSizeBeforeUpdate = certificatImmatriculationRepository.findAll().size();

        // Update the certificatImmatriculation
        CertificatImmatriculation updatedCertificatImmatriculation = certificatImmatriculationRepository.findById(certificatImmatriculation.getId()).get();
        // Disconnect from session so that the updates on updatedCertificatImmatriculation are not directly saved in db
        em.detach(updatedCertificatImmatriculation);
        updatedCertificatImmatriculation
            .numero(UPDATED_NUMERO)
            .codeQr(UPDATED_CODE_QR);
        CertificatImmatriculationDTO certificatImmatriculationDTO = certificatImmatriculationMapper.toDto(updatedCertificatImmatriculation);

        restCertificatImmatriculationMockMvc.perform(put("/api/certificat-immatriculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificatImmatriculationDTO)))
            .andExpect(status().isOk());

        // Validate the CertificatImmatriculation in the database
        List<CertificatImmatriculation> certificatImmatriculationList = certificatImmatriculationRepository.findAll();
        assertThat(certificatImmatriculationList).hasSize(databaseSizeBeforeUpdate);
        CertificatImmatriculation testCertificatImmatriculation = certificatImmatriculationList.get(certificatImmatriculationList.size() - 1);
        assertThat(testCertificatImmatriculation.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCertificatImmatriculation.getCodeQr()).isEqualTo(UPDATED_CODE_QR);
    }

    @Test
    @Transactional
    public void updateNonExistingCertificatImmatriculation() throws Exception {
        int databaseSizeBeforeUpdate = certificatImmatriculationRepository.findAll().size();

        // Create the CertificatImmatriculation
        CertificatImmatriculationDTO certificatImmatriculationDTO = certificatImmatriculationMapper.toDto(certificatImmatriculation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificatImmatriculationMockMvc.perform(put("/api/certificat-immatriculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificatImmatriculationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CertificatImmatriculation in the database
        List<CertificatImmatriculation> certificatImmatriculationList = certificatImmatriculationRepository.findAll();
        assertThat(certificatImmatriculationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCertificatImmatriculation() throws Exception {
        // Initialize the database
        certificatImmatriculationRepository.saveAndFlush(certificatImmatriculation);

        int databaseSizeBeforeDelete = certificatImmatriculationRepository.findAll().size();

        // Delete the certificatImmatriculation
        restCertificatImmatriculationMockMvc.perform(delete("/api/certificat-immatriculations/{id}", certificatImmatriculation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CertificatImmatriculation> certificatImmatriculationList = certificatImmatriculationRepository.findAll();
        assertThat(certificatImmatriculationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
