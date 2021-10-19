package com.supernettechnologie.impro.service;

import com.github.royken.converter.FrenchNumberToWords;
import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.*;
import com.supernettechnologie.impro.service.mapper.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

/**
 * Service Implementation for managing {@link Vente}.
 */
@Service
@Transactional
public class VenteService {

    private final Logger log = LoggerFactory.getLogger(VenteService.class);

    private final VenteRepository venteRepository;

    private final VenteMapper venteMapper;

    private final PersonnePhysiqueRepository personnePhysiqueRepository;

    private final PersonnePhysiqueMapper personnePhysiqueMapper;

    private final DocIdentificationPPRepository docIdentificationPPRepository;

    private final DocIdentificationPPMapper docIdentificationPPMapper;

    private final PersonneMoraleRepository personneMoraleRepository;

    private final PersonneMoraleMapper personneMoraleMapper;

    private final DocIdentificationPMRepository docIdentificationPMRepository;

    private final DocIdentificationPMMapper docIdentificationPMMapper;

    private final VehiculeMapper vehiculeMapper;

    private final VehiculeRepository vehiculeRepository;
    @Autowired
    private PlaqueImmatriculationRepository plaqueImmatriculationRepository;
    @Autowired
    private PlaqueImmatriculationMapper plaqueImmatriculationMapper;
    @Autowired
    private CertificatImmatriculationRepository certificatImmatriculationRepository;
    @Autowired
    private CertificatImmatriculationMapper certificatImmatriculationMapper;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private OrganisationMapper organisationMapper;
    @Autowired
    private ImmatriculationRepository immatriculationRepository;
    @Autowired
    private ImmatriculationMapper immatriculationMapper;
    @Autowired
    private ResourceLoader loader;
    @Autowired
    private ReportService reportService;
    @Autowired
    private MarqueVehiculeMapper marqueVehiculeMapper;
    @Autowired
    private MarqueVehiculeRepository marqueVehiculeRepository;
    @Autowired
    private TypeVehiculeMapper typeVehiculeMapper;
    @Autowired
    private TypeVehiculeRepository typeVehiculeRepository;
    @Autowired
    private  NationRepository nationRepository;

    public VenteService(VenteRepository venteRepository, VenteMapper venteMapper, PersonnePhysiqueRepository personnePhysiqueRepository, PersonnePhysiqueMapper personnePhysiqueMapper, DocIdentificationPPRepository docIdentificationPPRepository, DocIdentificationPPMapper docIdentificationPPMapper, PersonneMoraleRepository personneMoraleRepository, PersonneMoraleMapper personneMoraleMapper, DocIdentificationPMRepository docIdentificationPMRepository, DocIdentificationPMMapper docIdentificationPMMapper, VehiculeMapper vehiculeMapper, VehiculeRepository vehiculeRepository) {
        this.venteRepository = venteRepository;
        this.venteMapper = venteMapper;
        this.personnePhysiqueRepository = personnePhysiqueRepository;
        this.personnePhysiqueMapper = personnePhysiqueMapper;
        this.docIdentificationPPRepository = docIdentificationPPRepository;
        this.docIdentificationPPMapper = docIdentificationPPMapper;
        this.personneMoraleRepository = personneMoraleRepository;
        this.personneMoraleMapper = personneMoraleMapper;
        this.docIdentificationPMRepository = docIdentificationPMRepository;
        this.docIdentificationPMMapper = docIdentificationPMMapper;
        this.vehiculeMapper = vehiculeMapper;
        this.vehiculeRepository = vehiculeRepository;
    }

    /**
     * Save a vente.
     *
     * @param venteDTO the entity to save.
     * @return the persisted entity.
     */
    public VenteDTO save(VenteDTO venteDTO) {
        log.debug("Request to save Vente : {}", venteDTO);
        log.debug("Request to save Vente : {}", venteDTO.getVehiculeDTOStock());
        if (venteDTO.getDocIdentificationPPDTO() != null){//////////////Vente à une personne physique/////////////////////////////////////////////////
            if(venteDTO.getVehiculeDTOStock() == null){//////////////////////////////////Vehicule hors stock/////////////////////////////////////////PP
                PersonnePhysique personnePhysique = personnePhysiqueMapper.toEntity(venteDTO.getPersonnePhysiqueDTO());
                DocIdentificationPP docIdentificationPP = docIdentificationPPMapper.toEntity(venteDTO.getDocIdentificationPPDTO());
                Vehicule vehicule = vehiculeMapper.toEntity(venteDTO.getVehiculeDTO());
                Vente vente = venteMapper.toEntity(venteDTO);
                List<DocIdentificationPP> docIdentificationPPList = docIdentificationPPRepository.findAllByNip(venteDTO.getDocIdentificationPPDTO().getNip());
                if (docIdentificationPPList.size() > 0) {
                    DocIdentificationPP docIdentificationPPVerify = docIdentificationPPRepository.findAllByNip(venteDTO.getDocIdentificationPPDTO().getNip()).get(0);
                    PersonnePhysique personnePhysiqueVerify = personnePhysiqueRepository.findByDocIdentification(docIdentificationPPVerify);
                    if(personnePhysiqueVerify != null){////////////////////Les autres infos de la personne Physique existent déjà//////////////////////
                        vente.setPersonnePhysique(personnePhysiqueVerify);
                        String ordr = "" + (venteRepository.count() + 1);
                        vente.setNumeroVente(ordr);
                        vente = venteRepository.save(vente);
                    }else {///////////////////////////////////////////////Les autres infos de la personne Physique n'existent pas//////////////////////
                        personnePhysique.setDocIdentification(docIdentificationPPVerify);
                        personnePhysique = personnePhysiqueRepository.save(personnePhysique);
                        vente.setPersonnePhysique(personnePhysique);
                        String ordr = "" + (venteRepository.count() + 1);
                        vente.setNumeroVente(ordr);
                        vente = venteRepository.save(vente);
                    }
                }else {////////////////////////////////////////////////////Le Doc Identite de la personne Physique n'existe pas////////////////////////
                    if (venteDTO.getNationDTO() != null) {
                        Nation nation = nationRepository.findNationById(venteDTO.getNationDTO().getId());
                        docIdentificationPP.setNation(nation);
                    }
                    docIdentificationPP = docIdentificationPPRepository.save(docIdentificationPP);
                    personnePhysique.setDocIdentification(docIdentificationPP);
                    personnePhysique = personnePhysiqueRepository.save(personnePhysique);
                    vente.setPersonnePhysique(personnePhysique);
                    String ordr = "" + (venteRepository.count() + 1);
                    vente.setNumeroVente(ordr);
                    vente = venteRepository.save(vente);
                }


                if(vehiculeRepository.findByNumeroChassis(venteDTO.getVehiculeDTO().getNumeroChassis()).isPresent()){
                    System.out.notify();
                    System.out.println("Le vehicule existe déjà");
                    log.debug(" ******************************** Le vehicule existe déjà ");
                    throw Problem.builder()
                        .withStatus(Status.CONFLICT)
                        .withTitle("Already exist!!")
                        .with("message","Le vehicule existe déjà")
                        .build();
                }else{
                    vehicule.setVente(vente);
                    vehiculeRepository.save(vehicule);
                }
                log.debug("************************************PP///////////////////////////////////HStock : {}", venteDTO.getVehiculeDTO().getModel());
                log.debug("************************************PP///////////////////////////////////HStock : {}", venteDTO.getVehiculeDTO().getModel());
                log.debug("************************************PP///////////////////////////////////HStock : {}", venteDTO.getVehiculeDTOStock());
                log.debug("************************************PP///////////////////////////////////HStock : {}", venteDTO.getVehiculeDTOStock());
                return venteMapper.toDto(vente);
            }else{/////////////////////////////////////////////////////////////////////Vehicule du stock/////////////////////////////////////////////PP
                PersonnePhysique personnePhysique = personnePhysiqueMapper.toEntity(venteDTO.getPersonnePhysiqueDTO());
                DocIdentificationPP docIdentificationPP = docIdentificationPPMapper.toEntity(venteDTO.getDocIdentificationPPDTO());
                log.debug("************************************PP/////////////////////////////////// : {}", venteDTO.getVehiculeDTOStock().getModel());
                log.debug("************************************PP/////////////////////////////////// : {}", venteDTO.getVehiculeDTOStock().getModel());
                Vehicule vehiculeStock = vehiculeMapper.toEntity(venteDTO.getVehiculeDTOStock());
                Vente vente = venteMapper.toEntity(venteDTO);
                List<DocIdentificationPP> docIdentificationPPList = docIdentificationPPRepository.findAllByNip(venteDTO.getDocIdentificationPPDTO().getNip());
                if (docIdentificationPPList.size() > 0) {
                    DocIdentificationPP docIdentificationPPVerify = docIdentificationPPRepository.findAllByNip(venteDTO.getDocIdentificationPPDTO().getNip()).get(0);
                    PersonnePhysique personnePhysiqueVerify = personnePhysiqueRepository.findByDocIdentification(docIdentificationPPVerify);
                    if(personnePhysiqueVerify != null){////////////////////Les autres infos de la personne Physique existent déjà//////////////////////
                        vente.setPersonnePhysique(personnePhysiqueVerify);
                        String ordr = "" + (venteRepository.count() + 1);
                        vente.setNumeroVente(ordr);
                        vente = venteRepository.save(vente);
                    }else {///////////////////////////////////////////////Les autres infos de la personne Physique n'existent pas//////////////////////
                        personnePhysique.setDocIdentification(docIdentificationPPVerify);
                        personnePhysique = personnePhysiqueRepository.save(personnePhysique);
                        vente.setPersonnePhysique(personnePhysique);
                        String ordr = "" + (venteRepository.count() + 1);
                        vente.setNumeroVente(ordr);
                        vente = venteRepository.save(vente);
                    }
                }else {////////////////////////////////////////////////////Le Doc Identite de la personne Physique n'existe pas////////////////////////
                    if (venteDTO.getNationDTO() != null) {
                        Nation nation = nationRepository.findNationById(venteDTO.getNationDTO().getId());
                        docIdentificationPP.setNation(nation);
                    }
                    docIdentificationPP = docIdentificationPPRepository.save(docIdentificationPP);
                    personnePhysique.setDocIdentification(docIdentificationPP);
                    personnePhysique = personnePhysiqueRepository.save(personnePhysique);
                    vente.setPersonnePhysique(personnePhysique);
                    String ordr = "" + (venteRepository.count() + 1);
                    vente.setNumeroVente(ordr);
                    vente = venteRepository.save(vente);
                }

                vehiculeStock.setVente(vente);
                vehiculeRepository.save(vehiculeStock);
                return venteMapper.toDto(vente);
            }
        }else{////////////////////////////////////////////////////////////////Vente à une personne morale//////////////////////////////////////////////////////////
            if(venteDTO.getVehiculeDTOStock() == null){//////////////////////////////////Vehicule hors stock/////////////////////////////////////////////////////PM
                PersonneMorale personneMorale = personneMoraleMapper.toEntity(venteDTO.getPersonneMoraleDTO());
                DocIdentificationPM docIdentificationPM = docIdentificationPMMapper.toEntity(venteDTO.getDocIdentificationPMDTO());
                Vehicule vehicule = vehiculeMapper.toEntity(venteDTO.getVehiculeDTO());
                Vente vente = venteMapper.toEntity(venteDTO);
                DocIdentificationPM docIdentificationPMVerify = docIdentificationPMRepository.findByNumeroIFU(venteDTO.getDocIdentificationPMDTO().getNumeroIFU());
                if(docIdentificationPMVerify != null){//////////////////////Le Doc Identite de la personne morale existe déjà////////////////////////////////////
                    PersonneMorale personneMoraleVerify = personneMoraleRepository.findByNumeroIFU(docIdentificationPMVerify.getNumeroIFU());
                    if(personneMoraleVerify != null){////////////////////Les autres infos de la personne morale existent déjà////////////////////////////////////
                        vente.setPersonneMorale(personneMoraleVerify);
                        String ordr = "" + (venteRepository.count() + 1);
                        vente.setNumeroVente(ordr);
                        vente = venteRepository.save(vente);
                    }else {///////////////////////////////////////////////Les autres infos de la personne morale n'existent pas//////////////////////////////////
                        personneMorale.setNumeroIFU(docIdentificationPM.getNumeroIFU());
                        personneMorale = personneMoraleRepository.save(personneMorale);
                        vente.setPersonneMorale(personneMorale);
                        String ordr = "" + (venteRepository.count() + 1);
                        vente.setNumeroVente(ordr);
                        vente = venteRepository.save(vente);
                    }
                }else {////////////////////////////////////////////////////Le Doc Identite de la personne morale n'existe pas///////////////////////////////////
                    if (venteDTO.getNationDTO() != null) {
                        Nation nation = nationRepository.findNationById(venteDTO.getNationDTO().getId());
                        docIdentificationPM.setNation(nation);
                    }
                    docIdentificationPM = docIdentificationPMRepository.save(docIdentificationPM);
                    personneMorale.setNumeroIFU(docIdentificationPM.getNumeroIFU());
                    personneMorale = personneMoraleRepository.save(personneMorale);
                    vente.setPersonneMorale(personneMorale);
                    String ordr = "" + (venteRepository.count() + 1);
                    vente.setNumeroVente(ordr);
                    vente = venteRepository.save(vente);
                }
                if(vehiculeRepository.findByNumeroChassis(venteDTO.getVehiculeDTO().getNumeroChassis()).isPresent()){
                    System.out.notify();
                    System.out.println("Le vehicule existe déjà");
                    log.debug(" ******************************** Le vehicule existe déjà ");
                    throw Problem.builder()
                        .withStatus(Status.CONFLICT)
                        .withTitle("Already exist!!")
                        .with("message","Le vehicule existe déjà")
                        .build();
                }else{
                    vehicule.setVente(vente);
                    vehiculeRepository.save(vehicule);
                }
                log.debug("************************************PM///////////////////////////////////HStock : {}", venteDTO.getVehiculeDTO().getModel());
                log.debug("************************************PM///////////////////////////////////HStock : {}", venteDTO.getVehiculeDTO().getModel());
                log.debug("************************************PM///////////////////////////////////HStock : {}", venteDTO.getVehiculeDTOStock());
                log.debug("************************************PM///////////////////////////////////HStock : {}", venteDTO.getVehiculeDTOStock());
                return venteMapper.toDto(vente);
            }else{///////////////////////////////////////////////////////////////////////////////////////////////Le vehicule du stock/////////////////////////PM
                PersonneMorale personneMorale = personneMoraleMapper.toEntity(venteDTO.getPersonneMoraleDTO());
                DocIdentificationPM docIdentificationPM = docIdentificationPMMapper.toEntity(venteDTO.getDocIdentificationPMDTO());
                Vente vente = venteMapper.toEntity(venteDTO);
                Vehicule vehiculeStock = vehiculeMapper.toEntity(venteDTO.getVehiculeDTOStock());
                log.debug("************************************PM/////////////////////////////////// : {}", venteDTO.getVehiculeDTOStock().getModel());
                log.debug("************************************PM/////////////////////////////////// : {}", venteDTO.getVehiculeDTOStock().getModel());
                DocIdentificationPM docIdentificationPMVerify = docIdentificationPMRepository.findByNumeroIFU(venteDTO.getDocIdentificationPMDTO().getNumeroIFU());
                if(docIdentificationPMVerify != null){//////////////////////Le Doc Identite de la personne morale existe déjà////////////////////////////////////
                    PersonneMorale personneMoraleVerify = personneMoraleRepository.findByNumeroIFU(docIdentificationPMVerify.getNumeroIFU());
                    if(personneMoraleVerify != null){
                        vente.setPersonneMorale(personneMoraleVerify);
                        String ordr = "" + (venteRepository.count() + 1);
                        vente.setNumeroVente(ordr);
                        vente = venteRepository.save(vente);
                    }else {///////////////////////////////////////////////Les autres infos de la personne morale n'existent pas//////////////////////////////////
                        personneMorale.setNumeroIFU(docIdentificationPM.getNumeroIFU());
                        personneMorale = personneMoraleRepository.save(personneMorale);
                        vente.setPersonneMorale(personneMorale);
                        String ordr = "" + (venteRepository.count() + 1);
                        vente.setNumeroVente(ordr);
                        vente = venteRepository.save(vente);
                    }
                }else {////////////////////////////////////////////////////Le Doc Identite de la personne morale n'existe pas/////////////////////////////////////
                    if (venteDTO.getNationDTO() != null) {
                        Nation nation = nationRepository.findNationById(venteDTO.getNationDTO().getId());
                        docIdentificationPM.setNation(nation);
                    }
                    docIdentificationPM = docIdentificationPMRepository.save(docIdentificationPM);
                    personneMorale.setNumeroIFU(docIdentificationPM.getNumeroIFU());
                    personneMorale = personneMoraleRepository.save(personneMorale);
                    vente.setPersonneMorale(personneMorale);
                    String ordr = "" + (venteRepository.count() + 1);
                    vente.setNumeroVente(ordr);
                    vente = venteRepository.save(vente);
                }
                vehiculeStock.setVente(vente);
                vehiculeRepository.save(vehiculeStock);
                return venteMapper.toDto(vente);
            }
        }
    }

    /**
     * Get all the ventes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ventes");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        Page<VenteDTO> venteDTOPage = venteRepository.findAllByRevendeurId(pageable, personnePhysique.get().getOrganisation().getId())
            .map(venteMapper::toDto);

        for (VenteDTO venteDTO : venteDTOPage.getContent()){
            if (venteDTO.getPersonnePhysiqueId() != null) {
                Optional<PersonnePhysiqueDTO> personnePhysiqueDTO = personnePhysiqueRepository.findById(venteDTO.getPersonnePhysiqueId())
                    .map(personnePhysiqueMapper::toDto);
                venteDTO.setPersonnePhysiqueDTO(personnePhysiqueDTO.get());
            }else {
                Optional<PersonneMoraleDTO> personneMoraleDTO = personneMoraleRepository.findById(venteDTO.getPersonneMoraleId())
                    .map(personneMoraleMapper::toDto);
                venteDTO.setPersonneMoraleDTO(personneMoraleDTO.get());
            }
        }
        return venteDTOPage;
    }

    @Transactional(readOnly = true)
    public List<VenteDTO> findAlls() {
        log.debug("Request to get all Ventes");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<VenteDTO> venteDTOPage = venteMapper.toDto(venteRepository.findAllByRevendeurId(personnePhysique.get().getOrganisation().getId()));

        for (VenteDTO venteDTO : venteDTOPage){
            if (venteDTO.getPersonnePhysiqueId() != null) {
                Optional<PersonnePhysiqueDTO> personnePhysiqueDTO = personnePhysiqueRepository.findById(venteDTO.getPersonnePhysiqueId())
                    .map(personnePhysiqueMapper::toDto);
                venteDTO.setPersonnePhysiqueDTO(personnePhysiqueDTO.get());
            }else {
                Optional<PersonneMoraleDTO> personneMoraleDTO = personneMoraleRepository.findById(venteDTO.getPersonneMoraleId())
                    .map(personneMoraleMapper::toDto);
                venteDTO.setPersonneMoraleDTO(personneMoraleDTO.get());
            }
        }
        return venteDTOPage;
    }

    /**
     * Get one vente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VenteDTO> findOne(Long id) {
        log.debug("Request to get Vente : {}", id);
        Optional<VenteDTO> venteDTO = venteRepository.findById(id)
            .map(venteMapper::toDto);
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisationRepository.findById(venteDTO.get().getRevendeurId()).get());
        venteDTO.get().setOrganisationDTO(organisationDTO);
        if (venteDTO.get().getPersonnePhysiqueId() != null) {
            PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper
                .toDto(personnePhysiqueRepository.findById(venteDTO.get().getPersonnePhysiqueId()).get());
            venteDTO.get().setPersonnePhysiqueDTO(personnePhysiqueDTO);
        }else {
            PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper
                .toDto(personneMoraleRepository.findById(venteDTO.get().getPersonneMoraleId()).get());
            venteDTO.get().setPersonneMoraleDTO(personneMoraleDTO);
        }
        VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehiculeRepository.findByVenteId(venteDTO.get().getId()).get());
        venteDTO.get().setVehiculeDTO(vehiculeDTO);

        return venteDTO;
    }

    public Optional<VenteDTO> findOneQr(String qr) {
        log.debug("Request to get PersonnePhysique : {}", qr);
        List<PlaqueImmatriculation> plaqueImmatriculations = plaqueImmatriculationRepository.findAllByCodeQR(qr);
        Optional<CertificatImmatriculationDTO> certificatImmatriculationDTO = certificatImmatriculationRepository.findById(plaqueImmatriculations.get(0).getCertificatImmatriculation().getId()).map(certificatImmatriculationMapper::toDto);
        PlaqueImmatriculationDTO plaqueImmatriculationDTO = plaqueImmatriculationMapper.toDto(plaqueImmatriculations.get(0));
        Optional<Vehicule> vehicule = vehiculeRepository.findById(plaqueImmatriculationDTO.getVehiculeId());
        Optional<ImmatriculationDTO> immatriculationDTO = immatriculationRepository.findByCertificatImmatriculationId(certificatImmatriculationDTO.get().getId()).map(immatriculationMapper::toDto);
        Optional<VenteDTO> venteDTO = venteRepository.findById(vehicule.get().getVente().getId())
            .map(venteMapper::toDto);
        venteDTO.get().setImmatriculationDTO(immatriculationDTO.get());
        venteDTO.get().setPlaqueImmatriculationDTO(plaqueImmatriculationDTO);
        if (venteDTO.get().getPersonnePhysiqueId() != null) {
            Optional<PersonnePhysiqueDTO> personnePhysiqueDTO = personnePhysiqueRepository.findById(venteDTO.get().getPersonnePhysiqueId()).map(personnePhysiqueMapper::toDto);
            Optional<DocIdentificationPPDTO> docIdentificationPPDTO = docIdentificationPPRepository.findById(personnePhysiqueDTO.get().getDocIdentificationId()).map(docIdentificationPPMapper::toDto);
            Optional<OrganisationDTO> organisationDTO = organisationRepository.findById(venteDTO.get().getRevendeurId()).map(organisationMapper::toDto);
            venteDTO.get().setOrganisationDTO(organisationDTO.get());
            venteDTO.get().setPersonnePhysiqueDTO(personnePhysiqueDTO.get());
            venteDTO.get().setDocIdentificationPPDTO(docIdentificationPPDTO.get());
            venteDTO.get().setVehiculeDTO(vehiculeMapper.toDto(vehicule.get()));
        }else {
            Optional<PersonneMoraleDTO> personneMoraleDTO = personneMoraleRepository.findById(venteDTO.get().getPersonneMoraleId()).map(personneMoraleMapper::toDto);
            Optional<DocIdentificationPMDTO> docIdentificationPMDTO = docIdentificationPMRepository.findByPersonneMoraleId(personneMoraleDTO.get().getId()).map(docIdentificationPMMapper::toDto);
            Optional<OrganisationDTO> organisationDTO = organisationRepository.findById(venteDTO.get().getRevendeurId()).map(organisationMapper::toDto);
            venteDTO.get().setOrganisationDTO(organisationDTO.get());
            venteDTO.get().setPersonneMoraleDTO(personneMoraleDTO.get());
            venteDTO.get().setDocIdentificationPMDTO(docIdentificationPMDTO.get());
            venteDTO.get().setVehiculeDTO(vehiculeMapper.toDto(vehicule.get()));
        }

        System.out.println("Vehicule : "+venteDTO.get().getPersonnePhysiqueDTO());
        return venteDTO;
    }

    public Optional<VenteDTO> findOneByChassis(String chassis) {
        log.debug("Request to get PersonnePhysique : {}", chassis);
        Optional<Vehicule> vehicule = vehiculeRepository.findByNumeroChassis(chassis);
        if (vehicule.isPresent()) {
            System.out.println("Vehicule Id : " +chassis);
            List<PlaqueImmatriculation> plaqueImmatriculations = plaqueImmatriculationRepository.findAllByVehiculeId(vehicule.get().getId());
            Optional<CertificatImmatriculationDTO> certificatImmatriculationDTO = certificatImmatriculationRepository.findById(plaqueImmatriculations.get(0).getCertificatImmatriculation().getId()).map(certificatImmatriculationMapper::toDto);
            PlaqueImmatriculationDTO plaqueImmatriculationDTO = plaqueImmatriculationMapper.toDto(plaqueImmatriculations.get(0));
            Optional<ImmatriculationDTO> immatriculationDTO = immatriculationRepository.findByCertificatImmatriculationId(certificatImmatriculationDTO.get().getId()).map(immatriculationMapper::toDto);
            Optional<VenteDTO> venteDTO = venteRepository.findById(vehicule.get().getVente().getId())
                .map(venteMapper::toDto);
            venteDTO.get().setImmatriculationDTO(immatriculationDTO.get());
            venteDTO.get().setPlaqueImmatriculationDTO(plaqueImmatriculationDTO);
            if (venteDTO.get().getPersonnePhysiqueId() != null) {
                Optional<PersonnePhysiqueDTO> personnePhysiqueDTO = personnePhysiqueRepository.findById(venteDTO.get().getPersonnePhysiqueId()).map(personnePhysiqueMapper::toDto);
                Optional<DocIdentificationPPDTO> docIdentificationPPDTO = docIdentificationPPRepository.findById(personnePhysiqueDTO.get().getDocIdentificationId()).map(docIdentificationPPMapper::toDto);
                Optional<OrganisationDTO> organisationDTO = organisationRepository.findById(venteDTO.get().getRevendeurId()).map(organisationMapper::toDto);
                venteDTO.get().setOrganisationDTO(organisationDTO.get());
                venteDTO.get().setPersonnePhysiqueDTO(personnePhysiqueDTO.get());
                venteDTO.get().setDocIdentificationPPDTO(docIdentificationPPDTO.get());
                venteDTO.get().setVehiculeDTO(vehiculeMapper.toDto(vehicule.get()));
            }else {
                Optional<PersonneMoraleDTO> personneMoraleDTO = personneMoraleRepository.findById(venteDTO.get().getPersonneMoraleId()).map(personneMoraleMapper::toDto);
                Optional<DocIdentificationPMDTO> docIdentificationPMDTO = docIdentificationPMRepository.findByPersonneMoraleId(personneMoraleDTO.get().getId()).map(docIdentificationPMMapper::toDto);
                Optional<OrganisationDTO> organisationDTO = organisationRepository.findById(venteDTO.get().getRevendeurId()).map(organisationMapper::toDto);
                venteDTO.get().setOrganisationDTO(organisationDTO.get());
                venteDTO.get().setPersonneMoraleDTO(personneMoraleDTO.get());
                venteDTO.get().setDocIdentificationPMDTO(docIdentificationPMDTO.get());
                venteDTO.get().setVehiculeDTO(vehiculeMapper.toDto(vehicule.get()));
            }
            System.out.println("Vehicule : "+venteDTO.get().getPersonnePhysiqueDTO());
            return venteDTO;
        }else {
            System.out.println("Numero : " +chassis);
            List<PlaqueImmatriculation> plaqueImmatriculations = plaqueImmatriculationRepository.findAllByNumeroImmatriculation(chassis);
            System.out.println("Numero : " +plaqueImmatriculations.get(0).getId());
            Vehicule vehicule1 = vehiculeRepository.findByPlaqueImmatriculationsId(plaqueImmatriculations.get(0).getId());
            Optional<CertificatImmatriculationDTO> certificatImmatriculationDTO = certificatImmatriculationRepository.findById(plaqueImmatriculations.get(0).getCertificatImmatriculation().getId()).map(certificatImmatriculationMapper::toDto);
            PlaqueImmatriculationDTO plaqueImmatriculationDTO = plaqueImmatriculationMapper.toDto(plaqueImmatriculations.get(0));
            Optional<ImmatriculationDTO> immatriculationDTO = immatriculationRepository.findByCertificatImmatriculationId(certificatImmatriculationDTO.get().getId()).map(immatriculationMapper::toDto);
            Optional<VenteDTO> venteDTO = venteRepository.findById(vehicule1.getVente().getId())
                .map(venteMapper::toDto);
            venteDTO.get().setImmatriculationDTO(immatriculationDTO.get());
            venteDTO.get().setPlaqueImmatriculationDTO(plaqueImmatriculationDTO);
            if (venteDTO.get().getPersonnePhysiqueId() != null) {
                Optional<PersonnePhysiqueDTO> personnePhysiqueDTO = personnePhysiqueRepository.findById(venteDTO.get().getPersonnePhysiqueId()).map(personnePhysiqueMapper::toDto);
                Optional<DocIdentificationPPDTO> docIdentificationPPDTO = docIdentificationPPRepository.findById(personnePhysiqueDTO.get().getDocIdentificationId()).map(docIdentificationPPMapper::toDto);
                Optional<OrganisationDTO> organisationDTO = organisationRepository.findById(venteDTO.get().getRevendeurId()).map(organisationMapper::toDto);
                venteDTO.get().setOrganisationDTO(organisationDTO.get());
                venteDTO.get().setPersonnePhysiqueDTO(personnePhysiqueDTO.get());
                venteDTO.get().setDocIdentificationPPDTO(docIdentificationPPDTO.get());
                venteDTO.get().setVehiculeDTO(vehiculeMapper.toDto(vehicule1));
            }else {
                Optional<PersonneMoraleDTO> personneMoraleDTO = personneMoraleRepository.findById(venteDTO.get().getPersonneMoraleId()).map(personneMoraleMapper::toDto);
                Optional<DocIdentificationPMDTO> docIdentificationPMDTO = docIdentificationPMRepository.findByPersonneMoraleId(personneMoraleDTO.get().getId()).map(docIdentificationPMMapper::toDto);
                Optional<OrganisationDTO> organisationDTO = organisationRepository.findById(venteDTO.get().getRevendeurId()).map(organisationMapper::toDto);
                venteDTO.get().setOrganisationDTO(organisationDTO.get());
                venteDTO.get().setPersonneMoraleDTO(personneMoraleDTO.get());
                venteDTO.get().setDocIdentificationPMDTO(docIdentificationPMDTO.get());
                venteDTO.get().setVehiculeDTO(vehiculeMapper.toDto(vehicule1));
            }
            System.out.println("Vehicule : "+venteDTO.get().getPersonnePhysiqueDTO());
            return venteDTO;
        }

    }

    /**
     * Delete the vente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Vente : {}", id);
        venteRepository.deleteById(id);
    }

    public void findCertificatVente(Long id) throws JRException, IOException {
        Optional<VenteDTO> venteDTO = venteRepository.findById(id)
            .map(venteMapper::toDto);
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisationRepository.findById(venteDTO.get().getRevendeurId()).get());
        VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehiculeRepository.findByVenteId(venteDTO.get().getId()).get());


        if (venteDTO.get().getPersonnePhysiqueId() != null) {
            PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper
                .toDto(personnePhysiqueRepository.findById(venteDTO.get().getPersonnePhysiqueId()).get());
            PersonnePhysiqueDTO personnePhysiqueDTO2 = personnePhysiqueMapper
                .toDto(personnePhysiqueRepository.findByOrganisationId(organisationDTO.getId()).get());
            DocIdentificationPPDTO docIdentificationPPDTO = docIdentificationPPMapper.toDto(docIdentificationPPRepository.findById(personnePhysiqueDTO.getDocIdentificationId()).get());
            MarqueVehiculeDTO marqueVehiculeDTO = marqueVehiculeMapper
                .toDto(marqueVehiculeRepository.findById(vehiculeDTO.getMarqueVehiculeId()).get());

            String path = loader.getResource("classpath:certificat_vente.jrxml").getURI().getPath();

            final Map<String, Object> parameters = new HashMap<>();
            List<JasperPrint> jpList = new ArrayList<>();

            parameters.put("vendeur", (personnePhysiqueDTO2.getNom() + " " +personnePhysiqueDTO2.getPrenom()).toUpperCase());
            parameters.put("nomclient", personnePhysiqueDTO.getNom() + " " +personnePhysiqueDTO.getPrenom());
            parameters.put("profession", personnePhysiqueDTO.getResidence());
            parameters.put("adresse", personnePhysiqueDTO.getResidence());
            parameters.put("cnib", docIdentificationPPDTO.getNumeroDoc());
            parameters.put("phone", personnePhysiqueDTO.getTelephone());
            parameters.put("genre", vehiculeDTO.getTypes());
            parameters.put("modele", marqueVehiculeDTO.getLibelle());
            parameters.put("chassis", vehiculeDTO.getNumeroChassis());
            parameters.put("carross", vehiculeDTO.getRegime());
            parameters.put("credit", String.format("%,d", venteDTO.get().getQuantiteVendue()) + " FCFA");
            parameters.put("date", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));

            JasperReport jasperReport
                = JasperCompileManager.compileReport(path);
            jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
            JasperPrint jasperPrint
                = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            jpList.add(jasperPrint);
            /*new File("C:\\CertificatVente").mkdir();
            String chemin = "C:\\CertificatVente";
            reportService.generateCertificat(jpList, chemin+"\\"+ "CertificatVente_" + personnePhysiqueDTO.getNom() +".pdf");*/
            if (SystemUtils.IS_OS_LINUX) {
                new File("/root/CertificatVente/").mkdir();
                String chemin = "/root/CertificatVente/";
                reportService.generateCertificat(jpList, chemin+"CertificatVente_"+personnePhysiqueDTO.getNom()+".pdf");
            } else {
                new File("C:\\CertificatVente").mkdir();
                String chemin = "C:\\CertificatVente";
                reportService.generateCertificat(jpList, chemin+"\\CertificatVente_"+personnePhysiqueDTO.getNom()+".pdf");
            }
        }
        else {
            PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper
                .toDto(personneMoraleRepository.findById(venteDTO.get().getPersonneMoraleId()).get());
            PersonnePhysiqueDTO personnePhysiqueDTO2 = personnePhysiqueMapper
                .toDto(personnePhysiqueRepository.findByOrganisationId(organisationDTO.getId()).get());
            DocIdentificationPMDTO docIdentificationPMDTO = docIdentificationPMMapper
                .toDto(docIdentificationPMRepository.findById(venteDTO.get().getPersonneMoraleId()).get());
            MarqueVehiculeDTO marqueVehiculeDTO = marqueVehiculeMapper
                .toDto(marqueVehiculeRepository.findById(vehiculeDTO.getMarqueVehiculeId()).get());

            String path = loader.getResource("classpath:certificat_vente.jrxml").getURI().getPath();

            final Map<String, Object> parameters = new HashMap<>();
            List<JasperPrint> jpList = new ArrayList<>();

            parameters.put("vendeur", personnePhysiqueDTO2.getNom() + " " +personnePhysiqueDTO2.getPrenom());
            parameters.put("nomclient", personneMoraleDTO.getDenomination());
            parameters.put("profession", "null");
            parameters.put("adresse", docIdentificationPMDTO.getNumeroIFU());
            parameters.put("cnib", docIdentificationPMDTO.getNumeroRCCM());
            parameters.put("phone", docIdentificationPMDTO.getTelephone());
            parameters.put("genre", vehiculeDTO.getTypes());
            parameters.put("modele", marqueVehiculeDTO.getLibelle());
            parameters.put("chassis", vehiculeDTO.getNumeroChassis());
            parameters.put("carross", vehiculeDTO.getRegime());
            parameters.put("credit", String.format("%,d", venteDTO.get().getQuantiteVendue() ));
            parameters.put("date", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));

            JasperReport jasperReport
                = JasperCompileManager.compileReport(path);
            jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
            JasperPrint jasperPrint
                = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            jpList.add(jasperPrint);
            /*new File("C:\\CertificatVente").mkdir();
            String chemin = "C:\\CertificatVente";
            reportService.generateCertificat(jpList, chemin+"\\"+ "CertificatVente_" + personneMoraleDTO.getDenomination() +".pdf");*/
            if (SystemUtils.IS_OS_LINUX) {
                new File("/root/CertificatVente/").mkdir();
                String chemin = "/root/CertificatVente/";
                reportService.generateCertificat(jpList, chemin+"CertificatVente_"+personneMoraleDTO.getDenomination()+".pdf");
            } else {
                new File("C:\\CertificatVente").mkdir();
                String chemin = "C:\\CertificatVente";
                reportService.generateCertificat(jpList, chemin+"\\CertificatVente_"+personneMoraleDTO.getDenomination()+".pdf");
            }
        }

    }

    public void findCertificatConformite(Long id) throws IOException, JRException {
        Optional<VenteDTO> venteDTO = venteRepository.findById(id)
            .map(venteMapper::toDto);
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisationRepository.findById(venteDTO.get().getRevendeurId()).get());
        VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehiculeRepository.findByVenteId(venteDTO.get().getId()).get());

        if (venteDTO.get().getPersonnePhysiqueId() != null) {
            PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper
                .toDto(personnePhysiqueRepository.findById(venteDTO.get().getPersonnePhysiqueId()).get());
            PersonnePhysiqueDTO personnePhysiqueDTO2 = personnePhysiqueMapper
                .toDto(personnePhysiqueRepository.findByOrganisationId(organisationDTO.getId()).get());
            DocIdentificationPPDTO docIdentificationPPDTO = docIdentificationPPMapper.toDto(docIdentificationPPRepository.findById(personnePhysiqueDTO.getDocIdentificationId()).get());
            MarqueVehiculeDTO marqueVehiculeDTO = marqueVehiculeMapper
                .toDto(marqueVehiculeRepository.findById(vehiculeDTO.getMarqueVehiculeId()).get());

            String path = loader.getResource("classpath:conformite.jrxml").getURI().getPath();

            final Map<String, Object> parameters = new HashMap<>();
            List<JasperPrint> jpList = new ArrayList<>();

            parameters.put("vendeur", personnePhysiqueDTO2.getNom() + " " +personnePhysiqueDTO2.getPrenom());
            parameters.put("nomclient", personnePhysiqueDTO.getNom() + " " +personnePhysiqueDTO.getPrenom());
            parameters.put("places", String.valueOf(vehiculeDTO.getNbrPlace()));
            parameters.put("energie", vehiculeDTO.getEnergie());
            parameters.put("ptac", String.valueOf(vehiculeDTO.getPoidsVide()) + "/" + String.valueOf(vehiculeDTO.getChargeUtile()) + "/" + String.valueOf(vehiculeDTO.getPtac()));
            //parameters.put("type", typeVehiculeDTO.getLibelle());
            parameters.put("genre", vehiculeDTO.getTypes());
            parameters.put("marque", marqueVehiculeDTO.getLibelle());
            parameters.put("modele", vehiculeDTO.getModel());
            parameters.put("type", vehiculeDTO.getNumeroChassis());
            parameters.put("carross", vehiculeDTO.getRegime());
            parameters.put("date", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));

            JasperReport jasperReport
                = JasperCompileManager.compileReport(path);
            jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
            JasperPrint jasperPrint
                = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            jpList.add(jasperPrint);
            /*new File("C:\\CertificatComformite").mkdir();
            String chemin = "C:\\CertificatComformite";
            reportService.generateCertificat(jpList, chemin+"\\"+ "CertificatComformite_" + personnePhysiqueDTO.getNom() +".pdf");*/
            if (SystemUtils.IS_OS_LINUX) {
                new File("/root/CertificatComformite/").mkdir();
                String chemin = "/root/CertificatComformite/";
                reportService.generateCertificat(jpList, chemin+"CertificatComformite_"+personnePhysiqueDTO.getNom()+".pdf");
            } else {
                new File("C:\\CertificatComformite").mkdir();
                String chemin = "C:\\CertificatComformite";
                reportService.generateCertificat(jpList, chemin+"\\CertificatComformite_"+personnePhysiqueDTO.getNom()+".pdf");
            }


        }
        else {
            PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper
                .toDto(personneMoraleRepository.findById(venteDTO.get().getPersonneMoraleId()).get());
            PersonnePhysiqueDTO personnePhysiqueDTO2 = personnePhysiqueMapper
                .toDto(personnePhysiqueRepository.findByOrganisationId(organisationDTO.getId()).get());
            DocIdentificationPMDTO docIdentificationPMDTO = docIdentificationPMMapper
                .toDto(docIdentificationPMRepository.findById(venteDTO.get().getPersonneMoraleId()).get());
            MarqueVehiculeDTO marqueVehiculeDTO = marqueVehiculeMapper
                .toDto(marqueVehiculeRepository.findById(vehiculeDTO.getMarqueVehiculeId()).get());


            String path = loader.getResource("classpath:conformite.jrxml").getURI().getPath();

            final Map<String, Object> parameters = new HashMap<>();
            List<JasperPrint> jpList = new ArrayList<>();

            parameters.put("vendeur", personnePhysiqueDTO2.getNom() + " " +personnePhysiqueDTO2.getPrenom());
            parameters.put("nomclient", personneMoraleDTO.getDenomination());
            parameters.put("places", String.valueOf(vehiculeDTO.getNbrPlace()));
            parameters.put("energie", vehiculeDTO.getEnergie());
            parameters.put("ptac", String.valueOf(vehiculeDTO.getPoidsVide()) + "/" + String.valueOf(vehiculeDTO.getChargeUtile()) + "/" + String.valueOf(vehiculeDTO.getPtac()));
            //parameters.put("", typeVehiculeDTO.getLibelle());
            parameters.put("genre", vehiculeDTO.getTypes());
            parameters.put("marque", marqueVehiculeDTO.getLibelle());
            parameters.put("modele", vehiculeDTO.getModel());
            parameters.put("type", vehiculeDTO.getNumeroChassis());
            parameters.put("carross", vehiculeDTO.getRegime());
            parameters.put("date", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));

            JasperReport jasperReport
                = JasperCompileManager.compileReport(path);
            jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
            JasperPrint jasperPrint
                = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            jpList.add(jasperPrint);
            /*new File("C:\\CertificatComformite").mkdir();
            String chemin = "C:\\CertificatComformite";
            reportService.generateCertificat(jpList, chemin+"\\"+ "CertificatComformite_" + personneMoraleDTO.getDenomination() +".pdf");*/
            if (SystemUtils.IS_OS_LINUX) {
                new File("/root/CertificatComformite/").mkdir();
                String chemin = "/root/CertificatComformite/";
                reportService.generateCertificat(jpList, chemin+"CertificatComformite_"+personneMoraleDTO.getDenomination()+".pdf");
            } else {
                new File("C:\\CertificatComformite").mkdir();
                String chemin = "C:\\CertificatComformite";
                reportService.generateCertificat(jpList, chemin+"\\CertificatComformite_"+personneMoraleDTO.getDenomination()+".pdf");
            }
        }
    }
    public void findVenteFacture(Long id) throws IOException, JRException {

        Optional<VenteDTO> venteDTO = venteRepository.findById(id)
            .map(venteMapper::toDto);
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisationRepository.findById(venteDTO.get().getRevendeurId()).get());
        VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehiculeRepository.findByVenteId(venteDTO.get().getId()).get());

        if (venteDTO.get().getPersonnePhysiqueId() != null) {
            PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper
                .toDto(personnePhysiqueRepository.findById(venteDTO.get().getPersonnePhysiqueId()).get());
            PersonnePhysiqueDTO personnePhysiqueDTO2 = personnePhysiqueMapper
                .toDto(personnePhysiqueRepository.findByOrganisationId(organisationDTO.getId()).get());
            DocIdentificationPPDTO docIdentificationPPDTO = docIdentificationPPMapper
                .toDto(docIdentificationPPRepository.findById(personnePhysiqueDTO.getDocIdentificationId()).get());
            DocIdentificationPMDTO docIdentificationPMDTO = docIdentificationPMMapper
                .toDto(docIdentificationPMRepository.findByOrganisationId(organisationDTO.getId()));
            MarqueVehiculeDTO marqueVehiculeDTO = marqueVehiculeMapper
                .toDto(marqueVehiculeRepository.findById(vehiculeDTO.getMarqueVehiculeId()).get());

            String path = loader.getResource("classpath:facture_vente.jrxml").getURI().getPath();

            final Map<String, Object> parameters = new HashMap<>();
            List<JasperPrint> jpList = new ArrayList<>();
            String slettre = FrenchNumberToWords.convert(venteDTO.get().getQuantiteVendue()) + " ("+ String.format("%,d", venteDTO.get().getQuantiteVendue()) + ",00) FCFA";

            parameters.put("sth", organisationDTO.getNom());
            parameters.put("numero", venteDTO.get().getNumeroVente());
            parameters.put("client", personnePhysiqueDTO.getNom() + " "+ personnePhysiqueDTO.getPrenom());
            parameters.put("tel", personnePhysiqueDTO.getTelephone());
            parameters.put("pseudo", organisationDTO.getNom());
            parameters.put("orgtel", organisationDTO.getNumeroPhone());
            parameters.put("orgbp", docIdentificationPMDTO.getCodePostal());
            parameters.put("orgmail", docIdentificationPMDTO.getEmail());
            parameters.put("designation", vehiculeDTO.getModel());
            parameters.put("quantite", String.valueOf(1));
            parameters.put("prixuni", String.format("%,d", venteDTO.get().getQuantiteVendue()) + ",00");
            parameters.put("prixtotal", String.format("%,d", venteDTO.get().getQuantiteVendue()) + ",00");
            parameters.put("totalht", String.format("%,d", venteDTO.get().getQuantiteVendue()) + ",00");
            parameters.put("totalttc", String.format("%,d", venteDTO.get().getQuantiteVendue()) + ",00");
            parameters.put("somme", "Arrêté la présente facture à la somme de   "+ slettre);

            parameters.put("date", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));

            JasperReport jasperReport
                = JasperCompileManager.compileReport(path);
            jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
            JasperPrint jasperPrint
                = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            jpList.add(jasperPrint);
            /*new File("C:\\FactureVente").mkdir();
            String chemin = "C:\\FactureVente";
            reportService.generateCertificat(jpList, chemin+"\\"+ "FactureVenteN_" + personnePhysiqueDTO.getNom() +".pdf");*/
            if (SystemUtils.IS_OS_LINUX) {
                new File("/root/FactureVente/").mkdir();
                String chemin = "/root/FactureVente/";
                reportService.generateCertificat(jpList, chemin+"FactureVenteN_"+personnePhysiqueDTO.getNom()+".pdf");
            } else {
                new File("C:\\FactureVente").mkdir();
                String chemin = "C:\\FactureVente";
                reportService.generateCertificat(jpList, chemin+"\\FactureVenteN_"+personnePhysiqueDTO.getNom()+".pdf");
            }


        }
        else {
            PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper
                .toDto(personneMoraleRepository.findById(venteDTO.get().getPersonneMoraleId()).get());
            PersonnePhysiqueDTO personnePhysiqueDTO2 = personnePhysiqueMapper
                .toDto(personnePhysiqueRepository.findByOrganisationId(organisationDTO.getId()).get());
            DocIdentificationPMDTO docIdentificationPMDTO = docIdentificationPMMapper
                .toDto(docIdentificationPMRepository.findById(venteDTO.get().getPersonneMoraleId()).get());
            MarqueVehiculeDTO marqueVehiculeDTO = marqueVehiculeMapper
                .toDto(marqueVehiculeRepository.findById(vehiculeDTO.getMarqueVehiculeId()).get());

            String path = loader.getResource("classpath:facture_vente.jrxml").getURI().getPath();

            final Map<String, Object> parameters = new HashMap<>();
            List<JasperPrint> jpList = new ArrayList<>();
            String slettre = FrenchNumberToWords.convert(venteDTO.get().getQuantiteVendue()) + " ("+ String.format("%,d", venteDTO.get().getQuantiteVendue()) + ",00) FCFA";

            parameters.put("sth", organisationDTO.getNom());
            parameters.put("numero", venteDTO.get().getNumeroVente());
            parameters.put("client", personneMoraleDTO.getDenomination());
            parameters.put("tel", docIdentificationPMDTO.getTelephone());
            parameters.put("pseudo", organisationDTO.getNom());
            parameters.put("orgtel", organisationDTO.getNumeroPhone());
            parameters.put("orgbp", docIdentificationPMDTO.getCodePostal());
            parameters.put("orgmail", docIdentificationPMDTO.getEmail());
            parameters.put("designation", vehiculeDTO.getModel());
            parameters.put("quantite", String.valueOf(1));
            parameters.put("prixuni", String.format("%,d", venteDTO.get().getQuantiteVendue()) + ",00");
            parameters.put("prixtotal", String.format("%,d", venteDTO.get().getQuantiteVendue()) + ",00");
            parameters.put("totalht", String.format("%,d", venteDTO.get().getQuantiteVendue()) + ",00");
            parameters.put("totalttc", String.format("%,d", venteDTO.get().getQuantiteVendue()) + ",00");
            parameters.put("somme", "Arrêté la présente facture à la somme de   "+ slettre);

            parameters.put("date", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));

            JasperReport jasperReport
                = JasperCompileManager.compileReport(path);
            jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
            JasperPrint jasperPrint
                = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            jpList.add(jasperPrint);
            /*new File("C:\\FactureVente").mkdir();
            String chemin = "C:\\FactureVente";
            reportService.generateCertificat(jpList, chemin+"\\"+ "FactureVenteN_" + personneMoraleDTO.getDenomination() +".pdf");*/
            if (SystemUtils.IS_OS_LINUX) {
                new File("/root/FactureVente/").mkdir();
                String chemin = "/root/FactureVente/";
                reportService.generateCertificat(jpList, chemin+"FactureVenteN_"+personneMoraleDTO.getDenomination()+".pdf");
            } else {
                new File("C:\\FactureVente").mkdir();
                String chemin = "C:\\FactureVente";
                reportService.generateCertificat(jpList, chemin+"\\FactureVenteN_"+personneMoraleDTO.getDenomination()+".pdf");
            }
        }
    }
}
