package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.CommandeVehicule;
import com.supernettechnologie.impro.domain.LivraisonVehicule;
import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.domain.Vehicule;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.LivraisonVehiculeDTO;
import com.supernettechnologie.impro.service.mapper.LivraisonVehiculeMapper;
import com.supernettechnologie.impro.service.mapper.VehiculeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link LivraisonVehicule}.
 */
@Service
@Transactional
public class LivraisonVehiculeService {

    private final Logger log = LoggerFactory.getLogger(LivraisonVehiculeService.class);

    private final LivraisonVehiculeRepository livraisonVehiculeRepository;

    private final LivraisonVehiculeMapper livraisonVehiculeMapper;

    private final VehiculeMapper vehiculeMapper;

    private final VehiculeRepository vehiculeRepository;
    @Autowired
    PersonnePhysiqueRepository personnePhysiqueRepository;
    @Autowired
    private CommandeVehiculeRepository commandeVehiculeRepository;

    public LivraisonVehiculeService(LivraisonVehiculeRepository livraisonVehiculeRepository, LivraisonVehiculeMapper livraisonVehiculeMapper, VehiculeMapper vehiculeMapper, VehiculeRepository vehiculeRepository) {
        this.livraisonVehiculeRepository = livraisonVehiculeRepository;
        this.livraisonVehiculeMapper = livraisonVehiculeMapper;
        this.vehiculeMapper = vehiculeMapper;
        this.vehiculeRepository = vehiculeRepository;
    }

    /**
     * Save a livraisonVehicule.
     *
     * @param livraisonVehiculeDTO the entity to save.
     * @return the persisted entity.
     */
    public LivraisonVehiculeDTO save(LivraisonVehiculeDTO livraisonVehiculeDTO) {
        log.debug("Request to save LivraisonVehicule : {}", livraisonVehiculeDTO);
        List<Vehicule> vehicules = vehiculeMapper.toEntity(livraisonVehiculeDTO.getVehiculeDTOS());
        Optional<CommandeVehicule> commandeVehicule = commandeVehiculeRepository.findById(livraisonVehiculeDTO.getCommandeVehiculeId());
        commandeVehicule.get().setEstLivree(true);
        commandeVehiculeRepository.save(commandeVehicule.get());
        if (livraisonVehiculeDTO.getVehiculeDTOS() == null) {
            log.debug("******************************* Vehicule vide");
        }
        LivraisonVehicule livraisonVehicule = livraisonVehiculeMapper.toEntity(livraisonVehiculeDTO);
        livraisonVehicule.setVehicules(new HashSet<>(vehicules));
        livraisonVehicule = livraisonVehiculeRepository.save(livraisonVehicule);
        for (Vehicule vehicule : vehicules) {
            vehicule.setLivraisonVehicule(livraisonVehicule);
        }
        vehiculeRepository.saveAll(vehicules);
        return livraisonVehiculeMapper.toDto(livraisonVehicule);
    }

    /**
     * Get all the livraisonVehicules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LivraisonVehiculeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LivraisonVehicules");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        if(livraisonVehiculeRepository.findAllByConcessionnaireId(pageable, personnePhysique.get().getOrganisation().getId())
            .map(livraisonVehiculeMapper::toDto)!=null){
            return livraisonVehiculeRepository.findAllByConcessionnaireId(pageable, personnePhysique.get().getOrganisation().getId())
                .map(livraisonVehiculeMapper::toDto);
        }else {
            return livraisonVehiculeRepository.findAllByRevendeurId(pageable, personnePhysique.get().getOrganisation().getId())
                .map(livraisonVehiculeMapper::toDto);
        }
    }

    public Page<LivraisonVehiculeDTO> findAll1(Pageable pageable) {
        log.debug("Request to get all LivraisonVehicules");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        return livraisonVehiculeRepository.findAllByRevendeurId(pageable, personnePhysique.get().getOrganisation().getId())
            .map(livraisonVehiculeMapper::toDto);
    }

    /**
     * Get one livraisonVehicule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LivraisonVehiculeDTO> findOne(Long id) {
        log.debug("Request to get LivraisonVehicule : {}", id);
        return livraisonVehiculeRepository.findById(id)
            .map(livraisonVehiculeMapper::toDto);
    }

    /**
     * Delete the livraisonVehicule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LivraisonVehicule : {}", id);
        livraisonVehiculeRepository.deleteById(id);
    }
}
