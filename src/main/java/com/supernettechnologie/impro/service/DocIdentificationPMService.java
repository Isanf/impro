package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.DocIdentificationPM;
import com.supernettechnologie.impro.domain.DocIdentificationPP;
import com.supernettechnologie.impro.repository.DocIdentificationPMRepository;
import com.supernettechnologie.impro.service.dto.DocIdentificationPMDTO;
import com.supernettechnologie.impro.service.mapper.DocIdentificationPMMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DocIdentificationPM}.
 */
@Service
@Transactional
public class DocIdentificationPMService {

    private final Logger log = LoggerFactory.getLogger(DocIdentificationPMService.class);

    private final DocIdentificationPMRepository docIdentificationPMRepository;

    private final DocIdentificationPMMapper docIdentificationPMMapper;

    public DocIdentificationPMService(DocIdentificationPMRepository docIdentificationPMRepository, DocIdentificationPMMapper docIdentificationPMMapper) {
        this.docIdentificationPMRepository = docIdentificationPMRepository;
        this.docIdentificationPMMapper = docIdentificationPMMapper;
    }

    /**
     * Save a docIdentificationPM.
     *
     * @param docIdentificationPMDTO the entity to save.
     * @return the persisted entity.
     */
    public DocIdentificationPMDTO save(DocIdentificationPMDTO docIdentificationPMDTO) {
        log.debug("Request to save DocIdentificationPM : {}", docIdentificationPMDTO);
        DocIdentificationPM docIdentificationPM = docIdentificationPMMapper.toEntity(docIdentificationPMDTO);
        docIdentificationPM = docIdentificationPMRepository.save(docIdentificationPM);
        return docIdentificationPMMapper.toDto(docIdentificationPM);
    }

    /**
     * Get all the docIdentificationPMS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocIdentificationPMDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocIdentificationPMS");
        return docIdentificationPMRepository.findAll(pageable)
            .map(docIdentificationPMMapper::toDto);
    }

    /**
     * Get one docIdentificationPM by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocIdentificationPMDTO> findOne(Long id) {
        log.debug("Request to get DocIdentificationPM : {}", id);
        return docIdentificationPMRepository.findById(id)
            .map(docIdentificationPMMapper::toDto);
    }

    @Transactional(readOnly = true)
    public DocIdentificationPM findOneByIFU(String ifu) {
        log.debug("Request to get DocIdentificationPP : {}", ifu);
        return docIdentificationPMRepository.findByNumeroIFU(ifu);
    }

    /**
     * Delete the docIdentificationPM by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocIdentificationPM : {}", id);
        docIdentificationPMRepository.deleteById(id);
    }
}
