package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.Authority;
import com.supernettechnologie.impro.domain.Organisation;
import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.domain.Profil;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.CollaborationDTO;
import com.supernettechnologie.impro.service.dto.OrganisationDTO;
import com.supernettechnologie.impro.service.dto.ProfilDTO;
import com.supernettechnologie.impro.service.dto.UserDTO;
import com.supernettechnologie.impro.service.mapper.CollaborationMapper;
import com.supernettechnologie.impro.service.mapper.OrganisationMapper;
import com.supernettechnologie.impro.service.mapper.ProfilMapper;
import com.supernettechnologie.impro.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing {@link Profil}.
 */
@Service
@Transactional
public class ProfilService {

    private final Logger log = LoggerFactory.getLogger(ProfilService.class);

    private final ProfilRepository profilRepository;

    private final ProfilMapper profilMapper;

    private final AuthorityRepository authorityRepository;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    @Autowired
    private CollaborationRepository collaborationRepository;
    @Autowired
    private CollaborationMapper collaborationMapper;

    @Autowired
    private OrganisationMapper organisationMapper;
    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    public ProfilService(ProfilRepository profilRepository, ProfilMapper profilMapper, AuthorityRepository authorityRepository) {
        this.profilRepository = profilRepository;
        this.profilMapper = profilMapper;
        this.authorityRepository = authorityRepository;
    }

    /**
     * Save a profil.
     *
     * @param profilDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfilDTO save(ProfilDTO profilDTO) {
        log.debug("Request to save Profil ************* : {}", profilDTO.getRoles());
        Profil profil = profilMapper.toEntity(profilDTO);
        Set<Authority> authorities = new HashSet<>();
        for (String roles : profilDTO.getRoles()) {
            authorities.add(authorityRepository.findByName(roles));
        }
        profil.setAuthorities(authorities);
        /*Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        if(profilDTO.getOrganisationId() == null) {
            profil.setOrganisation(personnePhysique.get().getOrganisation());
        }*/
        profil.setCreatedby(SecurityUtils.getCurrentUserLogin().get());
        profil = profilRepository.save(profil);
        return profilMapper.toDto(profil);
    }

    /**
     * Get all the profils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profils");
        return profilRepository.findAll(pageable)
            .map(profilMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ProfilDTO> findAllCreatedBy() {
        List<Profil> profilList = profilRepository.
            findAllByCreatedby(SecurityUtils.getCurrentUserLogin().get());
        List<ProfilDTO> profilDTOS = new ArrayList<>();
        for (Profil profil : profilList){
            profilDTOS.add(profilMapper.toDto(profil));
        }
        return profilDTOS;
    }

    @Transactional(readOnly = true)
    public List<Profil> findAll() {
        log.debug("Request to get all Profils");
        return profilRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<ProfilDTO> findAllByMyOrganisation(Long id, Pageable pageable) {
        log.debug("Request to get all By My Organisation Profils");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        return profilRepository.findAllByOrganisationId(personnePhysique.get().getOrganisation().getId(),pageable)
            .map(profilMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProfilDTO> findAllForMyRevendeurs(Long id, Pageable pageable) {
        log.debug("Request to get all By My Organisation Profils");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<CollaborationDTO> collaborations = collaborationMapper.toDto(collaborationRepository.findAllByConcessionnaireId(personnePhysique.get().getOrganisation().getId()));
        List<OrganisationDTO> organisationDTOS = new ArrayList<>();
        for (CollaborationDTO collaboration : collaborations){
            Optional<Organisation> organisations = organisationRepository.findById(collaboration.getRevendeurId());
            organisationDTOS.add(organisationMapper.toDto(organisations.get()));
        }
        return profilRepository.findAllByOrganisationIn(organisationDTOS,pageable)
            .map(profilMapper::toDto);
    }

    /**
     * Get one profil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfilDTO> findOne(Long id) {
        log.debug("Request to get Profil : {}", id);
        return profilRepository.findById(id)
            .map(profilMapper::toDto);
    }

    /**
     * Delete the profil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Profil : {}", id);
        profilRepository.deleteById(id);
    }
}
