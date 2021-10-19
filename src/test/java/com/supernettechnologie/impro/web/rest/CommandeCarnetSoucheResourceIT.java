package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.ImproApp;
import com.supernettechnologie.impro.domain.CommandeCarnetSouche;
import com.supernettechnologie.impro.repository.CommandeCarnetSoucheRepository;
import com.supernettechnologie.impro.service.CommandeCarnetSoucheService;
import com.supernettechnologie.impro.service.dto.CommandeCarnetSoucheDTO;
import com.supernettechnologie.impro.service.mapper.CommandeCarnetSoucheMapper;

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
 * Integration tests for the {@link CommandeCarnetSoucheResource} REST controller.
 */
@SpringBootTest(classes = ImproApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommandeCarnetSoucheResourceIT {

    private static final String DEFAULT_NUMERO_COMMANDE_CS = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_COMMANDE_CS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_COMMANDE_CS = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_COMMANDE_CS = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TYPE_PAIEMENT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_PAIEMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EST_VALIDE = false;
    private static final Boolean UPDATED_EST_VALIDE = true;

    private static final Boolean DEFAULT_EST_TRAITEE = false;
    private static final Boolean UPDATED_EST_TRAITEE = true;

    private static final Boolean DEFAULT_EST_LIVREE = false;
    private static final Boolean UPDATED_EST_LIVREE = true;

    private static final String DEFAULT_PRIX_COMMANDE = "AAAAAAAAAA";
    private static final String UPDATED_PRIX_COMMANDE = "BBBBBBBBBB";

    @Autowired
    private CommandeCarnetSoucheRepository commandeCarnetSoucheRepository;

    @Autowired
    private CommandeCarnetSoucheMapper commandeCarnetSoucheMapper;

    @Autowired
    private CommandeCarnetSoucheService commandeCarnetSoucheService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandeCarnetSoucheMockMvc;

    private CommandeCarnetSouche commandeCarnetSouche;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeCarnetSouche createEntity(EntityManager em) {
        CommandeCarnetSouche commandeCarnetSouche = new CommandeCarnetSouche()
            .numeroCommandeCS(DEFAULT_NUMERO_COMMANDE_CS)
            .dateCommandeCS(DEFAULT_DATE_COMMANDE_CS)
            .typePaiement(DEFAULT_TYPE_PAIEMENT)
            .estValide(DEFAULT_EST_VALIDE)
            .estTraitee(DEFAULT_EST_TRAITEE)
            .estLivree(DEFAULT_EST_LIVREE)
            .prixCommande(DEFAULT_PRIX_COMMANDE);
        return commandeCarnetSouche;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeCarnetSouche createUpdatedEntity(EntityManager em) {
        CommandeCarnetSouche commandeCarnetSouche = new CommandeCarnetSouche()
            .numeroCommandeCS(UPDATED_NUMERO_COMMANDE_CS)
            .dateCommandeCS(UPDATED_DATE_COMMANDE_CS)
            .typePaiement(UPDATED_TYPE_PAIEMENT)
            .estValide(UPDATED_EST_VALIDE)
            .estTraitee(UPDATED_EST_TRAITEE)
            .estLivree(UPDATED_EST_LIVREE)
            .prixCommande(UPDATED_PRIX_COMMANDE);
        return commandeCarnetSouche;
    }

    @BeforeEach
    public void initTest() {
        commandeCarnetSouche = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandeCarnetSouche() throws Exception {
        int databaseSizeBeforeCreate = commandeCarnetSoucheRepository.findAll().size();
        // Create the CommandeCarnetSouche
        CommandeCarnetSoucheDTO commandeCarnetSoucheDTO = commandeCarnetSoucheMapper.toDto(commandeCarnetSouche);
        restCommandeCarnetSoucheMockMvc.perform(post("/api/commande-carnet-souches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeCarnetSoucheDTO)))
            .andExpect(status().isCreated());

        // Validate the CommandeCarnetSouche in the database
        List<CommandeCarnetSouche> commandeCarnetSoucheList = commandeCarnetSoucheRepository.findAll();
        assertThat(commandeCarnetSoucheList).hasSize(databaseSizeBeforeCreate + 1);
        CommandeCarnetSouche testCommandeCarnetSouche = commandeCarnetSoucheList.get(commandeCarnetSoucheList.size() - 1);
        assertThat(testCommandeCarnetSouche.getNumeroCommandeCS()).isEqualTo(DEFAULT_NUMERO_COMMANDE_CS);
        assertThat(testCommandeCarnetSouche.getDateCommandeCS()).isEqualTo(DEFAULT_DATE_COMMANDE_CS);
        assertThat(testCommandeCarnetSouche.getTypePaiement()).isEqualTo(DEFAULT_TYPE_PAIEMENT);
        assertThat(testCommandeCarnetSouche.isEstValide()).isEqualTo(DEFAULT_EST_VALIDE);
        assertThat(testCommandeCarnetSouche.isEstTraitee()).isEqualTo(DEFAULT_EST_TRAITEE);
        assertThat(testCommandeCarnetSouche.isEstLivree()).isEqualTo(DEFAULT_EST_LIVREE);
        assertThat(testCommandeCarnetSouche.getPrixCommande()).isEqualTo(DEFAULT_PRIX_COMMANDE);
    }

    @Test
    @Transactional
    public void createCommandeCarnetSoucheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeCarnetSoucheRepository.findAll().size();

        // Create the CommandeCarnetSouche with an existing ID
        commandeCarnetSouche.setId(1L);
        CommandeCarnetSoucheDTO commandeCarnetSoucheDTO = commandeCarnetSoucheMapper.toDto(commandeCarnetSouche);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeCarnetSoucheMockMvc.perform(post("/api/commande-carnet-souches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeCarnetSoucheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeCarnetSouche in the database
        List<CommandeCarnetSouche> commandeCarnetSoucheList = commandeCarnetSoucheRepository.findAll();
        assertThat(commandeCarnetSoucheList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommandeCarnetSouches() throws Exception {
        // Initialize the database
        commandeCarnetSoucheRepository.saveAndFlush(commandeCarnetSouche);

        // Get all the commandeCarnetSoucheList
        restCommandeCarnetSoucheMockMvc.perform(get("/api/commande-carnet-souches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeCarnetSouche.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCommandeCS").value(hasItem(DEFAULT_NUMERO_COMMANDE_CS)))
            .andExpect(jsonPath("$.[*].dateCommandeCS").value(hasItem(sameInstant(DEFAULT_DATE_COMMANDE_CS))))
            .andExpect(jsonPath("$.[*].typePaiement").value(hasItem(DEFAULT_TYPE_PAIEMENT)))
            .andExpect(jsonPath("$.[*].estValide").value(hasItem(DEFAULT_EST_VALIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].estTraitee").value(hasItem(DEFAULT_EST_TRAITEE.booleanValue())))
            .andExpect(jsonPath("$.[*].estLivree").value(hasItem(DEFAULT_EST_LIVREE.booleanValue())))
            .andExpect(jsonPath("$.[*].prixCommande").value(hasItem(DEFAULT_PRIX_COMMANDE)));
    }
    
    @Test
    @Transactional
    public void getCommandeCarnetSouche() throws Exception {
        // Initialize the database
        commandeCarnetSoucheRepository.saveAndFlush(commandeCarnetSouche);

        // Get the commandeCarnetSouche
        restCommandeCarnetSoucheMockMvc.perform(get("/api/commande-carnet-souches/{id}", commandeCarnetSouche.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandeCarnetSouche.getId().intValue()))
            .andExpect(jsonPath("$.numeroCommandeCS").value(DEFAULT_NUMERO_COMMANDE_CS))
            .andExpect(jsonPath("$.dateCommandeCS").value(sameInstant(DEFAULT_DATE_COMMANDE_CS)))
            .andExpect(jsonPath("$.typePaiement").value(DEFAULT_TYPE_PAIEMENT))
            .andExpect(jsonPath("$.estValide").value(DEFAULT_EST_VALIDE.booleanValue()))
            .andExpect(jsonPath("$.estTraitee").value(DEFAULT_EST_TRAITEE.booleanValue()))
            .andExpect(jsonPath("$.estLivree").value(DEFAULT_EST_LIVREE.booleanValue()))
            .andExpect(jsonPath("$.prixCommande").value(DEFAULT_PRIX_COMMANDE));
    }
    @Test
    @Transactional
    public void getNonExistingCommandeCarnetSouche() throws Exception {
        // Get the commandeCarnetSouche
        restCommandeCarnetSoucheMockMvc.perform(get("/api/commande-carnet-souches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandeCarnetSouche() throws Exception {
        // Initialize the database
        commandeCarnetSoucheRepository.saveAndFlush(commandeCarnetSouche);

        int databaseSizeBeforeUpdate = commandeCarnetSoucheRepository.findAll().size();

        // Update the commandeCarnetSouche
        CommandeCarnetSouche updatedCommandeCarnetSouche = commandeCarnetSoucheRepository.findById(commandeCarnetSouche.getId()).get();
        // Disconnect from session so that the updates on updatedCommandeCarnetSouche are not directly saved in db
        em.detach(updatedCommandeCarnetSouche);
        updatedCommandeCarnetSouche
            .numeroCommandeCS(UPDATED_NUMERO_COMMANDE_CS)
            .dateCommandeCS(UPDATED_DATE_COMMANDE_CS)
            .typePaiement(UPDATED_TYPE_PAIEMENT)
            .estValide(UPDATED_EST_VALIDE)
            .estTraitee(UPDATED_EST_TRAITEE)
            .estLivree(UPDATED_EST_LIVREE)
            .prixCommande(UPDATED_PRIX_COMMANDE);
        CommandeCarnetSoucheDTO commandeCarnetSoucheDTO = commandeCarnetSoucheMapper.toDto(updatedCommandeCarnetSouche);

        restCommandeCarnetSoucheMockMvc.perform(put("/api/commande-carnet-souches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeCarnetSoucheDTO)))
            .andExpect(status().isOk());

        // Validate the CommandeCarnetSouche in the database
        List<CommandeCarnetSouche> commandeCarnetSoucheList = commandeCarnetSoucheRepository.findAll();
        assertThat(commandeCarnetSoucheList).hasSize(databaseSizeBeforeUpdate);
        CommandeCarnetSouche testCommandeCarnetSouche = commandeCarnetSoucheList.get(commandeCarnetSoucheList.size() - 1);
        assertThat(testCommandeCarnetSouche.getNumeroCommandeCS()).isEqualTo(UPDATED_NUMERO_COMMANDE_CS);
        assertThat(testCommandeCarnetSouche.getDateCommandeCS()).isEqualTo(UPDATED_DATE_COMMANDE_CS);
        assertThat(testCommandeCarnetSouche.getTypePaiement()).isEqualTo(UPDATED_TYPE_PAIEMENT);
        assertThat(testCommandeCarnetSouche.isEstValide()).isEqualTo(UPDATED_EST_VALIDE);
        assertThat(testCommandeCarnetSouche.isEstTraitee()).isEqualTo(UPDATED_EST_TRAITEE);
        assertThat(testCommandeCarnetSouche.isEstLivree()).isEqualTo(UPDATED_EST_LIVREE);
        assertThat(testCommandeCarnetSouche.getPrixCommande()).isEqualTo(UPDATED_PRIX_COMMANDE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandeCarnetSouche() throws Exception {
        int databaseSizeBeforeUpdate = commandeCarnetSoucheRepository.findAll().size();

        // Create the CommandeCarnetSouche
        CommandeCarnetSoucheDTO commandeCarnetSoucheDTO = commandeCarnetSoucheMapper.toDto(commandeCarnetSouche);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeCarnetSoucheMockMvc.perform(put("/api/commande-carnet-souches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeCarnetSoucheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeCarnetSouche in the database
        List<CommandeCarnetSouche> commandeCarnetSoucheList = commandeCarnetSoucheRepository.findAll();
        assertThat(commandeCarnetSoucheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommandeCarnetSouche() throws Exception {
        // Initialize the database
        commandeCarnetSoucheRepository.saveAndFlush(commandeCarnetSouche);

        int databaseSizeBeforeDelete = commandeCarnetSoucheRepository.findAll().size();

        // Delete the commandeCarnetSouche
        restCommandeCarnetSoucheMockMvc.perform(delete("/api/commande-carnet-souches/{id}", commandeCarnetSouche.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommandeCarnetSouche> commandeCarnetSoucheList = commandeCarnetSoucheRepository.findAll();
        assertThat(commandeCarnetSoucheList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
