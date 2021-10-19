package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.ImmatriculationService;
import com.supernettechnologie.impro.service.dto.DatesModelDTO;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.ImmatriculationDTO;

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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.Immatriculation}.
 */
@RestController
@RequestMapping("/api")
public class ImmatriculationResource {

    private final Logger log = LoggerFactory.getLogger(ImmatriculationResource.class);

    private static final String ENTITY_NAME = "immatriculation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImmatriculationService immatriculationService;

    public ImmatriculationResource(ImmatriculationService immatriculationService) {
        this.immatriculationService = immatriculationService;
    }

    /**
     * {@code POST  /immatriculations} : Create a new immatriculation.
     *
     * @param immatriculationDTO the immatriculationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new immatriculationDTO, or with status {@code 400 (Bad Request)} if the immatriculation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/immatriculations")
    public ResponseEntity<ImmatriculationDTO> createImmatriculation(@RequestBody ImmatriculationDTO immatriculationDTO) throws URISyntaxException {
        log.debug("REST request to save Immatriculation : {}", immatriculationDTO);
        if (immatriculationDTO.getId() != null) {
            throw new BadRequestAlertException("A new immatriculation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImmatriculationDTO result = immatriculationService.save(immatriculationDTO);
        return ResponseEntity.created(new URI("/api/immatriculations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /immatriculations} : Updates an existing immatriculation.
     *
     * @param immatriculationDTO the immatriculationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated immatriculationDTO,
     * or with status {@code 400 (Bad Request)} if the immatriculationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the immatriculationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/immatriculations")
    public ResponseEntity<ImmatriculationDTO> updateImmatriculation(@RequestBody ImmatriculationDTO immatriculationDTO) throws URISyntaxException {
        log.debug("REST request to update Immatriculation : {}", immatriculationDTO);
        if (immatriculationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImmatriculationDTO result = immatriculationService.save(immatriculationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, immatriculationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /immatriculations} : get all the immatriculations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of immatriculations in body.
     */
    @GetMapping("/immatriculations")
    public ResponseEntity<List<ImmatriculationDTO>> getAllImmatriculations(Pageable pageable) {
        log.debug("REST request to get a page of Immatriculations");
        Page<ImmatriculationDTO> page = immatriculationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/immatriculation/total")
    public Long getImmatriculationsTotal() {
        return immatriculationService.totalAdmuin();
    }

    @GetMapping("/immatriculations/organisations")
    public ResponseEntity<List<ImmatriculationDTO>> getAllImmatriculationsOrganisation(Pageable pageable) {
        log.debug("REST request to get a page of Immatriculations");
        Page<ImmatriculationDTO> page = immatriculationService.findAllOrganisation(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/immatriculations/imjanvier")
    public int[] getImMonth(){

        int [] nbrIm = new int[12];

        nbrIm[0] = immatriculationService.imJanuary();
        nbrIm[1] = immatriculationService.imFebruary();
        nbrIm[2] = immatriculationService.imMarch();
        nbrIm[3] = immatriculationService.imApril();
        nbrIm[4] = immatriculationService.imMay();
        nbrIm[5] = immatriculationService.imJune();
        nbrIm[6] = immatriculationService.imJuly();
        nbrIm[7] = immatriculationService.imAugust();
        nbrIm[8] = immatriculationService.imSeptember();
        nbrIm[9] = immatriculationService.imOctober();
        nbrIm[10] = immatriculationService.imNovember();
        nbrIm[11] = immatriculationService.imDecember();

        log.debug(" ************************************** Resource Immatriculation Janvier : {}", nbrIm);
        return  nbrIm;
    }

    @PostMapping("/immatriculations/imperiode")
    public int[] getImMonth0(@RequestBody DatesModelDTO datesModelDTO){
        int[] nbImDay;
        nbImDay = immatriculationService.imDay0(datesModelDTO);
        log.debug(" ************************************** Resource ********* : {}", nbImDay);
        return  nbImDay;
    }
    @GetMapping("/immatriculationsImperiode/{dat}/{dat2}")
    public int[] getImMonth1(@PathVariable ZonedDateTime dat, @PathVariable ZonedDateTime dat2){
        int[] nbImDay;
        nbImDay = immatriculationService.imDay1(dat, dat2);
        log.debug(" ************************************** Resource ********* : {}", nbImDay);
        return  nbImDay;
    }
    @GetMapping("/immatriculationsImperiode")
    public int[] getImMonth(@RequestParam String dat, @RequestParam String dat1,
                            @RequestParam String dat2, @RequestParam String dat3,
                            @RequestParam String dat4, @RequestParam String dat5){
        ZonedDateTime dateIm = ZonedDateTime.of(
            Integer.parseInt(dat), Integer.parseInt(dat1), Integer.parseInt(dat2), 0, 0, 0,
            90000, ZoneId.systemDefault());
        ZonedDateTime dateIm2 = ZonedDateTime.of(
            Integer.parseInt(dat3), Integer.parseInt(dat4), Integer.parseInt(dat5), 0, 0, 0,
            90000, ZoneId.systemDefault());
        int[] nbImDay;
        nbImDay = immatriculationService.imDay(dateIm, dateIm2);

        log.debug(" ************************************** Resource ********* : {}", dateIm.toLocalDate().atStartOfDay());
        return  nbImDay;
    }

    @GetMapping("/immatriculationsIm")
    public int[] getIm(@RequestParam ZonedDateTime dat, @RequestParam ZonedDateTime dat1){
        int[] nbImDay;
        nbImDay = immatriculationService.imDay(dat, dat1);

        log.debug(" ************************************** Resource ********* : {}", dat.toLocalDate().atStartOfDay());
        return  nbImDay;
    }

    @GetMapping("/immatriculations4Im")
    public int[] get4Im(
        @RequestParam(value = "fromDate") LocalDate fromDate,
        @RequestParam(value = "toDate") LocalDate toDate
    ){
        Instant from = fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant to = toDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant();
        int[] nbImDay;
        nbImDay = immatriculationService.imDay(from.atZone(ZoneId.of("GMT")), to.atZone(ZoneId.of("GMT")));
        log.debug(" ************************************** Time To Time ******************************* : {} :",
            from.atZone(ZoneId.of("GMT"))+"--"+ to.atZone(ZoneId.of("GMT")));
        return  nbImDay;
    }

    @GetMapping("/immatriculationsImperiode4AllOrg")
    public int[] getImMonth4AllOrg(
        @RequestParam(value = "fromDate") LocalDate fromDate,
        @RequestParam(value = "toDate") LocalDate toDate
    ){
        Instant from = fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant to = toDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant();
        int[] nbImDay;
        nbImDay = immatriculationService.imDay4AllOrg(from.atZone(ZoneId.of("GMT")), to.atZone(ZoneId.of("GMT")));
        log.debug(" ************************************** Time To Time ******************************* : {} :",
            from.atZone(ZoneId.of("GMT"))+"--"+ to.atZone(ZoneId.of("GMT")));
        return  nbImDay;
    }

    @GetMapping("/immatriculationsFind4Org")
    public int get4Org(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
            @RequestParam(value = "idOrg") Long idOrg
    ){
            Instant from = fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant to = toDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant();
        int totIm;
        totIm = immatriculationService.find4Org(from.atZone(ZoneId.of("GMT")), to.atZone(ZoneId.of("GMT")), idOrg);
            log.debug(" ************************************** Time To Time ******************************* : {} :",
                from.atZone(ZoneId.of("GMT"))+"--"+ to.atZone(ZoneId.of("GMT")));
        log.debug(" ************************************** Resource *************************** : {}", totIm);
        log.debug(" ************************************** Resource *************************** : {}", totIm);
        return  totIm;
    }

    /**
     * {@code GET  /immatriculations/:id} : get the "id" immatriculation.
     *
     * @param id the id of the immatriculationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the immatriculationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/immatriculations/{id}")
    public ResponseEntity<ImmatriculationDTO> getImmatriculation(@PathVariable Long id) {
        log.debug("REST request to get Immatriculation : {}", id);
        Optional<ImmatriculationDTO> immatriculationDTO = immatriculationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(immatriculationDTO);
    }

    /**
     * {@code DELETE  /immatriculations/:id} : delete the "id" immatriculation.
     *
     * @param id the id of the immatriculationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/immatriculations/{id}")
    public ResponseEntity<Void> deleteImmatriculation(@PathVariable Long id) {
        log.debug("REST request to delete Immatriculation : {}", id);
        immatriculationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/immatriculations/AllAdmin")
    public Long getAllImmAdmin() {
        return immatriculationService.getAllAdmin();
    }

    @GetMapping("/immatriculations/AllOrganisation")
    public int getAllImmOrganisation() {

        return immatriculationService.getAllOrga();
    }
}
