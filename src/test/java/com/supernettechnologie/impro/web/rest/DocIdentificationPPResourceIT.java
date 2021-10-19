package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.DocIdentificationPP;
import com.supernettechnologie.impro.repository.DocIdentificationPPRepository;
import com.supernettechnologie.impro.service.DocIdentificationPPService;
import com.supernettechnologie.impro.service.dto.DocIdentificationPPDTO;
import com.supernettechnologie.impro.service.mapper.DocIdentificationPPMapper;

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

import com.supernettechnologie.impro.domain.enumeration.TypeDocIdentification;
/**
 * Integration tests for the {@link DocIdentificationPPResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DocIdentificationPPResourceIT {

    private static final String DEFAULT_NUMERO_DOC = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DOC = "BBBBBBBBBB";

    private static final String DEFAULT_NIP = "AAAAAAAAAA";
    private static final String UPDATED_NIP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ETABLISSEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ETABLISSEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_ETABLISSEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_AUTORITE_EMETTRICE = "AAAAAAAAAA";
    private static final String UPDATED_AUTORITE_EMETTRICE = "BBBBBBBBBB";

    private static final TypeDocIdentification DEFAULT_TYPE_DOC_IDENTIFICATION = TypeDocIdentification.CNIB;
    private static final TypeDocIdentification UPDATED_TYPE_DOC_IDENTIFICATION = TypeDocIdentification.PASSEPORT;

    @Autowired
    private DocIdentificationPPRepository docIdentificationPPRepository;

    @Autowired
    private DocIdentificationPPMapper docIdentificationPPMapper;

    @Autowired
    private DocIdentificationPPService docIdentificationPPService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocIdentificationPPMockMvc;

    private DocIdentificationPP docIdentificationPP;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocIdentificationPP createEntity(EntityManager em) {
        DocIdentificationPP docIdentificationPP = new DocIdentificationPP()
            .numeroDoc(DEFAULT_NUMERO_DOC)
            .nip(DEFAULT_NIP)
            .dateEtablissement(DEFAULT_DATE_ETABLISSEMENT)
            .lieuEtablissement(DEFAULT_LIEU_ETABLISSEMENT)
            .autoriteEmettrice(DEFAULT_AUTORITE_EMETTRICE)
            .typeDocIdentification(DEFAULT_TYPE_DOC_IDENTIFICATION);
        return docIdentificationPP;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocIdentificationPP createUpdatedEntity(EntityManager em) {
        DocIdentificationPP docIdentificationPP = new DocIdentificationPP()
            .numeroDoc(UPDATED_NUMERO_DOC)
            .nip(UPDATED_NIP)
            .dateEtablissement(UPDATED_DATE_ETABLISSEMENT)
            .lieuEtablissement(UPDATED_LIEU_ETABLISSEMENT)
            .autoriteEmettrice(UPDATED_AUTORITE_EMETTRICE)
            .typeDocIdentification(UPDATED_TYPE_DOC_IDENTIFICATION);
        return docIdentificationPP;
    }

    @BeforeEach
    public void initTest() {
        docIdentificationPP = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocIdentificationPP() throws Exception {
        int databaseSizeBeforeCreate = docIdentificationPPRepository.findAll().size();
        // Create the DocIdentificationPP
        DocIdentificationPPDTO docIdentificationPPDTO = docIdentificationPPMapper.toDto(docIdentificationPP);
        restDocIdentificationPPMockMvc.perform(post("/api/doc-identification-pps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docIdentificationPPDTO)))
            .andExpect(status().isCreated());

        // Validate the DocIdentificationPP in the database
        List<DocIdentificationPP> docIdentificationPPList = docIdentificationPPRepository.findAll();
        assertThat(docIdentificationPPList).hasSize(databaseSizeBeforeCreate + 1);
        DocIdentificationPP testDocIdentificationPP = docIdentificationPPList.get(docIdentificationPPList.size() - 1);
        assertThat(testDocIdentificationPP.getNumeroDoc()).isEqualTo(DEFAULT_NUMERO_DOC);
        assertThat(testDocIdentificationPP.getNip()).isEqualTo(DEFAULT_NIP);
        assertThat(testDocIdentificationPP.getDateEtablissement()).isEqualTo(DEFAULT_DATE_ETABLISSEMENT);
        assertThat(testDocIdentificationPP.getLieuEtablissement()).isEqualTo(DEFAULT_LIEU_ETABLISSEMENT);
        assertThat(testDocIdentificationPP.getAutoriteEmettrice()).isEqualTo(DEFAULT_AUTORITE_EMETTRICE);
        assertThat(testDocIdentificationPP.getTypeDocIdentification()).isEqualTo(DEFAULT_TYPE_DOC_IDENTIFICATION);
    }

    @Test
    @Transactional
    public void createDocIdentificationPPWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = docIdentificationPPRepository.findAll().size();

        // Create the DocIdentificationPP with an existing ID
        docIdentificationPP.setId(1L);
        DocIdentificationPPDTO docIdentificationPPDTO = docIdentificationPPMapper.toDto(docIdentificationPP);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocIdentificationPPMockMvc.perform(post("/api/doc-identification-pps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docIdentificationPPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocIdentificationPP in the database
        List<DocIdentificationPP> docIdentificationPPList = docIdentificationPPRepository.findAll();
        assertThat(docIdentificationPPList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocIdentificationPPS() throws Exception {
        // Initialize the database
        docIdentificationPPRepository.saveAndFlush(docIdentificationPP);

        // Get all the docIdentificationPPList
        restDocIdentificationPPMockMvc.perform(get("/api/doc-identification-pps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docIdentificationPP.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDoc").value(hasItem(DEFAULT_NUMERO_DOC)))
            .andExpect(jsonPath("$.[*].nip").value(hasItem(DEFAULT_NIP)))
            .andExpect(jsonPath("$.[*].dateEtablissement").value(hasItem(DEFAULT_DATE_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].lieuEtablissement").value(hasItem(DEFAULT_LIEU_ETABLISSEMENT)))
            .andExpect(jsonPath("$.[*].autoriteEmettrice").value(hasItem(DEFAULT_AUTORITE_EMETTRICE)))
            .andExpect(jsonPath("$.[*].typeDocIdentification").value(hasItem(DEFAULT_TYPE_DOC_IDENTIFICATION.toString())));
    }
    
    @Test
    @Transactional
    public void getDocIdentificationPP() throws Exception {
        // Initialize the database
        docIdentificationPPRepository.saveAndFlush(docIdentificationPP);

        // Get the docIdentificationPP
        restDocIdentificationPPMockMvc.perform(get("/api/doc-identification-pps/{id}", docIdentificationPP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(docIdentificationPP.getId().intValue()))
            .andExpect(jsonPath("$.numeroDoc").value(DEFAULT_NUMERO_DOC))
            .andExpect(jsonPath("$.nip").value(DEFAULT_NIP))
            .andExpect(jsonPath("$.dateEtablissement").value(DEFAULT_DATE_ETABLISSEMENT.toString()))
            .andExpect(jsonPath("$.lieuEtablissement").value(DEFAULT_LIEU_ETABLISSEMENT))
            .andExpect(jsonPath("$.autoriteEmettrice").value(DEFAULT_AUTORITE_EMETTRICE))
            .andExpect(jsonPath("$.typeDocIdentification").value(DEFAULT_TYPE_DOC_IDENTIFICATION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDocIdentificationPP() throws Exception {
        // Get the docIdentificationPP
        restDocIdentificationPPMockMvc.perform(get("/api/doc-identification-pps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocIdentificationPP() throws Exception {
        // Initialize the database
        docIdentificationPPRepository.saveAndFlush(docIdentificationPP);

        int databaseSizeBeforeUpdate = docIdentificationPPRepository.findAll().size();

        // Update the docIdentificationPP
        DocIdentificationPP updatedDocIdentificationPP = docIdentificationPPRepository.findById(docIdentificationPP.getId()).get();
        // Disconnect from session so that the updates on updatedDocIdentificationPP are not directly saved in db
        em.detach(updatedDocIdentificationPP);
        updatedDocIdentificationPP
            .numeroDoc(UPDATED_NUMERO_DOC)
            .nip(UPDATED_NIP)
            .dateEtablissement(UPDATED_DATE_ETABLISSEMENT)
            .lieuEtablissement(UPDATED_LIEU_ETABLISSEMENT)
            .autoriteEmettrice(UPDATED_AUTORITE_EMETTRICE)
            .typeDocIdentification(UPDATED_TYPE_DOC_IDENTIFICATION);
        DocIdentificationPPDTO docIdentificationPPDTO = docIdentificationPPMapper.toDto(updatedDocIdentificationPP);

        restDocIdentificationPPMockMvc.perform(put("/api/doc-identification-pps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docIdentificationPPDTO)))
            .andExpect(status().isOk());

        // Validate the DocIdentificationPP in the database
        List<DocIdentificationPP> docIdentificationPPList = docIdentificationPPRepository.findAll();
        assertThat(docIdentificationPPList).hasSize(databaseSizeBeforeUpdate);
        DocIdentificationPP testDocIdentificationPP = docIdentificationPPList.get(docIdentificationPPList.size() - 1);
        assertThat(testDocIdentificationPP.getNumeroDoc()).isEqualTo(UPDATED_NUMERO_DOC);
        assertThat(testDocIdentificationPP.getNip()).isEqualTo(UPDATED_NIP);
        assertThat(testDocIdentificationPP.getDateEtablissement()).isEqualTo(UPDATED_DATE_ETABLISSEMENT);
        assertThat(testDocIdentificationPP.getLieuEtablissement()).isEqualTo(UPDATED_LIEU_ETABLISSEMENT);
        assertThat(testDocIdentificationPP.getAutoriteEmettrice()).isEqualTo(UPDATED_AUTORITE_EMETTRICE);
        assertThat(testDocIdentificationPP.getTypeDocIdentification()).isEqualTo(UPDATED_TYPE_DOC_IDENTIFICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingDocIdentificationPP() throws Exception {
        int databaseSizeBeforeUpdate = docIdentificationPPRepository.findAll().size();

        // Create the DocIdentificationPP
        DocIdentificationPPDTO docIdentificationPPDTO = docIdentificationPPMapper.toDto(docIdentificationPP);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocIdentificationPPMockMvc.perform(put("/api/doc-identification-pps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docIdentificationPPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocIdentificationPP in the database
        List<DocIdentificationPP> docIdentificationPPList = docIdentificationPPRepository.findAll();
        assertThat(docIdentificationPPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocIdentificationPP() throws Exception {
        // Initialize the database
        docIdentificationPPRepository.saveAndFlush(docIdentificationPP);

        int databaseSizeBeforeDelete = docIdentificationPPRepository.findAll().size();

        // Delete the docIdentificationPP
        restDocIdentificationPPMockMvc.perform(delete("/api/doc-identification-pps/{id}", docIdentificationPP.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocIdentificationPP> docIdentificationPPList = docIdentificationPPRepository.findAll();
        assertThat(docIdentificationPPList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
