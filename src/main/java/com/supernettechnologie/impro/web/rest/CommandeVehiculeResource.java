package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.repository.PersonnePhysiqueRepository;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.CommandeVehiculeService;
import com.supernettechnologie.impro.service.InfoCommandeVehiculeService;
import com.supernettechnologie.impro.service.dto.CommandeVehiculeDTO;
import com.supernettechnologie.impro.service.dto.InfoCommandeVehiculeDTO;
import com.supernettechnologie.impro.service.util.RandomUtil;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.CommandeVehicule}.
 */
@RestController
@RequestMapping("/api")
public class CommandeVehiculeResource {

    private final Logger log = LoggerFactory.getLogger(CommandeVehiculeResource.class);

    private static final String ENTITY_NAME = "commandeVehicule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandeVehiculeService commandeVehiculeService;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    @Autowired
    private InfoCommandeVehiculeService infoCommandeVehiculeService;


    public CommandeVehiculeResource(CommandeVehiculeService commandeVehiculeService) {
        this.commandeVehiculeService = commandeVehiculeService;
    }

    /**
     * {@code POST  /commande-vehicules} : Create a new commandeVehicule.
     *
     * @param commandeVehiculeDTO the commandeVehiculeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandeVehiculeDTO, or with status {@code 400 (Bad Request)} if the commandeVehicule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commande-vehicules")
    public ResponseEntity<CommandeVehiculeDTO> createCommandeVehicule(@RequestBody CommandeVehiculeDTO commandeVehiculeDTO) throws URISyntaxException {
        log.debug("REST request to save CommandeVehicule : {}", commandeVehiculeDTO);
        if (commandeVehiculeDTO.getId() != null) {
            throw new BadRequestAlertException("A new commandeVehicule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        commandeVehiculeDTO.setRevendeurId(personnePhysique.get().getOrganisation().getId());
        commandeVehiculeDTO.setDateCommande(ZonedDateTime.now());
        commandeVehiculeDTO.setNumeroCommandeVehicule(RandomUtil.generateRandomSerialNumericString());
        List<InfoCommandeVehiculeDTO> infoCommandeVehiculeDTOS = commandeVehiculeDTO.getInfoCommandeVehiculeDTO();
        CommandeVehiculeDTO result = commandeVehiculeService.save(commandeVehiculeDTO);
        for (int i = 0; i<infoCommandeVehiculeDTOS.size(); i++){
            infoCommandeVehiculeDTOS.get(i).setDateCommande(ZonedDateTime.now());
            infoCommandeVehiculeDTOS.get(i).setCommandeVehiculeId(result.getId());
            infoCommandeVehiculeDTOS.get(i).setNumeroCommande(RandomUtil.generateRandomSerialNumericString());//Genere aleatoirement les numeros d'info-commande
            infoCommandeVehiculeService.save(infoCommandeVehiculeDTOS.get(i));
        }
        return ResponseEntity.created(new URI("/api/commande-vehicules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commande-vehicules} : Updates an existing commandeVehicule.
     *
     * @param commandeVehiculeDTO the commandeVehiculeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeVehiculeDTO,
     * or with status {@code 400 (Bad Request)} if the commandeVehiculeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandeVehiculeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commande-vehicules")
    public ResponseEntity<CommandeVehiculeDTO> updateCommandeVehicule(@RequestBody CommandeVehiculeDTO commandeVehiculeDTO) throws URISyntaxException {
        log.debug("REST request to update CommandeVehicule : {}", commandeVehiculeDTO);
        if (commandeVehiculeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommandeVehiculeDTO result = commandeVehiculeService.save(commandeVehiculeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandeVehiculeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commande-vehicules} : get all the commandeVehicules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandeVehicules in body.
     */
    @GetMapping("/commande-vehicules")
    public ResponseEntity<List<CommandeVehiculeDTO>> getAllCommandeVehicules(Pageable pageable) {
        log.debug("REST request to get a page of CommandeVehicules");
        Page<CommandeVehiculeDTO> page = commandeVehiculeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/commande-vehicules1")
    public ResponseEntity<List<CommandeVehiculeDTO>> getAllCommandeVehicules1(Pageable pageable) {
        log.debug("REST request to get a page of CommandeVehicules");
        Page<CommandeVehiculeDTO> page = commandeVehiculeService.findAll1(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/commande-vehicules/notdelivery")
    public ResponseEntity<List<CommandeVehiculeDTO>> getAllCommandeVehiculesNotDelivery(Pageable pageable) {
        log.debug("REST request to get a page of CommandeVehicules");
        Page<CommandeVehiculeDTO> page = commandeVehiculeService.findAllNotDelivery(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /commande-vehicules/:id} : get the "id" commandeVehicule.
     *
     * @param id the id of the commandeVehiculeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandeVehiculeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commande-vehicules/{id}")
    public ResponseEntity<CommandeVehiculeDTO> getCommandeVehicule(@PathVariable Long id) {
        log.debug("REST request to get CommandeVehicule : {}", id);
        Optional<CommandeVehiculeDTO> commandeVehiculeDTO = commandeVehiculeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commandeVehiculeDTO);
    }
    @GetMapping("/allcommandes")
    public ResponseEntity<List<CommandeVehiculeDTO>> getAllCommand() {
        log.debug("REST request to get a page of Ventes");
        List<CommandeVehiculeDTO> commandeVehiculeDTOS = commandeVehiculeService.findAlls();
        return ResponseEntity.ok().body(commandeVehiculeDTOS);
    }

    /**
     * {@code DELETE  /commande-vehicules/:id} : delete the "id" commandeVehicule.
     *
     * @param id the id of the commandeVehiculeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commande-vehicules/{id}")
    public ResponseEntity<Void> deleteCommandeVehicule(@PathVariable Long id) {
        log.debug("REST request to delete CommandeVehicule : {}", id);
        commandeVehiculeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
