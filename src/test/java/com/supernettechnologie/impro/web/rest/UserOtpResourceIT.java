package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.UserOtp;
import com.supernettechnologie.impro.repository.UserOtpRepository;
import com.supernettechnologie.impro.service.UserOtpService;
import com.supernettechnologie.impro.service.dto.UserOtpDTO;
import com.supernettechnologie.impro.service.mapper.UserOtpMapper;

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
 * Integration tests for the {@link UserOtpResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserOtpResourceIT {

    private static final Long DEFAULT_OTP_NUMBER = 1L;
    private static final Long UPDATED_OTP_NUMBER = 2L;

    private static final Boolean DEFAULT_OTP_USED = false;
    private static final Boolean UPDATED_OTP_USED = true;

    @Autowired
    private UserOtpRepository userOtpRepository;

    @Autowired
    private UserOtpMapper userOtpMapper;

    @Autowired
    private UserOtpService userOtpService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserOtpMockMvc;

    private UserOtp userOtp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserOtp createEntity(EntityManager em) {
        UserOtp userOtp = new UserOtp()
            .otpNumber(DEFAULT_OTP_NUMBER)
            .otpUsed(DEFAULT_OTP_USED);
        return userOtp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserOtp createUpdatedEntity(EntityManager em) {
        UserOtp userOtp = new UserOtp()
            .otpNumber(UPDATED_OTP_NUMBER)
            .otpUsed(UPDATED_OTP_USED);
        return userOtp;
    }

    @BeforeEach
    public void initTest() {
        userOtp = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserOtp() throws Exception {
        int databaseSizeBeforeCreate = userOtpRepository.findAll().size();
        // Create the UserOtp
        UserOtpDTO userOtpDTO = userOtpMapper.toDto(userOtp);
        restUserOtpMockMvc.perform(post("/api/user-otps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOtpDTO)))
            .andExpect(status().isCreated());

        // Validate the UserOtp in the database
        List<UserOtp> userOtpList = userOtpRepository.findAll();
        assertThat(userOtpList).hasSize(databaseSizeBeforeCreate + 1);
        UserOtp testUserOtp = userOtpList.get(userOtpList.size() - 1);
        assertThat(testUserOtp.getOtpNumber()).isEqualTo(DEFAULT_OTP_NUMBER);
        assertThat(testUserOtp.isOtpUsed()).isEqualTo(DEFAULT_OTP_USED);
    }

    @Test
    @Transactional
    public void createUserOtpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userOtpRepository.findAll().size();

        // Create the UserOtp with an existing ID
        userOtp.setId(1L);
        UserOtpDTO userOtpDTO = userOtpMapper.toDto(userOtp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserOtpMockMvc.perform(post("/api/user-otps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOtpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserOtp in the database
        List<UserOtp> userOtpList = userOtpRepository.findAll();
        assertThat(userOtpList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserOtps() throws Exception {
        // Initialize the database
        userOtpRepository.saveAndFlush(userOtp);

        // Get all the userOtpList
        restUserOtpMockMvc.perform(get("/api/user-otps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userOtp.getId().intValue())))
            .andExpect(jsonPath("$.[*].otpNumber").value(hasItem(DEFAULT_OTP_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].otpUsed").value(hasItem(DEFAULT_OTP_USED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUserOtp() throws Exception {
        // Initialize the database
        userOtpRepository.saveAndFlush(userOtp);

        // Get the userOtp
        restUserOtpMockMvc.perform(get("/api/user-otps/{id}", userOtp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userOtp.getId().intValue()))
            .andExpect(jsonPath("$.otpNumber").value(DEFAULT_OTP_NUMBER.intValue()))
            .andExpect(jsonPath("$.otpUsed").value(DEFAULT_OTP_USED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserOtp() throws Exception {
        // Get the userOtp
        restUserOtpMockMvc.perform(get("/api/user-otps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserOtp() throws Exception {
        // Initialize the database
        userOtpRepository.saveAndFlush(userOtp);

        int databaseSizeBeforeUpdate = userOtpRepository.findAll().size();

        // Update the userOtp
        UserOtp updatedUserOtp = userOtpRepository.findById(userOtp.getId()).get();
        // Disconnect from session so that the updates on updatedUserOtp are not directly saved in db
        em.detach(updatedUserOtp);
        updatedUserOtp
            .otpNumber(UPDATED_OTP_NUMBER)
            .otpUsed(UPDATED_OTP_USED);
        UserOtpDTO userOtpDTO = userOtpMapper.toDto(updatedUserOtp);

        restUserOtpMockMvc.perform(put("/api/user-otps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOtpDTO)))
            .andExpect(status().isOk());

        // Validate the UserOtp in the database
        List<UserOtp> userOtpList = userOtpRepository.findAll();
        assertThat(userOtpList).hasSize(databaseSizeBeforeUpdate);
        UserOtp testUserOtp = userOtpList.get(userOtpList.size() - 1);
        assertThat(testUserOtp.getOtpNumber()).isEqualTo(UPDATED_OTP_NUMBER);
        assertThat(testUserOtp.isOtpUsed()).isEqualTo(UPDATED_OTP_USED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserOtp() throws Exception {
        int databaseSizeBeforeUpdate = userOtpRepository.findAll().size();

        // Create the UserOtp
        UserOtpDTO userOtpDTO = userOtpMapper.toDto(userOtp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserOtpMockMvc.perform(put("/api/user-otps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOtpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserOtp in the database
        List<UserOtp> userOtpList = userOtpRepository.findAll();
        assertThat(userOtpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserOtp() throws Exception {
        // Initialize the database
        userOtpRepository.saveAndFlush(userOtp);

        int databaseSizeBeforeDelete = userOtpRepository.findAll().size();

        // Delete the userOtp
        restUserOtpMockMvc.perform(delete("/api/user-otps/{id}", userOtp.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserOtp> userOtpList = userOtpRepository.findAll();
        assertThat(userOtpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
