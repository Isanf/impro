package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.CommandeCarnetSouche;
import com.supernettechnologie.impro.domain.InfoCommandeCarnetASouche;
import com.supernettechnologie.impro.domain.Organisation;
import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.repository.InfoCommandeCarnetASoucheRepository;
import com.supernettechnologie.impro.repository.OrganisationRepository;
import com.supernettechnologie.impro.repository.PersonnePhysiqueRepository;
import com.supernettechnologie.impro.security.AuthoritiesConstants;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.CommandeCarnetSoucheService;
import com.supernettechnologie.impro.service.InfoCommandeCarnetASoucheService;
import com.supernettechnologie.impro.service.dto.InfoCommandeCarnetASoucheDTO;
import com.supernettechnologie.impro.service.mapper.CommandeCarnetSoucheMapper;
import com.supernettechnologie.impro.service.mapper.InfoCommandeCarnetASoucheMapper;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.CommandeCarnetSoucheDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import net.sf.jasperreports.engine.JRException;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.CommandeCarnetSouche}.
 */
@RestController
@RequestMapping("/api")
public class CommandeCarnetSoucheResource {

    private final Logger log = LoggerFactory.getLogger(CommandeCarnetSoucheResource.class);

    private static final String ENTITY_NAME = "commandeCarnetSouche";

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandeCarnetSoucheService commandeCarnetSoucheService;
    @Autowired
    private InfoCommandeCarnetASoucheService infoCommandeCarnetASoucheService;
    @Autowired
    private OrganisationRepository organisationRepository;


    public CommandeCarnetSoucheResource(CommandeCarnetSoucheService commandeCarnetSoucheService) {
        this.commandeCarnetSoucheService = commandeCarnetSoucheService;
    }

    /**
     * {@code POST  /commande-carnet-souches} : Create a new commandeCarnetSouche.
     *
     * @param commandeCarnetSoucheDTO the commandeCarnetSoucheDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandeCarnetSoucheDTO, or with status {@code 400 (Bad Request)} if the commandeCarnetSouche has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commande-carnet-souches")
    public ResponseEntity<CommandeCarnetSoucheDTO> createCommandeCarnetSouche(@RequestBody CommandeCarnetSoucheDTO commandeCarnetSoucheDTO) throws URISyntaxException, IOException, JRException {
        log.debug("REST request to save CommandeCarnetSouche : {}", commandeCarnetSoucheDTO);
        if (commandeCarnetSoucheDTO.getId() != null) {
            throw new BadRequestAlertException("A new commandeCarnetSouche cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandeCarnetSoucheDTO result = commandeCarnetSoucheService.save(commandeCarnetSoucheDTO);
        return ResponseEntity.created(new URI("/api/commande-carnet-souches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code PUT  /commande-carnet-souches} : Updates an existing commandeCarnetSouche.
     *
     * @param commandeCarnetSoucheDTO the commandeCarnetSoucheDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeCarnetSoucheDTO,
     * or with status {@code 400 (Bad Request)} if the commandeCarnetSoucheDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandeCarnetSoucheDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commande-carnet-souches")
    public ResponseEntity<CommandeCarnetSoucheDTO> updateCommandeCarnetSouche(@RequestBody CommandeCarnetSoucheDTO commandeCarnetSoucheDTO) throws URISyntaxException, IOException, JRException {
        log.debug("REST request to update CommandeCarnetSouche : {}", commandeCarnetSoucheDTO);
        if (commandeCarnetSoucheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommandeCarnetSoucheDTO result = commandeCarnetSoucheService.save(commandeCarnetSoucheDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code GET  /commande-carnet-souches} : get all the commandeCarnetSouches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandeCarnetSouches in body.
     */
    @GetMapping("/commande-carnet-souches")
    public ResponseEntity<List<CommandeCarnetSoucheDTO>> getAllCommandeCarnetSouches(Pageable pageable) {
        log.debug("REST request to get a page of CommandeCarnetSouches");
        Page<CommandeCarnetSoucheDTO> page = commandeCarnetSoucheService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/commande-carnet-souches/admin")
    public ResponseEntity<List<CommandeCarnetSoucheDTO>> getsAllCommandeCarnetSouches(Pageable pageable) {
        log.debug("REST request to get a page of CommandeCarnetSouches");
        Page<CommandeCarnetSoucheDTO> page = commandeCarnetSoucheService.findAlls(pageable);
        List<CommandeCarnetSoucheDTO> commandeCarnetSoucheDTOS = page.getContent();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(commandeCarnetSoucheDTOS);
    }

    /**
     * {@code GET  /commande-carnet-souches/:id} : get the "id" commandeCarnetSouche.
     *
     * @param id the id of the commandeCarnetSoucheDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandeCarnetSoucheDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commande-carnet-souches/{id}")
    public ResponseEntity<CommandeCarnetSoucheDTO> getCommandeCarnetSouche(@PathVariable Long id) {
        log.debug("REST request to get CommandeCarnetSouche : {}", id);
        Optional<CommandeCarnetSoucheDTO> commandeCarnetSoucheDTO = commandeCarnetSoucheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commandeCarnetSoucheDTO);
    }

    @GetMapping("/commande-carnet-souches/admin/{id}")
    public void adminValiderCommandeCarnetSouche(@PathVariable Long id) throws Exception {
        commandeCarnetSoucheService.findOneValidAdmin(id);
    }

    @GetMapping("/commande-carnet-souches/facture/{id}")
    public HttpServletResponse printFacture(@PathVariable Long id, HttpServletResponse response) throws Exception {
        return commandeCarnetSoucheService.printFacture(id, response);
    }

    /**
     * {@code DELETE  /commande-carnet-souches/:id} : delete the "id" commandeCarnetSouche.
     *
     * @param id the id of the commandeCarnetSoucheDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commande-carnet-souches/{id}")
    public ResponseEntity<Void> deleteCommandeCarnetSouche(@PathVariable Long id) {
        log.debug("REST request to delete CommandeCarnetSouche : {}", id);
        commandeCarnetSoucheService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/commande-carnet-souches/valider/{id}")
    public HttpServletResponse validerCommandeCarnetSouche(@PathVariable Long id, HttpServletResponse response) throws IOException, JRException {
        response = commandeCarnetSoucheService.findOneValid(id, response);
        return response;
    }
}
