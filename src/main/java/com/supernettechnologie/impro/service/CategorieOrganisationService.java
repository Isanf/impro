package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.CategorieOrganisation;
import com.supernettechnologie.impro.repository.CategorieOrganisationRepository;
import com.supernettechnologie.impro.service.dto.CategorieOrganisationDTO;
import com.supernettechnologie.impro.service.mapper.CategorieOrganisationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategorieOrganisation}.
 */
@Service
@Transactional
public class CategorieOrganisationService {

    private final Logger log = LoggerFactory.getLogger(CategorieOrganisationService.class);

    private final CategorieOrganisationRepository categorieOrganisationRepository;

    private final CategorieOrganisationMapper categorieOrganisationMapper;

    public CategorieOrganisationService(CategorieOrganisationRepository categorieOrganisationRepository, CategorieOrganisationMapper categorieOrganisationMapper) {
        this.categorieOrganisationRepository = categorieOrganisationRepository;
        this.categorieOrganisationMapper = categorieOrganisationMapper;
    }

    /**
     * Save a categorieOrganisation.
     *
     * @param categorieOrganisationDTO the entity to save.
     * @return the persisted entity.
     */
    public CategorieOrganisationDTO save(CategorieOrganisationDTO categorieOrganisationDTO) {
        log.debug("Request to save CategorieOrganisation : {}", categorieOrganisationDTO);
        CategorieOrganisation categorieOrganisation = categorieOrganisationMapper.toEntity(categorieOrganisationDTO);
        categorieOrganisation = categorieOrganisationRepository.save(categorieOrganisation);
        return categorieOrganisationMapper.toDto(categorieOrganisation);
    }

    /**
     * Get all the categorieOrganisations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategorieOrganisationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategorieOrganisations");
        return categorieOrganisationRepository.findAll(pageable)
            .map(categorieOrganisationMapper::toDto);
    }


    /**
     * Get one categorieOrganisation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategorieOrganisationDTO> findOne(Long id) {
        log.debug("Request to get CategorieOrganisation : {}", id);
        return categorieOrganisationRepository.findById(id)
            .map(categorieOrganisationMapper::toDto);
    }

    /**
     * Delete the categorieOrganisation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategorieOrganisation : {}", id);
        categorieOrganisationRepository.deleteById(id);
    }
}
