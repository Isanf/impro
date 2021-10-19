package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.PersonneMorale;
import com.supernettechnologie.impro.repository.PersonneMoraleRepository;
import com.supernettechnologie.impro.service.PersonneMoraleService;
import com.supernettechnologie.impro.service.dto.PersonneMoraleDTO;
import com.supernettechnologie.impro.service.mapper.PersonneMoraleMapper;
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
 * Integration tests for the {@link PersonneMoraleResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class PersonneMoraleResourceIT {

    private static final String DEFAULT_NUMERO_IFU = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IFU = "BBBBBBBBBB";

    private static final String DEFAULT_DENOMINATION = "AAAAAAAAAA";
    private static final String UPDATED_DENOMINATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PersonneMoraleRepository personneMoraleRepository;

    @Autowired
    private PersonneMoraleMapper personneMoraleMapper;

    @Autowired
    private PersonneMoraleService personneMoraleService;

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

    private MockMvc restPersonneMoraleMockMvc;

    private PersonneMorale personneMorale;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonneMoraleResource personneMoraleResource = new PersonneMoraleResource(personneMoraleService);
        this.restPersonneMoraleMockMvc = MockMvcBuilders.standaloneSetup(personneMoraleResource)
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
    public static PersonneMorale createEntity(EntityManager em) {
        PersonneMorale personneMorale = new PersonneMorale()
            .numeroIFU(DEFAULT_NUMERO_IFU)
            .denomination(DEFAULT_DENOMINATION)
            .dateCreate(DEFAULT_DATE_CREATE);
        return personneMorale;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonneMorale createUpdatedEntity(EntityManager em) {
        PersonneMorale personneMorale = new PersonneMorale()
            .numeroIFU(UPDATED_NUMERO_IFU)
            .denomination(UPDATED_DENOMINATION)
            .dateCreate(UPDATED_DATE_CREATE);
        return personneMorale;
    }

    @BeforeEach
    public void initTest() {
        personneMorale = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonneMorale() throws Exception {
        int databaseSizeBeforeCreate = personneMoraleRepository.findAll().size();

        // Create the PersonneMorale
        PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper.toDto(personneMorale);
        restPersonneMoraleMockMvc.perform(post("/api/personne-morales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneMoraleDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonneMorale in the database
        List<PersonneMorale> personneMoraleList = personneMoraleRepository.findAll();
        assertThat(personneMoraleList).hasSize(databaseSizeBeforeCreate + 1);
        PersonneMorale testPersonneMorale = personneMoraleList.get(personneMoraleList.size() - 1);
        assertThat(testPersonneMorale.getNumeroIFU()).isEqualTo(DEFAULT_NUMERO_IFU);
        assertThat(testPersonneMorale.getDenomination()).isEqualTo(DEFAULT_DENOMINATION);
        assertThat(testPersonneMorale.getDateCreate()).isEqualTo(DEFAULT_DATE_CREATE);
    }

    @Test
    @Transactional
    public void createPersonneMoraleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personneMoraleRepository.findAll().size();

        // Create the PersonneMorale with an existing ID
        personneMorale.setId(1L);
        PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper.toDto(personneMorale);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonneMoraleMockMvc.perform(post("/api/personne-morales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneMoraleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonneMorale in the database
        List<PersonneMorale> personneMoraleList = personneMoraleRepository.findAll();
        assertThat(personneMoraleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPersonneMorales() throws Exception {
        // Initialize the database
        personneMoraleRepository.saveAndFlush(personneMorale);

        // Get all the personneMoraleList
        restPersonneMoraleMockMvc.perform(get("/api/personne-morales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personneMorale.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroIFU").value(hasItem(DEFAULT_NUMERO_IFU)))
            .andExpect(jsonPath("$.[*].denomination").value(hasItem(DEFAULT_DENOMINATION)))
            .andExpect(jsonPath("$.[*].dateCreate").value(hasItem(DEFAULT_DATE_CREATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonneMorale() throws Exception {
        // Initialize the database
        personneMoraleRepository.saveAndFlush(personneMorale);

        // Get the personneMorale
        restPersonneMoraleMockMvc.perform(get("/api/personne-morales/{id}", personneMorale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personneMorale.getId().intValue()))
            .andExpect(jsonPath("$.numeroIFU").value(DEFAULT_NUMERO_IFU))
            .andExpect(jsonPath("$.denomination").value(DEFAULT_DENOMINATION))
            .andExpect(jsonPath("$.dateCreate").value(DEFAULT_DATE_CREATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonneMorale() throws Exception {
        // Get the personneMorale
        restPersonneMoraleMockMvc.perform(get("/api/personne-morales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonneMorale() throws Exception {
        // Initialize the database
        personneMoraleRepository.saveAndFlush(personneMorale);

        int databaseSizeBeforeUpdate = personneMoraleRepository.findAll().size();

        // Update the personneMorale
        PersonneMorale updatedPersonneMorale = personneMoraleRepository.findById(personneMorale.getId()).get();
        // Disconnect from session so that the updates on updatedPersonneMorale are not directly saved in db
        em.detach(updatedPersonneMorale);
        updatedPersonneMorale
            .numeroIFU(UPDATED_NUMERO_IFU)
            .denomination(UPDATED_DENOMINATION)
            .dateCreate(UPDATED_DATE_CREATE);
        PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper.toDto(updatedPersonneMorale);

        restPersonneMoraleMockMvc.perform(put("/api/personne-morales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneMoraleDTO)))
            .andExpect(status().isOk());

        // Validate the PersonneMorale in the database
        List<PersonneMorale> personneMoraleList = personneMoraleRepository.findAll();
        assertThat(personneMoraleList).hasSize(databaseSizeBeforeUpdate);
        PersonneMorale testPersonneMorale = personneMoraleList.get(personneMoraleList.size() - 1);
        assertThat(testPersonneMorale.getNumeroIFU()).isEqualTo(UPDATED_NUMERO_IFU);
        assertThat(testPersonneMorale.getDenomination()).isEqualTo(UPDATED_DENOMINATION);
        assertThat(testPersonneMorale.getDateCreate()).isEqualTo(UPDATED_DATE_CREATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonneMorale() throws Exception {
        int databaseSizeBeforeUpdate = personneMoraleRepository.findAll().size();

        // Create the PersonneMorale
        PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper.toDto(personneMorale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonneMoraleMockMvc.perform(put("/api/personne-morales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneMoraleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonneMorale in the database
        List<PersonneMorale> personneMoraleList = personneMoraleRepository.findAll();
        assertThat(personneMoraleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonneMorale() throws Exception {
        // Initialize the database
        personneMoraleRepository.saveAndFlush(personneMorale);

        int databaseSizeBeforeDelete = personneMoraleRepository.findAll().size();

        // Delete the personneMorale
        restPersonneMoraleMockMvc.perform(delete("/api/personne-morales/{id}", personneMorale.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonneMorale> personneMoraleList = personneMoraleRepository.findAll();
        assertThat(personneMoraleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
