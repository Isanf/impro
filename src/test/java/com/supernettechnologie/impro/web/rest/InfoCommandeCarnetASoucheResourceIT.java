package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.InfoCommandeCarnetASouche;
import com.supernettechnologie.impro.repository.InfoCommandeCarnetASoucheRepository;
import com.supernettechnologie.impro.service.InfoCommandeCarnetASoucheService;
import com.supernettechnologie.impro.service.dto.InfoCommandeCarnetASoucheDTO;
import com.supernettechnologie.impro.service.mapper.InfoCommandeCarnetASoucheMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.supernettechnologie.impro.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InfoCommandeCarnetASoucheResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InfoCommandeCarnetASoucheResourceIT {

    private static final String DEFAULT_NUMERO_COMMANDE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_COMMANDE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_COMMANDE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_COMMANDE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_QUANTITE_COMMANDE = 1L;
    private static final Long UPDATED_QUANTITE_COMMANDE = 2L;

    private static final Boolean DEFAULT_EST_DELIVER = false;
    private static final Boolean UPDATED_EST_DELIVER = true;

    private static final Boolean DEFAULT_EST_TRANSITER = false;
    private static final Boolean UPDATED_EST_TRANSITER = true;

    @Autowired
    private InfoCommandeCarnetASoucheRepository infoCommandeCarnetASoucheRepository;

    @Autowired
    private InfoCommandeCarnetASoucheMapper infoCommandeCarnetASoucheMapper;

    @Autowired
    private InfoCommandeCarnetASoucheService infoCommandeCarnetASoucheService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInfoCommandeCarnetASoucheMockMvc;

    private InfoCommandeCarnetASouche infoCommandeCarnetASouche;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoCommandeCarnetASouche createEntity(EntityManager em) {
        InfoCommandeCarnetASouche infoCommandeCarnetASouche = new InfoCommandeCarnetASouche()
            .numeroCommande(DEFAULT_NUMERO_COMMANDE)
            .dateCommande(DEFAULT_DATE_COMMANDE)
            .quantiteCommande(DEFAULT_QUANTITE_COMMANDE)
            .estDeliver(DEFAULT_EST_DELIVER)
            .estTransiter(DEFAULT_EST_TRANSITER);
        return infoCommandeCarnetASouche;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoCommandeCarnetASouche createUpdatedEntity(EntityManager em) {
        InfoCommandeCarnetASouche infoCommandeCarnetASouche = new InfoCommandeCarnetASouche()
            .numeroCommande(UPDATED_NUMERO_COMMANDE)
            .dateCommande(UPDATED_DATE_COMMANDE)
            .quantiteCommande(UPDATED_QUANTITE_COMMANDE)
            .estDeliver(UPDATED_EST_DELIVER)
            .estTransiter(UPDATED_EST_TRANSITER);
        return infoCommandeCarnetASouche;
    }

    @BeforeEach
    public void initTest() {
        infoCommandeCarnetASouche = createEntity(em);
    }

    @Test
    @Transactional
    public void createInfoCommandeCarnetASouche() throws Exception {
        int databaseSizeBeforeCreate = infoCommandeCarnetASoucheRepository.findAll().size();
        // Create the InfoCommandeCarnetASouche
        InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO = infoCommandeCarnetASoucheMapper.toDto(infoCommandeCarnetASouche);
        restInfoCommandeCarnetASoucheMockMvc.perform(post("/api/info-commande-carnet-a-souches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoCommandeCarnetASoucheDTO)))
            .andExpect(status().isCreated());

        // Validate the InfoCommandeCarnetASouche in the database
        List<InfoCommandeCarnetASouche> infoCommandeCarnetASoucheList = infoCommandeCarnetASoucheRepository.findAll();
        assertThat(infoCommandeCarnetASoucheList).hasSize(databaseSizeBeforeCreate + 1);
        InfoCommandeCarnetASouche testInfoCommandeCarnetASouche = infoCommandeCarnetASoucheList.get(infoCommandeCarnetASoucheList.size() - 1);
        assertThat(testInfoCommandeCarnetASouche.getNumeroCommande()).isEqualTo(DEFAULT_NUMERO_COMMANDE);
        assertThat(testInfoCommandeCarnetASouche.getDateCommande()).isEqualTo(DEFAULT_DATE_COMMANDE);
        assertThat(testInfoCommandeCarnetASouche.getQuantiteCommande()).isEqualTo(DEFAULT_QUANTITE_COMMANDE);
        assertThat(testInfoCommandeCarnetASouche.isEstDeliver()).isEqualTo(DEFAULT_EST_DELIVER);
        assertThat(testInfoCommandeCarnetASouche.isEstTransiter()).isEqualTo(DEFAULT_EST_TRANSITER);
    }

    @Test
    @Transactional
    public void createInfoCommandeCarnetASoucheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infoCommandeCarnetASoucheRepository.findAll().size();

        // Create the InfoCommandeCarnetASouche with an existing ID
        infoCommandeCarnetASouche.setId(1L);
        InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO = infoCommandeCarnetASoucheMapper.toDto(infoCommandeCarnetASouche);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoCommandeCarnetASoucheMockMvc.perform(post("/api/info-commande-carnet-a-souches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoCommandeCarnetASoucheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InfoCommandeCarnetASouche in the database
        List<InfoCommandeCarnetASouche> infoCommandeCarnetASoucheList = infoCommandeCarnetASoucheRepository.findAll();
        assertThat(infoCommandeCarnetASoucheList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInfoCommandeCarnetASouches() throws Exception {
        // Initialize the database
        infoCommandeCarnetASoucheRepository.saveAndFlush(infoCommandeCarnetASouche);

        // Get all the infoCommandeCarnetASoucheList
        restInfoCommandeCarnetASoucheMockMvc.perform(get("/api/info-commande-carnet-a-souches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoCommandeCarnetASouche.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCommande").value(hasItem(DEFAULT_NUMERO_COMMANDE)))
            .andExpect(jsonPath("$.[*].dateCommande").value(hasItem(sameInstant(DEFAULT_DATE_COMMANDE))))
            .andExpect(jsonPath("$.[*].quantiteCommande").value(hasItem(DEFAULT_QUANTITE_COMMANDE.intValue())))
            .andExpect(jsonPath("$.[*].estDeliver").value(hasItem(DEFAULT_EST_DELIVER.booleanValue())))
            .andExpect(jsonPath("$.[*].estTransiter").value(hasItem(DEFAULT_EST_TRANSITER.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getInfoCommandeCarnetASouche() throws Exception {
        // Initialize the database
        infoCommandeCarnetASoucheRepository.saveAndFlush(infoCommandeCarnetASouche);

        // Get the infoCommandeCarnetASouche
        restInfoCommandeCarnetASoucheMockMvc.perform(get("/api/info-commande-carnet-a-souches/{id}", infoCommandeCarnetASouche.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(infoCommandeCarnetASouche.getId().intValue()))
            .andExpect(jsonPath("$.numeroCommande").value(DEFAULT_NUMERO_COMMANDE))
            .andExpect(jsonPath("$.dateCommande").value(sameInstant(DEFAULT_DATE_COMMANDE)))
            .andExpect(jsonPath("$.quantiteCommande").value(DEFAULT_QUANTITE_COMMANDE.intValue()))
            .andExpect(jsonPath("$.estDeliver").value(DEFAULT_EST_DELIVER.booleanValue()))
            .andExpect(jsonPath("$.estTransiter").value(DEFAULT_EST_TRANSITER.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingInfoCommandeCarnetASouche() throws Exception {
        // Get the infoCommandeCarnetASouche
        restInfoCommandeCarnetASoucheMockMvc.perform(get("/api/info-commande-carnet-a-souches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInfoCommandeCarnetASouche() throws Exception {
        // Initialize the database
        infoCommandeCarnetASoucheRepository.saveAndFlush(infoCommandeCarnetASouche);

        int databaseSizeBeforeUpdate = infoCommandeCarnetASoucheRepository.findAll().size();

        // Update the infoCommandeCarnetASouche
        InfoCommandeCarnetASouche updatedInfoCommandeCarnetASouche = infoCommandeCarnetASoucheRepository.findById(infoCommandeCarnetASouche.getId()).get();
        // Disconnect from session so that the updates on updatedInfoCommandeCarnetASouche are not directly saved in db
        em.detach(updatedInfoCommandeCarnetASouche);
        updatedInfoCommandeCarnetASouche
            .numeroCommande(UPDATED_NUMERO_COMMANDE)
            .dateCommande(UPDATED_DATE_COMMANDE)
            .quantiteCommande(UPDATED_QUANTITE_COMMANDE)
            .estDeliver(UPDATED_EST_DELIVER)
            .estTransiter(UPDATED_EST_TRANSITER);
        InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO = infoCommandeCarnetASoucheMapper.toDto(updatedInfoCommandeCarnetASouche);

        restInfoCommandeCarnetASoucheMockMvc.perform(put("/api/info-commande-carnet-a-souches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoCommandeCarnetASoucheDTO)))
            .andExpect(status().isOk());

        // Validate the InfoCommandeCarnetASouche in the database
        List<InfoCommandeCarnetASouche> infoCommandeCarnetASoucheList = infoCommandeCarnetASoucheRepository.findAll();
        assertThat(infoCommandeCarnetASoucheList).hasSize(databaseSizeBeforeUpdate);
        InfoCommandeCarnetASouche testInfoCommandeCarnetASouche = infoCommandeCarnetASoucheList.get(infoCommandeCarnetASoucheList.size() - 1);
        assertThat(testInfoCommandeCarnetASouche.getNumeroCommande()).isEqualTo(UPDATED_NUMERO_COMMANDE);
        assertThat(testInfoCommandeCarnetASouche.getDateCommande()).isEqualTo(UPDATED_DATE_COMMANDE);
        assertThat(testInfoCommandeCarnetASouche.getQuantiteCommande()).isEqualTo(UPDATED_QUANTITE_COMMANDE);
        assertThat(testInfoCommandeCarnetASouche.isEstDeliver()).isEqualTo(UPDATED_EST_DELIVER);
        assertThat(testInfoCommandeCarnetASouche.isEstTransiter()).isEqualTo(UPDATED_EST_TRANSITER);
    }

    @Test
    @Transactional
    public void updateNonExistingInfoCommandeCarnetASouche() throws Exception {
        int databaseSizeBeforeUpdate = infoCommandeCarnetASoucheRepository.findAll().size();

        // Create the InfoCommandeCarnetASouche
        InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO = infoCommandeCarnetASoucheMapper.toDto(infoCommandeCarnetASouche);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoCommandeCarnetASoucheMockMvc.perform(put("/api/info-commande-carnet-a-souches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infoCommandeCarnetASoucheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InfoCommandeCarnetASouche in the database
        List<InfoCommandeCarnetASouche> infoCommandeCarnetASoucheList = infoCommandeCarnetASoucheRepository.findAll();
        assertThat(infoCommandeCarnetASoucheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInfoCommandeCarnetASouche() throws Exception {
        // Initialize the database
        infoCommandeCarnetASoucheRepository.saveAndFlush(infoCommandeCarnetASouche);

        int databaseSizeBeforeDelete = infoCommandeCarnetASoucheRepository.findAll().size();

        // Delete the infoCommandeCarnetASouche
        restInfoCommandeCarnetASoucheMockMvc.perform(delete("/api/info-commande-carnet-a-souches/{id}", infoCommandeCarnetASouche.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InfoCommandeCarnetASouche> infoCommandeCarnetASoucheList = infoCommandeCarnetASoucheRepository.findAll();
        assertThat(infoCommandeCarnetASoucheList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
