package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.CollaborationDTO;
import com.supernettechnologie.impro.service.dto.OrganisationDTO;
import com.supernettechnologie.impro.service.dto.TypeActeurDTO;
import com.supernettechnologie.impro.service.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Organisation}.
 */
@Service
@Transactional
public class OrganisationService {

    private final Logger log = LoggerFactory.getLogger(OrganisationService.class);

    private final OrganisationRepository organisationRepository;
    private final UserRepository userRepository;

    private final OrganisationMapper organisationMapper;

    private final CategorieOrganisationRepository categorieOrganisationRepository;

    private final TypeOrganisationRepository typeOrganisationRepository;

    private final DocIdentificationPMRepository docIdentificationPMRepository;
    private final DocIdentificationPMMapper docIdentificationPMMapper;
    @Autowired
    private TypeActeurRepository typeActeurRepository;
    @Autowired
    private TypeActeurMapper typeActeurMapper;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;
    @Autowired
    private CollaborationService collaborationService;

    @Autowired
    private CarteWRepository carteWRepository;
    @Autowired
    private CarteWMapper carteWMapper;

    @Autowired
    private CollaborationRepository collaborationRepository;
    @Autowired
    private CollaborationMapper collaborationMapper;

    @Autowired
    private OrganisationLocaliteMapper organisationLocaliteMapper;
    @Autowired
    private OrganisationLocaliteRepository organisationLocaliteRepository;

    private long count;

    public OrganisationService(OrganisationRepository organisationRepository, UserRepository userRepository, OrganisationMapper organisationMapper, CategorieOrganisationRepository categorieOrganisationRepository, TypeOrganisationRepository typeOrganisationRepository, DocIdentificationPMRepository docIdentificationPMRepository, DocIdentificationPMMapper docIdentificationPMMapper) {
        this.organisationRepository = organisationRepository;
        this.userRepository = userRepository;
        this.organisationMapper = organisationMapper;
        this.categorieOrganisationRepository = categorieOrganisationRepository;
        this.typeOrganisationRepository = typeOrganisationRepository;
        this.docIdentificationPMRepository = docIdentificationPMRepository;
        this.docIdentificationPMMapper = docIdentificationPMMapper;
    }

    /**
     * Save a organisation.
     *
     * @param organisationDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganisationDTO save(OrganisationDTO organisationDTO) {
        log.debug("Request to save Organisation : {}", organisationDTO);
        Organisation organisation = organisationMapper.toEntity(organisationDTO);
        OrganisationLocalite organisationLocalite = null;
        if(organisationDTO.getOrganisationlocaliteDTO() != null) {
            log.debug("*******************************avant********************: {}", organisationDTO);
             organisationLocalite = organisationLocaliteMapper.toEntity(organisationDTO.getOrganisationlocaliteDTO());
            log.debug("******************************apres*********************: {}", organisationDTO);
        }
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        Optional<TypeActeurDTO> typeActeurDTO = typeActeurRepository.findByNom(organisationDTO.getActeur()).map(typeActeurMapper::toDto);

        System.out.println("Type Acteur => "+ typeActeurDTO.get().getNom());
        if (typeActeurDTO.get().getNom().equals("REVENDEUR")) {
            CollaborationDTO collaborationDTO = new CollaborationDTO();
            collaborationDTO.setConcessionnaireId(personnePhysique.get().getOrganisation().getId());
            collaborationDTO.setDateDebut(LocalDate.now());
            DocIdentificationPM docIdentificationPM = docIdentificationPMMapper.toEntity(organisationDTO.getDocIdentificationPMDTO());
            if (docIdentificationPM.getNumeroIFU() == null) {
                docIdentificationPM.setNumero("25 37 83 11");
                docIdentificationPM.setCodePostal("10 BP 13120");
                docIdentificationPM.setEmail("supnet@yahoo.com");
            }
            organisation.setNumeroOrdre((int) organisationRepository.count()+1);
            organisation.setNumeroPhone(organisationDTO.getNumeroPhone());
            organisation.setTypeActeur(typeActeurMapper.toEntity(typeActeurDTO.get()));
            log.debug("*********************************** : {}", organisationRepository.count());
            if (organisationLocalite != null) {
                organisation.setOrganisationLocalite(organisationLocaliteRepository.save(organisationLocalite));
            }
            organisation = organisationRepository.save(organisation);
            collaborationDTO.setRevendeurId(organisation.getId());
            docIdentificationPM.setOrganisation(organisation);
            docIdentificationPMRepository.save(docIdentificationPM);
            collaborationService.save(collaborationDTO);

        }else {
            DocIdentificationPM docIdentificationPM = docIdentificationPMMapper.toEntity(organisationDTO.getDocIdentificationPMDTO());
            if (docIdentificationPM.getNumeroIFU() == null) {
                docIdentificationPM.setNumero("25 37 83 11");
                docIdentificationPM.setCodePostal("10 BP 13120");
                docIdentificationPM.setEmail("supnet@yahoo.com");
            }
            organisation.setNumeroOrdre((int) organisationRepository.count()+1);
            organisation.setNumeroPhone(organisationDTO.getNumeroPhone());
            organisation.setTypeActeur(typeActeurMapper.toEntity(typeActeurDTO.get()));
            log.debug("*********************************** : {}", organisationRepository.count());
            if (organisationLocalite != null) {
                organisation.setOrganisationLocalite(organisationLocaliteRepository.save(organisationLocalite));
            }
            organisation = organisationRepository.save(organisation);
            docIdentificationPM.setOrganisation(organisation);
            docIdentificationPMRepository.save(docIdentificationPM);
        }
        return organisationMapper.toDto(organisation);
    }

    /**
     * Get all the organisations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganisationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organisations");
        return organisationRepository.findAll(pageable)
            .map(organisationMapper::toDto);
    }


    @Transactional(readOnly = true)
    public List<OrganisationDTO> findAllRevendeurs() {
        log.debug("Request to get all Organisations");
        List<Collaboration> collaborations = collaborationRepository.findAll();
        List<OrganisationDTO> organisationDTOS = new ArrayList<>();
        for (Collaboration collaboration : collaborations){
            Optional<Organisation> organisations = organisationRepository.findById(collaboration.getRevendeur().getId());
            if (!organisationDTOS.contains(organisationMapper.toDto(organisations.get()))) {
                organisationDTOS.add(organisationMapper.toDto(organisations.get()));
            }
        }
        //System.out.println("Les concessionnaires : " +organisationDTOS.get(0));
        return organisationDTOS;
    }

    @Transactional(readOnly = true)
    public List<OrganisationDTO> findAllConcessionnaires() {
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<Collaboration> collaborations = collaborationRepository.findAllByRevendeurId(personnePhysique.get().getOrganisation().getId());
        List<OrganisationDTO> organisationDTOS = new ArrayList<>();
        for (Collaboration collaboration : collaborations){
            Optional<Organisation> organisations = organisationRepository.findById(collaboration.getConcessionnaire().getId());
            organisationDTOS.add(organisationMapper.toDto(organisations.get()));
        }
        return organisationDTOS;
    }

    @Transactional(readOnly = true)
    public List<OrganisationDTO> findAllConcessionnairestotal() {
        List<CarteW> carteWS = carteWRepository.findAll();
        List<OrganisationDTO> organisationDTOS = new ArrayList<>();
        for (CarteW carteW : carteWS){
            Optional<Organisation> organisations = organisationRepository.findById(carteW.getOrganisation().getId());
            organisationDTOS.add(organisationMapper.toDto(organisations.get()));
        }
        return organisationDTOS;
    }

    @Transactional(readOnly = true)
    public Page<OrganisationDTO> findAllOrganisationsfils(Long id, Pageable pageable) {
        log.debug("Request to get all Organisations");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        return organisationRepository.findAllByPereId(personnePhysique.get().getOrganisation().getId(), pageable)
            .map(organisationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<OrganisationDTO> findAllRevendeursCollaborant() {
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<CollaborationDTO> collaborations = collaborationMapper.toDto(collaborationRepository.findAllByConcessionnaireId(personnePhysique.get().getOrganisation().getId()));
        List<OrganisationDTO> organisationDTOS = new ArrayList<>();
        for (CollaborationDTO collaboration : collaborations){
            Optional<Organisation> organisations = organisationRepository.findById(collaboration.getRevendeurId());
            organisationDTOS.add(organisationMapper.toDto(organisations.get()));
        }
        return organisationDTOS;
    }

    @Transactional(readOnly = true)
    public List<OrganisationDTO> findAllRevendeursCollaborantWith(Long idOrg) {
        List<CollaborationDTO> collaborations = collaborationMapper.toDto(collaborationRepository.findAllByConcessionnaireId(idOrg));
        List<OrganisationDTO> organisationDTOS = new ArrayList<>();
        for (CollaborationDTO collaboration : collaborations){
            Optional<Organisation> organisations = organisationRepository.findById(collaboration.getRevendeurId());
            organisationDTOS.add(organisationMapper.toDto(organisations.get()));
        }
        return organisationDTOS;
    }

    @Transactional(readOnly = true)
    public Page<OrganisationDTO> findMyorganisation(Long id, Pageable pageable) {
        log.debug("Request to get all Organisations");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        return organisationRepository.findAllById(personnePhysique.get().getOrganisation().getId(), pageable)
            .map(organisationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Organisation findByUser(String login) {
        System.out.println(login);
        Optional<User> user = userRepository.findOneByLogin(login);
        Organisation organisation = new Organisation();
        if (user.isPresent()){
        organisation = organisationRepository.findOrganisationById(user.get().getOrganisation().getId());
        }
        return organisation;
    }

    /**
     * Get one organisation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrganisationDTO> findOne(Long id) {
        log.debug("Request to get Organisation : {}", id);
        return organisationRepository.findById(id)
            .map(organisationMapper::toDto);
    }

    /**
     * Delete the organisation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Organisation : {}", id);
        organisationRepository.deleteById(id);
    }

}
