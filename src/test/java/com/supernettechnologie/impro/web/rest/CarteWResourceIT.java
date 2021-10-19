package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.CarteW;
import com.supernettechnologie.impro.repository.CarteWRepository;
import com.supernettechnologie.impro.service.CarteWService;
import com.supernettechnologie.impro.service.dto.CarteWDTO;
import com.supernettechnologie.impro.service.mapper.CarteWMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CarteWResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CarteWResourceIT {

    private static final String DEFAULT_NUMERO_CARTE_W = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CARTE_W = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ETABLISSEMENT_CARTE_W = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ETABLISSEMENT_CARTE_W = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_EXPIRATION_CARTE_W = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXPIRATION_CARTE_W = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_ETABLISSEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_QR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_QR = "BBBBBBBBBB";

    @Autowired
    private CarteWRepository carteWRepository;

    @Autowired
    private CarteWMapper carteWMapper;

    @Autowired
    private CarteWService carteWService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarteWMockMvc;

    private CarteW carteW;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarteW createEntity(EntityManager em) {
        CarteW carteW = new CarteW()
            .numeroCarteW(DEFAULT_NUMERO_CARTE_W)
            .dateEtablissementCarteW(DEFAULT_DATE_ETABLISSEMENT_CARTE_W)
            .dateExpirationCarteW(DEFAULT_DATE_EXPIRATION_CARTE_W)
            .lieuEtablissement(DEFAULT_LIEU_ETABLISSEMENT)
            .codeQr(DEFAULT_CODE_QR);
        return carteW;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarteW createUpdatedEntity(EntityManager em) {
        CarteW carteW = new CarteW()
            .numeroCarteW(UPDATED_NUMERO_CARTE_W)
            .dateEtablissementCarteW(UPDATED_DATE_ETABLISSEMENT_CARTE_W)
            .dateExpirationCarteW(UPDATED_DATE_EXPIRATION_CARTE_W)
            .lieuEtablissement(UPDATED_LIEU_ETABLISSEMENT)
            .codeQr(UPDATED_CODE_QR);
        return carteW;
    }

    @BeforeEach
    public void initTest() {
        carteW = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarteW() throws Exception {
        int databaseSizeBeforeCreate = carteWRepository.findAll().size();
        // Create the CarteW
        CarteWDTO carteWDTO = carteWMapper.toDto(carteW);
        restCarteWMockMvc.perform(post("/api/carte-ws")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carteWDTO)))
            .andExpect(status().isCreated());

        // Validate the CarteW in the database
        List<CarteW> carteWList = carteWRepository.findAll();
        assertThat(carteWList).hasSize(databaseSizeBeforeCreate + 1);
        CarteW testCarteW = carteWList.get(carteWList.size() - 1);
        assertThat(testCarteW.getNumeroCarteW()).isEqualTo(DEFAULT_NUMERO_CARTE_W);
        assertThat(testCarteW.getDateEtablissementCarteW()).isEqualTo(DEFAULT_DATE_ETABLISSEMENT_CARTE_W);
        assertThat(testCarteW.getDateExpirationCarteW()).isEqualTo(DEFAULT_DATE_EXPIRATION_CARTE_W);
        assertThat(testCarteW.getLieuEtablissement()).isEqualTo(DEFAULT_LIEU_ETABLISSEMENT);
        assertThat(testCarteW.getCodeQr()).isEqualTo(DEFAULT_CODE_QR);
    }

    @Test
    @Transactional
    public void createCarteWWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carteWRepository.findAll().size();

        // Create the CarteW with an existing ID
        carteW.setId(1L);
        CarteWDTO carteWDTO = carteWMapper.toDto(carteW);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarteWMockMvc.perform(post("/api/carte-ws")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carteWDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarteW in the database
        List<CarteW> carteWList = carteWRepository.findAll();
        assertThat(carteWList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCarteWS() throws Exception {
        // Initialize the database
        carteWRepository.saveAndFlush(carteW);

        // Get all the carteWList
        restCarteWMockMvc.perform(get("/api/carte-ws?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carteW.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCarteW").value(hasItem(DEFAULT_NUMERO_CARTE_W)))
            .andExpect(jsonPath("$.[*].dateEtablissementCarteW").value(hasItem(DEFAULT_DATE_ETABLISSEMENT_CARTE_W.toString())))
            .andExpect(jsonPath("$.[*].dateExpirationCarteW").value(hasItem(DEFAULT_DATE_EXPIRATION_CARTE_W.toString())))
            .andExpect(jsonPath("$.[*].lieuEtablissement").value(hasItem(DEFAULT_LIEU_ETABLISSEMENT)))
            .andExpect(jsonPath("$.[*].codeQr").value(hasItem(DEFAULT_CODE_QR)));
    }
    
    @Test
    @Transactional
    public void getCarteW() throws Exception {
        // Initialize the database
        carteWRepository.saveAndFlush(carteW);

        // Get the carteW
        restCarteWMockMvc.perform(get("/api/carte-ws/{id}", carteW.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carteW.getId().intValue()))
            .andExpect(jsonPath("$.numeroCarteW").value(DEFAULT_NUMERO_CARTE_W))
            .andExpect(jsonPath("$.dateEtablissementCarteW").value(DEFAULT_DATE_ETABLISSEMENT_CARTE_W.toString()))
            .andExpect(jsonPath("$.dateExpirationCarteW").value(DEFAULT_DATE_EXPIRATION_CARTE_W.toString()))
            .andExpect(jsonPath("$.lieuEtablissement").value(DEFAULT_LIEU_ETABLISSEMENT))
            .andExpect(jsonPath("$.codeQr").value(DEFAULT_CODE_QR));
    }
    @Test
    @Transactional
    public void getNonExistingCarteW() throws Exception {
        // Get the carteW
        restCarteWMockMvc.perform(get("/api/carte-ws/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarteW() throws Exception {
        // Initialize the database
        carteWRepository.saveAndFlush(carteW);

        int databaseSizeBeforeUpdate = carteWRepository.findAll().size();

        // Update the carteW
        CarteW updatedCarteW = carteWRepository.findById(carteW.getId()).get();
        // Disconnect from session so that the updates on updatedCarteW are not directly saved in db
        em.detach(updatedCarteW);
        updatedCarteW
            .numeroCarteW(UPDATED_NUMERO_CARTE_W)
            .dateEtablissementCarteW(UPDATED_DATE_ETABLISSEMENT_CARTE_W)
            .dateExpirationCarteW(UPDATED_DATE_EXPIRATION_CARTE_W)
            .lieuEtablissement(UPDATED_LIEU_ETABLISSEMENT)
            .codeQr(UPDATED_CODE_QR);
        CarteWDTO carteWDTO = carteWMapper.toDto(updatedCarteW);

        restCarteWMockMvc.perform(put("/api/carte-ws")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carteWDTO)))
            .andExpect(status().isOk());

        // Validate the CarteW in the database
        List<CarteW> carteWList = carteWRepository.findAll();
        assertThat(carteWList).hasSize(databaseSizeBeforeUpdate);
        CarteW testCarteW = carteWList.get(carteWList.size() - 1);
        assertThat(testCarteW.getNumeroCarteW()).isEqualTo(UPDATED_NUMERO_CARTE_W);
        assertThat(testCarteW.getDateEtablissementCarteW()).isEqualTo(UPDATED_DATE_ETABLISSEMENT_CARTE_W);
        assertThat(testCarteW.getDateExpirationCarteW()).isEqualTo(UPDATED_DATE_EXPIRATION_CARTE_W);
        assertThat(testCarteW.getLieuEtablissement()).isEqualTo(UPDATED_LIEU_ETABLISSEMENT);
        assertThat(testCarteW.getCodeQr()).isEqualTo(UPDATED_CODE_QR);
    }

    @Test
    @Transactional
    public void updateNonExistingCarteW() throws Exception {
        int databaseSizeBeforeUpdate = carteWRepository.findAll().size();

        // Create the CarteW
        CarteWDTO carteWDTO = carteWMapper.toDto(carteW);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarteWMockMvc.perform(put("/api/carte-ws")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carteWDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarteW in the database
        List<CarteW> carteWList = carteWRepository.findAll();
        assertThat(carteWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarteW() throws Exception {
        // Initialize the database
        carteWRepository.saveAndFlush(carteW);

        int databaseSizeBeforeDelete = carteWRepository.findAll().size();

        // Delete the carteW
        restCarteWMockMvc.perform(delete("/api/carte-ws/{id}", carteW.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarteW> carteWList = carteWRepository.findAll();
        assertThat(carteWList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
