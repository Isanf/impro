package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.LivraisonCarnetSouche;
import com.supernettechnologie.impro.repository.LivraisonCarnetSoucheRepository;
import com.supernettechnologie.impro.service.LivraisonCarnetSoucheService;
import com.supernettechnologie.impro.service.dto.LivraisonCarnetSoucheDTO;
import com.supernettechnologie.impro.service.mapper.LivraisonCarnetSoucheMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.supernettechnologie.impro.web.rest.TestUtil.sameInstant;
import static com.supernettechnologie.impro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LivraisonCarnetSoucheResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class LivraisonCarnetSoucheResourceIT {

    private static final String DEFAULT_NUMERO_LIVRAISON_CS = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_LIVRAISON_CS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_LIVRAISON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_LIVRAISON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LivraisonCarnetSoucheRepository livraisonCarnetSoucheRepository;

    @Autowired
    private LivraisonCarnetSoucheMapper livraisonCarnetSoucheMapper;

    @Autowired
    private LivraisonCarnetSoucheService livraisonCarnetSoucheService;

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

    private MockMvc restLivraisonCarnetSoucheMockMvc;

    private LivraisonCarnetSouche livraisonCarnetSouche;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LivraisonCarnetSoucheResource livraisonCarnetSoucheResource = new LivraisonCarnetSoucheResource(livraisonCarnetSoucheService);
        this.restLivraisonCarnetSoucheMockMvc = MockMvcBuilders.standaloneSetup(livraisonCarnetSoucheResource)
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
    public static LivraisonCarnetSouche createEntity(EntityManager em) {
        LivraisonCarnetSouche livraisonCarnetSouche = new LivraisonCarnetSouche()
            .numeroLivraisonCS(DEFAULT_NUMERO_LIVRAISON_CS)
            .dateLivraison(DEFAULT_DATE_LIVRAISON);
        return livraisonCarnetSouche;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LivraisonCarnetSouche createUpdatedEntity(EntityManager em) {
        LivraisonCarnetSouche livraisonCarnetSouche = new LivraisonCarnetSouche()
            .numeroLivraisonCS(UPDATED_NUMERO_LIVRAISON_CS)
            .dateLivraison(UPDATED_DATE_LIVRAISON);
        return livraisonCarnetSouche;
    }

    @BeforeEach
    public void initTest() {
        livraisonCarnetSouche = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivraisonCarnetSouche() throws Exception {
        int databaseSizeBeforeCreate = livraisonCarnetSoucheRepository.findAll().size();

        // Create the LivraisonCarnetSouche
        LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO = livraisonCarnetSoucheMapper.toDto(livraisonCarnetSouche);
        restLivraisonCarnetSoucheMockMvc.perform(post("/api/livraison-carnet-souches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraisonCarnetSoucheDTO)))
            .andExpect(status().isCreated());

        // Validate the LivraisonCarnetSouche in the database
        List<LivraisonCarnetSouche> livraisonCarnetSoucheList = livraisonCarnetSoucheRepository.findAll();
        assertThat(livraisonCarnetSoucheList).hasSize(databaseSizeBeforeCreate + 1);
        LivraisonCarnetSouche testLivraisonCarnetSouche = livraisonCarnetSoucheList.get(livraisonCarnetSoucheList.size() - 1);
        assertThat(testLivraisonCarnetSouche.getNumeroLivraisonCS()).isEqualTo(DEFAULT_NUMERO_LIVRAISON_CS);
        assertThat(testLivraisonCarnetSouche.getDateLivraison()).isEqualTo(DEFAULT_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void createLivraisonCarnetSoucheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livraisonCarnetSoucheRepository.findAll().size();

        // Create the LivraisonCarnetSouche with an existing ID
        livraisonCarnetSouche.setId(1L);
        LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO = livraisonCarnetSoucheMapper.toDto(livraisonCarnetSouche);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivraisonCarnetSoucheMockMvc.perform(post("/api/livraison-carnet-souches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraisonCarnetSoucheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LivraisonCarnetSouche in the database
        List<LivraisonCarnetSouche> livraisonCarnetSoucheList = livraisonCarnetSoucheRepository.findAll();
        assertThat(livraisonCarnetSoucheList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLivraisonCarnetSouches() throws Exception {
        // Initialize the database
        livraisonCarnetSoucheRepository.saveAndFlush(livraisonCarnetSouche);

        // Get all the livraisonCarnetSoucheList
        restLivraisonCarnetSoucheMockMvc.perform(get("/api/livraison-carnet-souches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livraisonCarnetSouche.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroLivraisonCS").value(hasItem(DEFAULT_NUMERO_LIVRAISON_CS)))
            .andExpect(jsonPath("$.[*].dateLivraison").value(hasItem(sameInstant(DEFAULT_DATE_LIVRAISON))));
    }

    @Test
    @Transactional
    public void getLivraisonCarnetSouche() throws Exception {
        // Initialize the database
        livraisonCarnetSoucheRepository.saveAndFlush(livraisonCarnetSouche);

        // Get the livraisonCarnetSouche
        restLivraisonCarnetSoucheMockMvc.perform(get("/api/livraison-carnet-souches/{id}", livraisonCarnetSouche.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livraisonCarnetSouche.getId().intValue()))
            .andExpect(jsonPath("$.numeroLivraisonCS").value(DEFAULT_NUMERO_LIVRAISON_CS))
            .andExpect(jsonPath("$.dateLivraison").value(sameInstant(DEFAULT_DATE_LIVRAISON)));
    }

    @Test
    @Transactional
    public void getNonExistingLivraisonCarnetSouche() throws Exception {
        // Get the livraisonCarnetSouche
        restLivraisonCarnetSoucheMockMvc.perform(get("/api/livraison-carnet-souches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivraisonCarnetSouche() throws Exception {
        // Initialize the database
        livraisonCarnetSoucheRepository.saveAndFlush(livraisonCarnetSouche);

        int databaseSizeBeforeUpdate = livraisonCarnetSoucheRepository.findAll().size();

        // Update the livraisonCarnetSouche
        LivraisonCarnetSouche updatedLivraisonCarnetSouche = livraisonCarnetSoucheRepository.findById(livraisonCarnetSouche.getId()).get();
        // Disconnect from session so that the updates on updatedLivraisonCarnetSouche are not directly saved in db
        em.detach(updatedLivraisonCarnetSouche);
        updatedLivraisonCarnetSouche
            .numeroLivraisonCS(UPDATED_NUMERO_LIVRAISON_CS)
            .dateLivraison(UPDATED_DATE_LIVRAISON);
        LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO = livraisonCarnetSoucheMapper.toDto(updatedLivraisonCarnetSouche);

        restLivraisonCarnetSoucheMockMvc.perform(put("/api/livraison-carnet-souches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraisonCarnetSoucheDTO)))
            .andExpect(status().isOk());

        // Validate the LivraisonCarnetSouche in the database
        List<LivraisonCarnetSouche> livraisonCarnetSoucheList = livraisonCarnetSoucheRepository.findAll();
        assertThat(livraisonCarnetSoucheList).hasSize(databaseSizeBeforeUpdate);
        LivraisonCarnetSouche testLivraisonCarnetSouche = livraisonCarnetSoucheList.get(livraisonCarnetSoucheList.size() - 1);
        assertThat(testLivraisonCarnetSouche.getNumeroLivraisonCS()).isEqualTo(UPDATED_NUMERO_LIVRAISON_CS);
        assertThat(testLivraisonCarnetSouche.getDateLivraison()).isEqualTo(UPDATED_DATE_LIVRAISON);
    }

    @Test
    @Transactional
    public void updateNonExistingLivraisonCarnetSouche() throws Exception {
        int databaseSizeBeforeUpdate = livraisonCarnetSoucheRepository.findAll().size();

        // Create the LivraisonCarnetSouche
        LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO = livraisonCarnetSoucheMapper.toDto(livraisonCarnetSouche);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivraisonCarnetSoucheMockMvc.perform(put("/api/livraison-carnet-souches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraisonCarnetSoucheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LivraisonCarnetSouche in the database
        List<LivraisonCarnetSouche> livraisonCarnetSoucheList = livraisonCarnetSoucheRepository.findAll();
        assertThat(livraisonCarnetSoucheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLivraisonCarnetSouche() throws Exception {
        // Initialize the database
        livraisonCarnetSoucheRepository.saveAndFlush(livraisonCarnetSouche);

        int databaseSizeBeforeDelete = livraisonCarnetSoucheRepository.findAll().size();

        // Delete the livraisonCarnetSouche
        restLivraisonCarnetSoucheMockMvc.perform(delete("/api/livraison-carnet-souches/{id}", livraisonCarnetSouche.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LivraisonCarnetSouche> livraisonCarnetSoucheList = livraisonCarnetSoucheRepository.findAll();
        assertThat(livraisonCarnetSoucheList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
