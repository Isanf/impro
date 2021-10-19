package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.TypeOrganisation;
import com.supernettechnologie.impro.repository.TypeOrganisationRepository;
import com.supernettechnologie.impro.service.dto.TypeOrganisationDTO;
import com.supernettechnologie.impro.service.mapper.TypeOrganisationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeOrganisation}.
 */
@Service
@Transactional
public class TypeOrganisationService {

    private final Logger log = LoggerFactory.getLogger(TypeOrganisationService.class);

    private final TypeOrganisationRepository typeOrganisationRepository;

    private final TypeOrganisationMapper typeOrganisationMapper;

    public TypeOrganisationService(TypeOrganisationRepository typeOrganisationRepository, TypeOrganisationMapper typeOrganisationMapper) {
        this.typeOrganisationRepository = typeOrganisationRepository;
        this.typeOrganisationMapper = typeOrganisationMapper;
    }

    /**
     * Save a typeOrganisation.
     *
     * @param typeOrganisationDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeOrganisationDTO save(TypeOrganisationDTO typeOrganisationDTO) {
        log.debug("Request to save TypeOrganisation : {}", typeOrganisationDTO);
        TypeOrganisation typeOrganisation = typeOrganisationMapper.toEntity(typeOrganisationDTO);
        typeOrganisation = typeOrganisationRepository.save(typeOrganisation);
        return typeOrganisationMapper.toDto(typeOrganisation);
    }

    /**
     * Get all the typeOrganisations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeOrganisationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeOrganisations");
        return typeOrganisationRepository.findAll(pageable)
            .map(typeOrganisationMapper::toDto);
    }


    /**
     * Get one typeOrganisation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeOrganisationDTO> findOne(Long id) {
        log.debug("Request to get TypeOrganisation : {}", id);
        return typeOrganisationRepository.findById(id)
            .map(typeOrganisationMapper::toDto);
    }

    /**
     * Delete the typeOrganisation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeOrganisation : {}", id);
        typeOrganisationRepository.deleteById(id);
    }
}
