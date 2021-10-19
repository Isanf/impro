package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.Vehicule;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.service.PlaqueImmatriculationService;
import com.supernettechnologie.impro.service.dto.*;
import com.supernettechnologie.impro.service.mapper.*;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;

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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.PlaqueImmatriculation}.
 */
@RestController
@RequestMapping("/api")
public class PlaqueImmatriculationResource {

    private final Logger log = LoggerFactory.getLogger(PlaqueImmatriculationResource.class);

    private static final String ENTITY_NAME = "plaqueImmatriculation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaqueImmatriculationService plaqueImmatriculationService;

    public PlaqueImmatriculationResource(PlaqueImmatriculationService plaqueImmatriculationService) {
        this.plaqueImmatriculationService = plaqueImmatriculationService;
    }

    /**
     * {@code POST  /plaque-immatriculations} : Create a new plaqueImmatriculation.
     *
     * @param plaqueImmatriculationDTO the plaqueImmatriculationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plaqueImmatriculationDTO, or with status {@code 400 (Bad Request)} if the plaqueImmatriculation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plaque-immatriculations")
    public ResponseEntity<PlaqueImmatriculationDTO> createPlaqueImmatriculation(@RequestBody PlaqueImmatriculationDTO plaqueImmatriculationDTO) throws URISyntaxException {
        log.debug("REST request to save PlaqueImmatriculation : {}", plaqueImmatriculationDTO);
        if (plaqueImmatriculationDTO.getId() != null) {
            throw new BadRequestAlertException("A new plaqueImmatriculation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaqueImmatriculationDTO result = plaqueImmatriculationService.save(plaqueImmatriculationDTO);
        return ResponseEntity.created(new URI("/api/plaque-immatriculations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plaque-immatriculations} : Updates an existing plaqueImmatriculation.
     *
     * @param plaqueImmatriculationDTO the plaqueImmatriculationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaqueImmatriculationDTO,
     * or with status {@code 400 (Bad Request)} if the plaqueImmatriculationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plaqueImmatriculationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plaque-immatriculations")
    public ResponseEntity<PlaqueImmatriculationDTO> updatePlaqueImmatriculation(@RequestBody PlaqueImmatriculationDTO plaqueImmatriculationDTO) throws URISyntaxException {
        log.debug("REST request to update PlaqueImmatriculation : {}", plaqueImmatriculationDTO);
        if (plaqueImmatriculationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        PlaqueImmatriculationDTO result = plaqueImmatriculationService.save(plaqueImmatriculationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaqueImmatriculationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plaque-immatriculations} : get all the plaqueImmatriculations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plaqueImmatriculations in body.
     */
    @GetMapping("/plaque-immatriculations")
    public ResponseEntity<List<PlaqueImmatriculationDTO>> getAllPlaqueImmatriculations(Pageable pageable) {
        log.debug("REST request to get a page of PlaqueImmatriculations");
        Page<PlaqueImmatriculationDTO> page = plaqueImmatriculationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plaque-immatriculations/:id} : get the "id" plaqueImmatriculation.
     *
     * @param id the id of the plaqueImmatriculationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plaqueImmatriculationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plaque-immatriculations/{id}")
    public ResponseEntity<PlaqueImmatriculationDTO> getPlaqueImmatriculation(@PathVariable Long id) {
        log.debug("REST request to get PlaqueImmatriculation : {}", id);
        Optional<PlaqueImmatriculationDTO> plaqueImmatriculationDTO = plaqueImmatriculationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plaqueImmatriculationDTO);
    }

    @GetMapping("/plaque-immatriculations/by")
    public ResponseEntity<PlaqueImmatriculationDTO> getPlaqueImmatriculationByQr(@RequestParam("code") String qr) {
        log.debug("REST request to get PlaqueImmatriculation : {}", qr);
        PlaqueImmatriculationDTO plaqueImmatriculationDTO = plaqueImmatriculationService.findOneByQr(qr).get(0);
        return ResponseEntity.ok().body(plaqueImmatriculationDTO);
    }

    @GetMapping("/plaque-immatriculations/by/{id}")
    public ResponseEntity<PlaqueImmatriculationDTO> getImmatriculationByCerti(@PathVariable Long id) {
        log.debug("REST request to get PlaqueImmatriculation : {}", id);
        Optional<PlaqueImmatriculationDTO> plaqueImmatriculationDTO = plaqueImmatriculationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plaqueImmatriculationDTO);
    }

    /**
     * {@code DELETE  /plaque-immatriculations/:id} : delete the "id" plaqueImmatriculation.
     *
     * @param id the id of the plaqueImmatriculationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plaque-immatriculations/{id}")
    public ResponseEntity<Void> deletePlaqueImmatriculation(@PathVariable Long id) {
        log.debug("REST request to delete PlaqueImmatriculation : {}", id);
        plaqueImmatriculationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
