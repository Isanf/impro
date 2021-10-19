package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.config.Constants;
import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.AuthoritiesConstants;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.UserDTO;
import com.supernettechnologie.impro.service.mapper.DocIdentificationPPMapper;
import com.supernettechnologie.impro.service.mapper.OrganisationMapper;
import com.supernettechnologie.impro.service.mapper.PersonnePhysiqueMapper;
import com.supernettechnologie.impro.service.mapper.ProfilMapper;
import com.supernettechnologie.impro.service.util.RandomUtil;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    private final ProfilRepository profilRepository;

    private final ProfilMapper profilMapper;

    private final PersonnePhysiqueRepository personnePhysiqueRepository;

    private final PersonnePhysiqueMapper personnePhysiqueMapper;

    private final DocIdentificationPPRepository docIdentificationPPRepository;

    private final DocIdentificationPPMapper docIdentificationPPMapper;

    private final OrganisationRepository organisationRepository;

    private final OrganisationMapper organisationMapper;
    @Autowired
    private UserOtpRepository userOtpRepository;

    @Autowired
    private FirstloginRepository firstloginRepository;
    @Autowired
    private LogActivityService logActivityService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, CacheManager cacheManager, ProfilRepository profilRepository, ProfilMapper profilMapper, PersonnePhysiqueRepository personnePhysiqueRepository, PersonnePhysiqueMapper personnePhysiqueMapper, DocIdentificationPPRepository docIdentificationPPRepository, DocIdentificationPPMapper docIdentificationPPMapper, OrganisationRepository organisationRepository, OrganisationMapper organisationMapper, FirstloginRepository firstloginRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.profilRepository = profilRepository;
        this.profilMapper = profilMapper;
        this.personnePhysiqueRepository = personnePhysiqueRepository;
        this.personnePhysiqueMapper = personnePhysiqueMapper;
        this.docIdentificationPPRepository = docIdentificationPPRepository;
        this.docIdentificationPPMapper = docIdentificationPPMapper;
        this.organisationRepository = organisationRepository;
        this.organisationMapper = organisationMapper;
        this.firstloginRepository = firstloginRepository;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                this.clearUserCaches(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                this.clearUserCaches(user);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                this.clearUserCaches(user);
                return user;
            });
    }

    public User registerUser(UserDTO userDTO, String password) {
        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new UsernameAlreadyUsedException();
            }
        });
        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail().toLowerCase());
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser){
        if (existingUser.getActivated()) {
             return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Créer un Utilisateur et enregistrer temporairement ses données de connexion sur la table PersonnePhysique//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        user.setActivationKey("1");
        PersonnePhysique personnePhysique = personnePhysiqueMapper.toEntity(userDTO.getPersonnePhysiqueDTO());
        Organisation organisation = organisationMapper.toEntity(userDTO.getOrganisationDTO());
        DocIdentificationPP docIdentificationPP = docIdentificationPPMapper.toEntity(userDTO.getDocIdentificationPPDTO());
        docIdentificationPP = docIdentificationPPRepository.save(docIdentificationPP);
        personnePhysique.setDocIdentification(docIdentificationPP);
        log.debug("Profil id ******************** {}", userDTO.getProfilId());
        Optional<Profil> profil = profilRepository.findById(userDTO.getProfilId());
        Set<Authority> authorities = new HashSet<>();
        profil.ifPresent(value -> authorities.addAll(value.getAuthorities()));
        profil.ifPresent( value -> personnePhysique.setOrganisation(value.getOrganisation()));
        personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        user.setAuthorities(authorities);
        user.setOrganisation(organisation);
        user = userRepository.save(user);
        personnePhysique.setFlogin(userDTO.getLogin());
        personnePhysique.setFpassword(userDTO.getPassword());
        ////////////////////////////Generation OTP/////////////////////////////////////////////
        UserOtp userOtp = new UserOtp();
        userOtp.setUser(user);
        userOtp.setOtpNumber(Long.parseLong(RandomUtil.generateRandomUserOtp()));
        userOtp.setOtpUsed(false);
        userOtpRepository.save(userOtp);
        //////////////////////////////Fin Generation OTP //////////////////////////////////////
        personnePhysique.setFotp(userOtp.getOtpNumber());
        personnePhysique.setUser(user);
        personnePhysiqueRepository.save(personnePhysique);
        if(userDTO.getPersonnePhysiqueDTO().isGerant()){
            organisation.setGerant(personnePhysique);
        }
        organisationRepository.save(organisation);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email.toLowerCase());
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
            });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setLogin(userDTO.getLogin().toLowerCase());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setEmail(userDTO.getEmail().toLowerCase());
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
                user.setPassword(encryptedPassword);
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Changer son mot de passe lors de sa premiere connexion et effacer ses données de connexion de la table PersonnePhysique//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                user.getAuthorities().add(authorityRepository.findByName("FIRSTLOGIN_OK"));
                ///////////////////////////////Begin Clean User Data from PersonnePhysique/////////////////////////
                user.setActivationKey(null);//S'il y a une valeur dans le champ cela veut dire que c'est ça premiere connexion
                Optional<PersonnePhysique> personnePhysique =
                    personnePhysiqueRepository.findByUserLogin(user.getLogin());
                personnePhysique.get().setFlogin(null);
                personnePhysique.get().setFpassword(null);
                personnePhysique.get().setFotp(null);
                ///////////////////////////////End Clean User Data from PersonnePhysique/////////////////////////
                this.clearUserCaches(user);
                log.debug("Changed password for User: {}", user);
                log.debug("*********************/////User//////*************: {}", user.getAuthorities());
            });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
        //return userRepository.findAllByCreatedBy(pageable, SecurityUtils.getCurrentUserLogin().get()).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllByCreater(Pageable pageable) {
        //return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
        return userRepository.findAllByCreatedBy(pageable, SecurityUtils.getCurrentUserLogin().get()).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllByMyOrg(Pageable pageable) {
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        return userRepository.findAllByOrganisationId(pageable, personnePhysique.get().getOrganisation().getId()).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        Optional<User> user = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
        return user;
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(user -> {
                log.debug("Deleting not activated user {}", user.getLogin());
                userRepository.delete(user);
                this.clearUserCaches(user);
            });
    }

    /**
     * Gets a list of all the authorities.
     * @return a list of all the authorities.
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    public List<String> getAuthorities4STH() {
        String nom = "ROLE_ADMIN";
        return authorityRepository.findAllByName(nom).stream().map(Authority::getName).collect(Collectors.toList());
    }

    public List<String> getAuthorities4STHGuichet() {
        String nom = "STHGUICHET";
        return authorityRepository.findAllByName(nom).stream().map(Authority::getName).collect(Collectors.toList());
    }

    public List<String> getAuthorities4Concessionnaire() {
        String nom = "CONCESSIONNAIRE";
        return authorityRepository.findAllByName(nom).stream().map(Authority::getName).collect(Collectors.toList());
    }

    public List<String> getAuthorities4Revendeur() {
        String nom = "REVENDEUR";
        return authorityRepository.findAllByName(nom).stream().map(Authority::getName).collect(Collectors.toList());
    }

    public List<String> getAuthorities4DGTTM() {
        String nom = "DGTTM";
        return authorityRepository.findAllByName(nom).stream().map(Authority::getName).collect(Collectors.toList());
    }

    public List<String> getAuthorities4DOUANE() {
        String nom = "DOUANE";
        return authorityRepository.findAllByName(nom).stream().map(Authority::getName).collect(Collectors.toList());
    }

    public List<String> getAuthorities4DG_DGTTM() {
        String nom = "DG_DGTTM";
        return authorityRepository.findAllByName(nom).stream().map(Authority::getName).collect(Collectors.toList());
    }

    public List<String> getAuthorities4DG_DOUANE() {
        String nom = "DG_DOUANE";
        return authorityRepository.findAllByName(nom).stream().map(Authority::getName).collect(Collectors.toList());
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
    }

    public static Long getCurrentUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String id = null;
        if (authentication != null)
            if (authentication.getPrincipal() instanceof UserDetails)
                id = ((UserDetails) authentication.getPrincipal()).getUsername();
            else if (authentication.getPrincipal() instanceof String)
                id = (String) authentication.getPrincipal();
        try {
            return Long.valueOf(id != null ? id : "1"); //anonymoususer
        } catch (NumberFormatException e) {
            return 1L;
        }
    }

    public String exportReport(String reportFormat, String login) throws JRException, IOException {
        //String path = "C:\\Users\\SANFO ISSAKA\\Desktop\\Report";
        String path = null;
        if (SystemUtils.IS_OS_LINUX) {
            new File("/root/NouveauUtlisateur/").mkdir();
            path = "/root/NouveauUtlisateur/";
        } else {
            new File("C:\\NouveauUtlisateur").mkdir();
            path = "C:\\NouveauUtlisateur\\";
        }
        List<PersonnePhysique> user = personnePhysiqueRepository.findOneByUserLogin(login);
        //Load File and Compile it
        ClassPathResource cm = new ClassPathResource("InfosConnexion.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(cm.getInputStream());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(user);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Created by", "STH");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path+"User.html");
        }
        if(reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint, path+"User_"+user.get(0).getOrganisation().getNom() + ".pdf");
        }
        return "Report generated in: "+path;
    }

}
