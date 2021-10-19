package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.VehiculeOccasionalDTO;
import com.supernettechnologie.impro.service.dto.VehiculeTraversantDTO;
import com.supernettechnologie.impro.service.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing {@link VehiculeOccasional}.
 */
@Service
@Transactional
public class VehiculeOccasionalService {

    private final Logger log = LoggerFactory.getLogger(VehiculeOccasionalService.class);

    private final VehiculeOccasionalRepository vehiculeOccasionalRepository;

    private final VehiculeOccasionalMapper vehiculeOccasionalMapper;
    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;
    @Autowired
    private PersonnePhysiqueMapper personnePhysiqueMapper;
    @Autowired
    private CarteWMapper carteWMapper;
    @Autowired
    private DocIdentificationPPRepository docIdentificationPPRepository;
    @Autowired
    private DocIdentificationPPMapper docIdentificationPPMapper;
    @Autowired
    private PersonneMoraleRepository personneMoraleRepository;
    @Autowired
    private PersonneMoraleMapper personneMoraleMapper;
    @Autowired
    private DocIdentificationPMRepository docIdentificationPMRepository;
    @Autowired
    private DocIdentificationPMMapper docIdentificationPMMapper;
    @Autowired
    private  NationRepository nationRepository;

    public VehiculeOccasionalService(VehiculeOccasionalRepository vehiculeOccasionalRepository, VehiculeOccasionalMapper vehiculeOccasionalMapper) {
        this.vehiculeOccasionalRepository = vehiculeOccasionalRepository;
        this.vehiculeOccasionalMapper = vehiculeOccasionalMapper;
    }

    /**
     * Save a vehiculeOccasional.
     *
     * @param vehiculeOccasionalDTO the entity to save.
     * @return the persisted entity.
     */
    public VehiculeOccasionalDTO save(VehiculeOccasionalDTO vehiculeOccasionalDTO) {
        log.debug("Request to save VehiculeOccasional : {}", vehiculeOccasionalDTO);
       /* VehiculeOccasional vehiculeOccasional = vehiculeOccasionalMapper.toEntity(vehiculeOccasionalDTO);
        vehiculeOccasional = vehiculeOccasionalRepository.save(vehiculeOccasional);
        return vehiculeOccasionalMapper.toDto(vehiculeOccasional);*/
        if (vehiculeOccasionalDTO.getDocIdentificationPPDTO() != null){//////////////////pERSONNE pHYSIQUE//////////////
            PersonnePhysique personnePhysique = personnePhysiqueMapper.toEntity(vehiculeOccasionalDTO.getPersonnePhysiqueDTO());
            DocIdentificationPP docIdentificationPP = docIdentificationPPMapper.toEntity(vehiculeOccasionalDTO.getDocIdentificationPPDTO());
            CarteW carteW = carteWMapper.toEntity(vehiculeOccasionalDTO.getCarteWDTO());
            VehiculeOccasional vehiculeOccasional = vehiculeOccasionalMapper.toEntity(vehiculeOccasionalDTO);
            Optional<PersonnePhysique> personConnect = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
            DocIdentificationPP docIdentificationPPVerify = docIdentificationPPRepository.findByNip(vehiculeOccasionalDTO.getDocIdentificationPPDTO().getNip());
            if(docIdentificationPPVerify != null){//////////////////////Le Doc Identite de la personne Physique existe déjà////////////////////////
                PersonnePhysique personnePhysiqueVerify = personnePhysiqueRepository.findByDocIdentification(docIdentificationPPVerify);
                if(personnePhysiqueVerify != null){////////////////////Les autres infos de la personne Physique existent déjà//////////////////////
                    vehiculeOccasional.setPersonnePhysique(personnePhysiqueVerify);
                    vehiculeOccasional.setOrganisation(personConnect.get().getOrganisation());
                    vehiculeOccasional.setCreatedAt(ZonedDateTime.now());
                    vehiculeOccasional.setCarteW(carteW);
                    String ordr = "" + (vehiculeOccasionalRepository.count() + 1);
                    vehiculeOccasional = vehiculeOccasionalRepository.save(vehiculeOccasional);
                }else {///////////////////////////////////////////////Les autres infos de la personne Physique n'existent pas//////////////////////
                    personnePhysique.setDocIdentification(docIdentificationPPVerify);
                    personnePhysique = personnePhysiqueRepository.save(personnePhysique);
                    vehiculeOccasional.setPersonnePhysique(personnePhysique);
                    vehiculeOccasional.setOrganisation(personConnect.get().getOrganisation());
                    vehiculeOccasional.setCreatedAt(ZonedDateTime.now());
                    vehiculeOccasional.setCarteW(carteW);
                    String ordr = "" + (vehiculeOccasionalRepository.count() + 1);
                    vehiculeOccasional = vehiculeOccasionalRepository.save(vehiculeOccasional);
                }
            }else {////////////////////////////////////////////////////Le Doc Identite de la personne Physique n'existe pas////////////////////////
                if (vehiculeOccasionalDTO.getNationDTO() != null){
                    Nation nation = nationRepository.findNationById(vehiculeOccasionalDTO.getNationDTO().getId());
                    docIdentificationPP.setNation(nation);
                }
                docIdentificationPP = docIdentificationPPRepository.save(docIdentificationPP);
                personnePhysique.setDocIdentification(docIdentificationPP);
                personnePhysique = personnePhysiqueRepository.save(personnePhysique);
                vehiculeOccasional.setPersonnePhysique(personnePhysique);
                vehiculeOccasional.setOrganisation(personConnect.get().getOrganisation());
                vehiculeOccasional.setCreatedAt(ZonedDateTime.now());
                vehiculeOccasional.setCarteW(carteW);
                vehiculeOccasional = vehiculeOccasionalRepository.save(vehiculeOccasional);
            }
            return vehiculeOccasionalMapper.toDto(vehiculeOccasional);
        }else {///////////////////////////////////////////Personne Morale///////////////////////////////////////////////////
            PersonneMorale personneMorale = personneMoraleMapper.toEntity(vehiculeOccasionalDTO.getPersonneMoraleDTO());
            DocIdentificationPM docIdentificationPM = docIdentificationPMMapper.toEntity(vehiculeOccasionalDTO.getDocIdentificationPMDTO());
            CarteW carteW = carteWMapper.toEntity(vehiculeOccasionalDTO.getCarteWDTO());
            VehiculeOccasional vehiculeOccasional = vehiculeOccasionalMapper.toEntity(vehiculeOccasionalDTO);
            Optional<PersonnePhysique> personConnect = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
            DocIdentificationPM docIdentificationPMVerify = docIdentificationPMRepository.findByNumeroIFU(vehiculeOccasionalDTO.getDocIdentificationPMDTO().getNumeroIFU());
            if(docIdentificationPMVerify != null){//////////////////////Le Doc Identite de la personne morale existe déjà////////////////////////////////////
                PersonneMorale personneMoraleVerify = personneMoraleRepository.findByNumeroIFU(docIdentificationPMVerify.getNumeroIFU());
                if(personneMoraleVerify != null){////////////////////Les autres infos de la personne morale existent déjà////////////////////////////////////
                    vehiculeOccasional.setPersonneMorale(personneMoraleVerify);
                    vehiculeOccasional.setOrganisation(personConnect.get().getOrganisation());
                    vehiculeOccasional.setCreatedAt(ZonedDateTime.now());
                    vehiculeOccasional.setCarteW(carteW);
                    vehiculeOccasional = vehiculeOccasionalRepository.save(vehiculeOccasional);
                }else {///////////////////////////////////////////////Les autres infos de la personne morale n'existent pas//////////////////////////////////
                    personneMorale.setNumeroIFU(docIdentificationPM.getNumeroIFU());
                    personneMorale = personneMoraleRepository.save(personneMorale);
                    vehiculeOccasional.setPersonneMorale(personneMorale);
                    vehiculeOccasional.setOrganisation(personConnect.get().getOrganisation());
                    vehiculeOccasional.setCreatedAt(ZonedDateTime.now());
                    vehiculeOccasional.setCarteW(carteW);
                    vehiculeOccasional = vehiculeOccasionalRepository.save(vehiculeOccasional);
                }
            }else {////////////////////////////////////////////////////Le Doc Identite de la personne morale n'existe pas///////////////////////////////////
                if (vehiculeOccasionalDTO.getNationDTO() != null){
                    Nation nation = nationRepository.findNationById(vehiculeOccasionalDTO.getNationDTO().getId());
                    docIdentificationPM.setNation(nation);
                }
                docIdentificationPM = docIdentificationPMRepository.save(docIdentificationPM);
                personneMorale.setNumeroIFU(docIdentificationPM.getNumeroIFU());
                personneMorale = personneMoraleRepository.save(personneMorale);
                vehiculeOccasional.setPersonneMorale(personneMorale);
                vehiculeOccasional.setOrganisation(personConnect.get().getOrganisation());
                vehiculeOccasional.setCreatedAt(ZonedDateTime.now());
                vehiculeOccasional.setCarteW(carteW);
                vehiculeOccasional = vehiculeOccasionalRepository.save(vehiculeOccasional);
            }
            return vehiculeOccasionalMapper.toDto(vehiculeOccasional);
        }
    }

    /**
     * Get all the vehiculeOccasionals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehiculeOccasionalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehiculeOccasionals");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        Page<VehiculeOccasionalDTO> vehiculeOccasionalDTOPage = vehiculeOccasionalRepository.findByOrganisationId(pageable, personnePhysique.get().getOrganisation().getId())
            .map(vehiculeOccasionalMapper::toDto);
        /*Page<VehiculeTraversantDTO> vehiculeTravDTOPage =  vehiculeTraversantRepository.findAll(pageable)
            .map(vehiculeTraversantMapper::toDto);*/
        /*for (VehiculeTraversantDTO vehiculeTraversantDTO : vehiculeTravDTOPage.getContent()) {
            if (vehiculeTraversantDTO.getPersonnePhysiqueId() != null) {
                Optional<PersonnePhysiqueDTO> personnePhysiqueDTO = personnePhysiqueRepository.findById((long) 6255)
                    .map(personnePhysiqueMapper::toDto);
                vehiculeTraversantDTO.setPersonnePhysiqueDTO(personnePhysiqueDTO.get());
            } else{
                Optional<PersonneMoraleDTO> personneMoraleDTO = personneMoraleRepository.findById((long) 14952)
                    .map(personneMoraleMapper::toDto);
                vehiculeTraversantDTO.setPersonneMoraleDTO(personneMoraleDTO.get());
            }
        }*/
        return vehiculeOccasionalDTOPage;
        /*return vehiculeOccasionalRepository.findAll(pageable)
            .map(vehiculeOccasionalMapper::toDto);*/
    }


    /**
     * Get one vehiculeOccasional by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehiculeOccasionalDTO> findOne(Long id) {
        log.debug("Request to get VehiculeOccasional : {}", id);
        return vehiculeOccasionalRepository.findById(id)
            .map(vehiculeOccasionalMapper::toDto);
    }

    /**
     * Delete the vehiculeOccasional by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehiculeOccasional : {}", id);
        vehiculeOccasionalRepository.deleteById(id);
    }
}
