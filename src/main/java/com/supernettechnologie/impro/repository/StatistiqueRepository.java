package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.Statistique;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Statistique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatistiqueRepository extends JpaRepository<Statistique, Long>, JpaSpecificationExecutor<Statistique> {
}
