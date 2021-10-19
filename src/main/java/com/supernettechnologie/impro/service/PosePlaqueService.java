package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.PosePlaque;
import com.supernettechnologie.impro.repository.PosePlaqueRepository;
import com.supernettechnologie.impro.service.dto.PosePlaqueDTO;
import com.supernettechnologie.impro.service.mapper.PosePlaqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PosePlaque}.
 */
@Service
@Transactional
public class PosePlaqueService {

    private final Logger log = LoggerFactory.getLogger(PosePlaqueService.class);

    private final PosePlaqueRepository posePlaqueRepository;

    private final PosePlaqueMapper posePlaqueMapper;

    public PosePlaqueService(PosePlaqueRepository posePlaqueRepository, PosePlaqueMapper posePlaqueMapper) {
        this.posePlaqueRepository = posePlaqueRepository;
        this.posePlaqueMapper = posePlaqueMapper;
    }

    /**
     * Save a posePlaque.
     *
     * @param posePlaqueDTO the entity to save.
     * @return the persisted entity.
     */
    public PosePlaqueDTO save(PosePlaqueDTO posePlaqueDTO) {
        log.debug("Request to save PosePlaque : {}", posePlaqueDTO);
        PosePlaque posePlaque = posePlaqueMapper.toEntity(posePlaqueDTO);
        posePlaque = posePlaqueRepository.save(posePlaque);
        return posePlaqueMapper.toDto(posePlaque);
    }

    /**
     * Get all the posePlaques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PosePlaqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PosePlaques");
        return posePlaqueRepository.findAll(pageable)
            .map(posePlaqueMapper::toDto);
    }


    /**
     * Get one posePlaque by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PosePlaqueDTO> findOne(Long id) {
        log.debug("Request to get PosePlaque : {}", id);
        return posePlaqueRepository.findById(id)
            .map(posePlaqueMapper::toDto);
    }

    /**
     * Delete the posePlaque by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PosePlaque : {}", id);
        posePlaqueRepository.deleteById(id);
    }
}
