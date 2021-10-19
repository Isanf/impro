package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.User;
import com.supernettechnologie.impro.service.FirstloginService;
import com.supernettechnologie.impro.service.UserService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.FirstloginDTO;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.Firstlogin}.
 */
@RestController
@RequestMapping("/api")
public class FirstloginResource {

    private final Logger log = LoggerFactory.getLogger(FirstloginResource.class);

    private static final String ENTITY_NAME = "firstlogin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FirstloginService firstloginService;

    @Autowired
    private UserService userService;

    public FirstloginResource(FirstloginService firstloginService) {
        this.firstloginService = firstloginService;
    }

    /**
     * {@code POST  /firstlogins} : Create a new firstlogin.
     *
     * @param firstloginDTO the firstloginDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new firstloginDTO, or with status {@code 400 (Bad Request)} if the firstlogin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/firstlogins")
    public ResponseEntity<FirstloginDTO> createFirstlogin(@RequestBody FirstloginDTO firstloginDTO) throws URISyntaxException {
        log.debug("REST request to save Firstlogin : {}", firstloginDTO);
        if (firstloginDTO.getId() != null) {
            throw new BadRequestAlertException("A new firstlogin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FirstloginDTO result = firstloginService.save(firstloginDTO);
        return ResponseEntity.created(new URI("/api/firstlogins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /firstlogins} : Updates an existing firstlogin.
     *
     * @param firstloginDTO the firstloginDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated firstloginDTO,
     * or with status {@code 400 (Bad Request)} if the firstloginDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the firstloginDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/firstlogins")
    public ResponseEntity<FirstloginDTO> updateFirstlogin(@RequestBody FirstloginDTO firstloginDTO) throws URISyntaxException {
        log.debug("REST request to update Firstlogin : {}", firstloginDTO);
        if (firstloginDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FirstloginDTO result = firstloginService.save(firstloginDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, firstloginDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /firstlogins} : get all the firstlogins.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of firstlogins in body.
     */
    @GetMapping("/firstlogins")
    public ResponseEntity<List<FirstloginDTO>> getAllFirstlogins(Pageable pageable) {
        log.debug("REST request to get a page of Firstlogins");
        Page<FirstloginDTO> page = firstloginService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/firstlogins/myfirstlogin")
    public String getMyfirstlogin() {

        String f = firstloginService.findMyfirstlogin();
        log.debug("********************************************************************** : {}",f);
        log.debug("********************************************************************** : {}",f);
        return f;
    }
    @GetMapping("/firstlogins/mobile")
    public String firstLogin() {
        return firstloginService.findMyfirstlogin();
    }

    @GetMapping("/firstlogins/user")
    public Optional<User> getMyUser() {
        Optional<User> login = firstloginService.findMyUser();
        log.debug("********************************************************************** : {}", login);
        return login;
    }

    /**
     * {@code GET  /firstlogins/:id} : get the "id" firstlogin.
     *
     * @param id the id of the firstloginDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the firstloginDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/firstlogins/{id}")
    public ResponseEntity<FirstloginDTO> getFirstlogin(@PathVariable Long id) {
        log.debug("REST request to get Firstlogin : {}", id);
        Optional<FirstloginDTO> firstloginDTO = firstloginService.findOne(id);
        return ResponseUtil.wrapOrNotFound(firstloginDTO);
    }

    /**
     * {@code DELETE  /firstlogins/:id} : delete the "id" firstlogin.
     *
     * @param id the id of the firstloginDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/firstlogins/{id}")
    public ResponseEntity<Void> deleteFirstlogin(@PathVariable Long id) {
        log.debug("REST request to delete Firstlogin : {}", id);
        firstloginService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
