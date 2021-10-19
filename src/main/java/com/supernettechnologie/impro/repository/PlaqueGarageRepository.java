package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.PlaqueGarage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the PlaqueGarage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaqueGarageRepository extends JpaRepository<PlaqueGarage, Long>, JpaSpecificationExecutor<PlaqueGarage> {
    Page<PlaqueGarage> findAllByCarteWId(Long id, Pageable pageable);
    Optional<PlaqueGarage> findByCodeQrPlaque(String code);
}
