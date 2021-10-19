package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.TypeOrganisationService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.TypeOrganisationDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.TypeOrganisation}.
 */
@RestController
@RequestMapping("/api")
public class TypeOrganisationResource {

    private final Logger log = LoggerFactory.getLogger(TypeOrganisationResource.class);

    private static final String ENTITY_NAME = "typeOrganisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeOrganisationService typeOrganisationService;

    public TypeOrganisationResource(TypeOrganisationService typeOrganisationService) {
        this.typeOrganisationService = typeOrganisationService;
    }

    /**
     * {@code POST  /type-organisations} : Create a new typeOrganisation.
     *
     * @param typeOrganisationDTO the typeOrganisationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeOrganisationDTO, or with status {@code 400 (Bad Request)} if the typeOrganisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-organisations")
    public ResponseEntity<TypeOrganisationDTO> createTypeOrganisation(@Valid @RequestBody TypeOrganisationDTO typeOrganisationDTO) throws URISyntaxException {
        log.debug("REST request to save TypeOrganisation : {}", typeOrganisationDTO);
        if (typeOrganisationDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeOrganisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeOrganisationDTO result = typeOrganisationService.save(typeOrganisationDTO);
        return ResponseEntity.created(new URI("/api/type-organisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-organisations} : Updates an existing typeOrganisation.
     *
     * @param typeOrganisationDTO the typeOrganisationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOrganisationDTO,
     * or with status {@code 400 (Bad Request)} if the typeOrganisationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeOrganisationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-organisations")
    public ResponseEntity<TypeOrganisationDTO> updateTypeOrganisation(@Valid @RequestBody TypeOrganisationDTO typeOrganisationDTO) throws URISyntaxException {
        log.debug("REST request to update TypeOrganisation : {}", typeOrganisationDTO);
        if (typeOrganisationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeOrganisationDTO result = typeOrganisationService.save(typeOrganisationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeOrganisationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-organisations} : get all the typeOrganisations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeOrganisations in body.
     */
    @GetMapping("/type-organisations")
    public ResponseEntity<List<TypeOrganisationDTO>> getAllTypeOrganisations(Pageable pageable) {
        log.debug("REST request to get a page of TypeOrganisations");
        Page<TypeOrganisationDTO> page = typeOrganisationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-organisations/:id} : get the "id" typeOrganisation.
     *
     * @param id the id of the typeOrganisationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeOrganisationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-organisations/{id}")
    public ResponseEntity<TypeOrganisationDTO> getTypeOrganisation(@PathVariable Long id) {
        log.debug("REST request to get TypeOrganisation : {}", id);
        Optional<TypeOrganisationDTO> typeOrganisationDTO = typeOrganisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeOrganisationDTO);
    }

    /**
     * {@code DELETE  /type-organisations/:id} : delete the "id" typeOrganisation.
     *
     * @param id the id of the typeOrganisationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-organisations/{id}")
    public ResponseEntity<Void> deleteTypeOrganisation(@PathVariable Long id) {
        log.debug("REST request to delete TypeOrganisation : {}", id);
        typeOrganisationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
