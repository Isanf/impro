package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.repository.PersonnePhysiqueRepository;
import com.supernettechnologie.impro.service.PersonnePhysiqueService;
import com.supernettechnologie.impro.service.dto.PersonnePhysiqueDTO;
import com.supernettechnologie.impro.service.mapper.PersonnePhysiqueMapper;
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
 * Integration tests for the {@link PersonnePhysiqueResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class PersonnePhysiqueResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENCE = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENCE = "BBBBBBBBBB";

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    @Autowired
    private PersonnePhysiqueMapper personnePhysiqueMapper;

    @Autowired
    private PersonnePhysiqueService personnePhysiqueService;

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

    private MockMvc restPersonnePhysiqueMockMvc;

    private PersonnePhysique personnePhysique;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonnePhysiqueResource personnePhysiqueResource = new PersonnePhysiqueResource(personnePhysiqueService);
        this.restPersonnePhysiqueMockMvc = MockMvcBuilders.standaloneSetup(personnePhysiqueResource)
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
    public static PersonnePhysique createEntity(EntityManager em) {
        PersonnePhysique personnePhysique = new PersonnePhysique()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lieuNaissance(DEFAULT_LIEU_NAISSANCE)
            .telephone(DEFAULT_TELEPHONE)
            .residence(DEFAULT_RESIDENCE);
        return personnePhysique;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonnePhysique createUpdatedEntity(EntityManager em) {
        PersonnePhysique personnePhysique = new PersonnePhysique()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .telephone(UPDATED_TELEPHONE)
            .residence(UPDATED_RESIDENCE);
        return personnePhysique;
    }

    @BeforeEach
    public void initTest() {
        personnePhysique = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonnePhysique() throws Exception {
        int databaseSizeBeforeCreate = personnePhysiqueRepository.findAll().size();

        // Create the PersonnePhysique
        PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper.toDto(personnePhysique);
        restPersonnePhysiqueMockMvc.perform(post("/api/personne-physiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnePhysiqueDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonnePhysique in the database
        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeCreate + 1);
        PersonnePhysique testPersonnePhysique = personnePhysiqueList.get(personnePhysiqueList.size() - 1);
        assertThat(testPersonnePhysique.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPersonnePhysique.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPersonnePhysique.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testPersonnePhysique.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testPersonnePhysique.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testPersonnePhysique.getResidence()).isEqualTo(DEFAULT_RESIDENCE);
    }

    @Test
    @Transactional
    public void createPersonnePhysiqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personnePhysiqueRepository.findAll().size();

        // Create the PersonnePhysique with an existing ID
        personnePhysique.setId(1L);
        PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper.toDto(personnePhysique);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonnePhysiqueMockMvc.perform(post("/api/personne-physiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnePhysiqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonnePhysique in the database
        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPersonnePhysiques() throws Exception {
        // Initialize the database
        personnePhysiqueRepository.saveAndFlush(personnePhysique);

        // Get all the personnePhysiqueList
        restPersonnePhysiqueMockMvc.perform(get("/api/personne-physiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personnePhysique.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].residence").value(hasItem(DEFAULT_RESIDENCE)));
    }

    @Test
    @Transactional
    public void getPersonnePhysique() throws Exception {
        // Initialize the database
        personnePhysiqueRepository.saveAndFlush(personnePhysique);

        // Get the personnePhysique
        restPersonnePhysiqueMockMvc.perform(get("/api/personne-physiques/{id}", personnePhysique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personnePhysique.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuNaissance").value(DEFAULT_LIEU_NAISSANCE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.residence").value(DEFAULT_RESIDENCE));
    }

    @Test
    @Transactional
    public void getNonExistingPersonnePhysique() throws Exception {
        // Get the personnePhysique
        restPersonnePhysiqueMockMvc.perform(get("/api/personne-physiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonnePhysique() throws Exception {
        // Initialize the database
        personnePhysiqueRepository.saveAndFlush(personnePhysique);

        int databaseSizeBeforeUpdate = personnePhysiqueRepository.findAll().size();

        // Update the personnePhysique
        PersonnePhysique updatedPersonnePhysique = personnePhysiqueRepository.findById(personnePhysique.getId()).get();
        // Disconnect from session so that the updates on updatedPersonnePhysique are not directly saved in db
        em.detach(updatedPersonnePhysique);
        updatedPersonnePhysique
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .telephone(UPDATED_TELEPHONE)
            .residence(UPDATED_RESIDENCE);
        PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper.toDto(updatedPersonnePhysique);

        restPersonnePhysiqueMockMvc.perform(put("/api/personne-physiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnePhysiqueDTO)))
            .andExpect(status().isOk());

        // Validate the PersonnePhysique in the database
        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeUpdate);
        PersonnePhysique testPersonnePhysique = personnePhysiqueList.get(personnePhysiqueList.size() - 1);
        assertThat(testPersonnePhysique.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonnePhysique.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPersonnePhysique.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testPersonnePhysique.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testPersonnePhysique.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testPersonnePhysique.getResidence()).isEqualTo(UPDATED_RESIDENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonnePhysique() throws Exception {
        int databaseSizeBeforeUpdate = personnePhysiqueRepository.findAll().size();

        // Create the PersonnePhysique
        PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper.toDto(personnePhysique);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonnePhysiqueMockMvc.perform(put("/api/personne-physiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnePhysiqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonnePhysique in the database
        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonnePhysique() throws Exception {
        // Initialize the database
        personnePhysiqueRepository.saveAndFlush(personnePhysique);

        int databaseSizeBeforeDelete = personnePhysiqueRepository.findAll().size();

        // Delete the personnePhysique
        restPersonnePhysiqueMockMvc.perform(delete("/api/personne-physiques/{id}", personnePhysique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
