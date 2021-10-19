package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.Firstlogin;
import com.supernettechnologie.impro.domain.PersonnePhysique;
import com.supernettechnologie.impro.domain.User;
import com.supernettechnologie.impro.repository.FirstloginRepository;
import com.supernettechnologie.impro.repository.PersonnePhysiqueRepository;
import com.supernettechnologie.impro.repository.UserRepository;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.FirstloginDTO;
import com.supernettechnologie.impro.service.dto.OrganisationDTO;
import com.supernettechnologie.impro.service.dto.UserDTO;
import com.supernettechnologie.impro.service.mapper.FirstloginMapper;
import com.supernettechnologie.impro.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Firstlogin}.
 */
@Service
@Transactional
public class FirstloginService {

    private final Logger log = LoggerFactory.getLogger(FirstloginService.class);

    private final FirstloginRepository firstloginRepository;

    private final FirstloginMapper firstloginMapper;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public FirstloginService(FirstloginRepository firstloginRepository, FirstloginMapper firstloginMapper) {
        this.firstloginRepository = firstloginRepository;
        this.firstloginMapper = firstloginMapper;
    }

    /**
     * Save a firstlogin.
     *
     * @param firstloginDTO the entity to save.
     * @return the persisted entity.
     */
    public FirstloginDTO save(FirstloginDTO firstloginDTO) {
        log.debug("Request to save Firstlogin : {}", firstloginDTO);
        Firstlogin firstlogin = firstloginMapper.toEntity(firstloginDTO);
        firstlogin = firstloginRepository.save(firstlogin);
        return firstloginMapper.toDto(firstlogin);
    }

    /**
     * Get all the firstlogins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FirstloginDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Firstlogins");
        return firstloginRepository.findAll(pageable)
            .map(firstloginMapper::toDto);
    }

    public String findMyfirstlogin() {
        log.debug("Request to get all Organisations");
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        log.debug("****************************************** : {}", user.get().getActivationKey());
        return user.get().getActivationKey();
    }

    public Optional<String> firstLogin() {
        log.debug("Request to get all Organisations");
        return Optional.ofNullable(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getActivationKey());
    }

    public Optional<User> findMyUser(){
        log.debug("****************************************** : {}", userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()));
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
    }


    /**
     * Get one firstlogin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FirstloginDTO> findOne(Long id) {
        log.debug("Request to get Firstlogin : {}", id);
        return firstloginRepository.findById(id)
            .map(firstloginMapper::toDto);
    }

    /**
     * Delete the firstlogin by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Firstlogin : {}", id);
        firstloginRepository.deleteById(id);
    }

}
