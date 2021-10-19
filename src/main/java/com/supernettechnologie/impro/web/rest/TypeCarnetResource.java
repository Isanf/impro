package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.TypeCarnetService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.TypeCarnetDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.TypeCarnet}.
 */
@RestController
@RequestMapping("/api")
public class TypeCarnetResource {

    private final Logger log = LoggerFactory.getLogger(TypeCarnetResource.class);

    private static final String ENTITY_NAME = "typeCarnet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeCarnetService typeCarnetService;

    public TypeCarnetResource(TypeCarnetService typeCarnetService) {
        this.typeCarnetService = typeCarnetService;
    }

    /**
     * {@code POST  /type-carnets} : Create a new typeCarnet.
     *
     * @param typeCarnetDTO the typeCarnetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeCarnetDTO, or with status {@code 400 (Bad Request)} if the typeCarnet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-carnets")
    public ResponseEntity<TypeCarnetDTO> createTypeCarnet(@RequestBody TypeCarnetDTO typeCarnetDTO) throws URISyntaxException {
        log.debug("REST request to save TypeCarnet : {}", typeCarnetDTO);
        if (typeCarnetDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeCarnet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeCarnetDTO result = typeCarnetService.save(typeCarnetDTO);
        return ResponseEntity.created(new URI("/api/type-carnets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-carnets} : Updates an existing typeCarnet.
     *
     * @param typeCarnetDTO the typeCarnetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeCarnetDTO,
     * or with status {@code 400 (Bad Request)} if the typeCarnetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeCarnetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-carnets")
    public ResponseEntity<TypeCarnetDTO> updateTypeCarnet(@RequestBody TypeCarnetDTO typeCarnetDTO) throws URISyntaxException {
        log.debug("REST request to update TypeCarnet : {}", typeCarnetDTO);
        if (typeCarnetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeCarnetDTO result = typeCarnetService.save(typeCarnetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeCarnetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-carnets} : get all the typeCarnets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeCarnets in body.
     */
    @GetMapping("/type-carnets")
    public ResponseEntity<List<TypeCarnetDTO>> getAllTypeCarnets(Pageable pageable) {
        log.debug("REST request to get a page of TypeCarnets");
        Page<TypeCarnetDTO> page = typeCarnetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-carnets/:id} : get the "id" typeCarnet.
     *
     * @param id the id of the typeCarnetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeCarnetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-carnets/{id}")
    public ResponseEntity<TypeCarnetDTO> getTypeCarnet(@PathVariable Long id) {
        log.debug("REST request to get TypeCarnet : {}", id);
        Optional<TypeCarnetDTO> typeCarnetDTO = typeCarnetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeCarnetDTO);
    }

    /**
     * {@code DELETE  /type-carnets/:id} : delete the "id" typeCarnet.
     *
     * @param id the id of the typeCarnetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-carnets/{id}")
    public ResponseEntity<Void> deleteTypeCarnet(@PathVariable Long id) {
        log.debug("REST request to delete TypeCarnet : {}", id);
        typeCarnetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
