package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.User;
import com.supernettechnologie.impro.domain.UserOtp;
import com.supernettechnologie.impro.repository.UserOtpRepository;
import com.supernettechnologie.impro.repository.UserRepository;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.UserOtpDTO;
import com.supernettechnologie.impro.service.mapper.UserOtpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserOtp}.
 */
@Service
@Transactional
public class UserOtpService {

    private final Logger log = LoggerFactory.getLogger(UserOtpService.class);

    private final UserOtpRepository userOtpRepository;

    private final UserOtpMapper userOtpMapper;

    @Autowired
    private UserRepository userRepository;

    public UserOtpService(UserOtpRepository userOtpRepository, UserOtpMapper userOtpMapper) {
        this.userOtpRepository = userOtpRepository;
        this.userOtpMapper = userOtpMapper;
    }

    /**
     * Save a userOtp.
     *
     * @param userOtpDTO the entity to save.
     * @return the persisted entity.
     */
    public UserOtpDTO save(UserOtpDTO userOtpDTO) {
        log.debug("Request to save UserOtp : {}", userOtpDTO);
        UserOtp userOtp = userOtpMapper.toEntity(userOtpDTO);
        userOtp = userOtpRepository.save(userOtp);
        return userOtpMapper.toDto(userOtp);
    }

    /**
     * Get all the userOtps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserOtpDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserOtps");
        return userOtpRepository.findAll(pageable)
            .map(userOtpMapper::toDto);
    }


    /**
     * Get one userOtp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserOtpDTO> findOne(Long id) {
        log.debug("Request to get UserOtp : {}", id);
        return userOtpRepository.findById(id)
            .map(userOtpMapper::toDto);
    }

    /**
     * Delete the userOtp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserOtp : {}", id);
        userOtpRepository.deleteById(id);
    }

    public Optional<UserOtpDTO> findOneCheck(Long otp) {
        Optional<UserOtp> userOtp = userOtpRepository.findByOtpNumber(otp);
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        if (userOtp.get().getUser() != user) {
            return Optional.empty();
        }else {
            return userOtp.map(userOtpMapper::toDto);
        }
    }
}
