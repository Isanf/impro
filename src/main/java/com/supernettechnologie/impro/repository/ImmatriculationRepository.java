package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.Immatriculation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Immatriculation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImmatriculationRepository extends JpaRepository<Immatriculation, Long> {
    Optional<Immatriculation> findByCertificatImmatriculationId(Long id);
    Page<Immatriculation> findAllByOrganisationId(Pageable pageable, Long id);
    List<Immatriculation> findAllByOrganisationId(Long id);
    Boolean existsByCertificatImmatriculationId(Long id);
    int countAllByDateImmatriculationBetweenAndOrganisationId(
        ZonedDateTime dateImmatriculation,
        ZonedDateTime dateImmatriculation2,
        Long organisation_id);
    int countAllByDateImmatriculationBetween(ZonedDateTime dateImmatriculation, ZonedDateTime dateImmatriculation2);
    int countAllByDateImmatriculationAndOrganisationId(ZonedDateTime dateImmatriculation, Long organisation_id);
}
