package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.CommandeVehicule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the CommandeVehicule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeVehiculeRepository extends JpaRepository<CommandeVehicule, Long> {
    Page<CommandeVehicule> findAllByConcessionnaireId(Pageable pageable, Long id);
    Page<CommandeVehicule> findAllByRevendeurId(Pageable pageable, Long id);
    Page<CommandeVehicule> findAllByConcessionnaireIdAndEstLivreeFalse(Pageable pageable, Long id);
    Optional<CommandeVehicule> findByLivraisonVehiculesId(Long id);
}
