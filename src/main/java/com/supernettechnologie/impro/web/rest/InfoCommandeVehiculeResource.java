package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.InfoCommandeVehiculeService;
import com.supernettechnologie.impro.service.util.RandomUtil;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.InfoCommandeVehiculeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.InfoCommandeVehicule}.
 */
@RestController
@RequestMapping("/api")
public class InfoCommandeVehiculeResource {

    private final Logger log = LoggerFactory.getLogger(InfoCommandeVehiculeResource.class);

    private static final String ENTITY_NAME = "infoCommandeVehicule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfoCommandeVehiculeService infoCommandeVehiculeService;

    public InfoCommandeVehiculeResource(InfoCommandeVehiculeService infoCommandeVehiculeService) {
        this.infoCommandeVehiculeService = infoCommandeVehiculeService;
    }

    /**
     * {@code POST  /info-commande-vehicules} : Create a new infoCommandeVehicule.
     *
     * @param infoCommandeVehiculeDTO the infoCommandeVehiculeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoCommandeVehiculeDTO, or with status {@code 400 (Bad Request)} if the infoCommandeVehicule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/info-commande-vehicules")
    public ResponseEntity<InfoCommandeVehiculeDTO> createInfoCommandeVehicule(@RequestBody InfoCommandeVehiculeDTO infoCommandeVehiculeDTO) throws URISyntaxException {
        log.debug("REST request to save InfoCommandeVehicule : {}", infoCommandeVehiculeDTO);
        if (infoCommandeVehiculeDTO.getId() != null) {
            throw new BadRequestAlertException("A new infoCommandeVehicule cannot already have an ID", ENTITY_NAME, "idexists");
        }

        /*infoCommandeVehiculeDTO.setNumeroCommande(RandomUtil.generateRandomSerialNumericString());*/

        InfoCommandeVehiculeDTO result = infoCommandeVehiculeService.save(infoCommandeVehiculeDTO);
        return ResponseEntity.created(new URI("/api/info-commande-vehicules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /info-commande-vehicules} : Updates an existing infoCommandeVehicule.
     *
     * @param infoCommandeVehiculeDTO the infoCommandeVehiculeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoCommandeVehiculeDTO,
     * or with status {@code 400 (Bad Request)} if the infoCommandeVehiculeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoCommandeVehiculeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/info-commande-vehicules")
    public ResponseEntity<InfoCommandeVehiculeDTO> updateInfoCommandeVehicule(@RequestBody InfoCommandeVehiculeDTO infoCommandeVehiculeDTO) throws URISyntaxException {
        log.debug("REST request to update InfoCommandeVehicule : {}", infoCommandeVehiculeDTO);
        if (infoCommandeVehiculeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InfoCommandeVehiculeDTO result = infoCommandeVehiculeService.save(infoCommandeVehiculeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infoCommandeVehiculeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /info-commande-vehicules} : get all the infoCommandeVehicules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoCommandeVehicules in body.
     */
    @GetMapping("/info-commande-vehicules")
    public ResponseEntity<List<InfoCommandeVehiculeDTO>> getAllInfoCommandeVehicules(Pageable pageable) {
        log.debug("REST request to get a page of InfoCommandeVehicules");
        Page<InfoCommandeVehiculeDTO> page = infoCommandeVehiculeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /info-commande-vehicules/:id} : get the "id" infoCommandeVehicule.
     *
     * @param id the id of the infoCommandeVehiculeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoCommandeVehiculeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/info-commande-vehicules/{id}")
    public ResponseEntity<InfoCommandeVehiculeDTO> getInfoCommandeVehicule(@PathVariable Long id) {
        log.debug("REST request to get InfoCommandeVehicule : {}", id);
        Optional<InfoCommandeVehiculeDTO> infoCommandeVehiculeDTO = infoCommandeVehiculeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(infoCommandeVehiculeDTO);
    }


    /**
     * {@code DELETE  /info-commande-vehicules/:id} : delete the "id" infoCommandeVehicule.
     *
     * @param id the id of the infoCommandeVehiculeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/info-commande-vehicules/{id}")
    public ResponseEntity<Void> deleteInfoCommandeVehicule(@PathVariable Long id) {
        log.debug("REST request to delete InfoCommandeVehicule : {}", id);
        infoCommandeVehiculeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
