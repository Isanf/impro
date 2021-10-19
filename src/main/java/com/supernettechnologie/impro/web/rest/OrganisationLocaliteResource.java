package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.OrganisationLocaliteService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.OrganisationLocaliteDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.OrganisationLocalite}.
 */
@RestController
@RequestMapping("/api")
public class OrganisationLocaliteResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationLocaliteResource.class);

    private static final String ENTITY_NAME = "organisationLocalite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganisationLocaliteService organisationLocaliteService;

    public OrganisationLocaliteResource(OrganisationLocaliteService organisationLocaliteService) {
        this.organisationLocaliteService = organisationLocaliteService;
    }

    /**
     * {@code POST  /organisation-localites} : Create a new organisationLocalite.
     *
     * @param organisationLocaliteDTO the organisationLocaliteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organisationLocaliteDTO, or with status {@code 400 (Bad Request)} if the organisationLocalite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organisation-localites")
    public ResponseEntity<OrganisationLocaliteDTO> createOrganisationLocalite(@RequestBody OrganisationLocaliteDTO organisationLocaliteDTO) throws URISyntaxException {
        log.debug("REST request to save OrganisationLocalite : {}", organisationLocaliteDTO);
        if (organisationLocaliteDTO.getId() != null) {
            throw new BadRequestAlertException("A new organisationLocalite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganisationLocaliteDTO result = organisationLocaliteService.save(organisationLocaliteDTO);
        return ResponseEntity.created(new URI("/api/organisation-localites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organisation-localites} : Updates an existing organisationLocalite.
     *
     * @param organisationLocaliteDTO the organisationLocaliteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisationLocaliteDTO,
     * or with status {@code 400 (Bad Request)} if the organisationLocaliteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organisationLocaliteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organisation-localites")
    public ResponseEntity<OrganisationLocaliteDTO> updateOrganisationLocalite(@RequestBody OrganisationLocaliteDTO organisationLocaliteDTO) throws URISyntaxException {
        log.debug("REST request to update OrganisationLocalite : {}", organisationLocaliteDTO);
        if (organisationLocaliteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrganisationLocaliteDTO result = organisationLocaliteService.save(organisationLocaliteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organisationLocaliteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /organisation-localites} : get all the organisationLocalites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organisationLocalites in body.
     */
    @GetMapping("/organisation-localites")
    public ResponseEntity<List<OrganisationLocaliteDTO>> getAllOrganisationLocalites(Pageable pageable) {
        log.debug("REST request to get a page of OrganisationLocalites");
        Page<OrganisationLocaliteDTO> page = organisationLocaliteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organisation-localites/:id} : get the "id" organisationLocalite.
     *
     * @param id the id of the organisationLocaliteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organisationLocaliteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organisation-localites/{id}")
    public ResponseEntity<OrganisationLocaliteDTO> getOrganisationLocalite(@PathVariable Long id) {
        log.debug("REST request to get OrganisationLocalite : {}", id);
        Optional<OrganisationLocaliteDTO> organisationLocaliteDTO = organisationLocaliteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organisationLocaliteDTO);
    }

    /**
     * {@code DELETE  /organisation-localites/:id} : delete the "id" organisationLocalite.
     *
     * @param id the id of the organisationLocaliteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organisation-localites/{id}")
    public ResponseEntity<Void> deleteOrganisationLocalite(@PathVariable Long id) {
        log.debug("REST request to delete OrganisationLocalite : {}", id);
        organisationLocaliteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
