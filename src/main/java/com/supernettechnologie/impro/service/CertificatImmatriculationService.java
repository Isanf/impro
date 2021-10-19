package com.supernettechnologie.impro.service;


import com.supernettechnologie.impro.domain.CarnetASouche;
import com.supernettechnologie.impro.domain.CertificatImmatriculation;
import com.supernettechnologie.impro.domain.PlaqueImmatriculation;
import com.supernettechnologie.impro.repository.CarnetASoucheRepository;
import com.supernettechnologie.impro.repository.CertificatImmatriculationRepository;
import com.supernettechnologie.impro.repository.PlaqueImmatriculationRepository;
import com.supernettechnologie.impro.service.dto.CertificatImmatriculationDTO;
import com.supernettechnologie.impro.service.mapper.CarnetASoucheMapper;
import com.supernettechnologie.impro.service.mapper.CertificatImmatriculationMapper;
import com.supernettechnologie.impro.service.mapper.PlaqueImmatriculationMapper;
import org.bouncycastle.asn1.ocsp.CertID;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CertificatImmatriculation}.
 */
@Service
@Transactional
public class CertificatImmatriculationService {

    private final Logger log = LoggerFactory.getLogger(CertificatImmatriculationService.class);

    private final CertificatImmatriculationRepository certificatImmatriculationRepository;

    private final CertificatImmatriculationMapper certificatImmatriculationMapper;
    @Autowired
    private PlaqueImmatriculationRepository plaqueImmatriculationRepository;
    @Autowired
    private PlaqueImmatriculationMapper plaqueImmatriculationMapper;

    public CertificatImmatriculationService(CertificatImmatriculationRepository certificatImmatriculationRepository, CertificatImmatriculationMapper certificatImmatriculationMapper) {
        this.certificatImmatriculationRepository = certificatImmatriculationRepository;
        this.certificatImmatriculationMapper = certificatImmatriculationMapper;
    }

    /**
     * Save a certificatImmatriculation.
     *
     * @param certificatImmatriculationDTO the entity to save.
     * @return the persisted entity.
     */
    public CertificatImmatriculationDTO save(CertificatImmatriculationDTO certificatImmatriculationDTO) {
        log.debug("Request to save CertificatImmatriculation : {}", certificatImmatriculationDTO);
        CertificatImmatriculation certificatImmatriculation = certificatImmatriculationMapper.toEntity(certificatImmatriculationDTO);
        certificatImmatriculation = certificatImmatriculationRepository.save(certificatImmatriculation);
        return certificatImmatriculationMapper.toDto(certificatImmatriculation);
    }

    /**
     * Get all the certificatImmatriculations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CertificatImmatriculationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CertificatImmatriculations");
        return certificatImmatriculationRepository.findAll(pageable)
            .map(certificatImmatriculationMapper::toDto);
    }

    /**
     * Get one certificatImmatriculation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CertificatImmatriculationDTO> findOne(Long id) {
        log.debug("Request to get CertificatImmatriculation : {}", id);
        return certificatImmatriculationRepository.findById(id)
            .map(certificatImmatriculationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<CertificatImmatriculationDTO> findOneByQr(String qr) {
        log.debug("Request to get CertificatImmatriculation : {}", qr);
        Optional<CertificatImmatriculationDTO> certificatImmatriculationDTO = certificatImmatriculationRepository.findByCodeQr(qr).map(certificatImmatriculationMapper::toDto);
        if (!plaqueImmatriculationRepository.existsByCertificatImmatriculationId(certificatImmatriculationDTO.get().getId())) {
            return certificatImmatriculationDTO;
        }else {
            certificatImmatriculationDTO.get().setNumero("null");
            return certificatImmatriculationDTO;
        }
    }


    /**
     * Delete the certificatImmatriculation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CertificatImmatriculation : {}", id);
        certificatImmatriculationRepository.deleteById(id);
    }
}
