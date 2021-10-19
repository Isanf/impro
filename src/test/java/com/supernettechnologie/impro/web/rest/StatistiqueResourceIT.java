package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.Statistique;
import com.supernettechnologie.impro.repository.StatistiqueRepository;
import com.supernettechnologie.impro.service.StatistiqueService;
import com.supernettechnologie.impro.service.dto.StatistiqueDTO;
import com.supernettechnologie.impro.service.mapper.StatistiqueMapper;
import com.supernettechnologie.impro.service.dto.StatistiqueCriteria;
import com.supernettechnologie.impro.service.StatistiqueQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StatistiqueResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StatistiqueResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private StatistiqueRepository statistiqueRepository;

    @Autowired
    private StatistiqueMapper statistiqueMapper;

    @Autowired
    private StatistiqueService statistiqueService;

    @Autowired
    private StatistiqueQueryService statistiqueQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatistiqueMockMvc;

    private Statistique statistique;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statistique createEntity(EntityManager em) {
        Statistique statistique = new Statistique()
            .nom(DEFAULT_NOM);
        return statistique;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statistique createUpdatedEntity(EntityManager em) {
        Statistique statistique = new Statistique()
            .nom(UPDATED_NOM);
        return statistique;
    }

    @BeforeEach
    public void initTest() {
        statistique = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatistique() throws Exception {
        int databaseSizeBeforeCreate = statistiqueRepository.findAll().size();
        // Create the Statistique
        StatistiqueDTO statistiqueDTO = statistiqueMapper.toDto(statistique);
        restStatistiqueMockMvc.perform(post("/api/statistiques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statistiqueDTO)))
            .andExpect(status().isCreated());

        // Validate the Statistique in the database
        List<Statistique> statistiqueList = statistiqueRepository.findAll();
        assertThat(statistiqueList).hasSize(databaseSizeBeforeCreate + 1);
        Statistique testStatistique = statistiqueList.get(statistiqueList.size() - 1);
        assertThat(testStatistique.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createStatistiqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statistiqueRepository.findAll().size();

        // Create the Statistique with an existing ID
        statistique.setId(1L);
        StatistiqueDTO statistiqueDTO = statistiqueMapper.toDto(statistique);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatistiqueMockMvc.perform(post("/api/statistiques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statistiqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Statistique in the database
        List<Statistique> statistiqueList = statistiqueRepository.findAll();
        assertThat(statistiqueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStatistiques() throws Exception {
        // Initialize the database
        statistiqueRepository.saveAndFlush(statistique);

        // Get all the statistiqueList
        restStatistiqueMockMvc.perform(get("/api/statistiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statistique.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
    
    @Test
    @Transactional
    public void getStatistique() throws Exception {
        // Initialize the database
        statistiqueRepository.saveAndFlush(statistique);

        // Get the statistique
        restStatistiqueMockMvc.perform(get("/api/statistiques/{id}", statistique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statistique.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }


    @Test
    @Transactional
    public void getStatistiquesByIdFiltering() throws Exception {
        // Initialize the database
        statistiqueRepository.saveAndFlush(statistique);

        Long id = statistique.getId();

        defaultStatistiqueShouldBeFound("id.equals=" + id);
        defaultStatistiqueShouldNotBeFound("id.notEquals=" + id);

        defaultStatistiqueShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStatistiqueShouldNotBeFound("id.greaterThan=" + id);

        defaultStatistiqueShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStatistiqueShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStatistiquesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        statistiqueRepository.saveAndFlush(statistique);

        // Get all the statistiqueList where nom equals to DEFAULT_NOM
        defaultStatistiqueShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the statistiqueList where nom equals to UPDATED_NOM
        defaultStatistiqueShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllStatistiquesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statistiqueRepository.saveAndFlush(statistique);

        // Get all the statistiqueList where nom not equals to DEFAULT_NOM
        defaultStatistiqueShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the statistiqueList where nom not equals to UPDATED_NOM
        defaultStatistiqueShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllStatistiquesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        statistiqueRepository.saveAndFlush(statistique);

        // Get all the statistiqueList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultStatistiqueShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the statistiqueList where nom equals to UPDATED_NOM
        defaultStatistiqueShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllStatistiquesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        statistiqueRepository.saveAndFlush(statistique);

        // Get all the statistiqueList where nom is not null
        defaultStatistiqueShouldBeFound("nom.specified=true");

        // Get all the statistiqueList where nom is null
        defaultStatistiqueShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllStatistiquesByNomContainsSomething() throws Exception {
        // Initialize the database
        statistiqueRepository.saveAndFlush(statistique);

        // Get all the statistiqueList where nom contains DEFAULT_NOM
        defaultStatistiqueShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the statistiqueList where nom contains UPDATED_NOM
        defaultStatistiqueShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllStatistiquesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        statistiqueRepository.saveAndFlush(statistique);

        // Get all the statistiqueList where nom does not contain DEFAULT_NOM
        defaultStatistiqueShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the statistiqueList where nom does not contain UPDATED_NOM
        defaultStatistiqueShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatistiqueShouldBeFound(String filter) throws Exception {
        restStatistiqueMockMvc.perform(get("/api/statistiques?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statistique.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));

        // Check, that the count call also returns 1
        restStatistiqueMockMvc.perform(get("/api/statistiques/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatistiqueShouldNotBeFound(String filter) throws Exception {
        restStatistiqueMockMvc.perform(get("/api/statistiques?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatistiqueMockMvc.perform(get("/api/statistiques/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStatistique() throws Exception {
        // Get the statistique
        restStatistiqueMockMvc.perform(get("/api/statistiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatistique() throws Exception {
        // Initialize the database
        statistiqueRepository.saveAndFlush(statistique);

        int databaseSizeBeforeUpdate = statistiqueRepository.findAll().size();

        // Update the statistique
        Statistique updatedStatistique = statistiqueRepository.findById(statistique.getId()).get();
        // Disconnect from session so that the updates on updatedStatistique are not directly saved in db
        em.detach(updatedStatistique);
        updatedStatistique
            .nom(UPDATED_NOM);
        StatistiqueDTO statistiqueDTO = statistiqueMapper.toDto(updatedStatistique);

        restStatistiqueMockMvc.perform(put("/api/statistiques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statistiqueDTO)))
            .andExpect(status().isOk());

        // Validate the Statistique in the database
        List<Statistique> statistiqueList = statistiqueRepository.findAll();
        assertThat(statistiqueList).hasSize(databaseSizeBeforeUpdate);
        Statistique testStatistique = statistiqueList.get(statistiqueList.size() - 1);
        assertThat(testStatistique.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingStatistique() throws Exception {
        int databaseSizeBeforeUpdate = statistiqueRepository.findAll().size();

        // Create the Statistique
        StatistiqueDTO statistiqueDTO = statistiqueMapper.toDto(statistique);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatistiqueMockMvc.perform(put("/api/statistiques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statistiqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Statistique in the database
        List<Statistique> statistiqueList = statistiqueRepository.findAll();
        assertThat(statistiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatistique() throws Exception {
        // Initialize the database
        statistiqueRepository.saveAndFlush(statistique);

        int databaseSizeBeforeDelete = statistiqueRepository.findAll().size();

        // Delete the statistique
        restStatistiqueMockMvc.perform(delete("/api/statistiques/{id}", statistique.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Statistique> statistiqueList = statistiqueRepository.findAll();
        assertThat(statistiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
