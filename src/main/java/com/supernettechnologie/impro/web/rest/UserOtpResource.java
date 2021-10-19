package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.UserOtpService;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.UserOtpDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.UserOtp}.
 */
@RestController
@RequestMapping("/api")
public class UserOtpResource {

    private final Logger log = LoggerFactory.getLogger(UserOtpResource.class);

    private static final String ENTITY_NAME = "userOtp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserOtpService userOtpService;

    public UserOtpResource(UserOtpService userOtpService) {
        this.userOtpService = userOtpService;
    }

    /**
     * {@code POST  /user-otps} : Create a new userOtp.
     *
     * @param userOtpDTO the userOtpDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userOtpDTO, or with status {@code 400 (Bad Request)} if the userOtp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-otps")
    public ResponseEntity<UserOtpDTO> createUserOtp(@RequestBody UserOtpDTO userOtpDTO) throws URISyntaxException {
        log.debug("REST request to save UserOtp : {}", userOtpDTO);
        if (userOtpDTO.getId() != null) {
            throw new BadRequestAlertException("A new userOtp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserOtpDTO result = userOtpService.save(userOtpDTO);
        return ResponseEntity.created(new URI("/api/user-otps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-otps} : Updates an existing userOtp.
     *
     * @param userOtpDTO the userOtpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userOtpDTO,
     * or with status {@code 400 (Bad Request)} if the userOtpDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userOtpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-otps")
    public ResponseEntity<UserOtpDTO> updateUserOtp(@RequestBody UserOtpDTO userOtpDTO) throws URISyntaxException {
        log.debug("REST request to update UserOtp : {}", userOtpDTO);
        if (userOtpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserOtpDTO result = userOtpService.save(userOtpDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userOtpDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-otps} : get all the userOtps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userOtps in body.
     */
    @GetMapping("/user-otps")
    public ResponseEntity<List<UserOtpDTO>> getAllUserOtps(Pageable pageable) {
        log.debug("REST request to get a page of UserOtps");
        Page<UserOtpDTO> page = userOtpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-otps/:id} : get the "id" userOtp.
     *
     * @param id the id of the userOtpDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userOtpDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-otps/{id}")
    public ResponseEntity<UserOtpDTO> getUserOtp(@PathVariable Long id) {
        log.debug("REST request to get UserOtp : {}", id);
        Optional<UserOtpDTO> userOtpDTO = userOtpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userOtpDTO);
    }

    @GetMapping("/user-otps/check/{otp}")
    public ResponseEntity<UserOtpDTO> checkUserOtp(@PathVariable Long otp) {
        log.debug("REST request to get UserOtp : {}", otp);
        Optional<UserOtpDTO> userOtpDTO = userOtpService.findOneCheck(otp);
        return ResponseUtil.wrapOrNotFound(userOtpDTO);
    }

    /**
     * {@code DELETE  /user-otps/:id} : delete the "id" userOtp.
     *
     * @param id the id of the userOtpDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-otps/{id}")
    public ResponseEntity<Void> deleteUserOtp(@PathVariable Long id) {
        log.debug("REST request to delete UserOtp : {}", id);
        userOtpService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
