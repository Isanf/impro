package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.InfoCommandeCarnetASouche;
import com.supernettechnologie.impro.repository.InfoCommandeCarnetASoucheRepository;
import com.supernettechnologie.impro.service.dto.InfoCommandeCarnetASoucheDTO;
import com.supernettechnologie.impro.service.mapper.InfoCommandeCarnetASoucheMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InfoCommandeCarnetASouche}.
 */
@Service
@Transactional
public class InfoCommandeCarnetASoucheService {

    private final Logger log = LoggerFactory.getLogger(InfoCommandeCarnetASoucheService.class);

    private final InfoCommandeCarnetASoucheRepository infoCommandeCarnetASoucheRepository;

    private final InfoCommandeCarnetASoucheMapper infoCommandeCarnetASoucheMapper;

    public InfoCommandeCarnetASoucheService(InfoCommandeCarnetASoucheRepository infoCommandeCarnetASoucheRepository, InfoCommandeCarnetASoucheMapper infoCommandeCarnetASoucheMapper) {
        this.infoCommandeCarnetASoucheRepository = infoCommandeCarnetASoucheRepository;
        this.infoCommandeCarnetASoucheMapper = infoCommandeCarnetASoucheMapper;
    }

    /**
     * Save a infoCommandeCarnetASouche.
     *
     * @param infoCommandeCarnetASoucheDTO the entity to save.
     * @return the persisted entity.
     */
    public InfoCommandeCarnetASoucheDTO save(InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO) {
        log.debug("Request to save InfoCommandeCarnetASouche : {}", infoCommandeCarnetASoucheDTO);
        InfoCommandeCarnetASouche infoCommandeCarnetASouche = infoCommandeCarnetASoucheMapper.toEntity(infoCommandeCarnetASoucheDTO);
        infoCommandeCarnetASouche = infoCommandeCarnetASoucheRepository.save(infoCommandeCarnetASouche);
        return infoCommandeCarnetASoucheMapper.toDto(infoCommandeCarnetASouche);
    }

    /**
     * Get all the infoCommandeCarnetASouches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InfoCommandeCarnetASoucheDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InfoCommandeCarnetASouches");
        return infoCommandeCarnetASoucheRepository.findAll(pageable)
            .map(infoCommandeCarnetASoucheMapper::toDto);
    }


    /**
     * Get one infoCommandeCarnetASouche by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InfoCommandeCarnetASoucheDTO> findOne(Long id) {
        log.debug("Request to get InfoCommandeCarnetASouche : {}", id);
        return infoCommandeCarnetASoucheRepository.findById(id)
            .map(infoCommandeCarnetASoucheMapper::toDto);
    }

    /**
     * Delete the infoCommandeCarnetASouche by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InfoCommandeCarnetASouche : {}", id);

        infoCommandeCarnetASoucheRepository.deleteById(id);
    }
}
