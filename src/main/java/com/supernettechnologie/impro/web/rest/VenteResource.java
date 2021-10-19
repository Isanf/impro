package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.repository.PersonnePhysiqueRepository;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.VenteService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.VenteDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
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

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.Vente}.
 */
@RestController
@RequestMapping("/api")
public class VenteResource {

    private final Logger log = LoggerFactory.getLogger(VenteResource.class);

    private static final String ENTITY_NAME = "vente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VenteService venteService;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    public VenteResource(VenteService venteService) {
        this.venteService = venteService;
    }

    /**
     * {@code POST  /ventes} : Create a new vente.
     *
     * @param venteDTO the venteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new venteDTO, or with status {@code 400 (Bad Request)} if the vente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ventes")
    public ResponseEntity<VenteDTO> createVente(@RequestBody VenteDTO venteDTO) throws URISyntaxException {
        log.debug("REST request to save Vente : {}", venteDTO);
        if (venteDTO.getId() != null) {
            throw new BadRequestAlertException("A new vente cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        venteDTO.setRevendeurId(personnePhysique.get().getOrganisation().getId());

        venteDTO.setDateVente(ZonedDateTime.now());

        VenteDTO result = venteService.save(venteDTO);
        return ResponseEntity.created(new URI("/api/ventes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ventes} : Updates an existing vente.
     *
     * @param venteDTO the venteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated venteDTO,
     * or with status {@code 400 (Bad Request)} if the venteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the venteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ventes")
    public ResponseEntity<VenteDTO> updateVente(@RequestBody VenteDTO venteDTO) throws URISyntaxException {
        log.debug("REST request to update Vente : {}", venteDTO);
        if (venteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VenteDTO result = venteService.save(venteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code GET  /ventes} : get all the ventes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ventes in body.
     */
    @GetMapping("/ventes")
    public ResponseEntity<List<VenteDTO>> getAllVentes(Pageable pageable) {
        log.debug("REST request to get a page of Ventes");
        Page<VenteDTO> page = venteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/allventes")
    public ResponseEntity<List<VenteDTO>> getAllVent() {
        log.debug("REST request to get a page of Ventes");
        List<VenteDTO> venteDTOS = venteService.findAlls();
        return ResponseEntity.ok().body(venteDTOS);
    }

    /**
     * {@code GET  /ventes/:id} : get the "id" vente.
     *
     * @param id the id of the venteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the venteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ventes/{id}")
    public ResponseEntity<VenteDTO> getVente(@PathVariable Long id) {
        log.debug("REST request to get Vente : {}", id);
        Optional<VenteDTO> venteDTO = venteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(venteDTO);
    }

    @GetMapping("/ventes/vente/{id}")
    public void getVenteCertificatVente(@PathVariable Long id) throws IOException, JRException {

        venteService.findCertificatVente(id);
    }
    @GetMapping("/ventes/vente/mobile/{id}")
    public ResponseEntity<InputStreamResource> certificatVente(@PathVariable Long id){

        return null;
    }

    @GetMapping("/ventes/conformite/{id}")
    public void getVenteCertificatConformite(@PathVariable Long id) throws IOException, JRException {

        venteService.findCertificatConformite(id);

    }

    @GetMapping("/ventes/facture/{id}")
    public void getVenteFacture(@PathVariable Long id) throws IOException, JRException {
        venteService.findVenteFacture(id);
    }


    @GetMapping("/personale/infos")
    public ResponseEntity<VenteDTO> getPersonnePhysique(@RequestParam("data") String qr) {
        log.debug("REST request to get PersonnePhysique : {}", qr);
        // J'ai utiliser venteDTO pour avoir facilement toutes les données
        Optional<VenteDTO> venteDTO = venteService.findOneQr(qr);
        return ResponseUtil.wrapOrNotFound(venteDTO);
    }

    @GetMapping("/personale/infos/chassis")
    public ResponseEntity<VenteDTO> getPersonneByChassis(@RequestParam("data") String qr) {
        log.debug("REST request to get PersonnePhysique : {}", qr);
        // J'ai utiliser venteDTO pour avoir facilement toutes les données
        Optional<VenteDTO> venteDTO = venteService.findOneByChassis(qr);
        return ResponseUtil.wrapOrNotFound(venteDTO);
    }

    /**
     * {@code DELETE  /ventes/:id} : delete the "id" vente.
     *
     * @param id the id of the venteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ventes/{id}")
    public ResponseEntity<Void> deleteVente(@PathVariable Long id) {
        log.debug("REST request to delete Vente : {}", id);
        venteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
