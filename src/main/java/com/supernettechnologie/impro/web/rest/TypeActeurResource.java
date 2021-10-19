package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.TypeActeurService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.TypeActeurDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.TypeActeur}.
 */
@RestController
@RequestMapping("/api")
public class TypeActeurResource {

    private final Logger log = LoggerFactory.getLogger(TypeActeurResource.class);

    private static final String ENTITY_NAME = "typeActeur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeActeurService typeActeurService;

    public TypeActeurResource(TypeActeurService typeActeurService) {
        this.typeActeurService = typeActeurService;
    }

    /**
     * {@code POST  /type-acteurs} : Create a new typeActeur.
     *
     * @param typeActeurDTO the typeActeurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeActeurDTO, or with status {@code 400 (Bad Request)} if the typeActeur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-acteurs")
    public ResponseEntity<TypeActeurDTO> createTypeActeur(@Valid @RequestBody TypeActeurDTO typeActeurDTO) throws URISyntaxException {
        log.debug("REST request to save TypeActeur : {}", typeActeurDTO);
        if (typeActeurDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeActeur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeActeurDTO result = typeActeurService.save(typeActeurDTO);
        return ResponseEntity.created(new URI("/api/type-acteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-acteurs} : Updates an existing typeActeur.
     *
     * @param typeActeurDTO the typeActeurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeActeurDTO,
     * or with status {@code 400 (Bad Request)} if the typeActeurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeActeurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-acteurs")
    public ResponseEntity<TypeActeurDTO> updateTypeActeur(@Valid @RequestBody TypeActeurDTO typeActeurDTO) throws URISyntaxException {
        log.debug("REST request to update TypeActeur : {}", typeActeurDTO);
        if (typeActeurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeActeurDTO result = typeActeurService.save(typeActeurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeActeurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-acteurs} : get all the typeActeurs.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeActeurs in body.
     */
    @GetMapping("/type-acteurs")
    public ResponseEntity<List<TypeActeurDTO>> getAllTypeActeurs(Pageable pageable) {
        log.debug("REST request to get a page of TypeActeurs");
        Page<TypeActeurDTO> page = typeActeurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-acteurs/:id} : get the "id" typeActeur.
     *
     * @param id the id of the typeActeurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeActeurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-acteurs/{id}")
    public ResponseEntity<TypeActeurDTO> getTypeActeur(@PathVariable Long id) {
        log.debug("REST request to get TypeActeur : {}", id);
        Optional<TypeActeurDTO> typeActeurDTO = typeActeurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeActeurDTO);
    }

    /**
     * {@code DELETE  /type-acteurs/:id} : delete the "id" typeActeur.
     *
     * @param id the id of the typeActeurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-acteurs/{id}")
    public ResponseEntity<Void> deleteTypeActeur(@PathVariable Long id) {
        log.debug("REST request to delete TypeActeur : {}", id);
        typeActeurService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
