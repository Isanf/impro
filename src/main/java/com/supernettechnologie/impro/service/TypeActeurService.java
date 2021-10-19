package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.TypeActeur;
import com.supernettechnologie.impro.repository.OrganisationRepository;
import com.supernettechnologie.impro.repository.TypeActeurRepository;
import com.supernettechnologie.impro.service.dto.OrganisationDTO;
import com.supernettechnologie.impro.service.dto.TypeActeurDTO;
import com.supernettechnologie.impro.service.mapper.OrganisationMapper;
import com.supernettechnologie.impro.service.mapper.TypeActeurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeActeur}.
 */
@Service
@Transactional
public class TypeActeurService {

    private final Logger log = LoggerFactory.getLogger(TypeActeurService.class);

    private final TypeActeurRepository typeActeurRepository;

    private final TypeActeurMapper typeActeurMapper;
    @Autowired
    private OrganisationMapper organisationMapper;
    @Autowired
    private OrganisationRepository organisationRepository;

    public TypeActeurService(TypeActeurRepository typeActeurRepository, TypeActeurMapper typeActeurMapper) {
        this.typeActeurRepository = typeActeurRepository;
        this.typeActeurMapper = typeActeurMapper;
    }

    /**
     * Save a typeActeur.
     *
     * @param typeActeurDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeActeurDTO save(TypeActeurDTO typeActeurDTO) {
        log.debug("Request to save TypeActeur : {}", typeActeurDTO);
        TypeActeur typeActeur = typeActeurMapper.toEntity(typeActeurDTO);
        typeActeur = typeActeurRepository.save(typeActeur);
        return typeActeurMapper.toDto(typeActeur);
    }

    /**
     * Get all the typeActeurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeActeurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeActeurs");
        return typeActeurRepository.findAll(pageable)
            .map(typeActeurMapper::toDto);
    }


    /**
     * Get one typeActeur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeActeurDTO> findOne(Long id) {
        log.debug("Request to get TypeActeur : {}", id);

        Optional<TypeActeurDTO> typeActeurDTO = typeActeurRepository.findById(id)
            .map(typeActeurMapper::toDto);
        List<OrganisationDTO> organisationDTO = organisationMapper
            .toDto(organisationRepository.findAllByTypeActeurId(id));

        typeActeurDTO.get().setOrganisationDTO(organisationDTO.get(0));

        return typeActeurDTO;
    }

    /**
     * Delete the typeActeur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeActeur : {}", id);
        typeActeurRepository.deleteById(id);
    }
}
