package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.repository.MarqueVehiculeRepository;
import com.supernettechnologie.impro.repository.VehiculeRepository;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.MarqueVehiculeService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.MarqueVehiculeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.MarqueVehicule}.
 */
@RestController
@RequestMapping("/api")
public class MarqueVehiculeResource {

    private final Logger log = LoggerFactory.getLogger(MarqueVehiculeResource.class);

    private static final String ENTITY_NAME = "marqueVehicule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private MarqueVehiculeRepository marqueVehiculeRepository;

    private final MarqueVehiculeService marqueVehiculeService;

    public MarqueVehiculeResource(MarqueVehiculeService marqueVehiculeService) {
        this.marqueVehiculeService = marqueVehiculeService;
    }

    /**
     * {@code POST  /marque-vehicules} : Create a new marqueVehicule.
     *
     * @param marqueVehiculeDTO the marqueVehiculeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marqueVehiculeDTO, or with status {@code 400 (Bad Request)} if the marqueVehicule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/marque-vehicules")
    public ResponseEntity<MarqueVehiculeDTO> createMarqueVehicule(@RequestBody MarqueVehiculeDTO marqueVehiculeDTO) throws URISyntaxException {
        log.debug("REST request to save MarqueVehicule : {}", marqueVehiculeDTO);
        if (marqueVehiculeDTO.getId() != null) {
            throw new BadRequestAlertException("A new marqueVehicule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarqueVehiculeDTO result = marqueVehiculeService.save(marqueVehiculeDTO);
        return ResponseEntity.created(new URI("/api/marque-vehicules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/marque-vehicules/file")
    public void createStockFile(@RequestParam("file") MultipartFile file) {

        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file.getBytes())));
            while((line = br.readLine()) != null) {
                String[] data = line.split(";");
                MarqueVehicule m = new MarqueVehicule();
                m.setCode(data[0]);
                m.setLibelle(data[1]);
                marqueVehiculeRepository.save(m);
                log.debug("**************************** : {}", m.getLibelle());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * {@code PUT  /marque-vehicules} : Updates an existing marqueVehicule.
     *
     * @param marqueVehiculeDTO the marqueVehiculeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marqueVehiculeDTO,
     * or with status {@code 400 (Bad Request)} if the marqueVehiculeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marqueVehiculeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/marque-vehicules")
    public ResponseEntity<MarqueVehiculeDTO> updateMarqueVehicule(@RequestBody MarqueVehiculeDTO marqueVehiculeDTO) throws URISyntaxException {
        log.debug("REST request to update MarqueVehicule : {}", marqueVehiculeDTO);
        if (marqueVehiculeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MarqueVehiculeDTO result = marqueVehiculeService.save(marqueVehiculeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marqueVehiculeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /marque-vehicules} : get all the marqueVehicules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marqueVehicules in body.
     */
    @GetMapping("/marque-vehicules")
    public ResponseEntity<List<MarqueVehiculeDTO>> getAllMarqueVehicules(Pageable pageable) {
        log.debug("REST request to get a page of MarqueVehicules");
        Page<MarqueVehiculeDTO> page = marqueVehiculeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/allmarques")
    public ResponseEntity<List<MarqueVehiculeDTO>> getAllsMarqueVehicules() {
        log.debug("REST request to get a page of MarqueVehicules");
        List<MarqueVehiculeDTO> marqueVehiculeDTOS = marqueVehiculeService.findAlls();
        return ResponseEntity.ok().body(marqueVehiculeDTOS);
    }

    /**
     * {@code GET  /marque-vehicules/:id} : get the "id" marqueVehicule.
     *
     * @param id the id of the marqueVehiculeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marqueVehiculeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/marque-vehicules/{id}")
    public ResponseEntity<MarqueVehiculeDTO> getMarqueVehicule(@PathVariable Long id) {
        log.debug("REST request to get MarqueVehicule : {}", id);
        Optional<MarqueVehiculeDTO> marqueVehiculeDTO = marqueVehiculeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marqueVehiculeDTO);
    }

    /**
     * {@code DELETE  /marque-vehicules/:id} : delete the "id" marqueVehicule.
     *
     * @param id the id of the marqueVehiculeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/marque-vehicules/{id}")
    public ResponseEntity<Void> deleteMarqueVehicule(@PathVariable Long id) {
        log.debug("REST request to delete MarqueVehicule : {}", id);
        marqueVehiculeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
