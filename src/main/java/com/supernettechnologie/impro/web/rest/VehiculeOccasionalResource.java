package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.VehiculeOccasionalService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.VehiculeOccasionalDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.VehiculeOccasional}.
 */
@RestController
@RequestMapping("/api")
public class VehiculeOccasionalResource {

    private final Logger log = LoggerFactory.getLogger(VehiculeOccasionalResource.class);

    private static final String ENTITY_NAME = "vehiculeOccasional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehiculeOccasionalService vehiculeOccasionalService;

    public VehiculeOccasionalResource(VehiculeOccasionalService vehiculeOccasionalService) {
        this.vehiculeOccasionalService = vehiculeOccasionalService;
    }

    /**
     * {@code POST  /vehicule-occasionals} : Create a new vehiculeOccasional.
     *
     * @param vehiculeOccasionalDTO the vehiculeOccasionalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehiculeOccasionalDTO, or with status {@code 400 (Bad Request)} if the vehiculeOccasional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicule-occasionals")
    public ResponseEntity<VehiculeOccasionalDTO> createVehiculeOccasional(@RequestBody VehiculeOccasionalDTO vehiculeOccasionalDTO) throws URISyntaxException {
        log.debug("REST request to save VehiculeOccasional : {}", vehiculeOccasionalDTO);
        if (vehiculeOccasionalDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehiculeOccasional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehiculeOccasionalDTO result = vehiculeOccasionalService.save(vehiculeOccasionalDTO);
        return ResponseEntity.created(new URI("/api/vehicule-occasionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicule-occasionals} : Updates an existing vehiculeOccasional.
     *
     * @param vehiculeOccasionalDTO the vehiculeOccasionalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehiculeOccasionalDTO,
     * or with status {@code 400 (Bad Request)} if the vehiculeOccasionalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehiculeOccasionalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicule-occasionals")
    public ResponseEntity<VehiculeOccasionalDTO> updateVehiculeOccasional(@RequestBody VehiculeOccasionalDTO vehiculeOccasionalDTO) throws URISyntaxException {
        log.debug("REST request to update VehiculeOccasional : {}", vehiculeOccasionalDTO);
        if (vehiculeOccasionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehiculeOccasionalDTO result = vehiculeOccasionalService.save(vehiculeOccasionalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehiculeOccasionalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vehicule-occasionals} : get all the vehiculeOccasionals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehiculeOccasionals in body.
     */
    @GetMapping("/vehicule-occasionals")
    public ResponseEntity<List<VehiculeOccasionalDTO>> getAllVehiculeOccasionals(Pageable pageable) {
        log.debug("REST request to get a page of VehiculeOccasionals");
        Page<VehiculeOccasionalDTO> page = vehiculeOccasionalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicule-occasionals/:id} : get the "id" vehiculeOccasional.
     *
     * @param id the id of the vehiculeOccasionalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehiculeOccasionalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicule-occasionals/{id}")
    public ResponseEntity<VehiculeOccasionalDTO> getVehiculeOccasional(@PathVariable Long id) {
        log.debug("REST request to get VehiculeOccasional : {}", id);
        Optional<VehiculeOccasionalDTO> vehiculeOccasionalDTO = vehiculeOccasionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehiculeOccasionalDTO);
    }

    /**
     * {@code DELETE  /vehicule-occasionals/:id} : delete the "id" vehiculeOccasional.
     *
     * @param id the id of the vehiculeOccasionalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicule-occasionals/{id}")
    public ResponseEntity<Void> deleteVehiculeOccasional(@PathVariable Long id) {
        log.debug("REST request to delete VehiculeOccasional : {}", id);
        vehiculeOccasionalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
