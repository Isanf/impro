package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.TypeCarnet;
import com.supernettechnologie.impro.repository.TypeCarnetRepository;
import com.supernettechnologie.impro.service.dto.TypeCarnetDTO;
import com.supernettechnologie.impro.service.mapper.TypeCarnetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeCarnet}.
 */
@Service
@Transactional
public class TypeCarnetService {

    private final Logger log = LoggerFactory.getLogger(TypeCarnetService.class);

    private final TypeCarnetRepository typeCarnetRepository;

    private final TypeCarnetMapper typeCarnetMapper;

    public TypeCarnetService(TypeCarnetRepository typeCarnetRepository, TypeCarnetMapper typeCarnetMapper) {
        this.typeCarnetRepository = typeCarnetRepository;
        this.typeCarnetMapper = typeCarnetMapper;
    }

    /**
     * Save a typeCarnet.
     *
     * @param typeCarnetDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeCarnetDTO save(TypeCarnetDTO typeCarnetDTO) {
        log.debug("Request to save TypeCarnet : {}", typeCarnetDTO);
        TypeCarnet typeCarnet = typeCarnetMapper.toEntity(typeCarnetDTO);
        typeCarnet = typeCarnetRepository.save(typeCarnet);
        return typeCarnetMapper.toDto(typeCarnet);
    }

    /**
     * Get all the typeCarnets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeCarnetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeCarnets");
        return typeCarnetRepository.findAll(pageable)
            .map(typeCarnetMapper::toDto);
    }

    /**
     * Get one typeCarnet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeCarnetDTO> findOne(Long id) {
        log.debug("Request to get TypeCarnet : {}", id);
        return typeCarnetRepository.findById(id)
            .map(typeCarnetMapper::toDto);
    }

    /**
     * Delete the typeCarnet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeCarnet : {}", id);
        typeCarnetRepository.deleteById(id);
    }
}
