package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.PersonnePhysiqueService;
import com.supernettechnologie.impro.service.dto.VenteDTO;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.PersonnePhysiqueDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.PersonnePhysique}.
 */
@RestController
@RequestMapping("/api")
public class PersonnePhysiqueResource {

    private final Logger log = LoggerFactory.getLogger(PersonnePhysiqueResource.class);

    private static final String ENTITY_NAME = "personnePhysique";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonnePhysiqueService personnePhysiqueService;

    public PersonnePhysiqueResource(PersonnePhysiqueService personnePhysiqueService) {
        this.personnePhysiqueService = personnePhysiqueService;
    }

    /**
     * {@code POST  /personne-physiques} : Create a new personnePhysique.
     *
     * @param personnePhysiqueDTO the personnePhysiqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personnePhysiqueDTO, or with status {@code 400 (Bad Request)} if the personnePhysique has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personne-physiques")
    public ResponseEntity<PersonnePhysiqueDTO> createPersonnePhysique(@RequestBody PersonnePhysiqueDTO personnePhysiqueDTO) throws URISyntaxException {
        log.debug("****************************************************************** : {}", personnePhysiqueDTO);
        if (personnePhysiqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new personnePhysique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonnePhysiqueDTO result = personnePhysiqueService.save(personnePhysiqueDTO);
        return ResponseEntity.created(new URI("/api/personne-physiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personne-physiques} : Updates an existing personnePhysique.
     *
     * @param personnePhysiqueDTO the personnePhysiqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personnePhysiqueDTO,
     * or with status {@code 400 (Bad Request)} if the personnePhysiqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personnePhysiqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personne-physiques")
    public ResponseEntity<PersonnePhysiqueDTO> updatePersonnePhysique(@RequestBody PersonnePhysiqueDTO personnePhysiqueDTO) throws URISyntaxException {
        log.debug("REST request to update PersonnePhysique : {}", personnePhysiqueDTO);
        if (personnePhysiqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonnePhysiqueDTO result = personnePhysiqueService.save(personnePhysiqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personnePhysiqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /personne-physiques} : get all the personnePhysiques.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personnePhysiques in body.
     */
    @GetMapping("/personne-physiques")
    public ResponseEntity<List<PersonnePhysiqueDTO>> getAllPersonnePhysiques(Pageable pageable) {
        log.debug("REST request to get a page of PersonnePhysiques");
        Page<PersonnePhysiqueDTO> page = personnePhysiqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personne-physiques/:id} : get the "id" personnePhysique.
     *
     * @param id the id of the personnePhysiqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personnePhysiqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personne-physiques/{id}")
    public ResponseEntity<PersonnePhysiqueDTO> getPersonnePhysique(@PathVariable Long id) {
        log.debug("REST request to get PersonnePhysique : {}", id);
        Optional<PersonnePhysiqueDTO> personnePhysiqueDTO = personnePhysiqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personnePhysiqueDTO);
    }

    /**
     * {@code DELETE  /personne-physiques/:id} : delete the "id" personnePhysique.
     *
     * @param id the id of the personnePhysiqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personne-physiques/{id}")
    public ResponseEntity<Void> deletePersonnePhysique(@PathVariable Long id) {
        log.debug("REST request to delete PersonnePhysique : {}", id);
        personnePhysiqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
