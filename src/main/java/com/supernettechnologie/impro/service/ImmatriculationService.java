package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.Immatriculation;
import com.supernettechnologie.impro.domain.Organisation;
import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.*;
import com.supernettechnologie.impro.service.mapper.ImmatriculationMapper;
import com.supernettechnologie.impro.service.mapper.PersonneMoraleMapper;
import com.supernettechnologie.impro.service.mapper.PersonnePhysiqueMapper;
import com.supernettechnologie.impro.service.mapper.VehiculeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Immatriculation}.
 */
@Service
@Transactional
public class ImmatriculationService {

    private final Logger log = LoggerFactory.getLogger(ImmatriculationService.class);

    private final ImmatriculationRepository immatriculationRepository;

    private final ImmatriculationMapper immatriculationMapper;
    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;
    @Autowired
    private PersonnePhysiqueMapper personnePhysiqueMapper;
    @Autowired
    private PersonneMoraleRepository personneMoraleRepository;
    @Autowired
    private PersonneMoraleMapper personneMoraleMapper;
    @Autowired
    private VehiculeMapper vehiculeMapper;
    @Autowired
    private VehiculeRepository vehiculeRepository;
    @Autowired
    private OrganisationRepository organisationRepository;


    public ImmatriculationService(ImmatriculationRepository immatriculationRepository, ImmatriculationMapper immatriculationMapper) {
        this.immatriculationRepository = immatriculationRepository;
        this.immatriculationMapper = immatriculationMapper;
    }

    /**
     * Save a immatriculation.
     *
     * @param immatriculationDTO the entity to save.
     * @return the persisted entity.
     */
    public ImmatriculationDTO save(ImmatriculationDTO immatriculationDTO) {
        log.debug("Request to save Immatriculation : {}", immatriculationDTO);
        Immatriculation immatriculation = immatriculationMapper.toEntity(immatriculationDTO);
        immatriculation.setNumero(String.valueOf(immatriculationRepository.count()+1));
        immatriculation.setDateImmatriculation(ZonedDateTime.now());
        immatriculation = immatriculationRepository.save(immatriculation);
        return immatriculationMapper.toDto(immatriculation);
    }

    /**
     * Get all the immatriculations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ImmatriculationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Immatriculations");
        Page<ImmatriculationDTO> immatriculationDTOPage = immatriculationRepository.findAll(pageable)
            .map(immatriculationMapper::toDto);
        for (ImmatriculationDTO immatriculationDTO : immatriculationDTOPage.getContent()){
            if (immatriculationDTO.getPersonnePhysiqueId() != null) {
                Optional<PersonnePhysiqueDTO> personnePhysiqueDTO = personnePhysiqueRepository.findById(immatriculationDTO.getPersonnePhysiqueId())
                    .map(personnePhysiqueMapper::toDto);
                immatriculationDTO.setPersonnePhysiqueDTO(personnePhysiqueDTO.get());
            }else {
                Optional<PersonneMoraleDTO> personneMoraleDTO = personneMoraleRepository.findById(immatriculationDTO.getPersonneMoraleId())
                    .map(personneMoraleMapper::toDto);
                immatriculationDTO.setPersonneMoraleDTO(personneMoraleDTO.get());
            }
            VehiculeDTO vehiculeDTO = vehiculeMapper
                .toDto(vehiculeRepository.findById(immatriculationDTO.getVehiculeId()).get());
            immatriculationDTO.setVehiculeDTO(vehiculeDTO);
        }
        return immatriculationDTOPage;
    }


    public Page<ImmatriculationDTO> findAllOrganisation(Pageable pageable) {
        log.debug("Request to get all Immatriculations");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        Page<ImmatriculationDTO> immatriculationDTOPage = immatriculationRepository.findAllByOrganisationId(pageable, personnePhysique.get().getOrganisation().getId())
            .map(immatriculationMapper::toDto);
        List<ImmatriculationDTO> immatriculationDTOS = new ArrayList<>();
        for (ImmatriculationDTO immatriculationDTO : immatriculationDTOPage.getContent()){
            if (immatriculationDTO.getPersonnePhysiqueId() != null) {
                Optional<PersonnePhysiqueDTO> personnePhysiqueDTO = personnePhysiqueRepository.findById(immatriculationDTO.getPersonnePhysiqueId())
                    .map(personnePhysiqueMapper::toDto);
                immatriculationDTO.setPersonnePhysiqueDTO(personnePhysiqueDTO.get());
                immatriculationDTOS.add(immatriculationDTO);
                System.out.println("PP DTO : "+immatriculationDTO.getPersonnePhysiqueDTO().getNom());
            }else {
                Optional<PersonneMoraleDTO> personneMoraleDTO = personneMoraleRepository.findById(immatriculationDTO.getPersonneMoraleId())
                    .map(personneMoraleMapper::toDto);
                immatriculationDTO.setPersonneMoraleDTO(personneMoraleDTO.get());
                immatriculationDTOS.add(immatriculationDTO);
            }
            VehiculeDTO vehiculeDTO = vehiculeMapper
                .toDto(vehiculeRepository.findById(immatriculationDTO.getVehiculeId()).get());
            immatriculationDTO.setVehiculeDTO(vehiculeDTO);
        }

        immatriculationDTOPage = new PageImpl<>(immatriculationDTOS);
        return immatriculationDTOPage;
    }

    public int imJanuary(){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        ZonedDateTime dateIm = ZonedDateTime.of(
            2020, 1, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        ZonedDateTime dateIm2 = ZonedDateTime.of(
            2020, 2, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        int nbrIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(dateIm,dateIm2, personnePhysique.get().getOrganisation().getId());

        return nbrIm;
    }

    public int imFebruary(){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        ZonedDateTime dateIm = ZonedDateTime.of(
            2020, 2, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        ZonedDateTime dateIm2 = ZonedDateTime.of(
            2020, 3, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        int nbrIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(dateIm,dateIm2, personnePhysique.get().getOrganisation().getId());

        return nbrIm;
    }

    public int imMarch(){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        ZonedDateTime dateIm = ZonedDateTime.of(
            2020, 3, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        ZonedDateTime dateIm2 = ZonedDateTime.of(
            2020, 4, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        int nbrIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(dateIm,dateIm2, personnePhysique.get().getOrganisation().getId());
        log.debug(" **************************************Request Immatriculation Janvier : {}", nbrIm);

        return nbrIm;
    }

    public int imApril(){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        ZonedDateTime dateIm = ZonedDateTime.of(
            2020, 4, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        ZonedDateTime dateIm2 = ZonedDateTime.of(
            2020, 5, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        int nbrIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(dateIm,dateIm2, personnePhysique.get().getOrganisation().getId());

        return nbrIm;
    }

    public int imMay(){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        ZonedDateTime dateIm = ZonedDateTime.of(
            2020, 5, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        ZonedDateTime dateIm2 = ZonedDateTime.of(
            2020, 6, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        int nbrIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(dateIm,dateIm2, personnePhysique.get().getOrganisation().getId());
        log.debug(" **************************************Request Immatriculation Janvier : {}", nbrIm);

        return nbrIm;
    }

    public int imJune(){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        ZonedDateTime dateIm = ZonedDateTime.of(
            2020, 6, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        ZonedDateTime dateIm2 = ZonedDateTime.of(
            2020, 7, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        int nbrIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(dateIm,dateIm2, personnePhysique.get().getOrganisation().getId());

        return nbrIm;
    }

    public int imJuly(){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        ZonedDateTime dateIm = ZonedDateTime.of(
            2020, 7, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        ZonedDateTime dateIm2 = ZonedDateTime.of(
            2020, 8, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        int nbrIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(dateIm,dateIm2, personnePhysique.get().getOrganisation().getId());
        log.debug(" **************************************Request Immatriculation Janvier : {}", nbrIm);

        return nbrIm;
    }

    public int imAugust(){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        ZonedDateTime dateIm = ZonedDateTime.of(
            2020, 8, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        ZonedDateTime dateIm2 = ZonedDateTime.of(
            2020, 9, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        int nbrIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(dateIm,dateIm2, personnePhysique.get().getOrganisation().getId());

        return nbrIm;
    }

    public int imSeptember(){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        ZonedDateTime dateIm = ZonedDateTime.of(
            2020, 9, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        ZonedDateTime dateIm2 = ZonedDateTime.of(
            2020, 10, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        int nbrIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(dateIm,dateIm2, personnePhysique.get().getOrganisation().getId());
        log.debug(" **************************************Request Immatriculation Janvier : {}", nbrIm);

        return nbrIm;
    }

    public int imOctober(){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        ZonedDateTime dateIm = ZonedDateTime.of(
            2020, 10, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        ZonedDateTime dateIm2 = ZonedDateTime.of(
            2020, 11, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        int nbrIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(dateIm,dateIm2, personnePhysique.get().getOrganisation().getId());

        return nbrIm;
    }


    public int imNovember(){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        ZonedDateTime dateIm = ZonedDateTime.of(
            2020, 11, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        ZonedDateTime dateIm2 = ZonedDateTime.of(
            2020, 12, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        int nbrIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(dateIm,dateIm2, personnePhysique.get().getOrganisation().getId());

        return nbrIm;
    }

    public int imDecember(){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        ZonedDateTime dateIm = ZonedDateTime.of(
            2020, 12, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        ZonedDateTime dateIm2 = ZonedDateTime.of(
            2020, 1, 1, 0, 0, 0,
            90000, ZoneId.systemDefault());

        int nbrIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(dateIm,dateIm2, personnePhysique.get().getOrganisation().getId());

        return nbrIm;
    }


    public int[] imDay0(DatesModelDTO datesModelDTO){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        int x = 0;
        int i = 0;
        for(ZonedDateTime date = datesModelDTO.getDateDebut(); date.isBefore(datesModelDTO.getDateFin()); date = date.plusDays(1)){
            x++;
        }
        int[] nbImDay = new int[x];
        for(ZonedDateTime date = datesModelDTO.getDateDebut(); date.isBefore(datesModelDTO.getDateFin()); date = date.plusDays(1)){
            nbImDay[i] = immatriculationRepository.countAllByDateImmatriculationAndOrganisationId(date, personnePhysique.get().getOrganisation().getId());
            i++;
        }
        log.debug("********************************Service**************** : {}", nbImDay);
        return nbImDay;
    }
    public int[] imDay1(ZonedDateTime dat, ZonedDateTime dat2){
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        int x = 0;//for Days Table length
        int i = 0;//To browse Days Table
        for(ZonedDateTime date = dat; date.isBefore(dat2); date = date.plusDays(1)){
            x++;
        }
        int[] nbImDay = new int[x];
        for(ZonedDateTime date = dat; date.isBefore(dat2); date = date.plusDays(1)){
            nbImDay[i] = immatriculationRepository.countAllByDateImmatriculationAndOrganisationId(date, personnePhysique.get().getOrganisation().getId());
            i++;
        }
        log.debug("********************************Service**************** : {}", nbImDay);
        return nbImDay;
    }

    public int[] imDay(ZonedDateTime dat, ZonedDateTime dat2){
        Optional<PersonnePhysique> personnePhysique =
            personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        int x = 0;//for Days Table length
        int i = 0;//To browse Days Table
        for(ZonedDateTime date = dat; date.isBefore(dat2); date = date.plusDays(1)){
            x++;
        }
        int[] nbImDay = new int[x];
        for(ZonedDateTime date = dat; date.isBefore(dat2); date = date.plusDays(1)){
            nbImDay[i] = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(
                date,date.plusDays(1), personnePhysique.get().getOrganisation().getId());
            i++;
            log.debug("********************************Service**************** : {}", date);
        }
        log.debug("********************************Service**************** : {}", nbImDay);
        return nbImDay;
    }


    public int[] imDay4AllOrg(ZonedDateTime dat, ZonedDateTime dat2){
        int x = 0;//for Days Table length
        int i = 0;//To browse Days Table
        /*int y = 0; //for All SIP number
        int z = 0; //for All SIP number*/
        for(ZonedDateTime date = dat; date.isBefore(dat2); date = date.plusDays(1)){
            x++;
        }
        int[] nbImDay = new int[x];
        /*int[] nbImDay4SIP = new int[9];
        int[] ID = new int[9];
        List<Organisation> orgSIP = organisationRepository.findAll();
        for (z=0;z<9;z++){
            orgSIP.
        }*/
        for(ZonedDateTime date = dat; date.isBefore(dat2); date = date.plusDays(1)){
            nbImDay[i] = immatriculationRepository.countAllByDateImmatriculationBetween(
                date,date.plusDays(1));
            i++;
            log.debug("********************************Service**************** : {}", date);
        }
        log.debug("********************************Service**************** : {}", nbImDay);
        return nbImDay;
    }

    public int find4Org(ZonedDateTime dat, ZonedDateTime dat2, Long idOrg){
        int totIm = 0;
        totIm = immatriculationRepository.countAllByDateImmatriculationBetweenAndOrganisationId(
            dat,dat2.plusDays(1), idOrg);
        return totIm;
    }
    /**
     * Get one immatriculation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ImmatriculationDTO> findOne(Long id) {
        log.debug("Request to get Immatriculation : {}", id);
        return immatriculationRepository.findById(id)
            .map(immatriculationMapper::toDto);
    }

    /**
     * Delete the immatriculation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Immatriculation : {}", id);
        immatriculationRepository.deleteById(id);
    }

    public Long totalAdmuin() {
        System.out.println("=> Nombre : " +immatriculationRepository.count());
        return immatriculationRepository.count();
    }

    public Long getAllAdmin() {
        return immatriculationRepository.count();
    }

    public int getAllOrga() {
        Optional<PersonnePhysique> personnePhysique =
            personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<ImmatriculationDTO> immatriculationDTOS = immatriculationMapper
            .toDto(immatriculationRepository.findAllByOrganisationId(personnePhysique.get().getOrganisation().getId()));

        return immatriculationDTOS.size();
    }
}
