package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.PrixCertificatService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.PrixCertificatDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.PrixCertificat}.
 */
@RestController
@RequestMapping("/api")
public class PrixCertificatResource {

    private final Logger log = LoggerFactory.getLogger(PrixCertificatResource.class);

    private static final String ENTITY_NAME = "prixCertificat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrixCertificatService prixCertificatService;

    public PrixCertificatResource(PrixCertificatService prixCertificatService) {
        this.prixCertificatService = prixCertificatService;
    }

    /**
     * {@code POST  /prix-certificats} : Create a new prixCertificat.
     *
     * @param prixCertificatDTO the prixCertificatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prixCertificatDTO, or with status {@code 400 (Bad Request)} if the prixCertificat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prix-certificats")
    public ResponseEntity<PrixCertificatDTO> createPrixCertificat(@Valid @RequestBody PrixCertificatDTO prixCertificatDTO) throws URISyntaxException {
        log.debug("REST request to save PrixCertificat : {}", prixCertificatDTO);
        if (prixCertificatDTO.getId() != null) {
            throw new BadRequestAlertException("A new prixCertificat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrixCertificatDTO result = prixCertificatService.save(prixCertificatDTO);
        return ResponseEntity.created(new URI("/api/prix-certificats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prix-certificats} : Updates an existing prixCertificat.
     *
     * @param prixCertificatDTO the prixCertificatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prixCertificatDTO,
     * or with status {@code 400 (Bad Request)} if the prixCertificatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prixCertificatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prix-certificats")
    public ResponseEntity<PrixCertificatDTO> updatePrixCertificat(@Valid @RequestBody PrixCertificatDTO prixCertificatDTO) throws URISyntaxException {
        log.debug("REST request to update PrixCertificat : {}", prixCertificatDTO);
        if (prixCertificatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrixCertificatDTO result = prixCertificatService.save(prixCertificatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prixCertificatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prix-certificats} : get all the prixCertificats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prixCertificats in body.
     */
    @GetMapping("/prix-certificats")
    public ResponseEntity<List<PrixCertificatDTO>> getAllPrixCertificats(Pageable pageable) {
        log.debug("REST request to get a page of PrixCertificats");
        Page<PrixCertificatDTO> page = prixCertificatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prix-certificats/:id} : get the "id" prixCertificat.
     *
     * @param id the id of the prixCertificatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prixCertificatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prix-certificats/{id}")
    public ResponseEntity<PrixCertificatDTO> getPrixCertificat(@PathVariable Long id) {
        log.debug("REST request to get PrixCertificat : {}", id);
        Optional<PrixCertificatDTO> prixCertificatDTO = prixCertificatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prixCertificatDTO);
    }

    /**
     * {@code DELETE  /prix-certificats/:id} : delete the "id" prixCertificat.
     *
     * @param id the id of the prixCertificatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prix-certificats/{id}")
    public ResponseEntity<Void> deletePrixCertificat(@PathVariable Long id) {
        log.debug("REST request to delete PrixCertificat : {}", id);
        prixCertificatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
