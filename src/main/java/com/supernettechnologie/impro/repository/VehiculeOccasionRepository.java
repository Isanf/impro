package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.VehiculeOccasion;

import com.supernettechnologie.impro.domain.VehiculeTraversant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the VehiculeOccasion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculeOccasionRepository extends JpaRepository<VehiculeOccasion, Long> {
    List<VehiculeOccasion> findByOrganisationId(Long id);
}
