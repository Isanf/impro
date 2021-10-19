package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.StatistiqueService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.StatistiqueDTO;
import com.supernettechnologie.impro.service.dto.StatistiqueCriteria;
import com.supernettechnologie.impro.service.StatistiqueQueryService;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.Statistique}.
 */
@RestController
@RequestMapping("/api")
public class StatistiqueResource {

    private final Logger log = LoggerFactory.getLogger(StatistiqueResource.class);

    private static final String ENTITY_NAME = "statistique";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatistiqueService statistiqueService;

    private final StatistiqueQueryService statistiqueQueryService;

    public StatistiqueResource(StatistiqueService statistiqueService, StatistiqueQueryService statistiqueQueryService) {
        this.statistiqueService = statistiqueService;
        this.statistiqueQueryService = statistiqueQueryService;
    }

    /**
     * {@code POST  /statistiques} : Create a new statistique.
     *
     * @param statistiqueDTO the statistiqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statistiqueDTO, or with status {@code 400 (Bad Request)} if the statistique has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/statistiques")
    public ResponseEntity<StatistiqueDTO> createStatistique(@RequestBody StatistiqueDTO statistiqueDTO) throws URISyntaxException {
        log.debug("REST request to save Statistique : {}", statistiqueDTO);
        if (statistiqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new statistique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatistiqueDTO result = statistiqueService.save(statistiqueDTO);
        return ResponseEntity.created(new URI("/api/statistiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /statistiques} : Updates an existing statistique.
     *
     * @param statistiqueDTO the statistiqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statistiqueDTO,
     * or with status {@code 400 (Bad Request)} if the statistiqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statistiqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/statistiques")
    public ResponseEntity<StatistiqueDTO> updateStatistique(@RequestBody StatistiqueDTO statistiqueDTO) throws URISyntaxException {
        log.debug("REST request to update Statistique : {}", statistiqueDTO);
        if (statistiqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatistiqueDTO result = statistiqueService.save(statistiqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statistiqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /statistiques} : get all the statistiques.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statistiques in body.
     */
    @GetMapping("/statistiques")
    public ResponseEntity<List<StatistiqueDTO>> getAllStatistiques(StatistiqueCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Statistiques by criteria: {}", criteria);
        Page<StatistiqueDTO> page = statistiqueQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /statistiques/count} : count all the statistiques.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/statistiques/count")
    public ResponseEntity<Long> countStatistiques(StatistiqueCriteria criteria) {
        log.debug("REST request to count Statistiques by criteria: {}", criteria);
        return ResponseEntity.ok().body(statistiqueQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /statistiques/:id} : get the "id" statistique.
     *
     * @param id the id of the statistiqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statistiqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/statistiques/{id}")
    public ResponseEntity<StatistiqueDTO> getStatistique(@PathVariable Long id) {
        log.debug("REST request to get Statistique : {}", id);
        Optional<StatistiqueDTO> statistiqueDTO = statistiqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statistiqueDTO);
    }

    /**
     * {@code DELETE  /statistiques/:id} : delete the "id" statistique.
     *
     * @param id the id of the statistiqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/statistiques/{id}")
    public ResponseEntity<Void> deleteStatistique(@PathVariable Long id) {
        log.debug("REST request to delete Statistique : {}", id);

        statistiqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
