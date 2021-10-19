package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.TypeVehicule;
import com.supernettechnologie.impro.repository.TypeVehiculeRepository;
import com.supernettechnologie.impro.service.dto.TypeVehiculeDTO;
import com.supernettechnologie.impro.service.mapper.TypeVehiculeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeVehicule}.
 */
@Service
@Transactional
public class TypeVehiculeService {

    private final Logger log = LoggerFactory.getLogger(TypeVehiculeService.class);

    private final TypeVehiculeRepository typeVehiculeRepository;

    private final TypeVehiculeMapper typeVehiculeMapper;

    public TypeVehiculeService(TypeVehiculeRepository typeVehiculeRepository, TypeVehiculeMapper typeVehiculeMapper) {
        this.typeVehiculeRepository = typeVehiculeRepository;
        this.typeVehiculeMapper = typeVehiculeMapper;
    }

    /**
     * Save a typeVehicule.
     *
     * @param typeVehiculeDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeVehiculeDTO save(TypeVehiculeDTO typeVehiculeDTO) {
        log.debug("Request to save TypeVehicule : {}", typeVehiculeDTO);
        TypeVehicule typeVehicule = typeVehiculeMapper.toEntity(typeVehiculeDTO);
        typeVehicule = typeVehiculeRepository.save(typeVehicule);
        return typeVehiculeMapper.toDto(typeVehicule);
    }

    /**
     * Get all the typeVehicules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeVehiculeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeVehicules");
        return typeVehiculeRepository.findAll(pageable)
            .map(typeVehiculeMapper::toDto);
    }

    /**
     * Get one typeVehicule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeVehiculeDTO> findOne(Long id) {
        log.debug("Request to get TypeVehicule : {}", id);
        return typeVehiculeRepository.findById(id)
            .map(typeVehiculeMapper::toDto);
    }

    /**
     * Delete the typeVehicule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeVehicule : {}", id);
        typeVehiculeRepository.deleteById(id);
    }
}
