package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.CommandeCarnetSouche;
import com.supernettechnologie.impro.domain.InfoCommandeCarnetASouche;
import com.supernettechnologie.impro.domain.TypeCarnet;
import com.supernettechnologie.impro.repository.CarnetASoucheRepository;
import com.supernettechnologie.impro.repository.CommandeCarnetSoucheRepository;
import com.supernettechnologie.impro.repository.InfoCommandeCarnetASoucheRepository;
import com.supernettechnologie.impro.repository.TypeCarnetRepository;
import com.supernettechnologie.impro.service.CommandeCarnetSoucheService;
import com.supernettechnologie.impro.service.InfoCommandeCarnetASoucheService;
import com.supernettechnologie.impro.service.LivraisonCarnetSoucheService;
import com.supernettechnologie.impro.service.dto.CarnetASoucheDTO;
import com.supernettechnologie.impro.service.dto.CommandeCarnetSoucheDTO;
import com.supernettechnologie.impro.service.dto.InfoCommandeCarnetASoucheDTO;
import com.supernettechnologie.impro.service.mapper.CarnetASoucheMapper;
import com.supernettechnologie.impro.service.mapper.InfoCommandeCarnetASoucheMapper;
import com.supernettechnologie.impro.service.mapper.TypeCarnetMapper;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.LivraisonCarnetSoucheDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.LivraisonCarnetSouche}.
 */
@RestController
@RequestMapping("/api")
public class LivraisonCarnetSoucheResource {

    private final Logger log = LoggerFactory.getLogger(LivraisonCarnetSoucheResource.class);

    private static final String ENTITY_NAME = "livraisonCarnetSouche";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LivraisonCarnetSoucheService livraisonCarnetSoucheService;

    @Autowired
    private CommandeCarnetSoucheService commandeCarnetSoucheService;
    @Autowired
    private InfoCommandeCarnetASoucheService infoCommandeCarnetASoucheService;
    @Autowired
    private InfoCommandeCarnetASoucheRepository infoCommandeCarnetASoucheRepository;
    @Autowired
    private InfoCommandeCarnetASoucheMapper infoCommandeCarnetASoucheMapper;
    @Autowired
    private CarnetASoucheRepository carnetASoucheRepository;
    @Autowired
    private CarnetASoucheMapper carnetASoucheMapper;
    @Autowired
    private TypeCarnetRepository typeCarnetRepository;
    @Autowired
    private TypeCarnetMapper typeCarnetMapper;

    public LivraisonCarnetSoucheResource(LivraisonCarnetSoucheService livraisonCarnetSoucheService) {
        this.livraisonCarnetSoucheService = livraisonCarnetSoucheService;
    }

    /**
     * {@code POST  /livraison-carnet-souches} : Create a new livraisonCarnetSouche.
     *
     * @param livraisonCarnetSoucheDTO the livraisonCarnetSoucheDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livraisonCarnetSoucheDTO, or with status {@code 400 (Bad Request)} if the livraisonCarnetSouche has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/livraison-carnet-souches")
    public ResponseEntity<LivraisonCarnetSoucheDTO> createLivraisonCarnetSouche(@RequestBody LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO) throws URISyntaxException, IOException, JRException {
        log.debug("REST request to save LivraisonCarnetSouche : {}", livraisonCarnetSoucheDTO);
        if (livraisonCarnetSoucheDTO.getId() != null) {
            throw new BadRequestAlertException("A new livraisonCarnetSouche cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LivraisonCarnetSoucheDTO result = livraisonCarnetSoucheService.save(livraisonCarnetSoucheDTO);
        return ResponseEntity.created(new URI("/api/livraison-carnet-souches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/livraison-carnet-souches/{id}/livrer")
    public ResponseEntity<LivraisonCarnetSoucheDTO> livrerCommande(@PathVariable Long id) {
        Optional<CommandeCarnetSoucheDTO> commandeCarnetSouche = commandeCarnetSoucheService.findOne(id);

        List<InfoCommandeCarnetASouche> infoCommandeCarnetASouche = infoCommandeCarnetASoucheRepository.findByCommandeCarnetSoucheIdAndEstDeliverFalse(commandeCarnetSouche.get().getId());
        List<InfoCommandeCarnetASoucheDTO> infoCommandeCarnetASoucheDTOS = infoCommandeCarnetASoucheMapper.toDto(infoCommandeCarnetASouche);
        List<InfoCommandeCarnetASoucheDTO> infoCommandeCarnetASoucheDTOList = new ArrayList<>();
        List<CarnetASoucheDTO> carnetASoucheDTOS = new ArrayList<>();
        for (InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO : infoCommandeCarnetASoucheDTOS){
            infoCommandeCarnetASoucheDTO.setLibelle(typeCarnetRepository.findById(infoCommandeCarnetASoucheDTO.getTypeCarnetId()).map(typeCarnetMapper::toDto).get().getLibelle());
            int taille = infoCommandeCarnetASoucheDTO.getQuantiteCommande().intValue();
            Pageable limit = PageRequest.of(0, taille);
            Page<CarnetASoucheDTO> carnetASoucheDTOPage = carnetASoucheRepository.findAllByTypeCarnetIdAndConcessionnaireIsNull(limit, infoCommandeCarnetASoucheDTO.getTypeCarnetId()).map(carnetASoucheMapper::toDto);
            carnetASoucheDTOS.addAll(carnetASoucheDTOPage.getContent());
            infoCommandeCarnetASoucheDTOList.add(infoCommandeCarnetASoucheDTO);
        }

        LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO = new LivraisonCarnetSoucheDTO();
        livraisonCarnetSoucheDTO.setCommandeCarnetSoucheDTO(commandeCarnetSouche.get());
        livraisonCarnetSoucheDTO.setInfoCommandeCarnetASoucheDTO(infoCommandeCarnetASoucheDTOList);
        livraisonCarnetSoucheDTO.setCarnetASoucheDTO(carnetASoucheDTOS);
        System.out.println(livraisonCarnetSoucheDTO);
        return ResponseEntity.ok().body(livraisonCarnetSoucheDTO);
    }

    /**
     * {@code PUT  /livraison-carnet-souches} : Updates an existing livraisonCarnetSouche.
     *
     * @param livraisonCarnetSoucheDTO the livraisonCarnetSoucheDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livraisonCarnetSoucheDTO,
     * or with status {@code 400 (Bad Request)} if the livraisonCarnetSoucheDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the livraisonCarnetSoucheDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/livraison-carnet-souches")
    public ResponseEntity<LivraisonCarnetSoucheDTO> updateLivraisonCarnetSouche(@RequestBody LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO) throws URISyntaxException, IOException, JRException {
        log.debug("REST request to update LivraisonCarnetSouche : {}", livraisonCarnetSoucheDTO);
        if (livraisonCarnetSoucheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LivraisonCarnetSoucheDTO result = livraisonCarnetSoucheService.save(livraisonCarnetSoucheDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livraisonCarnetSoucheDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /livraison-carnet-souches} : get all the livraisonCarnetSouches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of livraisonCarnetSouches in body.
     */
    @GetMapping("/livraison-carnet-souches")
    public ResponseEntity<List<LivraisonCarnetSoucheDTO>> getAllLivraisonCarnetSouches(Pageable pageable) {
        log.debug("REST request to get a page of LivraisonCarnetSouches");
        Page<LivraisonCarnetSoucheDTO> page = livraisonCarnetSoucheService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /livraison-carnet-souches/:id} : get the "id" livraisonCarnetSouche.
     *
     * @param id the id of the livraisonCarnetSoucheDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the livraisonCarnetSoucheDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/livraison-carnet-souches/{id}")
    public ResponseEntity<LivraisonCarnetSoucheDTO> getLivraisonCarnetSouche(@PathVariable Long id) {
        log.debug("REST request to get LivraisonCarnetSouche : {}", id);
        Optional<LivraisonCarnetSoucheDTO> livraisonCarnetSoucheDTO = livraisonCarnetSoucheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(livraisonCarnetSoucheDTO);
    }


    @GetMapping("/livraison-carnet-souchesFind4Org")
    public int get4Org(
        @RequestParam(value = "fromDate") LocalDate fromDate,
        @RequestParam(value = "toDate") LocalDate toDate,
        @RequestParam(value = "idOrg") Long idOrg
    ){
        Instant from = fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant to = toDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant();
        int totIm;
        totIm = livraisonCarnetSoucheService.find4Org(from.atZone(ZoneId.of("GMT")), to.atZone(ZoneId.of("GMT")), idOrg);
        log.debug(" ************************************** Resource *************************** : {}", totIm);
        log.debug(" ************************************** Resource *************************** : {}", totIm);
        return  totIm;
    }


    /**
     * {@code DELETE  /livraison-carnet-souches/:id} : delete the "id" livraisonCarnetSouche.
     *
     * @param id the id of the livraisonCarnetSoucheDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/livraison-carnet-souches/{id}")
    public ResponseEntity<Void> deleteLivraisonCarnetSouche(@PathVariable Long id) {
        log.debug("REST request to delete LivraisonCarnetSouche : {}", id);
        livraisonCarnetSoucheService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
