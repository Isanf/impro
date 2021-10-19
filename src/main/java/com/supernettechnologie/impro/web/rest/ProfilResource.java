package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.DocIdentificationPP;
import com.supernettechnologie.impro.domain.Profil;
import com.supernettechnologie.impro.service.ProfilService;
import com.supernettechnologie.impro.service.dto.OrganisationDTO;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.ProfilDTO;

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
 * REST controller for managing {@link com.supernettechnologie.impro.domain.Profil}.
 */
@RestController
@RequestMapping("/api")
public class ProfilResource {

    private final Logger log = LoggerFactory.getLogger(ProfilResource.class);

    private static final String ENTITY_NAME = "profil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfilService profilService;

    public ProfilResource(ProfilService profilService) {
        this.profilService = profilService;
    }

    /**
     * {@code POST  /profils} : Create a new profil.
     *
     * @param profilDTO the profilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profilDTO, or with status {@code 400 (Bad Request)} if the profil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profils")
    public ResponseEntity<ProfilDTO> createProfil(@Valid @RequestBody ProfilDTO profilDTO) throws URISyntaxException {
        log.debug("REST request to save Profil : {}", profilDTO);
        if (profilDTO.getId() != null) {
            throw new BadRequestAlertException("A new profil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfilDTO result = profilService.save(profilDTO);
        return ResponseEntity.created(new URI("/api/profils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code PUT  /profils} : Updates an existing profil.
     *
     * @param profilDTO the profilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profilDTO,
     * or with status {@code 400 (Bad Request)} if the profilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profils")
    public ResponseEntity<ProfilDTO> updateProfil(@Valid @RequestBody ProfilDTO profilDTO) throws URISyntaxException {
        log.debug("REST request to update Profil : {}", profilDTO);
        if (profilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfilDTO result = profilService.save(profilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code GET  /profils} : get all the profils.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profils in body.
     */
    @GetMapping("/profils")
    public ResponseEntity<List<ProfilDTO>> getAllProfils(Pageable pageable) {
        log.debug("REST request to get a page of Profils");
        Page<ProfilDTO> page = profilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/profils/AllCreatedBy")
    public ResponseEntity<List<ProfilDTO>> getAllCretedBy() {
        log.debug("REST request to get a page of Organisations");
        List<ProfilDTO> profilDTOList = profilService.findAllCreatedBy();
        System.out.println("*************************************Recuperation Profils*****************************");
        System.out.println("*************************************Recuperation Profils*****************************");
        System.out.println("*************************************Recuperation Profils*****************************");
        return ResponseEntity.ok().body(profilDTOList);
    }

    @GetMapping("/profils-list")
    public List<Profil> getAllProfilsList() {
        log.debug("REST request to get a page of Profils");
        List<Profil> list = profilService.findAll();
        return list;
    }

    @GetMapping("/profils/ByMyOrganisation")
    public ResponseEntity<List<ProfilDTO>> getAllProfilsByMyOrganisation(Long id, Pageable pageable) {
        log.debug("REST request to get a page of Profils");
        Page<ProfilDTO> page = profilService.findAllByMyOrganisation(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/profils/ForMyRevendeurs")
    public ResponseEntity<List<ProfilDTO>> getAllProfilsForMyRevendeurs(Long id, Pageable pageable) {
        log.debug("REST request to get a page of Profils");
        Page<ProfilDTO> page = profilService.findAllForMyRevendeurs(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profils/:id} : get the "id" profil.
     *
     * @param id the id of the profilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profils/{id}")
    public ResponseEntity<ProfilDTO> getProfil(@PathVariable Long id) {
        log.debug("REST request to get Profil : {}", id);
        Optional<ProfilDTO> profilDTO = profilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profilDTO);
    }

    /**
     * {@code DELETE  /profils/:id} : delete the "id" profil.
     *
     * @param id the id of the profilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profils/{id}")
    public ResponseEntity<Void> deleteProfil(@PathVariable Long id) {
        log.debug("REST request to delete Profil : {}", id);
        profilService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
