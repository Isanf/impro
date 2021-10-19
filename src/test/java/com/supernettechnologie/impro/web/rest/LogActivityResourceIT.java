package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.LogActivity;
import com.supernettechnologie.impro.repository.LogActivityRepository;
import com.supernettechnologie.impro.service.LogActivityService;
import com.supernettechnologie.impro.service.dto.LogActivityDTO;
import com.supernettechnologie.impro.service.mapper.LogActivityMapper;

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
 * Integration tests for the {@link LogActivityResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LogActivityResourceIT {

    private static final String DEFAULT_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_ACTION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ACTION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LogActivityRepository logActivityRepository;

    @Autowired
    private LogActivityMapper logActivityMapper;

    @Autowired
    private LogActivityService logActivityService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogActivityMockMvc;

    private LogActivity logActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogActivity createEntity(EntityManager em) {
        LogActivity logActivity = new LogActivity()
            .principal(DEFAULT_PRINCIPAL)
            .url(DEFAULT_URL)
            .action(DEFAULT_ACTION)
            .ip(DEFAULT_IP)
            .dateAction(DEFAULT_DATE_ACTION);
        return logActivity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogActivity createUpdatedEntity(EntityManager em) {
        LogActivity logActivity = new LogActivity()
            .principal(UPDATED_PRINCIPAL)
            .url(UPDATED_URL)
            .action(UPDATED_ACTION)
            .ip(UPDATED_IP)
            .dateAction(UPDATED_DATE_ACTION);
        return logActivity;
    }

    @BeforeEach
    public void initTest() {
        logActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogActivity() throws Exception {
        int databaseSizeBeforeCreate = logActivityRepository.findAll().size();
        // Create the LogActivity
        LogActivityDTO logActivityDTO = logActivityMapper.toDto(logActivity);
        restLogActivityMockMvc.perform(post("/api/log-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the LogActivity in the database
        List<LogActivity> logActivityList = logActivityRepository.findAll();
        assertThat(logActivityList).hasSize(databaseSizeBeforeCreate + 1);
        LogActivity testLogActivity = logActivityList.get(logActivityList.size() - 1);
        assertThat(testLogActivity.getPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(testLogActivity.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testLogActivity.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testLogActivity.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testLogActivity.getDateAction()).isEqualTo(DEFAULT_DATE_ACTION);
    }

    @Test
    @Transactional
    public void createLogActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logActivityRepository.findAll().size();

        // Create the LogActivity with an existing ID
        logActivity.setId(1L);
        LogActivityDTO logActivityDTO = logActivityMapper.toDto(logActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogActivityMockMvc.perform(post("/api/log-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LogActivity in the database
        List<LogActivity> logActivityList = logActivityRepository.findAll();
        assertThat(logActivityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLogActivities() throws Exception {
        // Initialize the database
        logActivityRepository.saveAndFlush(logActivity);

        // Get all the logActivityList
        restLogActivityMockMvc.perform(get("/api/log-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].dateAction").value(hasItem(sameInstant(DEFAULT_DATE_ACTION))));
    }
    
    @Test
    @Transactional
    public void getLogActivity() throws Exception {
        // Initialize the database
        logActivityRepository.saveAndFlush(logActivity);

        // Get the logActivity
        restLogActivityMockMvc.perform(get("/api/log-activities/{id}", logActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logActivity.getId().intValue()))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
            .andExpect(jsonPath("$.dateAction").value(sameInstant(DEFAULT_DATE_ACTION)));
    }
    @Test
    @Transactional
    public void getNonExistingLogActivity() throws Exception {
        // Get the logActivity
        restLogActivityMockMvc.perform(get("/api/log-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogActivity() throws Exception {
        // Initialize the database
        logActivityRepository.saveAndFlush(logActivity);

        int databaseSizeBeforeUpdate = logActivityRepository.findAll().size();

        // Update the logActivity
        LogActivity updatedLogActivity = logActivityRepository.findById(logActivity.getId()).get();
        // Disconnect from session so that the updates on updatedLogActivity are not directly saved in db
        em.detach(updatedLogActivity);
        updatedLogActivity
            .principal(UPDATED_PRINCIPAL)
            .url(UPDATED_URL)
            .action(UPDATED_ACTION)
            .ip(UPDATED_IP)
            .dateAction(UPDATED_DATE_ACTION);
        LogActivityDTO logActivityDTO = logActivityMapper.toDto(updatedLogActivity);

        restLogActivityMockMvc.perform(put("/api/log-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logActivityDTO)))
            .andExpect(status().isOk());

        // Validate the LogActivity in the database
        List<LogActivity> logActivityList = logActivityRepository.findAll();
        assertThat(logActivityList).hasSize(databaseSizeBeforeUpdate);
        LogActivity testLogActivity = logActivityList.get(logActivityList.size() - 1);
        assertThat(testLogActivity.getPrincipal()).isEqualTo(UPDATED_PRINCIPAL);
        assertThat(testLogActivity.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testLogActivity.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testLogActivity.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testLogActivity.getDateAction()).isEqualTo(UPDATED_DATE_ACTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLogActivity() throws Exception {
        int databaseSizeBeforeUpdate = logActivityRepository.findAll().size();

        // Create the LogActivity
        LogActivityDTO logActivityDTO = logActivityMapper.toDto(logActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogActivityMockMvc.perform(put("/api/log-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LogActivity in the database
        List<LogActivity> logActivityList = logActivityRepository.findAll();
        assertThat(logActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLogActivity() throws Exception {
        // Initialize the database
        logActivityRepository.saveAndFlush(logActivity);

        int databaseSizeBeforeDelete = logActivityRepository.findAll().size();

        // Delete the logActivity
        restLogActivityMockMvc.perform(delete("/api/log-activities/{id}", logActivity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LogActivity> logActivityList = logActivityRepository.findAll();
        assertThat(logActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
