package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.VehiculeOccasional;

import com.supernettechnologie.impro.domain.VehiculeTraversant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VehiculeOccasional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculeOccasionalRepository extends JpaRepository<VehiculeOccasional, Long> {
    Page<VehiculeOccasional> findByOrganisationId(Pageable pageable, Long id);
}
