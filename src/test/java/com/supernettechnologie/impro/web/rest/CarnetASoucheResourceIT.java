package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.CarnetASouche;
import com.supernettechnologie.impro.repository.CarnetASoucheRepository;
import com.supernettechnologie.impro.service.CarnetASoucheService;
import com.supernettechnologie.impro.service.dto.CarnetASoucheDTO;
import com.supernettechnologie.impro.service.mapper.CarnetASoucheMapper;

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
 * Integration tests for the {@link CarnetASoucheResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CarnetASoucheResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_IMPRESSION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_IMPRESSION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CarnetASoucheRepository carnetASoucheRepository;

    @Autowired
    private CarnetASoucheMapper carnetASoucheMapper;

    @Autowired
    private CarnetASoucheService carnetASoucheService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarnetASoucheMockMvc;

    private CarnetASouche carnetASouche;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarnetASouche createEntity(EntityManager em) {
        CarnetASouche carnetASouche = new CarnetASouche()
            .numero(DEFAULT_NUMERO)
            .dateImpression(DEFAULT_DATE_IMPRESSION);
        return carnetASouche;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarnetASouche createUpdatedEntity(EntityManager em) {
        CarnetASouche carnetASouche = new CarnetASouche()
            .numero(UPDATED_NUMERO)
            .dateImpression(UPDATED_DATE_IMPRESSION);
        return carnetASouche;
    }

    @BeforeEach
    public void initTest() {
        carnetASouche = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarnetASouche() throws Exception {
        int databaseSizeBeforeCreate = carnetASoucheRepository.findAll().size();
        // Create the CarnetASouche
        CarnetASoucheDTO carnetASoucheDTO = carnetASoucheMapper.toDto(carnetASouche);
        restCarnetASoucheMockMvc.perform(post("/api/carnet-a-souches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carnetASoucheDTO)))
            .andExpect(status().isCreated());

        // Validate the CarnetASouche in the database
        List<CarnetASouche> carnetASoucheList = carnetASoucheRepository.findAll();
        assertThat(carnetASoucheList).hasSize(databaseSizeBeforeCreate + 1);
        CarnetASouche testCarnetASouche = carnetASoucheList.get(carnetASoucheList.size() - 1);
        assertThat(testCarnetASouche.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCarnetASouche.getDateImpression()).isEqualTo(DEFAULT_DATE_IMPRESSION);
    }

    @Test
    @Transactional
    public void createCarnetASoucheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carnetASoucheRepository.findAll().size();

        // Create the CarnetASouche with an existing ID
        carnetASouche.setId(1L);
        CarnetASoucheDTO carnetASoucheDTO = carnetASoucheMapper.toDto(carnetASouche);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarnetASoucheMockMvc.perform(post("/api/carnet-a-souches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carnetASoucheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarnetASouche in the database
        List<CarnetASouche> carnetASoucheList = carnetASoucheRepository.findAll();
        assertThat(carnetASoucheList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCarnetASouches() throws Exception {
        // Initialize the database
        carnetASoucheRepository.saveAndFlush(carnetASouche);

        // Get all the carnetASoucheList
        restCarnetASoucheMockMvc.perform(get("/api/carnet-a-souches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carnetASouche.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].dateImpression").value(hasItem(sameInstant(DEFAULT_DATE_IMPRESSION))));
    }
    
    @Test
    @Transactional
    public void getCarnetASouche() throws Exception {
        // Initialize the database
        carnetASoucheRepository.saveAndFlush(carnetASouche);

        // Get the carnetASouche
        restCarnetASoucheMockMvc.perform(get("/api/carnet-a-souches/{id}", carnetASouche.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carnetASouche.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.dateImpression").value(sameInstant(DEFAULT_DATE_IMPRESSION)));
    }
    @Test
    @Transactional
    public void getNonExistingCarnetASouche() throws Exception {
        // Get the carnetASouche
        restCarnetASoucheMockMvc.perform(get("/api/carnet-a-souches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarnetASouche() throws Exception {
        // Initialize the database
        carnetASoucheRepository.saveAndFlush(carnetASouche);

        int databaseSizeBeforeUpdate = carnetASoucheRepository.findAll().size();

        // Update the carnetASouche
        CarnetASouche updatedCarnetASouche = carnetASoucheRepository.findById(carnetASouche.getId()).get();
        // Disconnect from session so that the updates on updatedCarnetASouche are not directly saved in db
        em.detach(updatedCarnetASouche);
        updatedCarnetASouche
            .numero(UPDATED_NUMERO)
            .dateImpression(UPDATED_DATE_IMPRESSION);
        CarnetASoucheDTO carnetASoucheDTO = carnetASoucheMapper.toDto(updatedCarnetASouche);

        restCarnetASoucheMockMvc.perform(put("/api/carnet-a-souches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carnetASoucheDTO)))
            .andExpect(status().isOk());

        // Validate the CarnetASouche in the database
        List<CarnetASouche> carnetASoucheList = carnetASoucheRepository.findAll();
        assertThat(carnetASoucheList).hasSize(databaseSizeBeforeUpdate);
        CarnetASouche testCarnetASouche = carnetASoucheList.get(carnetASoucheList.size() - 1);
        assertThat(testCarnetASouche.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCarnetASouche.getDateImpression()).isEqualTo(UPDATED_DATE_IMPRESSION);
    }

    @Test
    @Transactional
    public void updateNonExistingCarnetASouche() throws Exception {
        int databaseSizeBeforeUpdate = carnetASoucheRepository.findAll().size();

        // Create the CarnetASouche
        CarnetASoucheDTO carnetASoucheDTO = carnetASoucheMapper.toDto(carnetASouche);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarnetASoucheMockMvc.perform(put("/api/carnet-a-souches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carnetASoucheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarnetASouche in the database
        List<CarnetASouche> carnetASoucheList = carnetASoucheRepository.findAll();
        assertThat(carnetASoucheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarnetASouche() throws Exception {
        // Initialize the database
        carnetASoucheRepository.saveAndFlush(carnetASouche);

        int databaseSizeBeforeDelete = carnetASoucheRepository.findAll().size();

        // Delete the carnetASouche
        restCarnetASoucheMockMvc.perform(delete("/api/carnet-a-souches/{id}", carnetASouche.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarnetASouche> carnetASoucheList = carnetASoucheRepository.findAll();
        assertThat(carnetASoucheList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
