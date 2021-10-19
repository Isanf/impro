package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.Nation;
import com.supernettechnologie.impro.service.NationService;
import com.supernettechnologie.impro.service.mapper.NationMapper;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.NationDTO;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.Nation}.
 */
@RestController
@RequestMapping("/api")
public class NationResource {

    private final Logger log = LoggerFactory.getLogger(NationResource.class);

    private static final String ENTITY_NAME = "nation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NationService nationService;

    private final NationMapper nationMapper;

    public NationResource(NationService nationService, NationMapper nationMapper) {
        this.nationService = nationService;
        this.nationMapper = nationMapper;
    }

    /**
     * {@code POST  /nations} : Create a new nation.
     *
     * @param nationDTO the nationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nationDTO, or with status {@code 400 (Bad Request)} if the nation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nations")
    public ResponseEntity<NationDTO> createNation(@RequestBody NationDTO nationDTO) throws URISyntaxException {
        log.debug("REST request to save Nation : {}", nationDTO);
        if (nationDTO.getId() != null) {
            throw new BadRequestAlertException("A new nation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NationDTO result = nationService.save(nationDTO);
        return ResponseEntity.created(new URI("/api/nations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nations} : Updates an existing nation.
     *
     * @param nationDTO the nationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nationDTO,
     * or with status {@code 400 (Bad Request)} if the nationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nations")
    public ResponseEntity<NationDTO> updateNation(@RequestBody NationDTO nationDTO) throws URISyntaxException {
        log.debug("REST request to update Nation : {}", nationDTO);
        if (nationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NationDTO result = nationService.save(nationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nations} : get all the nations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nations in body.
     */
    @GetMapping("/nations")
    public ResponseEntity<List<NationDTO>> getAllNations(Pageable pageable) {
        log.debug("REST request to get a page of Nations");
        Page<NationDTO> page = nationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/nationslist")
    public List<NationDTO> getAllNations() {
        log.debug("REST request to get a page of Nations");
        List<Nation> nations = new ArrayList<>();
        nations = nationService.findAlllist();
        return nationMapper.toDto(nations);
    }

    /**
     * {@code GET  /nations/:id} : get the "id" nation.
     *
     * @param id the id of the nationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nations/{id}")
    public ResponseEntity<NationDTO> getNation(@PathVariable Long id) {
        log.debug("REST request to get Nation : {}", id);
        Optional<NationDTO> nationDTO = nationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nationDTO);
    }

    /**
     * {@code DELETE  /nations/:id} : delete the "id" nation.
     *
     * @param id the id of the nationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nations/{id}")
    public ResponseEntity<Void> deleteNation(@PathVariable Long id) {
        log.debug("REST request to delete Nation : {}", id);
        nationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
