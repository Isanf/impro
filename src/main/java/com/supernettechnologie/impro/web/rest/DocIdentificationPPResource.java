package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.DocIdentificationPP;
import com.supernettechnologie.impro.service.DocIdentificationPPService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.DocIdentificationPPDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.DocIdentificationPP}.
 */
@RestController
@RequestMapping("/api")
public class DocIdentificationPPResource {

    private final Logger log = LoggerFactory.getLogger(DocIdentificationPPResource.class);

    private static final String ENTITY_NAME = "docIdentificationPP";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocIdentificationPPService docIdentificationPPService;

    public DocIdentificationPPResource(DocIdentificationPPService docIdentificationPPService) {
        this.docIdentificationPPService = docIdentificationPPService;
    }

    /**
     * {@code POST  /doc-identification-pps} : Create a new docIdentificationPP.
     *
     * @param docIdentificationPPDTO the docIdentificationPPDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docIdentificationPPDTO, or with status {@code 400 (Bad Request)} if the docIdentificationPP has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doc-identification-pps")
    public ResponseEntity<DocIdentificationPPDTO> createDocIdentificationPP(@RequestBody DocIdentificationPPDTO docIdentificationPPDTO) throws URISyntaxException {
        log.debug("REST request to save DocIdentificationPP : {}", docIdentificationPPDTO);
        if (docIdentificationPPDTO.getId() != null) {
            throw new BadRequestAlertException("A new docIdentificationPP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocIdentificationPPDTO result = docIdentificationPPService.save(docIdentificationPPDTO);
        return ResponseEntity.created(new URI("/api/doc-identification-pps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doc-identification-pps} : Updates an existing docIdentificationPP.
     *
     * @param docIdentificationPPDTO the docIdentificationPPDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docIdentificationPPDTO,
     * or with status {@code 400 (Bad Request)} if the docIdentificationPPDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the docIdentificationPPDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doc-identification-pps")
    public ResponseEntity<DocIdentificationPPDTO> updateDocIdentificationPP(@RequestBody DocIdentificationPPDTO docIdentificationPPDTO) throws URISyntaxException {
        log.debug("REST request to update DocIdentificationPP : {}", docIdentificationPPDTO);
        if (docIdentificationPPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocIdentificationPPDTO result = docIdentificationPPService.save(docIdentificationPPDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docIdentificationPPDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doc-identification-pps} : get all the docIdentificationPPS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docIdentificationPPS in body.
     */
    @GetMapping("/doc-identification-pps")
    public ResponseEntity<List<DocIdentificationPPDTO>> getAllDocIdentificationPPS(Pageable pageable) {
        log.debug("REST request to get a page of DocIdentificationPPS");
        Page<DocIdentificationPPDTO> page = docIdentificationPPService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/doc-identification-pps-list")
    public List<DocIdentificationPP> getAllDocIdentificationPPSList() {
        log.debug("REST request to get a page of DocIdentificationPPS");
        List<DocIdentificationPP> list = docIdentificationPPService.findAll();
        return list;
    }

    /**
     * {@code GET  /doc-identification-pps/:id} : get the "id" docIdentificationPP.
     *
     * @param id the id of the docIdentificationPPDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docIdentificationPPDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doc-identification-pps/{id}")
    public ResponseEntity<DocIdentificationPPDTO> getDocIdentificationPP(@PathVariable Long id) {
        log.debug("REST request to get DocIdentificationPP : {}", id);
        Optional<DocIdentificationPPDTO> docIdentificationPPDTO = docIdentificationPPService.findOne(id);
        return ResponseUtil.wrapOrNotFound(docIdentificationPPDTO);
    }


    @GetMapping("/doc-identification-pps-nip/{nip}")
    public DocIdentificationPP getDocIdPPByNip(@PathVariable String nip) {
        log.debug("REST request to get DocIdentificationPP : {}", nip);
        return docIdentificationPPService.findOneByNip(nip);
    }

    /**
     * {@code DELETE  /doc-identification-pps/:id} : delete the "id" docIdentificationPP.
     *
     * @param id the id of the docIdentificationPPDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doc-identification-pps/{id}")
    public ResponseEntity<Void> deleteDocIdentificationPP(@PathVariable Long id) {
        log.debug("REST request to delete DocIdentificationPP : {}", id);

        docIdentificationPPService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
