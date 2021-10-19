package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.PrixCertificat;
import com.supernettechnologie.impro.repository.PrixCertificatRepository;
import com.supernettechnologie.impro.service.dto.PrixCertificatDTO;
import com.supernettechnologie.impro.service.mapper.PrixCertificatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PrixCertificat}.
 */
@Service
@Transactional
public class PrixCertificatService {

    private final Logger log = LoggerFactory.getLogger(PrixCertificatService.class);

    private final PrixCertificatRepository prixCertificatRepository;

    private final PrixCertificatMapper prixCertificatMapper;

    public PrixCertificatService(PrixCertificatRepository prixCertificatRepository, PrixCertificatMapper prixCertificatMapper) {
        this.prixCertificatRepository = prixCertificatRepository;
        this.prixCertificatMapper = prixCertificatMapper;
    }

    /**
     * Save a prixCertificat.
     *
     * @param prixCertificatDTO the entity to save.
     * @return the persisted entity.
     */
    public PrixCertificatDTO save(PrixCertificatDTO prixCertificatDTO) {
        log.debug("Request to save PrixCertificat : {}", prixCertificatDTO);
        PrixCertificat prixCertificat = prixCertificatMapper.toEntity(prixCertificatDTO);
        prixCertificat = prixCertificatRepository.save(prixCertificat);
        return prixCertificatMapper.toDto(prixCertificat);
    }

    /**
     * Get all the prixCertificats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrixCertificatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrixCertificats");
        return prixCertificatRepository.findAll(pageable)
            .map(prixCertificatMapper::toDto);
    }


    /**
     * Get one prixCertificat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrixCertificatDTO> findOne(Long id) {
        log.debug("Request to get PrixCertificat : {}", id);
        return prixCertificatRepository.findById(id)
            .map(prixCertificatMapper::toDto);
    }

    /**
     * Delete the prixCertificat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrixCertificat : {}", id);
        prixCertificatRepository.deleteById(id);
    }
}
