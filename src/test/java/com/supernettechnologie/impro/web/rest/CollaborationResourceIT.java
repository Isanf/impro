package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.Collaboration;
import com.supernettechnologie.impro.repository.CollaborationRepository;
import com.supernettechnologie.impro.service.CollaborationService;
import com.supernettechnologie.impro.service.dto.CollaborationDTO;
import com.supernettechnologie.impro.service.mapper.CollaborationMapper;
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
 * Integration tests for the {@link CollaborationResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class CollaborationResourceIT {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO_COLLABORATION = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_COLLABORATION = "BBBBBBBBBB";

    @Autowired
    private CollaborationRepository collaborationRepository;

    @Autowired
    private CollaborationMapper collaborationMapper;

    @Autowired
    private CollaborationService collaborationService;

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

    private MockMvc restCollaborationMockMvc;

    private Collaboration collaboration;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CollaborationResource collaborationResource = new CollaborationResource(collaborationService);
        this.restCollaborationMockMvc = MockMvcBuilders.standaloneSetup(collaborationResource)
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
    public static Collaboration createEntity(EntityManager em) {
        Collaboration collaboration = new Collaboration()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .numeroCollaboration(DEFAULT_NUMERO_COLLABORATION);
        return collaboration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaboration createUpdatedEntity(EntityManager em) {
        Collaboration collaboration = new Collaboration()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .numeroCollaboration(UPDATED_NUMERO_COLLABORATION);
        return collaboration;
    }

    @BeforeEach
    public void initTest() {
        collaboration = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollaboration() throws Exception {
        int databaseSizeBeforeCreate = collaborationRepository.findAll().size();

        // Create the Collaboration
        CollaborationDTO collaborationDTO = collaborationMapper.toDto(collaboration);
        restCollaborationMockMvc.perform(post("/api/collaborations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborationDTO)))
            .andExpect(status().isCreated());

        // Validate the Collaboration in the database
        List<Collaboration> collaborationList = collaborationRepository.findAll();
        assertThat(collaborationList).hasSize(databaseSizeBeforeCreate + 1);
        Collaboration testCollaboration = collaborationList.get(collaborationList.size() - 1);
        assertThat(testCollaboration.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testCollaboration.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testCollaboration.getNumeroCollaboration()).isEqualTo(DEFAULT_NUMERO_COLLABORATION);
    }

    @Test
    @Transactional
    public void createCollaborationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collaborationRepository.findAll().size();

        // Create the Collaboration with an existing ID
        collaboration.setId(1L);
        CollaborationDTO collaborationDTO = collaborationMapper.toDto(collaboration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollaborationMockMvc.perform(post("/api/collaborations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Collaboration in the database
        List<Collaboration> collaborationList = collaborationRepository.findAll();
        assertThat(collaborationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCollaborations() throws Exception {
        // Initialize the database
        collaborationRepository.saveAndFlush(collaboration);

        // Get all the collaborationList
        restCollaborationMockMvc.perform(get("/api/collaborations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collaboration.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].numeroCollaboration").value(hasItem(DEFAULT_NUMERO_COLLABORATION)));
    }

    @Test
    @Transactional
    public void getCollaboration() throws Exception {
        // Initialize the database
        collaborationRepository.saveAndFlush(collaboration);

        // Get the collaboration
        restCollaborationMockMvc.perform(get("/api/collaborations/{id}", collaboration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collaboration.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.numeroCollaboration").value(DEFAULT_NUMERO_COLLABORATION));
    }

    @Test
    @Transactional
    public void getNonExistingCollaboration() throws Exception {
        // Get the collaboration
        restCollaborationMockMvc.perform(get("/api/collaborations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollaboration() throws Exception {
        // Initialize the database
        collaborationRepository.saveAndFlush(collaboration);

        int databaseSizeBeforeUpdate = collaborationRepository.findAll().size();

        // Update the collaboration
        Collaboration updatedCollaboration = collaborationRepository.findById(collaboration.getId()).get();
        // Disconnect from session so that the updates on updatedCollaboration are not directly saved in db
        em.detach(updatedCollaboration);
        updatedCollaboration
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .numeroCollaboration(UPDATED_NUMERO_COLLABORATION);
        CollaborationDTO collaborationDTO = collaborationMapper.toDto(updatedCollaboration);

        restCollaborationMockMvc.perform(put("/api/collaborations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborationDTO)))
            .andExpect(status().isOk());

        // Validate the Collaboration in the database
        List<Collaboration> collaborationList = collaborationRepository.findAll();
        assertThat(collaborationList).hasSize(databaseSizeBeforeUpdate);
        Collaboration testCollaboration = collaborationList.get(collaborationList.size() - 1);
        assertThat(testCollaboration.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testCollaboration.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testCollaboration.getNumeroCollaboration()).isEqualTo(UPDATED_NUMERO_COLLABORATION);
    }

    @Test
    @Transactional
    public void updateNonExistingCollaboration() throws Exception {
        int databaseSizeBeforeUpdate = collaborationRepository.findAll().size();

        // Create the Collaboration
        CollaborationDTO collaborationDTO = collaborationMapper.toDto(collaboration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollaborationMockMvc.perform(put("/api/collaborations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Collaboration in the database
        List<Collaboration> collaborationList = collaborationRepository.findAll();
        assertThat(collaborationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCollaboration() throws Exception {
        // Initialize the database
        collaborationRepository.saveAndFlush(collaboration);

        int databaseSizeBeforeDelete = collaborationRepository.findAll().size();

        // Delete the collaboration
        restCollaborationMockMvc.perform(delete("/api/collaborations/{id}", collaboration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Collaboration> collaborationList = collaborationRepository.findAll();
        assertThat(collaborationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
