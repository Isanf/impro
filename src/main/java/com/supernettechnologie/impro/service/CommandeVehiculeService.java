package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.CommandeVehiculeDTO;
import com.supernettechnologie.impro.service.dto.InfoCommandeVehiculeDTO;
import com.supernettechnologie.impro.service.dto.LivraisonCarnetSoucheDTO;
import com.supernettechnologie.impro.service.mapper.CommandeVehiculeMapper;
import com.supernettechnologie.impro.service.mapper.InfoCommandeCarnetASoucheMapper;
import com.supernettechnologie.impro.service.mapper.InfoCommandeVehiculeMapper;
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
import java.util.Set;

/**
 * Service Implementation for managing {@link CommandeVehicule}.
 */
@Service
@Transactional
public class CommandeVehiculeService {

    private final Logger log = LoggerFactory.getLogger(CommandeVehiculeService.class);

    private final CommandeVehiculeRepository commandeVehiculeRepository;

    private final CommandeVehiculeMapper commandeVehiculeMapper;

    private final InfoCommandeVehiculeRepository infoCommandeVehiculeRepository;

    @Autowired
    private InfoCommandeVehiculeService infoCommandeVehiculeService;
    @Autowired
    private InfoCommandeVehiculeMapper infoCommandeVehiculeMapper;
    @Autowired
    private MarqueVehiculeRepository marqueVehicueleRepository;
    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    @Autowired
    private TypeActeurRepository typeActeurRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogActivityService logActivityService;

    public CommandeVehiculeService(CommandeVehiculeRepository commandeVehiculeRepository, CommandeVehiculeMapper commandeVehiculeMapper, InfoCommandeVehiculeRepository infoCommandeVehiculeRepository) {
        this.commandeVehiculeRepository = commandeVehiculeRepository;
        this.commandeVehiculeMapper = commandeVehiculeMapper;
        this.infoCommandeVehiculeRepository = infoCommandeVehiculeRepository;
    }

    /**
     * Save a commandeVehicule.
     *
     * @param commandeVehiculeDTO the entity to save.
     * @return the persisted entity.
     */
    public CommandeVehiculeDTO save(CommandeVehiculeDTO commandeVehiculeDTO) {
        log.debug("Request to save CommandeVehicule : {}", commandeVehiculeDTO);
        CommandeVehicule commandeVehicule = commandeVehiculeMapper.toEntity(commandeVehiculeDTO);
        commandeVehicule = commandeVehiculeRepository.save(commandeVehicule);
        return commandeVehiculeMapper.toDto(commandeVehicule);
    }

    /**
     * Get all the commandeVehicules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommandeVehiculeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommandeVehicules");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
            return commandeVehiculeRepository.findAllByConcessionnaireId(pageable, personnePhysique.get().getOrganisation().getId())
                .map(commandeVehiculeMapper::toDto);
    }

    public Page<CommandeVehiculeDTO> findAll1(Pageable pageable) {
        log.debug("Request to get all CommandeVehicules");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        return commandeVehiculeRepository.findAllByRevendeurId(pageable, personnePhysique.get().getOrganisation().getId())
            .map(commandeVehiculeMapper::toDto);
    }

    public Page<CommandeVehiculeDTO> findAllNotDelivery(Pageable pageable) {
        log.debug("Request to get all CommandeVehicules");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        return commandeVehiculeRepository.findAllByConcessionnaireIdAndEstLivreeFalse(pageable, personnePhysique.get().getOrganisation().getId())
            .map(commandeVehiculeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<CommandeVehiculeDTO> findAlls() {
        log.debug("Request to get all CommandeVehicules");
        List<CommandeVehicule> commandeVehicules = commandeVehiculeRepository.findAll();
        List<CommandeVehiculeDTO> commandeVehiculeDTOS = commandeVehiculeMapper.toDto(commandeVehicules);
        for (int i = 0; i<commandeVehiculeDTOS.size(); i++){
            Optional<Organisation> organisation = organisationRepository.findById(commandeVehiculeDTOS.get(i).getConcessionnaireId());
            commandeVehiculeDTOS.get(i).setConcessionnaireName(organisation.get().getNom());
            System.out.println(organisation);
        }
        return commandeVehiculeDTOS;
    }

    /*@Transactional(readOnly = true)
    public List<CommandeVehiculeDTO> findAlls() {
        log.debug("Request to get all CommandeVehicules");
        List<CommandeVehicule> commandeVehicules = commandeVehiculeRepository.findAll();
        List<CommandeVehiculeDTO> commandeVehiculeDTOS = commandeVehiculeMapper.toDto(commandeVehicules);
        for (int i = 0; i<commandeVehiculeDTOS.size(); i++){
            Optional<Organisation> organisation = organisationRepository.findById(commandeVehiculeDTOS.get(i).getConcessionnaireId());
            commandeVehiculeDTOS.get(i).setConcessionnaireName(organisation.get().getNom());
            System.out.println(organisation);
        }
        return commandeVehiculeDTOS;
    }
*/
    /**
     * Get one commandeVehicule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommandeVehiculeDTO> findOne(Long id) {
        log.debug("Request to get CommandeVehicule : {}", id);
        return commandeVehiculeRepository.findById(id)
            .map(commandeVehiculeMapper::toDto);
    }

    /**
     * Delete the commandeVehicule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommandeVehicule : {}", id);
        commandeVehiculeRepository.deleteById(id);
    }

    public void findOneValidAdmin(Long id) {
        log.debug("Request to get CommandeCarnetSouche : {}", id);
        Optional<CommandeVehicule> commandeVehicule = commandeVehiculeRepository.findById(id);
        List<InfoCommandeVehicule> infoCommandeVehicule = infoCommandeVehiculeRepository.findByCommandeVehiculeId(id);
        log.debug("infosCCAS {}", infoCommandeVehicule.size());

        for (int i = 0; i < infoCommandeVehicule.size(); i++) {
            Long quantiteCommande = infoCommandeVehicule.get(i).getQuantiteCommande();
            List<MarqueVehicule> marqueVehicules = marqueVehicueleRepository.findByInfoCommandeVehicules(infoCommandeVehicule.get(i));
            String libelle = marqueVehicules.get(0).getLibelle();
        }

    }
}
