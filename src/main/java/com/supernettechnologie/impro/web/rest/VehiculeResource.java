package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.Vehicule;
import com.supernettechnologie.impro.service.VehiculeService;
import com.supernettechnologie.impro.service.mapper.VehiculeMapper;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.VehiculeDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.Vehicule}.
 */
@RestController
@RequestMapping("/api")
public class VehiculeResource {

    private final Logger log = LoggerFactory.getLogger(VehiculeResource.class);

    private static final String ENTITY_NAME = "vehicule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehiculeService vehiculeService;


    private final VehiculeMapper vehiculeMapper;

    public VehiculeResource(VehiculeService vehiculeService, VehiculeMapper vehiculeMapper) {
        this.vehiculeService = vehiculeService;
        this.vehiculeMapper = vehiculeMapper;
    }

    /**
     * {@code POST  /vehicules} : Create a new vehicule.
     *
     * @param vehiculeDTO the vehiculeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehiculeDTO, or with status {@code 400 (Bad Request)} if the vehicule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicules")
    public ResponseEntity<VehiculeDTO> createVehicule(@RequestBody VehiculeDTO vehiculeDTO) throws URISyntaxException {
        log.debug("REST request to save Vehicule : {}", vehiculeDTO);
        if (vehiculeDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehiculeDTO result = vehiculeService.save(vehiculeDTO);
        return ResponseEntity.created(new URI("/api/vehicules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code PUT  /vehicules} : Updates an existing vehicule.
     *
     * @param vehiculeDTO the vehiculeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehiculeDTO,
     * or with status {@code 400 (Bad Request)} if the vehiculeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehiculeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicules")
    public ResponseEntity<VehiculeDTO> updateVehicule(@RequestBody VehiculeDTO vehiculeDTO) throws URISyntaxException {
        log.debug("REST request to update Vehicule : {}", vehiculeDTO);
        if (vehiculeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehiculeDTO result = vehiculeService.save(vehiculeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code GET  /vehicules} : get all the vehicules.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicules in body.
     */
    @GetMapping("/vehicules")
    public ResponseEntity<List<VehiculeDTO>> getAllVehicules(Pageable pageable) {
        log.debug("REST request to get a page of Vehicules");
        Page<VehiculeDTO> page = vehiculeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/vehicules/all")
    public ResponseEntity<List<VehiculeDTO>> getAllVehiculesDelivAndNotSell(Pageable pageable) {
        log.debug("REST request to get a page of Vehicules");
        Page<VehiculeDTO> page = vehiculeService.findAllDelivAndNotSell(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/vehiculesForRevendeur")
    public ResponseEntity<List<VehiculeDTO>> getAllForRevendeur() {
        log.debug("REST request to get a page of Vehicules");
        List<VehiculeDTO> vehiculeDTOS = vehiculeService.findAllForRevendeur();
        return ResponseEntity.ok().body(vehiculeDTOS);
    }

    @GetMapping("/vehiculesForConcessionnaire")
    public ResponseEntity<List<VehiculeDTO>> getAllForConcessionnaire() {
        log.debug("REST request to get a page of Vehicules");
        List<VehiculeDTO> vehiculeDTOS = vehiculeService.findAllForConcessionnaire();
        return ResponseEntity.ok().body(vehiculeDTOS);
    }

    @GetMapping("/vehicules/allnotdelivnotsell")
    public ResponseEntity<List<VehiculeDTO>> getAllVehiculesNotDelivOrNotSell(Pageable pageable) {
        log.debug("REST request to get a page of Vehicules");
        Page<VehiculeDTO> page = vehiculeService.findAllNotDelivAndNotSell(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/vehicules/allDeliveryAndNotSell")
    public ResponseEntity<List<VehiculeDTO>> getAllVehiculesDeliveryAndNotSell(Pageable pageable) {
        log.debug("REST request to get a page of Vehicules");
        Page<VehiculeDTO> page = vehiculeService.findAllDeliveryAndNotSell(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/vehiculesRevendeurs")
    public ResponseEntity<List<VehiculeDTO>> getAllVehiculesRevendeurs() {
        log.debug("REST request to get a page of Vehicules");
        List<VehiculeDTO> vehiculeDTOS = vehiculeService.findAllRevendeurs();
        return ResponseEntity.ok().body(vehiculeDTOS);
    }

    @GetMapping("/vehiculesConcessionnaire")
    public ResponseEntity<List<VehiculeDTO>> getAllVehiculesConcessionnaire() {
        log.debug("REST request to get a page of Vehicules");
        List<VehiculeDTO> vehiculeDTOS = vehiculeService.findAllConcessionnaire();
        return ResponseEntity.ok().body(vehiculeDTOS);
    }

    @GetMapping("/vehiculesVehiculeRevendeur")
    public ResponseEntity<List<VehiculeDTO>> getVehiculeRevendeur() {
        log.debug("REST request to get a page of Vehicules");
        List<VehiculeDTO> vehiculeDTOS = vehiculeService.findVehiculeRevendeur();
        return ResponseEntity.ok().body(vehiculeDTOS);
    }

    @GetMapping("/vehiculeshorsstock")
    public ResponseEntity<List<VehiculeDTO>> getAllVehiculeshorsstock() {
        log.debug("REST request to get a page of Vehicules");
        List<VehiculeDTO> vehiculeDTOS = vehiculeService.findAllHorsStock();
        return ResponseEntity.ok().body(vehiculeDTOS);
    }

    @GetMapping("/vehicules/marque/{id}")
    public ResponseEntity<List<VehiculeDTO>> getAllVehiculesByMark(@PathVariable Long id) {
        log.debug("REST request to get a page of Vehicules");
        List<VehiculeDTO> page = vehiculeService.findAllMarque(id);
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /vehicules/:id} : get the "id" vehicule.
     *
     * @param id the id of the vehiculeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehiculeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicules/{id}")
    public ResponseEntity<VehiculeDTO> getVehicule(@PathVariable Long id) {
        log.debug("REST request to get Vehicule : {}", id);
        Optional<VehiculeDTO> vehiculeDTO = vehiculeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehiculeDTO);
    }

    @GetMapping("/vehicules-chassis/{chassis}")
    public VehiculeDTO  getOneByChssis(@PathVariable String chassis) {
        log.debug("REST request to get DocIdentificationPP : {}", chassis);
        Vehicule vehicule = vehiculeService.findOneByChssis(chassis);
        log.debug("*****************************************//////////////**************** : {}", vehicule.getModel());
        return vehiculeMapper.toDto(vehicule);
    }

    /**
     * {@code DELETE  /vehicules/:id} : delete the "id" vehicule.
     *
     * @param id the id of the vehiculeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicules/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        log.debug("REST request to delete Vehicule : {}", id);
        vehiculeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
