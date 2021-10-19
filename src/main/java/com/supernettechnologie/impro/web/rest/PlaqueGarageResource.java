package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.CustomResponse;
import com.supernettechnologie.impro.service.PlaqueGarageService;
import com.supernettechnologie.impro.service.dto.CarteWDTO;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.PlaqueGarageDTO;
import com.supernettechnologie.impro.service.dto.PlaqueGarageCriteria;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.PlaqueGarage}.
 */
@RestController
@RequestMapping("/api")
public class PlaqueGarageResource {

    private final Logger log = LoggerFactory.getLogger(PlaqueGarageResource.class);

    private static final String ENTITY_NAME = "plaqueGarage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaqueGarageService plaqueGarageService;


    public PlaqueGarageResource(PlaqueGarageService plaqueGarageService) {
        this.plaqueGarageService = plaqueGarageService;

    }

    /**
     * {@code POST  /plaque-garages} : Create a new plaqueGarage.
     *
     * @param plaqueGarageDTO the plaqueGarageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plaqueGarageDTO, or with status {@code 400 (Bad Request)} if the plaqueGarage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plaque-garages")
    public ResponseEntity<PlaqueGarageDTO> createPlaqueGarage(@RequestBody List<PlaqueGarageDTO> plaqueGarageDTO) throws URISyntaxException {

        PlaqueGarageDTO result = plaqueGarageService.save(plaqueGarageDTO);
        return ResponseEntity.created(new URI("/api/plaque-garages/"))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code PUT  /plaque-garages} : Updates an existing plaqueGarage.
     *
     * @param plaqueGarageDTO the plaqueGarageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaqueGarageDTO,
     * or with status {@code 400 (Bad Request)} if the plaqueGarageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plaqueGarageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plaque-garages")
    public ResponseEntity<PlaqueGarageDTO> updatePlaqueGarage(@RequestBody List<PlaqueGarageDTO> plaqueGarageDTO) throws URISyntaxException {
        log.debug("REST request to update PlaqueGarage : {}", plaqueGarageDTO);

        PlaqueGarageDTO result = plaqueGarageService.save(plaqueGarageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code GET  /plaque-garages} : get all the plaqueGarages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plaqueGarages in body.
     */
    @GetMapping("/plaque-garages")
    public ResponseEntity<List<PlaqueGarageDTO>> getAllPlaqueGarages(Pageable pageable) {
        log.debug("REST request to get PlaqueGarages by criteria: {}");
        Page<PlaqueGarageDTO> page = plaqueGarageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/plaque-garages/{id}/garage")
    public ResponseEntity<List<PlaqueGarageDTO>> getAllPlaqueGaragesBy(@PathVariable Long id, Pageable pageable ) {
        log.debug("REST request to get PlaqueGarages by criteria: {}");

        Page<PlaqueGarageDTO> page = plaqueGarageService.findAllByGaragiste(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/plaque-garages/infos")
    public ResponseEntity<CarteWDTO> getGaragesByQr(@RequestParam("data") String id ) {
        log.debug("REST request to get PlaqueGarages by criteria: {}");

        Optional<CarteWDTO> carteWDTO = plaqueGarageService.findGaragisteByQr(id);
        return ResponseUtil.wrapOrNotFound(carteWDTO);
    }

    /**
     * {@code GET  /plaque-garages/:id} : get the "id" plaqueGarage.
     *
     * @param id the id of the plaqueGarageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plaqueGarageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plaque-garages/{id}")
    public ResponseEntity<PlaqueGarageDTO> getPlaqueGarage(@PathVariable Long id) {
        log.debug("REST request to get PlaqueGarage : {}", id);
        Optional<PlaqueGarageDTO> plaqueGarageDTO = plaqueGarageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plaqueGarageDTO);
    }

    /**
     * {@code DELETE  /plaque-garages/:id} : delete the "id" plaqueGarage.
     *
     * @param id the id of the plaqueGarageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plaque-garages/{id}")
    public ResponseEntity<Void> deletePlaqueGarage(@PathVariable Long id) {
        log.debug("REST request to delete PlaqueGarage : {}", id);

        plaqueGarageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
