package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.service.dto.StatistiqueDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.supernettechnologie.impro.domain.Statistique}.
 */
public interface StatistiqueService {

    /**
     * Save a statistique.
     *
     * @param statistiqueDTO the entity to save.
     * @return the persisted entity.
     */
    StatistiqueDTO save(StatistiqueDTO statistiqueDTO);

    /**
     * Get all the statistiques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StatistiqueDTO> findAll(Pageable pageable);


    /**
     * Get the "id" statistique.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatistiqueDTO> findOne(Long id);

    /**
     * Delete the "id" statistique.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
