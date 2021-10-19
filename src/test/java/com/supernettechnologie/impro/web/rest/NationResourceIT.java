package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.Nation;
import com.supernettechnologie.impro.repository.NationRepository;
import com.supernettechnologie.impro.service.NationService;
import com.supernettechnologie.impro.service.dto.NationDTO;
import com.supernettechnologie.impro.service.mapper.NationMapper;

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
 * Integration tests for the {@link NationResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NationResourceIT {

    private static final String DEFAULT_ISO = "AAAAAAAAAA";
    private static final String UPDATED_ISO = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NICENAME = "AAAAAAAAAA";
    private static final String UPDATED_NICENAME = "BBBBBBBBBB";

    private static final String DEFAULT_ISO_3 = "AAAAAAAAAA";
    private static final String UPDATED_ISO_3 = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMCODE = 1;
    private static final Integer UPDATED_NUMCODE = 2;

    private static final Integer DEFAULT_PHONECODE = 1;
    private static final Integer UPDATED_PHONECODE = 2;

    @Autowired
    private NationRepository nationRepository;

    @Autowired
    private NationMapper nationMapper;

    @Autowired
    private NationService nationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNationMockMvc;

    private Nation nation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nation createEntity(EntityManager em) {
        Nation nation = new Nation()
            .iso(DEFAULT_ISO)
            .name(DEFAULT_NAME)
            .nicename(DEFAULT_NICENAME)
            .iso3(DEFAULT_ISO_3)
            .numcode(DEFAULT_NUMCODE)
            .phonecode(DEFAULT_PHONECODE);
        return nation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nation createUpdatedEntity(EntityManager em) {
        Nation nation = new Nation()
            .iso(UPDATED_ISO)
            .name(UPDATED_NAME)
            .nicename(UPDATED_NICENAME)
            .iso3(UPDATED_ISO_3)
            .numcode(UPDATED_NUMCODE)
            .phonecode(UPDATED_PHONECODE);
        return nation;
    }

    @BeforeEach
    public void initTest() {
        nation = createEntity(em);
    }

    @Test
    @Transactional
    public void createNation() throws Exception {
        int databaseSizeBeforeCreate = nationRepository.findAll().size();
        // Create the Nation
        NationDTO nationDTO = nationMapper.toDto(nation);
        restNationMockMvc.perform(post("/api/nations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nationDTO)))
            .andExpect(status().isCreated());

        // Validate the Nation in the database
        List<Nation> nationList = nationRepository.findAll();
        assertThat(nationList).hasSize(databaseSizeBeforeCreate + 1);
        Nation testNation = nationList.get(nationList.size() - 1);
        assertThat(testNation.getIso()).isEqualTo(DEFAULT_ISO);
        assertThat(testNation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNation.getNicename()).isEqualTo(DEFAULT_NICENAME);
        assertThat(testNation.getIso3()).isEqualTo(DEFAULT_ISO_3);
        assertThat(testNation.getNumcode()).isEqualTo(DEFAULT_NUMCODE);
        assertThat(testNation.getPhonecode()).isEqualTo(DEFAULT_PHONECODE);
    }

    @Test
    @Transactional
    public void createNationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nationRepository.findAll().size();

        // Create the Nation with an existing ID
        nation.setId(1L);
        NationDTO nationDTO = nationMapper.toDto(nation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNationMockMvc.perform(post("/api/nations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nation in the database
        List<Nation> nationList = nationRepository.findAll();
        assertThat(nationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNations() throws Exception {
        // Initialize the database
        nationRepository.saveAndFlush(nation);

        // Get all the nationList
        restNationMockMvc.perform(get("/api/nations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nation.getId().intValue())))
            .andExpect(jsonPath("$.[*].iso").value(hasItem(DEFAULT_ISO)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].nicename").value(hasItem(DEFAULT_NICENAME)))
            .andExpect(jsonPath("$.[*].iso3").value(hasItem(DEFAULT_ISO_3)))
            .andExpect(jsonPath("$.[*].numcode").value(hasItem(DEFAULT_NUMCODE)))
            .andExpect(jsonPath("$.[*].phonecode").value(hasItem(DEFAULT_PHONECODE)));
    }
    
    @Test
    @Transactional
    public void getNation() throws Exception {
        // Initialize the database
        nationRepository.saveAndFlush(nation);

        // Get the nation
        restNationMockMvc.perform(get("/api/nations/{id}", nation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nation.getId().intValue()))
            .andExpect(jsonPath("$.iso").value(DEFAULT_ISO))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.nicename").value(DEFAULT_NICENAME))
            .andExpect(jsonPath("$.iso3").value(DEFAULT_ISO_3))
            .andExpect(jsonPath("$.numcode").value(DEFAULT_NUMCODE))
            .andExpect(jsonPath("$.phonecode").value(DEFAULT_PHONECODE));
    }
    @Test
    @Transactional
    public void getNonExistingNation() throws Exception {
        // Get the nation
        restNationMockMvc.perform(get("/api/nations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNation() throws Exception {
        // Initialize the database
        nationRepository.saveAndFlush(nation);

        int databaseSizeBeforeUpdate = nationRepository.findAll().size();

        // Update the nation
        Nation updatedNation = nationRepository.findById(nation.getId()).get();
        // Disconnect from session so that the updates on updatedNation are not directly saved in db
        em.detach(updatedNation);
        updatedNation
            .iso(UPDATED_ISO)
            .name(UPDATED_NAME)
            .nicename(UPDATED_NICENAME)
            .iso3(UPDATED_ISO_3)
            .numcode(UPDATED_NUMCODE)
            .phonecode(UPDATED_PHONECODE);
        NationDTO nationDTO = nationMapper.toDto(updatedNation);

        restNationMockMvc.perform(put("/api/nations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nationDTO)))
            .andExpect(status().isOk());

        // Validate the Nation in the database
        List<Nation> nationList = nationRepository.findAll();
        assertThat(nationList).hasSize(databaseSizeBeforeUpdate);
        Nation testNation = nationList.get(nationList.size() - 1);
        assertThat(testNation.getIso()).isEqualTo(UPDATED_ISO);
        assertThat(testNation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNation.getNicename()).isEqualTo(UPDATED_NICENAME);
        assertThat(testNation.getIso3()).isEqualTo(UPDATED_ISO_3);
        assertThat(testNation.getNumcode()).isEqualTo(UPDATED_NUMCODE);
        assertThat(testNation.getPhonecode()).isEqualTo(UPDATED_PHONECODE);
    }

    @Test
    @Transactional
    public void updateNonExistingNation() throws Exception {
        int databaseSizeBeforeUpdate = nationRepository.findAll().size();

        // Create the Nation
        NationDTO nationDTO = nationMapper.toDto(nation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNationMockMvc.perform(put("/api/nations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nation in the database
        List<Nation> nationList = nationRepository.findAll();
        assertThat(nationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNation() throws Exception {
        // Initialize the database
        nationRepository.saveAndFlush(nation);

        int databaseSizeBeforeDelete = nationRepository.findAll().size();

        // Delete the nation
        restNationMockMvc.perform(delete("/api/nations/{id}", nation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nation> nationList = nationRepository.findAll();
        assertThat(nationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
