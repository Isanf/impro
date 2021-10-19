package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.LivraisonVehiculeDTO;
import com.supernettechnologie.impro.service.dto.OrganisationDTO;
import com.supernettechnologie.impro.service.dto.VehiculeDTO;
import com.supernettechnologie.impro.service.mapper.LivraisonVehiculeMapper;
import com.supernettechnologie.impro.service.mapper.StockMapper;
import com.supernettechnologie.impro.service.mapper.VehiculeMapper;
import com.supernettechnologie.impro.service.mapper.VenteMapper;
import com.supernettechnologie.impro.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;


/**
 * Service Implementation for managing {@link Vehicule}.
 */
@Service
@Transactional
public class VehiculeService {

    private final Logger log = LoggerFactory.getLogger(VehiculeService.class);

    private final VehiculeRepository vehiculeRepository;

    private final VehiculeMapper vehiculeMapper;
    @Autowired
    private LivraisonVehiculeRepository livraisonVehiculeRepository;
    @Autowired
    private LivraisonVehiculeMapper livraisonVehiculeMapper;
    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;
    @Autowired
    private PlaqueImmatriculationRepository plaqueImmatriculationRepository;

    private VehiculeDTO vehiculeDTO;
    @Autowired
    private VenteRepository venteRepository;
    @Autowired
    private VenteMapper venteMapper;

    private final StockRepository stockRepository;

    private final StockMapper stockMapper;


    public VehiculeService(VehiculeRepository vehiculeRepository, VehiculeMapper vehiculeMapper, StockRepository stockRepository, StockMapper stockMapper) {
        this.vehiculeRepository = vehiculeRepository;
        this.vehiculeMapper = vehiculeMapper;
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    /**
     * Save a vehicule.
     *
     * @param vehiculeDTO the entity to save.
     * @return the persisted entity.
     */
    public VehiculeDTO save(VehiculeDTO vehiculeDTO) {
        log.debug("Request to save Vehicule : {}", vehiculeDTO);
        Vehicule vehicule = vehiculeMapper.toEntity(vehiculeDTO);
        Stock stock = stockMapper.toEntity(vehiculeDTO.getStockDTO());
        String s =""+(stockRepository.count()+1);
        stock.setNumeroStock(s);
        stock.setDateStock(ZonedDateTime.now());
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        stock.setConcessionnaire(personnePhysique.get().getOrganisation());
        stock = stockRepository.save(stock);
        vehicule.setStock(stock);
        vehicule = vehiculeRepository.save(vehicule);
        Optional<Vehicule> vehiculeVerify = vehiculeRepository.findByNumeroChassis(vehiculeDTO.getNumeroChassis());
        if(vehiculeVerify.isPresent()){
            System.out.println("Le vehicule existe déjà");
            log.debug(" ******************************** Le vehicule existe déjà ");
            return vehiculeMapper.toDto(vehicule);
        }else{
            return vehiculeMapper.toDto(vehicule);
        }
    }

    /**
     * Get all the vehicules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehiculeDTO> findAll(Pageable pageable) {
        log.debug("**************************Tous les Vehicules");

        return vehiculeRepository.findAll(pageable)
            .map(vehiculeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<VehiculeDTO> findAllDelivAndNotSell(Pageable pageable) {
        log.debug("Request to get all Vehicules");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        LivraisonVehicule livraisonVehicule = livraisonVehiculeRepository.findByRevendeurId(personnePhysique.get().getOrganisation().getId());

        return vehiculeRepository.findAllByLivraisonVehiculeIsNotNull(pageable)
            .map(vehiculeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAllForRevendeur() {
        log.debug("***********************************************findAllForRevendeur");
        List<Vehicule> vehiculeList = null;
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        //List<LivraisonVehicule> livraisonVehicules = livraisonVehiculeRepository.findAllByRevendeurId(personnePhysique.get().getOrganisation().getId());
        List<LivraisonVehicule> livraisonVehicules = livraisonVehiculeRepository.findLivraisonVehiculesByRevendeurId(personnePhysique.get().getOrganisation().getId());
        List<LivraisonVehiculeDTO> livraisonVehiculeDTOS = livraisonVehiculeMapper.toDto(livraisonVehicules);

        List<VehiculeDTO> vehiculeDTOList = new ArrayList<>();
        for (LivraisonVehicule livraisonVehicule : livraisonVehicules){
            vehiculeList = vehiculeRepository.findAllByLivraisonVehiculeId(livraisonVehicule.getId());
            for (Vehicule vehicule : vehiculeList){
                VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehicule);
                System.out.println("**************************************Livraison : "+vehiculeDTO.getTypeVehiculeId());
                System.out.println("**************************************Livraison : "+vehiculeDTO.getTypeVehiculeId());
                System.out.println("**************************************Livraison : "+vehiculeDTO.getTypeVehiculeId());
                    vehiculeDTOList.add(vehiculeDTO);
            }
        }

        return vehiculeDTOList;
    }

    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAllForConcessionnaire() {
        log.debug("***********************************************findAllForRevendeur");
        List<Vehicule> vehiculeList = null;
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<Stock> stocks = stockRepository.findAllByConcessionnaireId(personnePhysique.get().getOrganisation().getId());
        List<VehiculeDTO> vehiculeDTOList = new ArrayList<>();
        for (Stock stock : stocks) {
            vehiculeList = vehiculeRepository.findAllByStockId(stock.getId());
            for (Vehicule vehicule : vehiculeList) {
                VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehicule);
                System.out.println("Livraison : " + vehiculeDTO);
                vehiculeDTOList.add(vehiculeDTO);
            }
        }
        return vehiculeDTOList;
    }

    public Page<VehiculeDTO> findAllNotDelivAndNotSell(Pageable pageable) {
        log.debug("Request to get all Vehicules");
        return vehiculeRepository.findAllByLivraisonVehiculeIsNullAndVenteIsNull(pageable)
            .map(vehiculeMapper::toDto);
    }
    public Page<VehiculeDTO> findAllDeliveryAndNotSell(Pageable pageable) {
        log.debug("Request to get all Vehicules");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<LivraisonVehicule> livraisonVehicules = livraisonVehiculeRepository.findAllByRevendeurId(personnePhysique.get().getOrganisation().getId());
        List<VehiculeDTO> vehiculeDTOList = new ArrayList<>();
        for (LivraisonVehicule livraisonVehicule : livraisonVehicules){
            List<Vehicule> vehiculeList = vehiculeRepository.findAllByLivraisonVehiculeId(livraisonVehicule.getId());
            for (Vehicule vehicule : vehiculeList){
                VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehicule);
                System.out.println("Livraison : "+vehiculeDTO);
                //log.debug("Livraison : "+vehiculeDTO);
                if (!plaqueImmatriculationRepository.existsByVehiculeId(vehiculeDTO.getId()) && vehiculeDTO.getVenteId() == null){
                    vehiculeDTOList.add(vehiculeDTO);
                }
            }
        }
        return new PageImpl<>(vehiculeDTOList);
    }

    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAllRevendeurs() {
        log.debug("***********************************************findAllRevendeurs");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<LivraisonVehicule> livraisonVehicules = livraisonVehiculeRepository.findAllByRevendeurId(personnePhysique.get().getOrganisation().getId());
        //List<LivraisonVehicule> livraisonVehicules = livraisonVehiculeRepository.findLivraisonVehiculesByRevendeurId(personnePhysique.get().getOrganisation().getId());

        List<VehiculeDTO> vehiculeDTOList = new ArrayList<>();
        for (LivraisonVehicule livraisonVehicule : livraisonVehicules){
            List<Vehicule> vehiculeList = vehiculeRepository.findAllByLivraisonVehiculeId(livraisonVehicule.getId());
            for (Vehicule vehicule : vehiculeList){
                VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehicule);
                System.out.println("Livraison : "+vehiculeDTO);
                //log.debug("Livraison : "+vehiculeDTO);
                if (!plaqueImmatriculationRepository.existsByVehiculeId(vehiculeDTO.getId()) && vehiculeDTO.getVenteId() != null){
                    vehiculeDTOList.add(vehiculeDTO);
                }
            }
        }
        return vehiculeDTOList;
    }

    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAllConcessionnaire() {
        log.debug("********************************************************************findAllConcessionnaire");
        log.debug("********************************************************************findAllConcessionnaire");
        log.debug("********************************************************************findAllConcessionnaire");
        log.debug("Request to get all Vehicules");
        System.out.println("********************************************************************findAllConcessionnaire");
        System.out.println("********************************************************************findAllConcessionnaire");
        System.out.println("********************************************************************findAllConcessionnaire");
        List<Vehicule> vehiculeList = null;
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<Vente> ventes = venteRepository.findAllByRevendeurId(personnePhysique.get().getOrganisation().getId());//Revendeur concerne le concessionnaire aussi
        List<VehiculeDTO> vehiculeDTOList = new ArrayList<>();
        for (Vente vente : ventes){
            vehiculeList = vehiculeRepository.findAllByVenteId(vente.getId());
            for (Vehicule vehicule : vehiculeList){
                VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehicule);
                System.out.println("vehicules : "+vehiculeDTO);
                if (!plaqueImmatriculationRepository.existsByVehiculeId(vehiculeDTO.getId()) && vehiculeDTO.getVenteId() != null && vehiculeDTO.getLivraisonVehiculeId() == null){
                    vehiculeDTOList.add(vehiculeDTO);
                }
            }
        }
        return vehiculeDTOList;
    }
    @Transactional(readOnly = true)
    public List<VehiculeDTO> findVehiculeRevendeur() {
        log.debug("********************************************************************findVehiculeRevendeur");
        log.debug("********************************************************************findVehiculeRevendeur");
        log.debug("********************************************************************findVehiculeRevendeur");
        System.out.println("********************************************************************findVehiculeRevendeur");
        System.out.println("********************************************************************findVehiculeRevendeur");
        System.out.println("********************************************************************findVehiculeRevendeur");
        List<Vehicule> vehiculeList = null;
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<Vente> ventes = venteRepository.findAllByRevendeurId(personnePhysique.get().getOrganisation().getId());//Revendeur concerne le concessionnaire aussi
        List<VehiculeDTO> vehiculeDTOList = new ArrayList<>();
        for (Vente vente : ventes){
            vehiculeList = vehiculeRepository.findAllByVenteId(vente.getId());
            for (Vehicule vehicule : vehiculeList){
                VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehicule);

                if (!plaqueImmatriculationRepository.existsByVehiculeId(vehiculeDTO.getId()) && vehiculeDTO.getVenteId() != null && vehiculeDTO.getLivraisonVehiculeId() != null){
                    vehiculeDTOList.add(vehiculeDTO);
                    System.out.println("vehicules : "+vehiculeDTO);
                }
            }
        }
        return vehiculeDTOList;
    }

    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAllHorsStock () {
        log.debug("******************************Request to getAllHorsStock///////////////////////");
        List<Vehicule> vehiculeList = null;
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<Vente> ventes = venteRepository.findAllByRevendeurId(personnePhysique.get().getOrganisation().getId());//Revendeur concerne le concessionnaire aussi
        List<VehiculeDTO> vehiculeDTOList = new ArrayList<>();
        for (Vente vente : ventes){
            vehiculeList = vehiculeRepository.findAllByVenteIdAndStockIsNull(vente.getId());
            for (Vehicule vehicule : vehiculeList){
                VehiculeDTO vehiculeDTO = vehiculeMapper.toDto(vehicule);
                System.out.println("vehicules : "+vehiculeDTO);
                //if (!plaqueImmatriculationRepository.existsByVehiculeId(vehiculeDTO.getId()) && vehiculeDTO.getVenteId() != null && vehiculeDTO.getLivraisonVehiculeId() == null){
                    vehiculeDTOList.add(vehiculeDTO);
                //}
            }
        }
        return vehiculeDTOList;
    }


    /**
     * Get one vehicule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehiculeDTO> findOne(Long id) {
        log.debug("Request to get Vehicule : {}", id);
        return vehiculeRepository.findById(id)
            .map(vehiculeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Vehicule findOneByChssis(String chassis) {
        log.debug("Request to get DocIdentificationPP : {}", chassis);
        Vehicule vehicule = vehiculeRepository.findOneByNumeroChassis(chassis);
        log.debug("*****************************************//////////////******* : {}", vehicule.getEnergie());
        return vehicule;
    }

    /**
     * Delete the vehicule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Vehicule : {}", id);
        vehiculeRepository.deleteById(id);
    }

    public List<VehiculeDTO> findAllMarque(Long id) {
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<Vehicule> vehicules = vehiculeRepository.findByMarqueVehiculeIdAndLivraisonVehiculeIsNotNull(id);
        List<VehiculeDTO> vehiculeDTOS = vehiculeMapper.toDto(vehicules);
        List<VehiculeDTO> vehiculeDTOList = new ArrayList<>();
        for (VehiculeDTO vehicule : vehiculeDTOS){
            System.out.println("Livraison V Id : " +vehicule.getId());
            Optional<LivraisonVehicule> livraisonVehicules = livraisonVehiculeRepository.findById(vehicule.getLivraisonVehiculeId());
            if (livraisonVehicules.get().getRevendeur().getId() == personnePhysique.get().getOrganisation().getId() && vehicule.getVenteId() == null) {
                vehiculeDTOList.add(vehicule);
            }

        }
        return vehiculeDTOList;
    }
}
