package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.domain.PlaqueImmatriculation;
import com.supernettechnologie.impro.domain.Vehicule;
import com.supernettechnologie.impro.domain.Vente;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.service.dto.PersonnePhysiqueDTO;
import com.supernettechnologie.impro.service.dto.VenteDTO;
import com.supernettechnologie.impro.service.mapper.PersonnePhysiqueMapper;
import com.supernettechnologie.impro.service.mapper.VenteMapper;
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
 * Service Implementation for managing {@link PersonnePhysique}.
 */
@Service
@Transactional
public class PersonnePhysiqueService {

    private final Logger log = LoggerFactory.getLogger(PersonnePhysiqueService.class);

    private final PersonnePhysiqueRepository personnePhysiqueRepository;

    private final PersonnePhysiqueMapper personnePhysiqueMapper;

    @Autowired
    private PlaqueImmatriculationRepository plaqueImmatriculationRepository;
    @Autowired
    private VehiculeRepository vehiculeRepository;
    @Autowired
    private VenteRepository venteRepository;
    @Autowired
    private PersonneMoraleRepository personneMoraleRepository;
    @Autowired
    private VenteMapper venteMapper;

    public PersonnePhysiqueService(PersonnePhysiqueRepository personnePhysiqueRepository, PersonnePhysiqueMapper personnePhysiqueMapper) {
        this.personnePhysiqueRepository = personnePhysiqueRepository;
        this.personnePhysiqueMapper = personnePhysiqueMapper;
    }

    /**
     * Save a personnePhysique.
     *
     * @param personnePhysiqueDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonnePhysiqueDTO save(PersonnePhysiqueDTO personnePhysiqueDTO) {
        log.debug("Request to save PersonnePhysique : {}", personnePhysiqueDTO);
        PersonnePhysique personnePhysique = personnePhysiqueMapper.toEntity(personnePhysiqueDTO);
        personnePhysique = personnePhysiqueRepository.save(personnePhysique);
        return personnePhysiqueMapper.toDto(personnePhysique);
    }

    /**
     * Get all the personnePhysiques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonnePhysiqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonnePhysiques");
        return personnePhysiqueRepository.findAll(pageable)
            .map(personnePhysiqueMapper::toDto);
    }


    /**
     * Get one personnePhysique by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonnePhysiqueDTO> findOne(Long id) {
        log.debug("Request to get PersonnePhysique : {}", id);
        return personnePhysiqueRepository.findById(id)
            .map(personnePhysiqueMapper::toDto);
    }



    /**
     * Delete the personnePhysique by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonnePhysique : {}", id);
        personnePhysiqueRepository.deleteById(id);
    }
}
