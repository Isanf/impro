package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.UserDeviceIdService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.UserDeviceIdDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.UserDeviceId}.
 */
@RestController
@RequestMapping("/api")
public class UserDeviceIdResource {

    private final Logger log = LoggerFactory.getLogger(UserDeviceIdResource.class);

    private static final String ENTITY_NAME = "userDeviceId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserDeviceIdService userDeviceIdService;

    public UserDeviceIdResource(UserDeviceIdService userDeviceIdService) {
        this.userDeviceIdService = userDeviceIdService;
    }

    /**
     * {@code POST  /user-device-ids} : Create a new userDeviceId.
     *
     * @param userDeviceIdDTO the userDeviceIdDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDeviceIdDTO, or with status {@code 400 (Bad Request)} if the userDeviceId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-device-ids")
    public ResponseEntity<UserDeviceIdDTO> createUserDeviceId(@RequestBody UserDeviceIdDTO userDeviceIdDTO) throws URISyntaxException {
        log.debug("REST request to save UserDeviceId : {}", userDeviceIdDTO);
        if (userDeviceIdDTO.getId() != null) {
            throw new BadRequestAlertException("A new userDeviceId cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDeviceIdDTO result = userDeviceIdService.save(userDeviceIdDTO);
        return ResponseEntity.created(new URI("/api/user-device-ids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-device-ids} : Updates an existing userDeviceId.
     *
     * @param userDeviceIdDTO the userDeviceIdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDeviceIdDTO,
     * or with status {@code 400 (Bad Request)} if the userDeviceIdDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDeviceIdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-device-ids")
    public ResponseEntity<UserDeviceIdDTO> updateUserDeviceId(@RequestBody UserDeviceIdDTO userDeviceIdDTO) throws URISyntaxException {
        log.debug("REST request to update UserDeviceId : {}", userDeviceIdDTO);
        if (userDeviceIdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserDeviceIdDTO result = userDeviceIdService.save(userDeviceIdDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDeviceIdDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-device-ids} : get all the userDeviceIds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userDeviceIds in body.
     */
    @GetMapping("/user-device-ids")
    public List<UserDeviceIdDTO> getAllUserDeviceIds() {
        log.debug("REST request to get all UserDeviceIds");
        return userDeviceIdService.findAll();
    }

    /**
     * {@code GET  /user-device-ids/:id} : get the "id" userDeviceId.
     *
     * @param id the id of the userDeviceIdDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDeviceIdDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-device-ids/{id}")
    public ResponseEntity<UserDeviceIdDTO> getUserDeviceId(@PathVariable Long id) {
        log.debug("REST request to get UserDeviceId : {}", id);
        Optional<UserDeviceIdDTO> userDeviceIdDTO = userDeviceIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDeviceIdDTO);
    }

    @GetMapping("/user-device-ids/device/{name}")
    public ResponseEntity<UserDeviceIdDTO> getUserDeviceName(@PathVariable String name) {
        log.debug("REST request to get UserDeviceId : {}", name);
        Optional<UserDeviceIdDTO> userDeviceIdDTO = userDeviceIdService.findOneName(name);
        return ResponseUtil.wrapOrNotFound(userDeviceIdDTO);
    }

    /**
     * {@code DELETE  /user-device-ids/:id} : delete the "id" userDeviceId.
     *
     * @param id the id of the userDeviceIdDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-device-ids/{id}")
    public ResponseEntity<Void> deleteUserDeviceId(@PathVariable Long id) {
        log.debug("REST request to delete UserDeviceId : {}", id);
        userDeviceIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
