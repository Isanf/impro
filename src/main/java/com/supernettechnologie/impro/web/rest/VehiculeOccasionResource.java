package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.domain.VehiculeOccasion;
import com.supernettechnologie.impro.repository.PersonnePhysiqueRepository;
import com.supernettechnologie.impro.repository.VehiculeOccasionRepository;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.VehiculeTraversantDTO;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.VehiculeOccasion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VehiculeOccasionResource {

    private final Logger log = LoggerFactory.getLogger(VehiculeOccasionResource.class);

    private static final String ENTITY_NAME = "vehiculeOccasion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehiculeOccasionRepository vehiculeOccasionRepository;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    public VehiculeOccasionResource(VehiculeOccasionRepository vehiculeOccasionRepository) {
        this.vehiculeOccasionRepository = vehiculeOccasionRepository;
    }

    /**
     * {@code POST  /vehicule-occasions} : Create a new vehiculeOccasion.
     *
     * @param vehiculeOccasion the vehiculeOccasion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehiculeOccasion, or with status {@code 400 (Bad Request)} if the vehiculeOccasion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicule-occasions")
    public ResponseEntity<VehiculeOccasion> createVehiculeOccasion(@RequestBody VehiculeOccasion vehiculeOccasion) throws URISyntaxException {
        log.debug("REST request to save VehiculeOccasion : {}", vehiculeOccasion);
        if (vehiculeOccasion.getId() != null) {
            throw new BadRequestAlertException("A new vehiculeOccasion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        vehiculeOccasion.setOrganisation(personnePhysique.get().getOrganisation());
        VehiculeOccasion result = vehiculeOccasionRepository.save(vehiculeOccasion);
        vehiculeOccasion.setCreatedAt(ZonedDateTime.now());
        return ResponseEntity.created(new URI("/api/vehicule-occasions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code PUT  /vehicule-occasions} : Updates an existing vehiculeOccasion.
     *
     * @param vehiculeOccasion the vehiculeOccasion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehiculeOccasion,
     * or with status {@code 400 (Bad Request)} if the vehiculeOccasion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehiculeOccasion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicule-occasions")
    public ResponseEntity<VehiculeOccasion> updateVehiculeOccasion(@RequestBody VehiculeOccasion vehiculeOccasion) throws URISyntaxException {
        log.debug("REST request to update VehiculeOccasion : {}", vehiculeOccasion);
        if (vehiculeOccasion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        vehiculeOccasion.setOrganisation(personnePhysique.get().getOrganisation());
        vehiculeOccasion.setCreatedAt(ZonedDateTime.now());
        VehiculeOccasion result = vehiculeOccasionRepository.save(vehiculeOccasion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code GET  /vehicule-occasions} : get all the vehiculeOccasions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehiculeOccasions in body.
     */
    @GetMapping("/vehicule-occasions")
    public ResponseEntity<List<VehiculeOccasion>> getAllVehiculeOccasions(Pageable pageable) {
        log.debug("REST request to get a page of VehiculeOccasions");
        Page<VehiculeOccasion> page = vehiculeOccasionRepository.findAll(pageable);
        for (int i = 0 ; i <= page.getTotalPages(); i++){
            log.debug("************************************************************VehiculeOcasionF1");
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/vehicule-occasions-rec")
    public List<VehiculeOccasion> findAll() {
        log.debug("Request to get all VehiculeTraversants");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<VehiculeOccasion> vehiculeOccasions = vehiculeOccasionRepository.findByOrganisationId(
            personnePhysique.get().getOrganisation().getId());
        log.debug("************************************************************VehiculeOcasionF2");
        return vehiculeOccasions;
    }

    /**
     * {@code GET  /vehicule-occasions/:id} : get the "id" vehiculeOccasion.
     *
     * @param id the id of the vehiculeOccasion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehiculeOccasion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicule-occasions/{id}")
    public ResponseEntity<VehiculeOccasion> getVehiculeOccasion(@PathVariable Long id) {
        log.debug("REST request to get VehiculeOccasion : {}", id);
        Optional<VehiculeOccasion> vehiculeOccasion = vehiculeOccasionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vehiculeOccasion);
    }

    /**
     * {@code DELETE  /vehicule-occasions/:id} : delete the "id" vehiculeOccasion.
     *
     * @param id the id of the vehiculeOccasion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicule-occasions/{id}")
    public ResponseEntity<Void> deleteVehiculeOccasion(@PathVariable Long id) {
        log.debug("REST request to delete VehiculeOccasion : {}", id);

        vehiculeOccasionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
