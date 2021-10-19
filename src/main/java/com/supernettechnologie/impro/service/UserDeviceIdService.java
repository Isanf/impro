package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.User;
import com.supernettechnologie.impro.domain.UserDeviceId;
import com.supernettechnologie.impro.repository.UserDeviceIdRepository;
import com.supernettechnologie.impro.repository.UserRepository;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.UserDeviceIdDTO;
import com.supernettechnologie.impro.service.mapper.UserDeviceIdMapper;
import com.supernettechnologie.impro.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserDeviceId}.
 */
@Service
@Transactional
public class UserDeviceIdService {

    private final Logger log = LoggerFactory.getLogger(UserDeviceIdService.class);

    private final UserDeviceIdRepository userDeviceIdRepository;

    private final UserDeviceIdMapper userDeviceIdMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public UserDeviceIdService(UserDeviceIdRepository userDeviceIdRepository, UserDeviceIdMapper userDeviceIdMapper) {
        this.userDeviceIdRepository = userDeviceIdRepository;
        this.userDeviceIdMapper = userDeviceIdMapper;
    }

    /**
     * Save a userDeviceId.
     *
     * @param userDeviceIdDTO the entity to save.
     * @return the persisted entity.
     */
    public UserDeviceIdDTO save(UserDeviceIdDTO userDeviceIdDTO) {
        log.debug("Request to save UserDeviceId : {}", userDeviceIdDTO);
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        UserDeviceId userDeviceId = userDeviceIdMapper.toEntity(userDeviceIdDTO);
        userDeviceId.setUser(user);
        userDeviceId = userDeviceIdRepository.save(userDeviceId);
        return userDeviceIdMapper.toDto(userDeviceId);
    }

    /**
     * Get all the userDeviceIds.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserDeviceIdDTO> findAll() {
        log.debug("Request to get all UserDeviceIds");
        return userDeviceIdRepository.findAll().stream()
            .map(userDeviceIdMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one userDeviceId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserDeviceIdDTO> findOne(Long id) {
        log.debug("Request to get UserDeviceId : {}", id);
        return userDeviceIdRepository.findById(id)
            .map(userDeviceIdMapper::toDto);
    }

    /**
     * Delete the userDeviceId by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserDeviceId : {}", id);
        userDeviceIdRepository.deleteById(id);
    }

    public Optional<UserDeviceIdDTO> findOneName(String name) {
        return userDeviceIdRepository.findByDeviceId(name)
            .map(userDeviceIdMapper::toDto);
    }
}
