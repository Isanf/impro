package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.CategorieOrganisationService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.CategorieOrganisationDTO;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.CategorieOrganisation}.
 */
@RestController
@RequestMapping("/api")
public class CategorieOrganisationResource {

    private final Logger log = LoggerFactory.getLogger(CategorieOrganisationResource.class);

    private static final String ENTITY_NAME = "categorieOrganisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieOrganisationService categorieOrganisationService;

    public CategorieOrganisationResource(CategorieOrganisationService categorieOrganisationService) {
        this.categorieOrganisationService = categorieOrganisationService;
    }

    /**
     * {@code POST  /categorie-organisations} : Create a new categorieOrganisation.
     *
     * @param categorieOrganisationDTO the categorieOrganisationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieOrganisationDTO, or with status {@code 400 (Bad Request)} if the categorieOrganisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorie-organisations")
    public ResponseEntity<CategorieOrganisationDTO> createCategorieOrganisation(@Valid @RequestBody CategorieOrganisationDTO categorieOrganisationDTO) throws URISyntaxException {
        log.debug("REST request to save CategorieOrganisation : {}", categorieOrganisationDTO);
        if (categorieOrganisationDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorieOrganisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieOrganisationDTO result = categorieOrganisationService.save(categorieOrganisationDTO);
        return ResponseEntity.created(new URI("/api/categorie-organisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorie-organisations} : Updates an existing categorieOrganisation.
     *
     * @param categorieOrganisationDTO the categorieOrganisationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieOrganisationDTO,
     * or with status {@code 400 (Bad Request)} if the categorieOrganisationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieOrganisationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorie-organisations")
    public ResponseEntity<CategorieOrganisationDTO> updateCategorieOrganisation(@Valid @RequestBody CategorieOrganisationDTO categorieOrganisationDTO) throws URISyntaxException {
        log.debug("REST request to update CategorieOrganisation : {}", categorieOrganisationDTO);
        if (categorieOrganisationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategorieOrganisationDTO result = categorieOrganisationService.save(categorieOrganisationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorieOrganisationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categorie-organisations} : get all the categorieOrganisations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorieOrganisations in body.
     */
    @GetMapping("/categorie-organisations")
    public ResponseEntity<List<CategorieOrganisationDTO>> getAllCategorieOrganisations(Pageable pageable) {
        log.debug("REST request to get a page of CategorieOrganisations");
        Page<CategorieOrganisationDTO> page = categorieOrganisationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categorie-organisations/:id} : get the "id" categorieOrganisation.
     *
     * @param id the id of the categorieOrganisationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieOrganisationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorie-organisations/{id}")
    public ResponseEntity<CategorieOrganisationDTO> getCategorieOrganisation(@PathVariable Long id) {
        log.debug("REST request to get CategorieOrganisation : {}", id);
        Optional<CategorieOrganisationDTO> categorieOrganisationDTO = categorieOrganisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorieOrganisationDTO);
    }

    /**
     * {@code DELETE  /categorie-organisations/:id} : delete the "id" categorieOrganisation.
     *
     * @param id the id of the categorieOrganisationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorie-organisations/{id}")
    public ResponseEntity<Void> deleteCategorieOrganisation(@PathVariable Long id) {
        log.debug("REST request to delete CategorieOrganisation : {}", id);
        categorieOrganisationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
