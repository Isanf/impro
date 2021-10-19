package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.PersonneMorale;
import com.supernettechnologie.impro.repository.PersonneMoraleRepository;
import com.supernettechnologie.impro.service.dto.PersonneMoraleDTO;
import com.supernettechnologie.impro.service.mapper.PersonneMoraleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PersonneMorale}.
 */
@Service
@Transactional
public class PersonneMoraleService {

    private final Logger log = LoggerFactory.getLogger(PersonneMoraleService.class);

    private final PersonneMoraleRepository personneMoraleRepository;

    private final PersonneMoraleMapper personneMoraleMapper;

    public PersonneMoraleService(PersonneMoraleRepository personneMoraleRepository, PersonneMoraleMapper personneMoraleMapper) {
        this.personneMoraleRepository = personneMoraleRepository;
        this.personneMoraleMapper = personneMoraleMapper;
    }

    /**
     * Save a personneMorale.
     *
     * @param personneMoraleDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonneMoraleDTO save(PersonneMoraleDTO personneMoraleDTO) {
        log.debug("Request to save PersonneMorale : {}", personneMoraleDTO);
        PersonneMorale personneMorale = personneMoraleMapper.toEntity(personneMoraleDTO);
        personneMorale = personneMoraleRepository.save(personneMorale);
        return personneMoraleMapper.toDto(personneMorale);
    }

    /**
     * Get all the personneMorales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonneMoraleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonneMorales");
        return personneMoraleRepository.findAll(pageable)
            .map(personneMoraleMapper::toDto);
    }


    /**
     * Get one personneMorale by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonneMoraleDTO> findOne(Long id) {
        log.debug("Request to get PersonneMorale : {}", id);
        return personneMoraleRepository.findById(id)
            .map(personneMoraleMapper::toDto);
    }

    /**
     * Delete the personneMorale by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonneMorale : {}", id);
        personneMoraleRepository.deleteById(id);
    }
}
