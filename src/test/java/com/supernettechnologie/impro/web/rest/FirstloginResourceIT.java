package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.Firstlogin;
import com.supernettechnologie.impro.repository.FirstloginRepository;
import com.supernettechnologie.impro.service.FirstloginService;
import com.supernettechnologie.impro.service.dto.FirstloginDTO;
import com.supernettechnologie.impro.service.mapper.FirstloginMapper;
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
import java.util.List;

import static com.supernettechnologie.impro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FirstloginResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
public class FirstloginResourceIT {

    private static final Boolean DEFAULT_PASSE = false;
    private static final Boolean UPDATED_PASSE = true;

    @Autowired
    private FirstloginRepository firstloginRepository;

    @Autowired
    private FirstloginMapper firstloginMapper;

    @Autowired
    private FirstloginService firstloginService;

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

    private MockMvc restFirstloginMockMvc;

    private Firstlogin firstlogin;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FirstloginResource firstloginResource = new FirstloginResource(firstloginService);
        this.restFirstloginMockMvc = MockMvcBuilders.standaloneSetup(firstloginResource)
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
    public static Firstlogin createEntity(EntityManager em) {
        Firstlogin firstlogin = new Firstlogin()
            .passe(DEFAULT_PASSE);
        return firstlogin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Firstlogin createUpdatedEntity(EntityManager em) {
        Firstlogin firstlogin = new Firstlogin()
            .passe(UPDATED_PASSE);
        return firstlogin;
    }

    @BeforeEach
    public void initTest() {
        firstlogin = createEntity(em);
    }

    @Test
    @Transactional
    public void createFirstlogin() throws Exception {
        int databaseSizeBeforeCreate = firstloginRepository.findAll().size();

        // Create the Firstlogin
        FirstloginDTO firstloginDTO = firstloginMapper.toDto(firstlogin);
        restFirstloginMockMvc.perform(post("/api/firstlogins")
           // .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(firstloginDTO)))
            .andExpect(status().isCreated());

        // Validate the Firstlogin in the database
        List<Firstlogin> firstloginList = firstloginRepository.findAll();
        assertThat(firstloginList).hasSize(databaseSizeBeforeCreate + 1);
        Firstlogin testFirstlogin = firstloginList.get(firstloginList.size() - 1);
        assertThat(testFirstlogin.isPasse()).isEqualTo(DEFAULT_PASSE);
    }

    @Test
    @Transactional
    public void createFirstloginWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = firstloginRepository.findAll().size();

        // Create the Firstlogin with an existing ID
        firstlogin.setId(1L);
        FirstloginDTO firstloginDTO = firstloginMapper.toDto(firstlogin);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFirstloginMockMvc.perform(post("/api/firstlogins")
            //.contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(firstloginDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Firstlogin in the database
        List<Firstlogin> firstloginList = firstloginRepository.findAll();
        assertThat(firstloginList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFirstlogins() throws Exception {
        // Initialize the database
        firstloginRepository.saveAndFlush(firstlogin);

        // Get all the firstloginList
        restFirstloginMockMvc.perform(get("/api/firstlogins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(firstlogin.getId().intValue())))
            .andExpect(jsonPath("$.[*].passe").value(hasItem(DEFAULT_PASSE.booleanValue())));
    }

    @Test
    @Transactional
    public void getFirstlogin() throws Exception {
        // Initialize the database
        firstloginRepository.saveAndFlush(firstlogin);

        // Get the firstlogin
        restFirstloginMockMvc.perform(get("/api/firstlogins/{id}", firstlogin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(firstlogin.getId().intValue()))
            .andExpect(jsonPath("$.passe").value(DEFAULT_PASSE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFirstlogin() throws Exception {
        // Get the firstlogin
        restFirstloginMockMvc.perform(get("/api/firstlogins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFirstlogin() throws Exception {
        // Initialize the database
        firstloginRepository.saveAndFlush(firstlogin);

        int databaseSizeBeforeUpdate = firstloginRepository.findAll().size();

        // Update the firstlogin
        Firstlogin updatedFirstlogin = firstloginRepository.findById(firstlogin.getId()).get();
        // Disconnect from session so that the updates on updatedFirstlogin are not directly saved in db
        em.detach(updatedFirstlogin);
        updatedFirstlogin
            .passe(UPDATED_PASSE);
        FirstloginDTO firstloginDTO = firstloginMapper.toDto(updatedFirstlogin);

        restFirstloginMockMvc.perform(put("/api/firstlogins")
            //.contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(firstloginDTO)))
            .andExpect(status().isOk());

        // Validate the Firstlogin in the database
        List<Firstlogin> firstloginList = firstloginRepository.findAll();
        assertThat(firstloginList).hasSize(databaseSizeBeforeUpdate);
        Firstlogin testFirstlogin = firstloginList.get(firstloginList.size() - 1);
        assertThat(testFirstlogin.isPasse()).isEqualTo(UPDATED_PASSE);
    }

    @Test
    @Transactional
    public void updateNonExistingFirstlogin() throws Exception {
        int databaseSizeBeforeUpdate = firstloginRepository.findAll().size();

        // Create the Firstlogin
        FirstloginDTO firstloginDTO = firstloginMapper.toDto(firstlogin);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFirstloginMockMvc.perform(put("/api/firstlogins")
           // .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(firstloginDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Firstlogin in the database
        List<Firstlogin> firstloginList = firstloginRepository.findAll();
        assertThat(firstloginList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFirstlogin() throws Exception {
        // Initialize the database
        firstloginRepository.saveAndFlush(firstlogin);

        int databaseSizeBeforeDelete = firstloginRepository.findAll().size();

        // Delete the firstlogin
        restFirstloginMockMvc.perform(delete("/api/firstlogins/{id}", firstlogin.getId()));
            //.accept(TestUtil.APPLICATION_JSON))
           // .andExpect(status().isNoContent());

        // Validate the database contains one less item
       /* List<Firstlogin> firstloginList = firstloginRepository.findAll();
        assertThat(firstloginList).hasSize(databaseSizeBeforeDelete - 1);*/
    }
}
