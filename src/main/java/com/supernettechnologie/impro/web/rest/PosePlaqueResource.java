package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.PosePlaqueService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.PosePlaqueDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.PosePlaque}.
 */
@RestController
@RequestMapping("/api")
public class PosePlaqueResource {

    private final Logger log = LoggerFactory.getLogger(PosePlaqueResource.class);

    private static final String ENTITY_NAME = "posePlaque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PosePlaqueService posePlaqueService;

    public PosePlaqueResource(PosePlaqueService posePlaqueService) {
        this.posePlaqueService = posePlaqueService;
    }

    /**
     * {@code POST  /pose-plaques} : Create a new posePlaque.
     *
     * @param posePlaqueDTO the posePlaqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new posePlaqueDTO, or with status {@code 400 (Bad Request)} if the posePlaque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pose-plaques")
    public ResponseEntity<PosePlaqueDTO> createPosePlaque(@RequestBody PosePlaqueDTO posePlaqueDTO) throws URISyntaxException {
        log.debug("REST request to save PosePlaque : {}", posePlaqueDTO);
        if (posePlaqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new posePlaque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PosePlaqueDTO result = posePlaqueService.save(posePlaqueDTO);
        return ResponseEntity.created(new URI("/api/pose-plaques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pose-plaques} : Updates an existing posePlaque.
     *
     * @param posePlaqueDTO the posePlaqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated posePlaqueDTO,
     * or with status {@code 400 (Bad Request)} if the posePlaqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the posePlaqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pose-plaques")
    public ResponseEntity<PosePlaqueDTO> updatePosePlaque(@RequestBody PosePlaqueDTO posePlaqueDTO) throws URISyntaxException {
        log.debug("REST request to update PosePlaque : {}", posePlaqueDTO);
        if (posePlaqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PosePlaqueDTO result = posePlaqueService.save(posePlaqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, posePlaqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pose-plaques} : get all the posePlaques.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of posePlaques in body.
     */
    @GetMapping("/pose-plaques")
    public ResponseEntity<List<PosePlaqueDTO>> getAllPosePlaques(Pageable pageable) {
        log.debug("REST request to get a page of PosePlaques");
        Page<PosePlaqueDTO> page = posePlaqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pose-plaques/:id} : get the "id" posePlaque.
     *
     * @param id the id of the posePlaqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the posePlaqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pose-plaques/{id}")
    public ResponseEntity<PosePlaqueDTO> getPosePlaque(@PathVariable Long id) {
        log.debug("REST request to get PosePlaque : {}", id);
        Optional<PosePlaqueDTO> posePlaqueDTO = posePlaqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(posePlaqueDTO);
    }

    /**
     * {@code DELETE  /pose-plaques/:id} : delete the "id" posePlaque.
     *
     * @param id the id of the posePlaqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pose-plaques/{id}")
    public ResponseEntity<Void> deletePosePlaque(@PathVariable Long id) {
        log.debug("REST request to delete PosePlaque : {}", id);
        posePlaqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
