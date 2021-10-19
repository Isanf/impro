package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.InfoCommandeCarnetASoucheService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.InfoCommandeCarnetASoucheDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.InfoCommandeCarnetASouche}.
 */
@RestController
@RequestMapping("/api")
public class InfoCommandeCarnetASoucheResource {

    private final Logger log = LoggerFactory.getLogger(InfoCommandeCarnetASoucheResource.class);

    private static final String ENTITY_NAME = "infoCommandeCarnetASouche";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfoCommandeCarnetASoucheService infoCommandeCarnetASoucheService;

    public InfoCommandeCarnetASoucheResource(InfoCommandeCarnetASoucheService infoCommandeCarnetASoucheService) {
        this.infoCommandeCarnetASoucheService = infoCommandeCarnetASoucheService;
    }

    /**
     * {@code POST  /info-commande-carnet-a-souches} : Create a new infoCommandeCarnetASouche.
     *
     * @param infoCommandeCarnetASoucheDTO the infoCommandeCarnetASoucheDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoCommandeCarnetASoucheDTO, or with status {@code 400 (Bad Request)} if the infoCommandeCarnetASouche has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/info-commande-carnet-a-souches")
    public ResponseEntity<InfoCommandeCarnetASoucheDTO> createInfoCommandeCarnetASouche(@RequestBody InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO) throws URISyntaxException {
        log.debug("REST request to save InfoCommandeCarnetASouche : {}", infoCommandeCarnetASoucheDTO);
        if (infoCommandeCarnetASoucheDTO.getId() != null) {
            throw new BadRequestAlertException("A new infoCommandeCarnetASouche cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfoCommandeCarnetASoucheDTO result = infoCommandeCarnetASoucheService.save(infoCommandeCarnetASoucheDTO);
        return ResponseEntity.created(new URI("/api/info-commande-carnet-a-souches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /info-commande-carnet-a-souches} : Updates an existing infoCommandeCarnetASouche.
     *
     * @param infoCommandeCarnetASoucheDTO the infoCommandeCarnetASoucheDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoCommandeCarnetASoucheDTO,
     * or with status {@code 400 (Bad Request)} if the infoCommandeCarnetASoucheDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoCommandeCarnetASoucheDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/info-commande-carnet-a-souches")
    public ResponseEntity<InfoCommandeCarnetASoucheDTO> updateInfoCommandeCarnetASouche(@RequestBody InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO) throws URISyntaxException {
        log.debug("REST request to update InfoCommandeCarnetASouche : {}", infoCommandeCarnetASoucheDTO);
        if (infoCommandeCarnetASoucheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InfoCommandeCarnetASoucheDTO result = infoCommandeCarnetASoucheService.save(infoCommandeCarnetASoucheDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infoCommandeCarnetASoucheDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /info-commande-carnet-a-souches} : get all the infoCommandeCarnetASouches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoCommandeCarnetASouches in body.
     */
    @GetMapping("/info-commande-carnet-a-souches")
    public ResponseEntity<List<InfoCommandeCarnetASoucheDTO>> getAllInfoCommandeCarnetASouches(Pageable pageable) {
        log.debug("REST request to get a page of InfoCommandeCarnetASouches");
        Page<InfoCommandeCarnetASoucheDTO> page = infoCommandeCarnetASoucheService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /info-commande-carnet-a-souches/:id} : get the "id" infoCommandeCarnetASouche.
     *
     * @param id the id of the infoCommandeCarnetASoucheDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoCommandeCarnetASoucheDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/info-commande-carnet-a-souches/{id}")
    public ResponseEntity<InfoCommandeCarnetASoucheDTO> getInfoCommandeCarnetASouche(@PathVariable Long id) {
        log.debug("REST request to get InfoCommandeCarnetASouche : {}", id);
        Optional<InfoCommandeCarnetASoucheDTO> infoCommandeCarnetASoucheDTO = infoCommandeCarnetASoucheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(infoCommandeCarnetASoucheDTO);
    }

    /**
     * {@code DELETE  /info-commande-carnet-a-souches/:id} : delete the "id" infoCommandeCarnetASouche.
     *
     * @param id the id of the infoCommandeCarnetASoucheDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/info-commande-carnet-a-souches/{id}")
    public ResponseEntity<Void> deleteInfoCommandeCarnetASouche(@PathVariable Long id) {
        log.debug("REST request to delete InfoCommandeCarnetASouche : {}", id);

        infoCommandeCarnetASoucheService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
