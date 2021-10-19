package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.OrganisationLocalite;
import com.supernettechnologie.impro.repository.OrganisationLocaliteRepository;
import com.supernettechnologie.impro.service.dto.OrganisationLocaliteDTO;
import com.supernettechnologie.impro.service.mapper.OrganisationLocaliteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OrganisationLocalite}.
 */
@Service
@Transactional
public class OrganisationLocaliteService {

    private final Logger log = LoggerFactory.getLogger(OrganisationLocaliteService.class);

    private final OrganisationLocaliteRepository organisationLocaliteRepository;

    private final OrganisationLocaliteMapper organisationLocaliteMapper;

    public OrganisationLocaliteService(OrganisationLocaliteRepository organisationLocaliteRepository, OrganisationLocaliteMapper organisationLocaliteMapper) {
        this.organisationLocaliteRepository = organisationLocaliteRepository;
        this.organisationLocaliteMapper = organisationLocaliteMapper;
    }

    /**
     * Save a organisationLocalite.
     *
     * @param organisationLocaliteDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganisationLocaliteDTO save(OrganisationLocaliteDTO organisationLocaliteDTO) {
        log.debug("Request to save OrganisationLocalite : {}", organisationLocaliteDTO);
        OrganisationLocalite organisationLocalite = organisationLocaliteMapper.toEntity(organisationLocaliteDTO);
        organisationLocalite = organisationLocaliteRepository.save(organisationLocalite);
        return organisationLocaliteMapper.toDto(organisationLocalite);
    }

    /**
     * Get all the organisationLocalites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganisationLocaliteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganisationLocalites");
        return organisationLocaliteRepository.findAll(pageable)
            .map(organisationLocaliteMapper::toDto);
    }

    /**
     * Get one organisationLocalite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrganisationLocaliteDTO> findOne(Long id) {
        log.debug("Request to get OrganisationLocalite : {}", id);
        return organisationLocaliteRepository.findById(id)
            .map(organisationLocaliteMapper::toDto);
    }

    /**
     * Delete the organisationLocalite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrganisationLocalite : {}", id);
        organisationLocaliteRepository.deleteById(id);
    }
}
