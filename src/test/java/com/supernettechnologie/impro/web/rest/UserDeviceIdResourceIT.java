package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.UserDeviceId;
import com.supernettechnologie.impro.repository.UserDeviceIdRepository;
import com.supernettechnologie.impro.service.UserDeviceIdService;
import com.supernettechnologie.impro.service.dto.UserDeviceIdDTO;
import com.supernettechnologie.impro.service.mapper.UserDeviceIdMapper;

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
 * Integration tests for the {@link UserDeviceIdResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserDeviceIdResourceIT {

    private static final String DEFAULT_ADRESS_MAC = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS_MAC = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    @Autowired
    private UserDeviceIdRepository userDeviceIdRepository;

    @Autowired
    private UserDeviceIdMapper userDeviceIdMapper;

    @Autowired
    private UserDeviceIdService userDeviceIdService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserDeviceIdMockMvc;

    private UserDeviceId userDeviceId;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDeviceId createEntity(EntityManager em) {
        UserDeviceId userDeviceId = new UserDeviceId()
            .adressMac(DEFAULT_ADRESS_MAC)
            .deviceId(DEFAULT_DEVICE_ID);
        return userDeviceId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDeviceId createUpdatedEntity(EntityManager em) {
        UserDeviceId userDeviceId = new UserDeviceId()
            .adressMac(UPDATED_ADRESS_MAC)
            .deviceId(UPDATED_DEVICE_ID);
        return userDeviceId;
    }

    @BeforeEach
    public void initTest() {
        userDeviceId = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDeviceId() throws Exception {
        int databaseSizeBeforeCreate = userDeviceIdRepository.findAll().size();
        // Create the UserDeviceId
        UserDeviceIdDTO userDeviceIdDTO = userDeviceIdMapper.toDto(userDeviceId);
        restUserDeviceIdMockMvc.perform(post("/api/user-device-ids")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDeviceIdDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDeviceId in the database
        List<UserDeviceId> userDeviceIdList = userDeviceIdRepository.findAll();
        assertThat(userDeviceIdList).hasSize(databaseSizeBeforeCreate + 1);
        UserDeviceId testUserDeviceId = userDeviceIdList.get(userDeviceIdList.size() - 1);
        assertThat(testUserDeviceId.getAdressMac()).isEqualTo(DEFAULT_ADRESS_MAC);
        assertThat(testUserDeviceId.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
    }

    @Test
    @Transactional
    public void createUserDeviceIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDeviceIdRepository.findAll().size();

        // Create the UserDeviceId with an existing ID
        userDeviceId.setId(1L);
        UserDeviceIdDTO userDeviceIdDTO = userDeviceIdMapper.toDto(userDeviceId);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDeviceIdMockMvc.perform(post("/api/user-device-ids")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDeviceIdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDeviceId in the database
        List<UserDeviceId> userDeviceIdList = userDeviceIdRepository.findAll();
        assertThat(userDeviceIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserDeviceIds() throws Exception {
        // Initialize the database
        userDeviceIdRepository.saveAndFlush(userDeviceId);

        // Get all the userDeviceIdList
        restUserDeviceIdMockMvc.perform(get("/api/user-device-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDeviceId.getId().intValue())))
            .andExpect(jsonPath("$.[*].adressMac").value(hasItem(DEFAULT_ADRESS_MAC)))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID)));
    }
    
    @Test
    @Transactional
    public void getUserDeviceId() throws Exception {
        // Initialize the database
        userDeviceIdRepository.saveAndFlush(userDeviceId);

        // Get the userDeviceId
        restUserDeviceIdMockMvc.perform(get("/api/user-device-ids/{id}", userDeviceId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userDeviceId.getId().intValue()))
            .andExpect(jsonPath("$.adressMac").value(DEFAULT_ADRESS_MAC))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID));
    }
    @Test
    @Transactional
    public void getNonExistingUserDeviceId() throws Exception {
        // Get the userDeviceId
        restUserDeviceIdMockMvc.perform(get("/api/user-device-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDeviceId() throws Exception {
        // Initialize the database
        userDeviceIdRepository.saveAndFlush(userDeviceId);

        int databaseSizeBeforeUpdate = userDeviceIdRepository.findAll().size();

        // Update the userDeviceId
        UserDeviceId updatedUserDeviceId = userDeviceIdRepository.findById(userDeviceId.getId()).get();
        // Disconnect from session so that the updates on updatedUserDeviceId are not directly saved in db
        em.detach(updatedUserDeviceId);
        updatedUserDeviceId
            .adressMac(UPDATED_ADRESS_MAC)
            .deviceId(UPDATED_DEVICE_ID);
        UserDeviceIdDTO userDeviceIdDTO = userDeviceIdMapper.toDto(updatedUserDeviceId);

        restUserDeviceIdMockMvc.perform(put("/api/user-device-ids")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDeviceIdDTO)))
            .andExpect(status().isOk());

        // Validate the UserDeviceId in the database
        List<UserDeviceId> userDeviceIdList = userDeviceIdRepository.findAll();
        assertThat(userDeviceIdList).hasSize(databaseSizeBeforeUpdate);
        UserDeviceId testUserDeviceId = userDeviceIdList.get(userDeviceIdList.size() - 1);
        assertThat(testUserDeviceId.getAdressMac()).isEqualTo(UPDATED_ADRESS_MAC);
        assertThat(testUserDeviceId.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDeviceId() throws Exception {
        int databaseSizeBeforeUpdate = userDeviceIdRepository.findAll().size();

        // Create the UserDeviceId
        UserDeviceIdDTO userDeviceIdDTO = userDeviceIdMapper.toDto(userDeviceId);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDeviceIdMockMvc.perform(put("/api/user-device-ids")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userDeviceIdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDeviceId in the database
        List<UserDeviceId> userDeviceIdList = userDeviceIdRepository.findAll();
        assertThat(userDeviceIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserDeviceId() throws Exception {
        // Initialize the database
        userDeviceIdRepository.saveAndFlush(userDeviceId);

        int databaseSizeBeforeDelete = userDeviceIdRepository.findAll().size();

        // Delete the userDeviceId
        restUserDeviceIdMockMvc.perform(delete("/api/user-device-ids/{id}", userDeviceId.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserDeviceId> userDeviceIdList = userDeviceIdRepository.findAll();
        assertThat(userDeviceIdList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
