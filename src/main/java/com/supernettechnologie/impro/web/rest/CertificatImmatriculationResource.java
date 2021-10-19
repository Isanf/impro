package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.PlaqueImmatriculation;
import com.supernettechnologie.impro.repository.PlaqueImmatriculationRepository;
import com.supernettechnologie.impro.service.CertificatImmatriculationService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.CertificatImmatriculationDTO;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.CertificatImmatriculation}.
 */
@RestController
@RequestMapping("/api")
public class CertificatImmatriculationResource {

    private final Logger log = LoggerFactory.getLogger(CertificatImmatriculationResource.class);

    private static final String ENTITY_NAME = "certificatImmatriculation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CertificatImmatriculationService certificatImmatriculationService;
    @Autowired
    private PlaqueImmatriculationRepository plaqueImmatriculationRepository;

    public CertificatImmatriculationResource(CertificatImmatriculationService certificatImmatriculationService) {
        this.certificatImmatriculationService = certificatImmatriculationService;
    }

    /**
     * {@code POST  /certificat-immatriculations} : Create a new certificatImmatriculation.
     *
     * @param certificatImmatriculationDTO the certificatImmatriculationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new certificatImmatriculationDTO, or with status {@code 400 (Bad Request)} if the certificatImmatriculation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/certificat-immatriculations")
    public ResponseEntity<CertificatImmatriculationDTO> createCertificatImmatriculation(@RequestBody CertificatImmatriculationDTO certificatImmatriculationDTO) throws URISyntaxException {
        log.debug("REST request to save CertificatImmatriculation : {}", certificatImmatriculationDTO);
        if (certificatImmatriculationDTO.getId() != null) {
            throw new BadRequestAlertException("A new certificatImmatriculation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CertificatImmatriculationDTO result = certificatImmatriculationService.save(certificatImmatriculationDTO);
        return ResponseEntity.created(new URI("/api/certificat-immatriculations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /certificat-immatriculations} : Updates an existing certificatImmatriculation.
     *
     * @param certificatImmatriculationDTO the certificatImmatriculationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificatImmatriculationDTO,
     * or with status {@code 400 (Bad Request)} if the certificatImmatriculationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the certificatImmatriculationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/certificat-immatriculations")
    public ResponseEntity<CertificatImmatriculationDTO> updateCertificatImmatriculation(@RequestBody CertificatImmatriculationDTO certificatImmatriculationDTO) throws URISyntaxException {
        log.debug("REST request to update CertificatImmatriculation : {}", certificatImmatriculationDTO);
        if (certificatImmatriculationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CertificatImmatriculationDTO result = certificatImmatriculationService.save(certificatImmatriculationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, certificatImmatriculationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /certificat-immatriculations} : get all the certificatImmatriculations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of certificatImmatriculations in body.
     */
    @GetMapping("/certificat-immatriculations")
    public ResponseEntity<List<CertificatImmatriculationDTO>> getAllCertificatImmatriculations(Pageable pageable) {
        log.debug("REST request to get a page of CertificatImmatriculations");
        Page<CertificatImmatriculationDTO> page = certificatImmatriculationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /certificat-immatriculations/:id} : get the "id" certificatImmatriculation.
     *
     * @param id the id of the certificatImmatriculationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the certificatImmatriculationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/certificat-immatriculations/{id}")
    public ResponseEntity<CertificatImmatriculationDTO> getCertificatImmatriculation(@PathVariable Long id) {
        log.debug("REST request to get CertificatImmatriculation : {}", id);
        Optional<CertificatImmatriculationDTO> certificatImmatriculationDTO = certificatImmatriculationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(certificatImmatriculationDTO);
    }

    @GetMapping("/certificat-immatriculations/by/{qr}")
    public ResponseEntity<CertificatImmatriculationDTO> getCertificatImmatriculationByQrMob(@PathVariable String qr) {
        log.debug("REST request to get CertificatImmatriculation : {}", qr);
        Optional<CertificatImmatriculationDTO> certificatImmatriculationDTO = certificatImmatriculationService.findOneByQr(qr);
        return ResponseUtil.wrapOrNotFound(certificatImmatriculationDTO);
    }
    @GetMapping("/certificat-immatriculations/by")
    public ResponseEntity<CertificatImmatriculationDTO> getCertificatImmatriculationByQr(@RequestParam("code") String qr) {
        log.debug("REST request to get CertificatImmatriculation : {}", qr);
        Optional<CertificatImmatriculationDTO> certificatImmatriculationDTO = certificatImmatriculationService.findOneByQr(qr);
        return ResponseUtil.wrapOrNotFound(certificatImmatriculationDTO);
    }


    /**
     * {@code DELETE  /certificat-immatriculations/:id} : delete the "id" certificatImmatriculation.
     *
     * @param id the id of the certificatImmatriculationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/certificat-immatriculations/{id}")
    public ResponseEntity<Void> deleteCertificatImmatriculation(@PathVariable Long id) {
        log.debug("REST request to delete CertificatImmatriculation : {}", id);
        certificatImmatriculationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
