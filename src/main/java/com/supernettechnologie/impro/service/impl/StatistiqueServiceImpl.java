package com.supernettechnologie.impro.service.impl;

import com.supernettechnologie.impro.service.StatistiqueService;
import com.supernettechnologie.impro.domain.Statistique;
import com.supernettechnologie.impro.repository.StatistiqueRepository;
import com.supernettechnologie.impro.service.dto.StatistiqueDTO;
import com.supernettechnologie.impro.service.mapper.StatistiqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Statistique}.
 */
@Service
@Transactional
public class StatistiqueServiceImpl implements StatistiqueService {

    private final Logger log = LoggerFactory.getLogger(StatistiqueServiceImpl.class);

    private final StatistiqueRepository statistiqueRepository;

    private final StatistiqueMapper statistiqueMapper;

    public StatistiqueServiceImpl(StatistiqueRepository statistiqueRepository, StatistiqueMapper statistiqueMapper) {
        this.statistiqueRepository = statistiqueRepository;
        this.statistiqueMapper = statistiqueMapper;
    }

    /**
     * Save a statistique.
     *
     * @param statistiqueDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StatistiqueDTO save(StatistiqueDTO statistiqueDTO) {
        log.debug("Request to save Statistique : {}", statistiqueDTO);
        Statistique statistique = statistiqueMapper.toEntity(statistiqueDTO);
        statistique = statistiqueRepository.save(statistique);
        return statistiqueMapper.toDto(statistique);
    }

    /**
     * Get all the statistiques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StatistiqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Statistiques");
        return statistiqueRepository.findAll(pageable)
            .map(statistiqueMapper::toDto);
    }


    /**
     * Get one statistique by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StatistiqueDTO> findOne(Long id) {
        log.debug("Request to get Statistique : {}", id);
        return statistiqueRepository.findById(id)
            .map(statistiqueMapper::toDto);
    }

    /**
     * Delete the statistique by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Statistique : {}", id);

        statistiqueRepository.deleteById(id);
    }
}
