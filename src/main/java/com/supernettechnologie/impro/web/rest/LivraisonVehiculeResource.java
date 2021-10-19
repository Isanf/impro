package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.repository.PersonnePhysiqueRepository;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.LivraisonVehiculeService;
import com.supernettechnologie.impro.service.util.RandomUtil;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.LivraisonVehiculeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.LivraisonVehicule}.
 */
@RestController
@RequestMapping("/api")
public class LivraisonVehiculeResource {

    private final Logger log = LoggerFactory.getLogger(LivraisonVehiculeResource.class);

    private static final String ENTITY_NAME = "livraisonVehicule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LivraisonVehiculeService livraisonVehiculeService;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    public LivraisonVehiculeResource(LivraisonVehiculeService livraisonVehiculeService) {
        this.livraisonVehiculeService = livraisonVehiculeService;
    }

    /**
     * {@code POST  /livraison-vehicules} : Create a new livraisonVehicule.
     *
     * @param livraisonVehiculeDTO the livraisonVehiculeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livraisonVehiculeDTO, or with status {@code 400 (Bad Request)} if the livraisonVehicule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/livraison-vehicules")
    public ResponseEntity<LivraisonVehiculeDTO> createLivraisonVehicule(@RequestBody LivraisonVehiculeDTO livraisonVehiculeDTO) throws URISyntaxException {
        log.debug("REST request to save LivraisonVehicule : {}", livraisonVehiculeDTO);
        if (livraisonVehiculeDTO.getId() != null) {
            throw new BadRequestAlertException("A new livraisonVehicule cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        livraisonVehiculeDTO.setConcessionnaireId(personnePhysique.get().getOrganisation().getId());

        livraisonVehiculeDTO.setDateLivraison(ZonedDateTime.now());
        livraisonVehiculeDTO.setNumeroLivraison(RandomUtil.generateRandomSerialNumericString());

        LivraisonVehiculeDTO result = livraisonVehiculeService.save(livraisonVehiculeDTO);

        return ResponseEntity.created(new URI("/api/livraison-vehicules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);

    }

    /**
     * {@code PUT  /livraison-vehicules} : Updates an existing livraisonVehicule.
     *
     * @param livraisonVehiculeDTO the livraisonVehiculeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livraisonVehiculeDTO,
     * or with status {@code 400 (Bad Request)} if the livraisonVehiculeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the livraisonVehiculeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/livraison-vehicules")
    public ResponseEntity<LivraisonVehiculeDTO> updateLivraisonVehicule(@RequestBody LivraisonVehiculeDTO livraisonVehiculeDTO) throws URISyntaxException {
        log.debug("REST request to update LivraisonVehicule : {}", livraisonVehiculeDTO);
        if (livraisonVehiculeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LivraisonVehiculeDTO result = livraisonVehiculeService.save(livraisonVehiculeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livraisonVehiculeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /livraison-vehicules} : get all the livraisonVehicules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of livraisonVehicules in body.
     */
    @GetMapping("/livraison-vehicules")
    public ResponseEntity<List<LivraisonVehiculeDTO>> getAllLivraisonVehicules(Pageable pageable) {
        log.debug("REST request to get a page of LivraisonVehicules");
        Page<LivraisonVehiculeDTO> page = livraisonVehiculeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/livraison-vehicules1")
    public ResponseEntity<List<LivraisonVehiculeDTO>> getAllLivraisonVehicules1(Pageable pageable) {
        log.debug("REST request to get a page of LivraisonVehicules");
        Page<LivraisonVehiculeDTO> page = livraisonVehiculeService.findAll1(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /livraison-vehicules/:id} : get the "id" livraisonVehicule.
     *
     * @param id the id of the livraisonVehiculeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the livraisonVehiculeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/livraison-vehicules/{id}")
    public ResponseEntity<LivraisonVehiculeDTO> getLivraisonVehicule(@PathVariable Long id) {
        log.debug("REST request to get LivraisonVehicule : {}", id);
        Optional<LivraisonVehiculeDTO> livraisonVehiculeDTO = livraisonVehiculeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(livraisonVehiculeDTO);
    }

    /**
     * {@code DELETE  /livraison-vehicules/:id} : delete the "id" livraisonVehicule.
     *
     * @param id the id of the livraisonVehiculeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/livraison-vehicules/{id}")
    public ResponseEntity<Void> deleteLivraisonVehicule(@PathVariable Long id) {
        log.debug("REST request to delete LivraisonVehicule : {}", id);
        livraisonVehiculeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
