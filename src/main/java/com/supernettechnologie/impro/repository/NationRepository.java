package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.Nation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Nation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NationRepository extends JpaRepository<Nation, Long> {
    Nation findNationById(Long id);
}
