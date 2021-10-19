package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.VehiculeTraversant;

import com.supernettechnologie.impro.service.dto.VehiculeTraversantDTO;
import com.supernettechnologie.impro.service.dto.VenteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VehiculeTraversant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculeTraversantRepository extends JpaRepository<VehiculeTraversant, Long> {
    Page<VehiculeTraversant> findByOrganisationId(Pageable pageable, Long id);
}
