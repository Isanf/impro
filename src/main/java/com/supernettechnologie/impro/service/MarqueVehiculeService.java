package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.MarqueVehicule;
import com.supernettechnologie.impro.repository.MarqueVehiculeRepository;
import com.supernettechnologie.impro.service.dto.MarqueVehiculeDTO;
import com.supernettechnologie.impro.service.mapper.MarqueVehiculeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MarqueVehicule}.
 */
@Service
@Transactional
public class MarqueVehiculeService {

    private final Logger log = LoggerFactory.getLogger(MarqueVehiculeService.class);

    private final MarqueVehiculeRepository marqueVehiculeRepository;

    private final MarqueVehiculeMapper marqueVehiculeMapper;

    public MarqueVehiculeService(MarqueVehiculeRepository marqueVehiculeRepository, MarqueVehiculeMapper marqueVehiculeMapper) {
        this.marqueVehiculeRepository = marqueVehiculeRepository;
        this.marqueVehiculeMapper = marqueVehiculeMapper;
    }

    /**
     * Save a marqueVehicule.
     *
     * @param marqueVehiculeDTO the entity to save.
     * @return the persisted entity.
     */
    public MarqueVehiculeDTO save(MarqueVehiculeDTO marqueVehiculeDTO) {
        log.debug("Request to save MarqueVehicule : {}", marqueVehiculeDTO);
        MarqueVehicule marqueVehicule = marqueVehiculeMapper.toEntity(marqueVehiculeDTO);
        marqueVehicule = marqueVehiculeRepository.save(marqueVehicule);
        return marqueVehiculeMapper.toDto(marqueVehicule);
    }

    /**
     * Get all the marqueVehicules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MarqueVehiculeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MarqueVehicules");
        return marqueVehiculeRepository.findAll(pageable)
            .map(marqueVehiculeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<MarqueVehiculeDTO> findAlls() {
        log.debug("Request to get all MarqueVehicules");
        List<MarqueVehicule> marqueVehicules = marqueVehiculeRepository.findAll();
        return marqueVehiculeMapper.toDto(marqueVehicules);
    }

    /**
     * Get one marqueVehicule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MarqueVehiculeDTO> findOne(Long id) {
        log.debug("Request to get MarqueVehicule : {}", id);
        return marqueVehiculeRepository.findById(id)
            .map(marqueVehiculeMapper::toDto);
    }

    /**
     * Delete the marqueVehicule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MarqueVehicule : {}", id);
        marqueVehiculeRepository.deleteById(id);
    }
}
