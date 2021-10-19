package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.VehiculeTraversantService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.VehiculeTraversantDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.VehiculeTraversant}.
 */
@RestController
@RequestMapping("/api")
public class VehiculeTraversantResource {

    private final Logger log = LoggerFactory.getLogger(VehiculeTraversantResource.class);

    private static final String ENTITY_NAME = "vehiculeTraversant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehiculeTraversantService vehiculeTraversantService;

    public VehiculeTraversantResource(VehiculeTraversantService vehiculeTraversantService) {
        this.vehiculeTraversantService = vehiculeTraversantService;
    }

    /**
     * {@code POST  /vehicule-traversants} : Create a new vehiculeTraversant.
     *
     * @param vehiculeTraversantDTO the vehiculeTraversantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehiculeTraversantDTO, or with status {@code 400 (Bad Request)} if the vehiculeTraversant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicule-traversants")
    public ResponseEntity<VehiculeTraversantDTO> createVehiculeTraversant(@RequestBody VehiculeTraversantDTO vehiculeTraversantDTO) throws URISyntaxException {
        log.debug("REST request to save VehiculeTraversant : {}", vehiculeTraversantDTO);
        if (vehiculeTraversantDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehiculeTraversant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehiculeTraversantDTO result = vehiculeTraversantService.save(vehiculeTraversantDTO);
        return ResponseEntity.created(new URI("/api/vehicule-traversants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicule-traversants} : Updates an existing vehiculeTraversant.
     *
     * @param vehiculeTraversantDTO the vehiculeTraversantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehiculeTraversantDTO,
     * or with status {@code 400 (Bad Request)} if the vehiculeTraversantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehiculeTraversantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicule-traversants")
    public ResponseEntity<VehiculeTraversantDTO> updateVehiculeTraversant(@RequestBody VehiculeTraversantDTO vehiculeTraversantDTO) throws URISyntaxException {
        log.debug("REST request to update VehiculeTraversant : {}", vehiculeTraversantDTO);
        if (vehiculeTraversantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehiculeTraversantDTO result = vehiculeTraversantService.save(vehiculeTraversantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehiculeTraversantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vehicule-traversants} : get all the vehiculeTraversants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehiculeTraversants in body.
     */
    @GetMapping("/vehicule-traversants")
    public ResponseEntity<List<VehiculeTraversantDTO>> getAllVehiculeTraversants(Pageable pageable) {
        log.debug("REST request to get a page of VehiculeTraversants");
        Page<VehiculeTraversantDTO> page = vehiculeTraversantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicule-traversants/:id} : get the "id" vehiculeTraversant.
     *
     * @param id the id of the vehiculeTraversantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehiculeTraversantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicule-traversants/{id}")
    public ResponseEntity<VehiculeTraversantDTO> getVehiculeTraversant(@PathVariable Long id) {
        log.debug("REST request to get VehiculeTraversant : {}", id);
        Optional<VehiculeTraversantDTO> vehiculeTraversantDTO = vehiculeTraversantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehiculeTraversantDTO);
    }

    /**
     * {@code DELETE  /vehicule-traversants/:id} : delete the "id" vehiculeTraversant.
     *
     * @param id the id of the vehiculeTraversantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicule-traversants/{id}")
    public ResponseEntity<Void> deleteVehiculeTraversant(@PathVariable Long id) {
        log.debug("REST request to delete VehiculeTraversant : {}", id);
        vehiculeTraversantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
