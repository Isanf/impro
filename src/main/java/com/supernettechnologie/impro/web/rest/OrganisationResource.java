package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.OrganisationService;
import com.supernettechnologie.impro.service.dto.OrganisationDTO;
import com.supernettechnologie.impro.service.mapper.OrganisationMapper;
import com.supernettechnologie.impro.service.mapper.VehiculeMapper;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.Organisation}.
 */
@RestController
@RequestMapping("/api")
public class OrganisationResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationResource.class);
    @Autowired
    private OrganisationMapper organisationMapper;

    private static final String ENTITY_NAME = "organisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganisationService organisationService;

    public OrganisationResource(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    /**
     * {@code POST  /organisations} : Create a new organisation.
     *
     * @param organisationDTO the organisationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organisationDTO, or with status {@code 400 (Bad Request)} if the organisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organisations")
    public ResponseEntity<OrganisationDTO> createOrganisation(@Valid @RequestBody OrganisationDTO organisationDTO) throws URISyntaxException {
        log.debug("REST request to save Organisation : {}", organisationDTO);
        if (organisationDTO.getId() != null) {
            throw new BadRequestAlertException("A new organisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        organisationDTO.setNumeroOrdre(1);
        OrganisationDTO result = organisationService.save(organisationDTO);
        return ResponseEntity.created(new URI("/api/organisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organisations} : Updates an existing organisation.
     *
     * @param organisationDTO the organisationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisationDTO,
     * or with status {@code 400 (Bad Request)} if the organisationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organisationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organisations")
    public ResponseEntity<OrganisationDTO> updateOrganisation(@Valid @RequestBody OrganisationDTO organisationDTO) throws URISyntaxException {
        log.debug("REST request to update Organisation : {}", organisationDTO);
        if (organisationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrganisationDTO result = organisationService.save(organisationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organisationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /organisations} : get all the organisations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organisations in body.
     */
    @GetMapping("/organisations")
    public ResponseEntity<List<OrganisationDTO>> getAllOrganisations(Pageable pageable) {
        log.debug("REST request to get a page of Organisations");
        Page<OrganisationDTO> page = organisationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/allorganisations")
    public ResponseEntity<List<OrganisationDTO>> getAllConcess() {
        log.debug("REST request to get a page of Ventes");
        List<OrganisationDTO> organisationDTOS = organisationService.findAllConcessionnaires();
        return ResponseEntity.ok().body(organisationDTOS);
    }

    @GetMapping("/organisations/concessionaire")
    public ResponseEntity<List<OrganisationDTO>> getAllConcessionnaires() {
        log.debug("**** Avoir les Concessionnaires en collaboration");
        List<OrganisationDTO> organisationDTOS = organisationService.findAllConcessionnaires();
        return ResponseEntity.ok().body(organisationDTOS);
    }

    @GetMapping("/organisations/concessionnairetotal")
    public ResponseEntity<List<OrganisationDTO>> getAllConcessionnairestotal() {
        log.debug("**** Avoir les Concessionnaires en collaboration");
        List<OrganisationDTO> organisationDTOS = organisationService.findAllConcessionnairestotal();
        return ResponseEntity.ok().body(organisationDTOS);
    }


    @GetMapping("/organisations/revendeurs")
    public ResponseEntity<List<OrganisationDTO>> getAllOrganisationsRevendeurs() {
        log.debug("REST request to get a page of Organisations");
        List<OrganisationDTO> page = organisationService.findAllRevendeurs();
        return ResponseEntity.ok().body(page);
    }


    @GetMapping("/organisations/revendeurcollaborant")
    public ResponseEntity<List<OrganisationDTO>> getAllRevendeursCollaborant() {
        log.debug("REST request to get a page of Organisations");
        List<OrganisationDTO> page = organisationService.findAllRevendeursCollaborant();
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/organisations/revendeurcollaborantWith")
    public ResponseEntity<List<OrganisationDTO>> getAllRevendeursCollaborantWith(
        @RequestParam(value = "idOrg") Long idOrg) {
        log.debug("REST request to get a page of Organisations");
        List<OrganisationDTO> organisationDTOList = organisationService.findAllRevendeursCollaborantWith(idOrg);
        return ResponseEntity.ok().body(organisationDTOList);
    }

    @GetMapping("/organisations/myorganisation")
    public ResponseEntity<List<OrganisationDTO>> getMyorganisation(Long id, Pageable pageable) {
        log.debug("REST request to get a page of Organisations");
        Page<OrganisationDTO> page = organisationService.findMyorganisation(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/organisations/allorganisationsfils")
    public ResponseEntity<List<OrganisationDTO>> getAllOrganisationsfils(Long id, Pageable pageable) {
        log.debug("REST request to get a page of Organisations");
        Page<OrganisationDTO> page = organisationService.findAllOrganisationsfils(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/organisations/findByUser")
    public OrganisationDTO getByUser(@RequestParam(value = "login") String login) {
        return organisationMapper.toDto(organisationService.findByUser(login));
    }

    /**
     * {@code GET  /organisations/:id} : get the "id" organisation.
     *
     * @param id the id of the organisationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organisationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organisations/{id}")
    public ResponseEntity<OrganisationDTO> getOrganisation(@PathVariable Long id) {
        log.debug("REST request to get Organisation : {}", id);
        Optional<OrganisationDTO> organisationDTO = organisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organisationDTO);
    }

    /**
     * {@code DELETE  /organisations/:id} : delete the "id" organisation.
     *
     * @param id the id of the organisationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organisations/{id}")
    public ResponseEntity<Void> deleteOrganisation(@PathVariable Long id) {
        log.debug("REST request to delete Organisation : {}", id);
        organisationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
