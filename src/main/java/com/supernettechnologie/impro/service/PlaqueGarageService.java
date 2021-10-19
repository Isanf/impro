package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.CustomResponse;
import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.domain.PlaqueGarage;
import com.supernettechnologie.impro.domain.User;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.*;
import com.supernettechnologie.impro.service.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PlaqueGarage}.
 */
@Service
@Transactional
public class PlaqueGarageService {

    private final Logger log = LoggerFactory.getLogger(PlaqueGarageService.class);

    private final PlaqueGarageRepository plaqueGarageRepository;

    private final PlaqueGarageMapper plaqueGarageMapper;
    @Autowired
    private OrganisationMapper organisationMapper;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private LogActivityService logActivityService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;
    @Autowired
    private CarteWRepository carteWRepository;
    @Autowired
    private CarteWMapper carteWMapper;
    @Autowired
    private PersonneMoraleRepository personneMoraleRepository;
    @Autowired
    private PersonneMoraleMapper personneMoraleMapper;
    @Autowired
    DocIdentificationPMRepository docIdentificationPMRepository;
    @Autowired
    private DocIdentificationPMMapper docIdentificationPMMapper;

    public PlaqueGarageService(PlaqueGarageRepository plaqueGarageRepository, PlaqueGarageMapper plaqueGarageMapper) {
        this.plaqueGarageRepository = plaqueGarageRepository;
        this.plaqueGarageMapper = plaqueGarageMapper;
    }

    /**
     * Save a plaqueGarage.
     *
     * @param plaqueGarageDTO the entity to save.
     * @return the persisted entity.
     */
    public PlaqueGarageDTO save(List<PlaqueGarageDTO> plaqueGarageDTO) {
        log.debug("Request to save PlaqueGarage : {}", plaqueGarageDTO);
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        int i = 1;
        List<PlaqueGarage> plaqueGarageList = plaqueGarageMapper.toEntity(plaqueGarageDTO);
        for (PlaqueGarage plaqueGarage : plaqueGarageList){
            plaqueGarage.setNumeroOrdre(String.valueOf(plaqueGarageRepository.count()+i));
            plaqueGarage.setCreatedAt(ZonedDateTime.now());
            i++;
        }
        User user = userRepository.findById(personnePhysique.get().getUser().getId()).get();
        LogActivityDTO logActivityDTO = new LogActivityDTO();
        logActivityDTO.setAction("Attributions de plaques garagistes");
        logActivityDTO.setPrincipal(user.getLogin().toUpperCase());
        logActivityService.save(logActivityDTO);
        plaqueGarageRepository.saveAll(plaqueGarageList);
        return plaqueGarageMapper.toDto(plaqueGarageList.get(0));
    }

    /**
     * Get all the plaqueGarages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlaqueGarageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlaqueGarages");
        return plaqueGarageRepository.findAll(pageable)
            .map(plaqueGarageMapper::toDto);
    }


    /**
     * Get one plaqueGarage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlaqueGarageDTO> findOne(Long id) {
        log.debug("Request to get PlaqueGarage : {}", id);
        return plaqueGarageRepository.findById(id)
            .map(plaqueGarageMapper::toDto);
    }

    /**
     * Delete the plaqueGarage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlaqueGarage : {}", id);

        plaqueGarageRepository.deleteById(id);
    }

    public Page<PlaqueGarageDTO> findAllByGaragiste(Long id, Pageable pageable) {
        System.out.println("L'id : " +id);
        return plaqueGarageRepository.findAllByCarteWId(id, pageable).map(plaqueGarageMapper::toDto);
    }

    public Optional<CarteWDTO> findGaragisteByQr(String id) {
        PlaqueGarage plaqueGarage = plaqueGarageRepository.findByCodeQrPlaque(id).get();
        Optional<CarteWDTO> carteWDTO = carteWRepository.findById(plaqueGarage.getCarteW().getId()).map(carteWMapper::toDto);
        OrganisationDTO organisationDTO = organisationMapper.toDto(plaqueGarage.getCarteW().getOrganisation());
        DocIdentificationPMDTO docIdentificationPMDTO = docIdentificationPMMapper.toDto(docIdentificationPMRepository.findByOrganisationId(organisationDTO.getId()));
        organisationDTO.setDocIdentificationPMDTO(docIdentificationPMDTO);
        carteWDTO.get().setOrganisationDTO(organisationDTO);
        carteWDTO.get().setPlaqueGarageDTO(plaqueGarageMapper.toDto(plaqueGarage));

        return carteWDTO;
    }
}
