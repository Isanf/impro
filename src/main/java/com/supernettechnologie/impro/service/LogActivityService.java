package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.LogActivity;
import com.supernettechnologie.impro.repository.LogActivityRepository;
import com.supernettechnologie.impro.service.dto.LogActivityDTO;
import com.supernettechnologie.impro.service.mapper.LogActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LogActivity}.
 */
@Service
@Transactional
public class LogActivityService {

    private final Logger log = LoggerFactory.getLogger(LogActivityService.class);

    private final LogActivityRepository logActivityRepository;

    private final LogActivityMapper logActivityMapper;

    public LogActivityService(LogActivityRepository logActivityRepository, LogActivityMapper logActivityMapper) {
        this.logActivityRepository = logActivityRepository;
        this.logActivityMapper = logActivityMapper;
    }

    /**
     * Save a logActivity.
     *
     * @param logActivityDTO the entity to save.
     * @return the persisted entity.
     */
    public LogActivityDTO save(LogActivityDTO logActivityDTO) {
        log.debug("Request to save LogActivity : {}", logActivityDTO);
        LogActivity logActivity = logActivityMapper.toEntity(logActivityDTO);
        logActivity.setDateAction(ZonedDateTime.now());
        logActivity = logActivityRepository.save(logActivity);
        return logActivityMapper.toDto(logActivity);
    }

    /**
     * Get all the logActivities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LogActivityDTO> findAll() {
        log.debug("Request to get all LogActivities");
        return logActivityRepository.findAll().stream()
            .map(logActivityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one logActivity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LogActivityDTO> findOne(Long id) {
        log.debug("Request to get LogActivity : {}", id);
        return logActivityRepository.findById(id)
            .map(logActivityMapper::toDto);
    }

    /**
     * Delete the logActivity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LogActivity : {}", id);

        logActivityRepository.deleteById(id);
    }
}
