package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.Nation;
import com.supernettechnologie.impro.repository.NationRepository;
import com.supernettechnologie.impro.service.dto.NationDTO;
import com.supernettechnologie.impro.service.mapper.NationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Nation}.
 */
@Service
@Transactional
public class NationService {

    private final Logger log = LoggerFactory.getLogger(NationService.class);

    private final NationRepository nationRepository;

    private final NationMapper nationMapper;

    public NationService(NationRepository nationRepository, NationMapper nationMapper) {
        this.nationRepository = nationRepository;
        this.nationMapper = nationMapper;
    }

    /**
     * Save a nation.
     *
     * @param nationDTO the entity to save.
     * @return the persisted entity.
     */
    public NationDTO save(NationDTO nationDTO) {
        log.debug("Request to save Nation : {}", nationDTO);
        Nation nation = nationMapper.toEntity(nationDTO);
        nation = nationRepository.save(nation);
        return nationMapper.toDto(nation);
    }

    /**
     * Get all the nations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nations");
        return nationRepository.findAll(pageable)
            .map(nationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<Nation> findAlllist() {
        log.debug("Request to get all Nations");
        List<Nation> nations = new ArrayList<>();
        nations = nationRepository.findAll();
        return nations;
    }


    /**
     * Get one nation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NationDTO> findOne(Long id) {
        log.debug("Request to get Nation : {}", id);
        return nationRepository.findById(id)
            .map(nationMapper::toDto);
    }

    /**
     * Delete the nation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Nation : {}", id);
        nationRepository.deleteById(id);
    }
}
