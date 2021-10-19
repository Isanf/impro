package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.CarnetASoucheService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.CarnetASoucheDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import net.sf.jasperreports.engine.JRException;
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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.CarnetASouche}.
 */
@RestController
@RequestMapping("/api")
public class CarnetASoucheResource {

    private final Logger log = LoggerFactory.getLogger(CarnetASoucheResource.class);

    private static final String ENTITY_NAME = "carnetASouche";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarnetASoucheService carnetASoucheService;

    public CarnetASoucheResource(CarnetASoucheService carnetASoucheService) {
        this.carnetASoucheService = carnetASoucheService;
    }

    /**
     * {@code POST  /carnet-a-souches} : Create a new carnetASouche.
     *
     * @param carnetASoucheDTO the carnetASoucheDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carnetASoucheDTO, or with status {@code 400 (Bad Request)} if the carnetASouche has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carnet-a-souches")
    public ResponseEntity<CarnetASoucheDTO> createCarnetASouche(@RequestBody CarnetASoucheDTO carnetASoucheDTO) throws Exception {
        log.debug("REST request to save CarnetASouche : {}", carnetASoucheDTO);
        if (carnetASoucheDTO.getId() != null) {
            throw new BadRequestAlertException("A new carnetASouche cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarnetASoucheDTO result = carnetASoucheService.save(carnetASoucheDTO);
        return ResponseEntity.created(new URI("/api/carnet-a-souches/"))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code PUT  /carnet-a-souches} : Updates an existing carnetASouche.
     *
     * @param carnetASoucheDTO the carnetASoucheDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carnetASoucheDTO,
     * or with status {@code 400 (Bad Request)} if the carnetASoucheDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carnetASoucheDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carnet-a-souches")
    public ResponseEntity<CarnetASoucheDTO> updateCarnetASouche(@RequestBody CarnetASoucheDTO carnetASoucheDTO) throws Exception {
        log.debug("REST request to update CarnetASouche : {}", carnetASoucheDTO);
        if (carnetASoucheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarnetASoucheDTO result = carnetASoucheService.save(carnetASoucheDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code GET  /carnet-a-souches} : get all the carnetASouches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carnetASouches in body.
     */
    @GetMapping("/carnet-a-souches")
    public ResponseEntity<List<CarnetASoucheDTO>> getAllCarnetASouches(Pageable pageable) {
        log.debug("REST request to get a page of CarnetASouches");
        Page<CarnetASoucheDTO> page = carnetASoucheService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/carnet-a-souches/admin")
    public ResponseEntity<List<CarnetASoucheDTO>> getAllCarnetASouchesAdmin(Pageable pageable) {
        log.debug("REST request to get a page of CarnetASouches");
        Page<CarnetASoucheDTO> page = carnetASoucheService.findAllAdmin(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /carnet-a-souches/:id} : get the "id" carnetASouche.
     *
     * @param id the id of the carnetASoucheDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carnetASoucheDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carnet-a-souches/{id}")
    public ResponseEntity<CarnetASoucheDTO> getCarnetASouche(@PathVariable Long id) {
        log.debug("REST request to get CarnetASouche : {}", id);
        Optional<CarnetASoucheDTO> carnetASoucheDTO = carnetASoucheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carnetASoucheDTO);
    }

    @GetMapping("/carnet-a-souches/carnet/{id}")
    public ResponseEntity<List<CarnetASoucheDTO>> getCarnetASoucheByInfoId(@PathVariable Long id) {
        log.debug("REST request to get CarnetASouche : {}", id);
        Page<CarnetASoucheDTO> page = carnetASoucheService.findOneByInfoId(id);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code DELETE  /carnet-a-souches/:id} : delete the "id" carnetASouche.
     *
     * @param id the id of the carnetASoucheDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carnet-a-souches/{id}")
    public ResponseEntity<Void> deleteCarnetASouche(@PathVariable Long id) {
        log.debug("REST request to delete CarnetASouche : {}", id);

        carnetASoucheService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/carnet-a-souches/nombreCertif")
    public int nbCarnetCertif(){
        return carnetASoucheService.nbCarnetCertif();
    }

    @GetMapping("/carnet-a-souches/nombreCertif4Org")
    public int nbCarnetCertif4Org(
        @RequestParam(value = "fromDate") LocalDate fromDate,
        @RequestParam(value = "toDate") LocalDate toDate,
        @RequestParam(value = "idOrg") Long idOrg
    ){
        Instant from = fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant to = toDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant();
        return carnetASoucheService.nbCarnetCertif4Org(from.atZone(ZoneId.of("GMT")), to.atZone(ZoneId.of("GMT")), idOrg);
    }

    @GetMapping("/carnet-a-souches/nbCarnet4Org")
    public int nbCarnet4Org(
        @RequestParam(value = "fromDate") LocalDate fromDate,
        @RequestParam(value = "toDate") LocalDate toDate,
        @RequestParam(value = "idOrg") Long idOrg
    ){
        Instant from = fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant to = toDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant();
        return carnetASoucheService.nbCarnet4Org(from.atZone(ZoneId.of("GMT")), to.atZone(ZoneId.of("GMT")), idOrg);
    }

    @GetMapping("/carnet-a-souches/nombreCertifUsed")
    public int nbCarnetCertifUsed(){
        return carnetASoucheService.nbCarnetCertifUsed();
    }

    @GetMapping("/carnet-a-souches/nombreUsed")
    public int nbUsed(){
        return carnetASoucheService.nbUsed();
    }

    @GetMapping("/carnet-a-souches/nombre")
    public Long nbCarnet(){
        return carnetASoucheService.nbCarnet();
    }

    @GetMapping("/carnet-a-souches/nombreAdmin")
    public Long nbCarnetAdmin(){
        return carnetASoucheService.nbCarnetAdmin();
    }

    @GetMapping("/carnet-a-souches/nombreAdminUsed")
    public int nbCarnetAdminUsed(){
        return carnetASoucheService.nbCarnetAdminUsed();
    }

    @GetMapping("/carnet-a-souches/nombreAdminAll")
    public int nbCarnetAdminAllCertif(){
        return carnetASoucheService.nbCarnetAdminAllCertif();
    }
}
