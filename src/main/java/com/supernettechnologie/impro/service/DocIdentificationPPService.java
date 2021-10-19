package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.DocIdentificationPP;
import com.supernettechnologie.impro.repository.DocIdentificationPPRepository;
import com.supernettechnologie.impro.service.dto.DocIdentificationPPDTO;
import com.supernettechnologie.impro.service.mapper.DocIdentificationPPMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DocIdentificationPP}.
 */
@Service
@Transactional
public class DocIdentificationPPService {

    private final Logger log = LoggerFactory.getLogger(DocIdentificationPPService.class);

    private final DocIdentificationPPRepository docIdentificationPPRepository;

    private final DocIdentificationPPMapper docIdentificationPPMapper;

    public DocIdentificationPPService(DocIdentificationPPRepository docIdentificationPPRepository, DocIdentificationPPMapper docIdentificationPPMapper) {
        this.docIdentificationPPRepository = docIdentificationPPRepository;
        this.docIdentificationPPMapper = docIdentificationPPMapper;
    }

    /**
     * Save a docIdentificationPP.
     *
     * @param docIdentificationPPDTO the entity to save.
     * @return the persisted entity.
     */
    public DocIdentificationPPDTO save(DocIdentificationPPDTO docIdentificationPPDTO) {
        log.debug("Request to save DocIdentificationPP : {}", docIdentificationPPDTO);
        DocIdentificationPP docIdentificationPP = docIdentificationPPMapper.toEntity(docIdentificationPPDTO);
        docIdentificationPP = docIdentificationPPRepository.save(docIdentificationPP);
        return docIdentificationPPMapper.toDto(docIdentificationPP);
    }

    /**
     * Get all the docIdentificationPPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocIdentificationPPDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocIdentificationPPS");
        return docIdentificationPPRepository.findAll(pageable)
            .map(docIdentificationPPMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<DocIdentificationPP> findAll() {
        log.debug("Request to get all DocIdentificationPPS");
        return docIdentificationPPRepository.findAll();
    }


    /**
     * Get one docIdentificationPP by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocIdentificationPPDTO> findOne(Long id) {
        log.debug("Request to get DocIdentificationPP : {}", id);
        return docIdentificationPPRepository.findById(id)
            .map(docIdentificationPPMapper::toDto);
    }

    @Transactional(readOnly = true)
    public DocIdentificationPP findOneByNip(String nip) {
        log.debug("Request to get DocIdentificationPP : {}", nip);
        return docIdentificationPPRepository.findByNip(nip);
    }

    /**
     * Delete the docIdentificationPP by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocIdentificationPP : {}", id);

        docIdentificationPPRepository.deleteById(id);
    }
}
