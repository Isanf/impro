package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.LogActivityService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.LogActivityDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.LogActivity}.
 */
@RestController
@RequestMapping("/api")
public class LogActivityResource {

    private final Logger log = LoggerFactory.getLogger(LogActivityResource.class);

    private static final String ENTITY_NAME = "logActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogActivityService logActivityService;

    public LogActivityResource(LogActivityService logActivityService) {
        this.logActivityService = logActivityService;
    }

    /**
     * {@code POST  /log-activities} : Create a new logActivity.
     *
     * @param logActivityDTO the logActivityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logActivityDTO, or with status {@code 400 (Bad Request)} if the logActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/log-activities")
    public ResponseEntity<LogActivityDTO> createLogActivity(@RequestBody LogActivityDTO logActivityDTO) throws URISyntaxException {
        log.debug("REST request to save LogActivity : {}", logActivityDTO);
        if (logActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new logActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogActivityDTO result = logActivityService.save(logActivityDTO);
        return ResponseEntity.created(new URI("/api/log-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /log-activities} : Updates an existing logActivity.
     *
     * @param logActivityDTO the logActivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logActivityDTO,
     * or with status {@code 400 (Bad Request)} if the logActivityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logActivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/log-activities")
    public ResponseEntity<LogActivityDTO> updateLogActivity(@RequestBody LogActivityDTO logActivityDTO) throws URISyntaxException {
        log.debug("REST request to update LogActivity : {}", logActivityDTO);
        if (logActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogActivityDTO result = logActivityService.save(logActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /log-activities} : get all the logActivities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logActivities in body.
     */
    @GetMapping("/log-activities")
    public List<LogActivityDTO> getAllLogActivities() {
        log.debug("REST request to get all LogActivities");
        return logActivityService.findAll();
    }

    /**
     * {@code GET  /log-activities/:id} : get the "id" logActivity.
     *
     * @param id the id of the logActivityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logActivityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/log-activities/{id}")
    public ResponseEntity<LogActivityDTO> getLogActivity(@PathVariable Long id) {
        log.debug("REST request to get LogActivity : {}", id);
        Optional<LogActivityDTO> logActivityDTO = logActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logActivityDTO);
    }

    /**
     * {@code DELETE  /log-activities/:id} : delete the "id" logActivity.
     *
     * @param id the id of the logActivityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/log-activities/{id}")
    public ResponseEntity<Void> deleteLogActivity(@PathVariable Long id) {
        log.debug("REST request to delete LogActivity : {}", id);

        logActivityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
