package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.*;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PlaqueImmatriculation}.
 */
@Service
@Transactional
public class PlaqueImmatriculationService {

    private final Logger log = LoggerFactory.getLogger(PlaqueImmatriculationService.class);

    private final PlaqueImmatriculationRepository plaqueImmatriculationRepository;

    private final PlaqueImmatriculationMapper plaqueImmatriculationMapper;
    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private VenteMapper venteMapper;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    @Autowired
    private PersonnePhysiqueMapper personnePhysiqueMapper;

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
    private VehiculeMapper vehiculeMapper;

    @Autowired
    private VehiculeRepository vehiculeRepository;
    @Autowired
    private CertificatImmatriculationRepository certificatImmatriculationRepository;
    @Autowired
    private CertificatImmatriculationMapper certificatImmatriculationMapper;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private OrganisationMapper organisationMapper;
    @Autowired
    private ImmatriculationService immatriculationService;
    @Autowired
    private CarnetASoucheRepository carnetASoucheRepository;
    @Autowired
    private CarnetASoucheMapper carnetASoucheMapper;
    @Autowired
    private CarnetASoucheService carnetASoucheService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogActivityService logActivityService;
    @Autowired
    private CarteWRepository carteWRepository;
    @Autowired
    private CarteWMapper carteWMapper;

    public PlaqueImmatriculationService(PlaqueImmatriculationRepository plaqueImmatriculationRepository, PlaqueImmatriculationMapper plaqueImmatriculationMapper) {
        this.plaqueImmatriculationRepository = plaqueImmatriculationRepository;
        this.plaqueImmatriculationMapper = plaqueImmatriculationMapper;
    }

    /**
     * Save a plaqueImmatriculation.
     *
     * @param plaqueImmatriculationDTO the entity to save.
     * @return the persisted entity.
     */
    public PlaqueImmatriculationDTO save(PlaqueImmatriculationDTO plaqueImmatriculationDTO) {
        log.debug("Request to save PlaqueImmatriculation : {}", plaqueImmatriculationDTO);
        System.out.println("certificat Id : " +plaqueImmatriculationDTO.getCertificatImmatriculationId());
        PlaqueImmatriculation plaqueImmatriculation = plaqueImmatriculationMapper.toEntity(plaqueImmatriculationDTO);
        /*PersonnePhysique personnePhysiqueVerif = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        CarteW carteW = carteWRepository.findByOrganisationId(personnePhysiqueVerif.getOrganisation().getId()).get();
        if (carteW != null && plaqueImmatriculation.getNumeroImmatriculation().contains(carteW.getNumeroCarteW())) {

        }*/
        Optional<CertificatImmatriculation> certificatImmatriculation = certificatImmatriculationRepository.findById(plaqueImmatriculationDTO.getCertificatImmatriculationId());
        List<PlaqueImmatriculation> plaqueImmatriculations = plaqueImmatriculationRepository.findByNumeroImmatriculation(plaqueImmatriculationDTO.getNumeroImmatriculation());
        System.out.println("plaque immatriculation Id : " +plaqueImmatriculationDTO);
        if (plaqueImmatriculations.size() > 1) {
            plaqueImmatriculations.get(0).setVehicule(plaqueImmatriculation.getVehicule());
            plaqueImmatriculations.get(0).setCertificatImmatriculation(certificatImmatriculation.get());
            plaqueImmatriculations.get(1).setVehicule(plaqueImmatriculation.getVehicule());
            plaqueImmatriculations.get(1).setCertificatImmatriculation(certificatImmatriculation.get());
        }
        plaqueImmatriculations.get(0).setVehicule(plaqueImmatriculation.getVehicule());
        plaqueImmatriculations.get(0).setCertificatImmatriculation(certificatImmatriculation.get());
        Optional<PersonnePhysiqueDTO> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get()).map(personnePhysiqueMapper::toDto);
        Optional<Vehicule> vehicule = vehiculeRepository.findById(plaqueImmatriculationDTO.getVehiculeId());
        Optional<VenteDTO> venteDTO = venteRepository.findByVehiculesId(vehicule.get().getId())
            .map(venteMapper::toDto);
        ImmatriculationDTO immatriculationDTO = new ImmatriculationDTO();
        immatriculationDTO.setVehiculeId(plaqueImmatriculationDTO.getVehiculeId());
        immatriculationDTO.setOrganisationId(personnePhysique.get().getOrganisationId());
        immatriculationDTO.setCertificatImmatriculationId(plaqueImmatriculationDTO.getCertificatImmatriculationId());

        if (venteDTO.get().getPersonnePhysiqueId() != null) {
            Optional<PersonnePhysiqueDTO> personnePhysiqueDTO = personnePhysiqueRepository.findById(venteDTO.get().getPersonnePhysiqueId()).map(personnePhysiqueMapper::toDto);
            Optional<DocIdentificationPPDTO> docIdentificationPPDTO = docIdentificationPPRepository.findById(personnePhysiqueDTO.get().getDocIdentificationId()).map(docIdentificationPPMapper::toDto);
            Optional<OrganisationDTO> organisationDTO = organisationRepository.findById(venteDTO.get().getRevendeurId()).map(organisationMapper::toDto);
            venteDTO.get().setOrganisationDTO(organisationDTO.get());
            immatriculationDTO.setPersonnePhysiqueId(venteDTO.get().getPersonnePhysiqueId());
            venteDTO.get().setPersonnePhysiqueDTO(personnePhysiqueDTO.get());
            venteDTO.get().setDocIdentificationPPDTO(docIdentificationPPDTO.get());
        }else {
            Optional<PersonneMorale> personneMorale = personneMoraleRepository.findById(venteDTO.get().getPersonneMoraleId());
//            Optional<DocIdentificationPMDTO> docIdentificationPMDTO = docIdentificationPMRepository.findByPersonneMoraleId(personneMoraleDTO.get().getId()).map(docIdentificationPMMapper::toDto);
            Optional<OrganisationDTO> organisationDTO = organisationRepository.findById(venteDTO.get().getRevendeurId()).map(organisationMapper::toDto);
            venteDTO.get().setOrganisationDTO(organisationDTO.get());
            immatriculationDTO.setPersonneMoraleId(personneMorale.get().getId());
        }
        immatriculationService.save(immatriculationDTO);
        User user = userRepository.findById(personnePhysique.get().getUserId()).get();
        LogActivityDTO logActivityDTO = new LogActivityDTO();
        logActivityDTO.setAction("Pose d'une nouvelle immatriculation");
        logActivityDTO.setPrincipal(user.getLogin().toUpperCase());
        logActivityService.save(logActivityDTO);
        plaqueImmatriculations = plaqueImmatriculationRepository.saveAll(plaqueImmatriculations);
        return plaqueImmatriculationMapper.toDto(plaqueImmatriculations.get(0));
    }

    /**
     * Get all the plaqueImmatriculations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlaqueImmatriculationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlaqueImmatriculations");
        return plaqueImmatriculationRepository.findAllByVehiculeNotNull(pageable)
            .map(plaqueImmatriculationMapper::toDto);
    }

    /**
     * Get one plaqueImmatriculation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlaqueImmatriculationDTO> findOne(Long id) {
        log.debug("Request to get PlaqueImmatriculation : {}", id);
        return plaqueImmatriculationRepository.findById(id)
            .map(plaqueImmatriculationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<PlaqueImmatriculationDTO> findOneByQr(String id) {
        log.debug("Request to get PlaqueImmatriculation : {}", id);
        List<PlaqueImmatriculation> plaqueImmatriculations = plaqueImmatriculationRepository.findAllByCodeQR(id);
        List<PlaqueImmatriculationDTO> plaqueImmatriculationDTOS = plaqueImmatriculationMapper.toDto(plaqueImmatriculations);
        return plaqueImmatriculationDTOS;
    }

    @Transactional(readOnly = true)
    public Optional<PlaqueImmatriculationDTO> findOneByCerti(Long id) {
        log.debug("Request to get PlaqueImmatriculation : {}", id);
        return plaqueImmatriculationRepository.findById(id)
            .map(plaqueImmatriculationMapper::toDto);
    }

    /**
     * Delete the plaqueImmatriculation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlaqueImmatriculation : {}", id);
        plaqueImmatriculationRepository.deleteById(id);
    }
}
