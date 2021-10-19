package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.CarteWService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.CarteWDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.CarteW}.
 */
@RestController
@RequestMapping("/api")
public class CarteWResource {

    private final Logger log = LoggerFactory.getLogger(CarteWResource.class);

    private static final String ENTITY_NAME = "carteW";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarteWService carteWService;

    public CarteWResource(CarteWService carteWService) {
        this.carteWService = carteWService;
    }

    /**
     * {@code POST  /carte-ws} : Create a new carteW.
     *
     * @param carteWDTO the carteWDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carteWDTO, or with status {@code 400 (Bad Request)} if the carteW has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carte-ws")
    public ResponseEntity<CarteWDTO> createCarteW(@RequestBody CarteWDTO carteWDTO) throws Exception {
        log.debug("REST request to save CarteW : {}", carteWDTO);
        if (carteWDTO.getId() != null) {
            throw new BadRequestAlertException("A new carteW cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarteWDTO result = carteWService.save(carteWDTO);
        return ResponseEntity.created(new URI("/api/carte-ws/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code PUT  /carte-ws} : Updates an existing carteW.
     *
     * @param carteWDTO the carteWDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carteWDTO,
     * or with status {@code 400 (Bad Request)} if the carteWDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carteWDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carte-ws")
    public ResponseEntity<CarteWDTO> updateCarteW(@RequestBody CarteWDTO carteWDTO) throws Exception {
        log.debug("REST request to update CarteW : {}", carteWDTO);
        if (carteWDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarteWDTO result = carteWService.save(carteWDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ""))
            .body(result);
    }

    /**
     * {@code GET  /carte-ws} : get all the carteWS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carteWS in body.
     */
    @GetMapping("/carte-ws")
    public ResponseEntity<List<CarteWDTO>> getAllCarteWS(Pageable pageable) {
        log.debug("REST request to get a page of CarteWS");
        Page<CarteWDTO> page = carteWService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carte-ws/:id} : get the "id" carteW.
     *
     * @param id the id of the carteWDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carteWDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carte-ws/{id}")
    public ResponseEntity<CarteWDTO> getCarteW(@PathVariable Long id) {
        log.debug("REST request to get CarteW : {}", id);
        Optional<CarteWDTO> carteWDTO = carteWService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carteWDTO);
    }

    @GetMapping("/carte-ws/print/{id}")
    public void printCarteW(HttpServletResponse response, @PathVariable Long id) throws IOException, JRException {
        log.debug("REST request to get CarteW : {}", id);
        carteWService.findOnePrint(response, id);
    }

    /**
     * {@code DELETE  /carte-ws/:id} : delete the "id" carteW.
     *
     * @param id the id of the carteWDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carte-ws/{id}")
    public ResponseEntity<Void> deleteCarteW(@PathVariable Long id) {
        log.debug("REST request to delete CarteW : {}", id);
        carteWService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
