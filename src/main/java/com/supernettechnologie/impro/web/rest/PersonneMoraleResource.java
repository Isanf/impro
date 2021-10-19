package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.PersonneMoraleService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.PersonneMoraleDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.PersonneMorale}.
 */
@RestController
@RequestMapping("/api")
public class PersonneMoraleResource {

    private final Logger log = LoggerFactory.getLogger(PersonneMoraleResource.class);

    private static final String ENTITY_NAME = "personneMorale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonneMoraleService personneMoraleService;

    public PersonneMoraleResource(PersonneMoraleService personneMoraleService) {
        this.personneMoraleService = personneMoraleService;
    }

    /**
     * {@code POST  /personne-morales} : Create a new personneMorale.
     *
     * @param personneMoraleDTO the personneMoraleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personneMoraleDTO, or with status {@code 400 (Bad Request)} if the personneMorale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personne-morales")
    public ResponseEntity<PersonneMoraleDTO> createPersonneMorale(@RequestBody PersonneMoraleDTO personneMoraleDTO) throws URISyntaxException {
        log.debug("REST request to save PersonneMorale : {}", personneMoraleDTO);
        if (personneMoraleDTO.getId() != null) {
            throw new BadRequestAlertException("A new personneMorale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonneMoraleDTO result = personneMoraleService.save(personneMoraleDTO);
        return ResponseEntity.created(new URI("/api/personne-morales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personne-morales} : Updates an existing personneMorale.
     *
     * @param personneMoraleDTO the personneMoraleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personneMoraleDTO,
     * or with status {@code 400 (Bad Request)} if the personneMoraleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personneMoraleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personne-morales")
    public ResponseEntity<PersonneMoraleDTO> updatePersonneMorale(@RequestBody PersonneMoraleDTO personneMoraleDTO) throws URISyntaxException {
        log.debug("REST request to update PersonneMorale : {}", personneMoraleDTO);
        if (personneMoraleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonneMoraleDTO result = personneMoraleService.save(personneMoraleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personneMoraleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /personne-morales} : get all the personneMorales.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personneMorales in body.
     */
    @GetMapping("/personne-morales")
    public ResponseEntity<List<PersonneMoraleDTO>> getAllPersonneMorales(Pageable pageable) {
        log.debug("REST request to get a page of PersonneMorales");
        Page<PersonneMoraleDTO> page = personneMoraleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personne-morales/:id} : get the "id" personneMorale.
     *
     * @param id the id of the personneMoraleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personneMoraleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personne-morales/{id}")
    public ResponseEntity<PersonneMoraleDTO> getPersonneMorale(@PathVariable Long id) {
        log.debug("REST request to get PersonneMorale : {}", id);
        Optional<PersonneMoraleDTO> personneMoraleDTO = personneMoraleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personneMoraleDTO);
    }

    /**
     * {@code DELETE  /personne-morales/:id} : delete the "id" personneMorale.
     *
     * @param id the id of the personneMoraleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personne-morales/{id}")
    public ResponseEntity<Void> deletePersonneMorale(@PathVariable Long id) {
        log.debug("REST request to delete PersonneMorale : {}", id);
        personneMoraleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
