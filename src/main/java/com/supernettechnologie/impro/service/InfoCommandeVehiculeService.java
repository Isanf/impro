package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.CommandeVehicule;
import com.supernettechnologie.impro.domain.InfoCommandeVehicule;
import com.supernettechnologie.impro.repository.CommandeVehiculeRepository;
import com.supernettechnologie.impro.repository.InfoCommandeVehiculeRepository;
import com.supernettechnologie.impro.service.dto.InfoCommandeVehiculeDTO;
import com.supernettechnologie.impro.service.mapper.CommandeVehiculeMapper;
import com.supernettechnologie.impro.service.mapper.InfoCommandeVehiculeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InfoCommandeVehicule}.
 */
@Service
@Transactional
public class InfoCommandeVehiculeService {

    private final Logger log = LoggerFactory.getLogger(InfoCommandeVehiculeService.class);

    private final InfoCommandeVehiculeRepository infoCommandeVehiculeRepository;

    private final InfoCommandeVehiculeMapper infoCommandeVehiculeMapper;

    /*private final CommandeVehiculeRepository commandeVehiculeRepository;

    private final CommandeVehiculeMapper commandeVehiculeMapper;*/

    public InfoCommandeVehiculeService(InfoCommandeVehiculeRepository infoCommandeVehiculeRepository, InfoCommandeVehiculeMapper infoCommandeVehiculeMapper/*, CommandeVehiculeRepository commandeVehiculeRepository, CommandeVehiculeMapper commandeVehiculeMapper*/) {
        this.infoCommandeVehiculeRepository = infoCommandeVehiculeRepository;
        this.infoCommandeVehiculeMapper = infoCommandeVehiculeMapper;
        /*this.commandeVehiculeRepository = commandeVehiculeRepository;
        this.commandeVehiculeMapper = commandeVehiculeMapper;*/
    }

    /**
     * Save a infoCommandeVehicule.
     *
     * @param infoCommandeVehiculeDTO the entity to save.
     * @return the persisted entity.
     */
    public InfoCommandeVehiculeDTO save(InfoCommandeVehiculeDTO infoCommandeVehiculeDTO) {
        log.debug("Request to save InfoCommandeVehicule : {}", infoCommandeVehiculeDTO);
        /*CommandeVehicule commandeVehicule = commandeVehiculeMapper.toEntity(infoCommandeVehiculeDTO.getCommandeVehiculeDTO());
        commandeVehicule = commandeVehiculeRepository.save(commandeVehicule);*/
        InfoCommandeVehicule infoCommandeVehicule = infoCommandeVehiculeMapper.toEntity(infoCommandeVehiculeDTO);
       /* infoCommandeVehicule.setCommandeVehicule(commandeVehicule);*/
        infoCommandeVehicule = infoCommandeVehiculeRepository.save(infoCommandeVehicule);
        return infoCommandeVehiculeMapper.toDto(infoCommandeVehicule);
    }

    /**
     * Get all the infoCommandeVehicules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InfoCommandeVehiculeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InfoCommandeVehicules");
        return infoCommandeVehiculeRepository.findAll(pageable)
            .map(infoCommandeVehiculeMapper::toDto);
    }

    /**
     * Get one infoCommandeVehicule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InfoCommandeVehiculeDTO> findOne(Long id) {
        log.debug("Request to get InfoCommandeVehicule : {}", id);
        return infoCommandeVehiculeRepository.findById(id)
            .map(infoCommandeVehiculeMapper::toDto);
    }

    /**
     * Delete the infoCommandeVehicule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InfoCommandeVehicule : {}", id);
        infoCommandeVehiculeRepository.deleteById(id);
    }
}
