package com.supernettechnologie.impro.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.supernettechnologie.impro.domain.Statistique;
import com.supernettechnologie.impro.domain.*; // for static metamodels
import com.supernettechnologie.impro.repository.StatistiqueRepository;
import com.supernettechnologie.impro.service.dto.StatistiqueCriteria;
import com.supernettechnologie.impro.service.dto.StatistiqueDTO;
import com.supernettechnologie.impro.service.mapper.StatistiqueMapper;

/**
 * Service for executing complex queries for {@link Statistique} entities in the database.
 * The main input is a {@link StatistiqueCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StatistiqueDTO} or a {@link Page} of {@link StatistiqueDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatistiqueQueryService extends QueryService<Statistique> {

    private final Logger log = LoggerFactory.getLogger(StatistiqueQueryService.class);

    private final StatistiqueRepository statistiqueRepository;

    private final StatistiqueMapper statistiqueMapper;

    public StatistiqueQueryService(StatistiqueRepository statistiqueRepository, StatistiqueMapper statistiqueMapper) {
        this.statistiqueRepository = statistiqueRepository;
        this.statistiqueMapper = statistiqueMapper;
    }

    /**
     * Return a {@link List} of {@link StatistiqueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StatistiqueDTO> findByCriteria(StatistiqueCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Statistique> specification = createSpecification(criteria);
        return statistiqueMapper.toDto(statistiqueRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StatistiqueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StatistiqueDTO> findByCriteria(StatistiqueCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Statistique> specification = createSpecification(criteria);
        return statistiqueRepository.findAll(specification, page)
            .map(statistiqueMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StatistiqueCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Statistique> specification = createSpecification(criteria);
        return statistiqueRepository.count(specification);
    }

    /**
     * Function to convert {@link StatistiqueCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Statistique> createSpecification(StatistiqueCriteria criteria) {
        Specification<Statistique> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Statistique_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Statistique_.nom));
            }
        }
        return specification;
    }
}
