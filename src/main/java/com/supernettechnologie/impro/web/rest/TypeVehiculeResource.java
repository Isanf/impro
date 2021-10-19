package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.MarqueVehicule;
import com.supernettechnologie.impro.domain.TypeVehicule;
import com.supernettechnologie.impro.repository.MarqueVehiculeRepository;
import com.supernettechnologie.impro.repository.TypeVehiculeRepository;
import com.supernettechnologie.impro.service.TypeVehiculeService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.TypeVehiculeDTO;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.TypeVehicule}.
 */
@RestController
@RequestMapping("/api")
public class TypeVehiculeResource {

    private final Logger log = LoggerFactory.getLogger(TypeVehiculeResource.class);

    private static final String ENTITY_NAME = "typeVehicule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeVehiculeService typeVehiculeService;

    @Autowired
    private TypeVehiculeRepository typeVehiculeRepository;

    public TypeVehiculeResource(TypeVehiculeService typeVehiculeService) {
        this.typeVehiculeService = typeVehiculeService;
    }

    /**
     * {@code POST  /type-vehicules} : Create a new typeVehicule.
     *
     * @param typeVehiculeDTO the typeVehiculeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeVehiculeDTO, or with status {@code 400 (Bad Request)} if the typeVehicule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-vehicules")
    public ResponseEntity<TypeVehiculeDTO> createTypeVehicule(@RequestBody TypeVehiculeDTO typeVehiculeDTO) throws URISyntaxException {
        log.debug("REST request to save TypeVehicule : {}", typeVehiculeDTO);
        if (typeVehiculeDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeVehicule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeVehiculeDTO result = typeVehiculeService.save(typeVehiculeDTO);
        return ResponseEntity.created(new URI("/api/type-vehicules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/type-vehicules/file")
    public void createStockFile(@RequestParam("file") MultipartFile file) {

        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file.getBytes())));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                TypeVehicule t = new TypeVehicule();
                t.setCode(data[0]);
                t.setLibelle(data[1]);
                t.setNombrePlaque(Long.parseLong(data[2]));
                t.setEstCycleMoteur(Boolean.parseBoolean(data[3]));
                typeVehiculeRepository.save(t);
                log.debug("**************************** : {}", t.getLibelle());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@code PUT  /type-vehicules} : Updates an existing typeVehicule.
     *
     * @param typeVehiculeDTO the typeVehiculeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeVehiculeDTO,
     * or with status {@code 400 (Bad Request)} if the typeVehiculeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeVehiculeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-vehicules")
    public ResponseEntity<TypeVehiculeDTO> updateTypeVehicule(@RequestBody TypeVehiculeDTO typeVehiculeDTO) throws URISyntaxException {
        log.debug("REST request to update TypeVehicule : {}", typeVehiculeDTO);
        if (typeVehiculeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeVehiculeDTO result = typeVehiculeService.save(typeVehiculeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeVehiculeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-vehicules} : get all the typeVehicules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeVehicules in body.
     */
    @GetMapping("/type-vehicules")
    public ResponseEntity<List<TypeVehiculeDTO>> getAllTypeVehicules(Pageable pageable) {
        log.debug("REST request to get a page of TypeVehicules");
        Page<TypeVehiculeDTO> page = typeVehiculeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-vehicules/:id} : get the "id" typeVehicule.
     *
     * @param id the id of the typeVehiculeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeVehiculeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-vehicules/{id}")
    public ResponseEntity<TypeVehiculeDTO> getTypeVehicule(@PathVariable Long id) {
        log.debug("REST request to get TypeVehicule : {}", id);
        Optional<TypeVehiculeDTO> typeVehiculeDTO = typeVehiculeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeVehiculeDTO);
    }

    /**
     * {@code DELETE  /type-vehicules/:id} : delete the "id" typeVehicule.
     *
     * @param id the id of the typeVehiculeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-vehicules/{id}")
    public ResponseEntity<Void> deleteTypeVehicule(@PathVariable Long id) {
        log.debug("REST request to delete TypeVehicule : {}", id);
        typeVehiculeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
