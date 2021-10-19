package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.PersonneMoraleDTO;
import com.supernettechnologie.impro.service.dto.PersonnePhysiqueDTO;
import com.supernettechnologie.impro.service.dto.VehiculeTraversantDTO;
import com.supernettechnologie.impro.service.dto.VenteDTO;
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
 * Service Implementation for managing {@link VehiculeTraversant}.
 */
@Service
@Transactional
public class VehiculeTraversantService {

    private final Logger log = LoggerFactory.getLogger(VehiculeTraversantService.class);

    private final VehiculeTraversantRepository vehiculeTraversantRepository;

    private final VehiculeTraversantMapper vehiculeTraversantMapper;

    private final PersonnePhysiqueRepository personnePhysiqueRepository;

    private final PersonnePhysiqueMapper personnePhysiqueMapper;

    private final DocIdentificationPPRepository docIdentificationPPRepository;

    private final DocIdentificationPPMapper docIdentificationPPMapper;

    private final PersonneMoraleRepository personneMoraleRepository;

    private final PersonneMoraleMapper personneMoraleMapper;

    private final DocIdentificationPMRepository docIdentificationPMRepository;

    private final DocIdentificationPMMapper docIdentificationPMMapper;
    @Autowired
    private  NationRepository nationRepository;


    public VehiculeTraversantService(VehiculeTraversantRepository vehiculeTraversantRepository, VehiculeTraversantMapper vehiculeTraversantMapper, PersonnePhysiqueRepository personnePhysiqueRepository, PersonnePhysiqueMapper personnePhysiqueMapper, DocIdentificationPPRepository docIdentificationPPRepository, DocIdentificationPPMapper docIdentificationPPMapper, PersonneMoraleRepository personneMoraleRepository, PersonneMoraleMapper personneMoraleMapper, DocIdentificationPMRepository docIdentificationPMRepository, DocIdentificationPMMapper docIdentificationPMMapper) {
        this.vehiculeTraversantRepository = vehiculeTraversantRepository;
        this.vehiculeTraversantMapper = vehiculeTraversantMapper;
        this.personnePhysiqueRepository = personnePhysiqueRepository;
        this.personnePhysiqueMapper = personnePhysiqueMapper;
        this.docIdentificationPPRepository = docIdentificationPPRepository;
        this.docIdentificationPPMapper = docIdentificationPPMapper;
        this.personneMoraleRepository = personneMoraleRepository;
        this.personneMoraleMapper = personneMoraleMapper;
        this.docIdentificationPMRepository = docIdentificationPMRepository;
        this.docIdentificationPMMapper = docIdentificationPMMapper;
    }

    /**
     * Save a vehiculeTraversant.
     *
     * @param vehiculeTraversantDTO the entity to save.
     * @return the persisted entity.
     */
    public VehiculeTraversantDTO save(VehiculeTraversantDTO vehiculeTraversantDTO) {
        log.debug("Request to save VehiculeTraversant : {}", vehiculeTraversantDTO);
        if (vehiculeTraversantDTO.getDocIdentificationPPDTO() != null){
            PersonnePhysique personnePhysique = personnePhysiqueMapper.toEntity(vehiculeTraversantDTO.getPersonnePhysiqueDTO());
            DocIdentificationPP docIdentificationPP = docIdentificationPPMapper.toEntity(vehiculeTraversantDTO.getDocIdentificationPPDTO());
            VehiculeTraversant vehiculeTraversant = vehiculeTraversantMapper.toEntity(vehiculeTraversantDTO);
            Optional<PersonnePhysique> personConnect = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
            DocIdentificationPP docIdentificationPPVerify = docIdentificationPPRepository.findByNip(vehiculeTraversantDTO.getDocIdentificationPPDTO().getNip());
            if(docIdentificationPPVerify != null){//////////////////////Le Doc Identite de la personne Physique existe déjà////////////////////////
                PersonnePhysique personnePhysiqueVerify = personnePhysiqueRepository.findByDocIdentification(docIdentificationPPVerify);
                if(personnePhysiqueVerify != null){////////////////////Les autres infos de la personne Physique existent déjà//////////////////////
                    vehiculeTraversant.setPersonnePhysique(personnePhysiqueVerify);
                    vehiculeTraversant.setOrganisation(personConnect.get().getOrganisation());
                    vehiculeTraversant.setCreatedAt(ZonedDateTime.now());
                    String ordr = "" + (vehiculeTraversantRepository.count() + 1);
                    vehiculeTraversant = vehiculeTraversantRepository.save(vehiculeTraversant);
                }else {///////////////////////////////////////////////Les autres infos de la personne Physique n'existent pas//////////////////////
                    personnePhysique.setDocIdentification(docIdentificationPPVerify);
                    personnePhysique = personnePhysiqueRepository.save(personnePhysique);
                    vehiculeTraversant.setPersonnePhysique(personnePhysique);
                    vehiculeTraversant.setOrganisation(personConnect.get().getOrganisation());
                    vehiculeTraversant.setCreatedAt(ZonedDateTime.now());
                    String ordr = "" + (vehiculeTraversantRepository.count() + 1);
                    vehiculeTraversant = vehiculeTraversantRepository.save(vehiculeTraversant);
                }
            }else {////////////////////////////////////////////////////Le Doc Identite de la personne Physique n'existe pas////////////////////////
                if (vehiculeTraversantDTO.getNationDTO() != null){
                    Nation nation = nationRepository.findNationById(vehiculeTraversantDTO.getNationDTO().getId());
                    docIdentificationPP.setNation(nation);
                }
                docIdentificationPP = docIdentificationPPRepository.save(docIdentificationPP);
                personnePhysique.setDocIdentification(docIdentificationPP);
                personnePhysique = personnePhysiqueRepository.save(personnePhysique);
                vehiculeTraversant.setPersonnePhysique(personnePhysique);
                vehiculeTraversant.setOrganisation(personConnect.get().getOrganisation());
                vehiculeTraversant.setCreatedAt(ZonedDateTime.now());
                String ordr = "" + (vehiculeTraversantRepository.count() + 1);
                vehiculeTraversant = vehiculeTraversantRepository.save(vehiculeTraversant);
            }
            return vehiculeTraversantMapper.toDto(vehiculeTraversant);
        }else {///////////////////////////////////////////Personne Morale//////////////////////////////////////////////
            PersonneMorale personneMorale = personneMoraleMapper.toEntity(vehiculeTraversantDTO.getPersonneMoraleDTO());
            DocIdentificationPM docIdentificationPM = docIdentificationPMMapper.toEntity(vehiculeTraversantDTO.getDocIdentificationPMDTO());
            VehiculeTraversant vehiculeTraversant = vehiculeTraversantMapper.toEntity(vehiculeTraversantDTO);
            Optional<PersonnePhysique> personConnect = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
            DocIdentificationPM docIdentificationPMVerify = docIdentificationPMRepository.findByNumeroIFU(vehiculeTraversantDTO.getDocIdentificationPMDTO().getNumeroIFU());
            if(docIdentificationPMVerify != null){//////////////////////Le Doc Identite de la personne morale existe déjà////////////////////////////////////
                PersonneMorale personneMoraleVerify = personneMoraleRepository.findByNumeroIFU(docIdentificationPMVerify.getNumeroIFU());
                if(personneMoraleVerify != null){////////////////////Les autres infos de la personne morale existent déjà////////////////////////////////////
                    vehiculeTraversant.setPersonneMorale(personneMoraleVerify);
                    vehiculeTraversant.setOrganisation(personConnect.get().getOrganisation());
                    vehiculeTraversant.setCreatedAt(ZonedDateTime.now());
                    String ordr = "" + (vehiculeTraversantRepository.count() + 1);
                    vehiculeTraversant = vehiculeTraversantRepository.save(vehiculeTraversant);
                }else {///////////////////////////////////////////////Les autres infos de la personne morale n'existent pas//////////////////////////////////
                    personneMorale.setNumeroIFU(docIdentificationPM.getNumeroIFU());
                    personneMorale = personneMoraleRepository.save(personneMorale);
                    vehiculeTraversant.setPersonneMorale(personneMorale);
                    vehiculeTraversant.setOrganisation(personConnect.get().getOrganisation());
                    vehiculeTraversant.setCreatedAt(ZonedDateTime.now());
                    String ordr = "" + (vehiculeTraversantRepository.count() + 1);
                    vehiculeTraversant = vehiculeTraversantRepository.save(vehiculeTraversant);
                }
            }else {////////////////////////////////////////////////////Le Doc Identite de la personne morale n'existe pas///////////////////////////////////
                if (vehiculeTraversantDTO.getNationDTO() != null){
                    Nation nation = nationRepository.findNationById(vehiculeTraversantDTO.getNationDTO().getId());
                    docIdentificationPM.setNation(nation);
                }
                docIdentificationPM = docIdentificationPMRepository.save(docIdentificationPM);
                personneMorale.setNumeroIFU(docIdentificationPM.getNumeroIFU());
                personneMorale = personneMoraleRepository.save(personneMorale);
                vehiculeTraversant.setPersonneMorale(personneMorale);
                vehiculeTraversant.setOrganisation(personConnect.get().getOrganisation());
                vehiculeTraversant.setCreatedAt(ZonedDateTime.now());
                String ordr = "" + (vehiculeTraversantRepository.count() + 1);
                vehiculeTraversant = vehiculeTraversantRepository.save(vehiculeTraversant);
            }
            return vehiculeTraversantMapper.toDto(vehiculeTraversant);
        }

    }

    /**
     * Get all the vehiculeTraversants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehiculeTraversantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehiculeTraversants");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        Page<VehiculeTraversantDTO> vehiculeTravDTOPage = vehiculeTraversantRepository.findByOrganisationId(pageable, personnePhysique.get().getOrganisation().getId())
            .map(vehiculeTraversantMapper::toDto);
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
        return vehiculeTravDTOPage;
    }


    /**
     * Get one vehiculeTraversant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehiculeTraversantDTO> findOne(Long id) {
        log.debug("Request to get VehiculeTraversant : {}", id);
        return vehiculeTraversantRepository.findById(id)
            .map(vehiculeTraversantMapper::toDto);
    }

    /**
     * Delete the vehiculeTraversant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehiculeTraversant : {}", id);
        vehiculeTraversantRepository.deleteById(id);
    }
}
