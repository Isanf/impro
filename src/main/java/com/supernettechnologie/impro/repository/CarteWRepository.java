package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.CarteW;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the CarteW entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarteWRepository extends JpaRepository<CarteW, Long> {
    Optional<CarteW> findByOrganisationId(Long id);
    Boolean existsByOrganisationId(Long id);
}
