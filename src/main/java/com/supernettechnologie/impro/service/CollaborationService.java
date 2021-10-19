package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.Collaboration;
import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.domain.User;
import com.supernettechnologie.impro.repository.CollaborationRepository;
import com.supernettechnologie.impro.repository.OrganisationRepository;
import com.supernettechnologie.impro.repository.PersonnePhysiqueRepository;
import com.supernettechnologie.impro.repository.UserRepository;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.CollaborationDTO;
import com.supernettechnologie.impro.service.dto.LogActivityDTO;
import com.supernettechnologie.impro.service.dto.OrganisationDTO;
import com.supernettechnologie.impro.service.mapper.CollaborationMapper;
import com.supernettechnologie.impro.service.mapper.OrganisationMapper;
import com.supernettechnologie.impro.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Collaboration}.
 */
@Service
@Transactional
public class CollaborationService {

    private final Logger log = LoggerFactory.getLogger(CollaborationService.class);

    private final CollaborationRepository collaborationRepository;

    private final CollaborationMapper collaborationMapper;
    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private OrganisationMapper organisationMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogActivityService logActivityService;

    public CollaborationService(CollaborationRepository collaborationRepository, CollaborationMapper collaborationMapper) {
        this.collaborationRepository = collaborationRepository;
        this.collaborationMapper = collaborationMapper;
    }

    /**
     * Save a collaboration.
     *
     * @param collaborationDTO the entity to save.
     * @return the persisted entity.
     */
    public CollaborationDTO save(CollaborationDTO collaborationDTO) {
        log.debug("Request to save Collaboration : {}", collaborationDTO);
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        User user = userRepository.findById(personnePhysique.get().getUser().getId()).get();
        LogActivityDTO logActivityDTO = new LogActivityDTO();
        logActivityDTO.setAction("création d'une nouvelle collaboration");
        logActivityDTO.setPrincipal(user.getLogin().toUpperCase());
        logActivityService.save(logActivityDTO);
        collaborationDTO.setConcessionnaireId(personnePhysique.get().getOrganisation().getId());
        collaborationDTO.setDateDebut(LocalDate.now());
        collaborationDTO.setNumeroCollaboration(RandomUtil.generateRandomNumericString());
        Collaboration collaboration = collaborationMapper.toEntity(collaborationDTO);
        collaboration = collaborationRepository.save(collaboration);
        return collaborationMapper.toDto(collaboration);
    }

    /**
     * Get all the collaborations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CollaborationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Collaborations");
        Page<CollaborationDTO> collaborationDTOPage = collaborationRepository.findAll(pageable)
            .map(collaborationMapper::toDto);
        List<CollaborationDTO> collaborationDTOList = new ArrayList<>();
        for (CollaborationDTO collaborationDTO : collaborationDTOPage.getContent()){
            OrganisationDTO organisationDTO = organisationMapper.toDto(organisationRepository.findById(collaborationDTO.getRevendeurId()).get());
            OrganisationDTO organisationDTO1 = organisationMapper.toDto(organisationRepository.findById(collaborationDTO.getConcessionnaireId()).get());
            collaborationDTO.setRevendeurs(organisationDTO);
            collaborationDTO.setConcessionnaires(organisationDTO1);
            collaborationDTOList.add(collaborationDTO);
        }
        return new PageImpl<>(collaborationDTOList);
    }

    public Page<CollaborationDTO> findAllByme(Pageable pageable) {
        log.debug("Request to get all Collaborations");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        Page<CollaborationDTO> collaborationDTOPage = collaborationRepository.findAllByConcessionnaireId(pageable, personnePhysique.get().getOrganisation().getId())
            .map(collaborationMapper::toDto);
        List<CollaborationDTO> collaborationDTOList = new ArrayList<>();
        for (CollaborationDTO collaborationDTO : collaborationDTOPage.getContent()){
            OrganisationDTO organisationDTO = organisationMapper.toDto(organisationRepository.findById(collaborationDTO.getRevendeurId()).get());
            OrganisationDTO organisationDTO1 = organisationMapper.toDto(organisationRepository.findById(collaborationDTO.getConcessionnaireId()).get());
            collaborationDTO.setRevendeurs(organisationDTO);
            collaborationDTO.setConcessionnaires(organisationDTO1);
            collaborationDTOList.add(collaborationDTO);
        }
        return new PageImpl<>(collaborationDTOList);
    }

    /**
     * Get one collaboration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CollaborationDTO> findOne(Long id) {
        log.debug("Request to get Collaboration : {}", id);
        return collaborationRepository.findById(id)
            .map(collaborationMapper::toDto);
    }

    /**
     * Delete the collaboration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Collaboration : {}", id);
        collaborationRepository.deleteById(id);
    }
}
