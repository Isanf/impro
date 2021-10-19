package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.DocIdentificationPM;
import com.supernettechnologie.impro.domain.DocIdentificationPP;
import com.supernettechnologie.impro.service.DocIdentificationPMService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.DocIdentificationPMDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.DocIdentificationPM}.
 */
@RestController
@RequestMapping("/api")
public class DocIdentificationPMResource {

    private final Logger log = LoggerFactory.getLogger(DocIdentificationPMResource.class);

    private static final String ENTITY_NAME = "docIdentificationPM";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocIdentificationPMService docIdentificationPMService;

    public DocIdentificationPMResource(DocIdentificationPMService docIdentificationPMService) {
        this.docIdentificationPMService = docIdentificationPMService;
    }

    /**
     * {@code POST  /doc-identification-pms} : Create a new docIdentificationPM.
     *
     * @param docIdentificationPMDTO the docIdentificationPMDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docIdentificationPMDTO, or with status {@code 400 (Bad Request)} if the docIdentificationPM has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doc-identification-pms")
    public ResponseEntity<DocIdentificationPMDTO> createDocIdentificationPM(@RequestBody DocIdentificationPMDTO docIdentificationPMDTO) throws URISyntaxException {
        log.debug("REST request to save DocIdentificationPM : {}", docIdentificationPMDTO);
        if (docIdentificationPMDTO.getId() != null) {
            throw new BadRequestAlertException("A new docIdentificationPM cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocIdentificationPMDTO result = docIdentificationPMService.save(docIdentificationPMDTO);
        return ResponseEntity.created(new URI("/api/doc-identification-pms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doc-identification-pms} : Updates an existing docIdentificationPM.
     *
     * @param docIdentificationPMDTO the docIdentificationPMDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docIdentificationPMDTO,
     * or with status {@code 400 (Bad Request)} if the docIdentificationPMDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the docIdentificationPMDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doc-identification-pms")
    public ResponseEntity<DocIdentificationPMDTO> updateDocIdentificationPM(@RequestBody DocIdentificationPMDTO docIdentificationPMDTO) throws URISyntaxException {
        log.debug("REST request to update DocIdentificationPM : {}", docIdentificationPMDTO);
        if (docIdentificationPMDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocIdentificationPMDTO result = docIdentificationPMService.save(docIdentificationPMDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docIdentificationPMDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doc-identification-pms} : get all the docIdentificationPMS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docIdentificationPMS in body.
     */
    @GetMapping("/doc-identification-pms")
    public ResponseEntity<List<DocIdentificationPMDTO>> getAllDocIdentificationPMS(Pageable pageable) {
        log.debug("REST request to get a page of DocIdentificationPMS");
        Page<DocIdentificationPMDTO> page = docIdentificationPMService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doc-identification-pms/:id} : get the "id" docIdentificationPM.
     *
     * @param id the id of the docIdentificationPMDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docIdentificationPMDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doc-identification-pms/{id}")
    public ResponseEntity<DocIdentificationPMDTO> getDocIdentificationPM(@PathVariable Long id) {
        log.debug("REST request to get DocIdentificationPM : {}", id);
        Optional<DocIdentificationPMDTO> docIdentificationPMDTO = docIdentificationPMService.findOne(id);
        return ResponseUtil.wrapOrNotFound(docIdentificationPMDTO);
    }

    @GetMapping("/doc-identification-pms-ifu/{ifu}")
    public DocIdentificationPM getDocIdPMByIFU(@PathVariable String ifu) {
        log.debug("REST request to get DocIdentificationPP : {}", ifu);
        return docIdentificationPMService.findOneByIFU(ifu);
    }

    /**
     * {@code DELETE  /doc-identification-pms/:id} : delete the "id" docIdentificationPM.
     *
     * @param id the id of the docIdentificationPMDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doc-identification-pms/{id}")
    public ResponseEntity<Void> deleteDocIdentificationPM(@PathVariable Long id) {
        log.debug("REST request to delete DocIdentificationPM : {}", id);
        docIdentificationPMService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
