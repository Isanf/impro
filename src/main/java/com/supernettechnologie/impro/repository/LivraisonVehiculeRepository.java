package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.LivraisonVehicule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the LivraisonVehicule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivraisonVehiculeRepository extends JpaRepository<LivraisonVehicule, Long> {
    List<LivraisonVehicule> findLivraisonVehiculesByRevendeurId(Long id);
    List<LivraisonVehicule> findLivraisonVehiculesByConcessionnaireId(Long id);
    Page<LivraisonVehicule> findAllByRevendeurId(Pageable pageable, Long id);
    List<LivraisonVehicule> findAllByRevendeurId(Long id);
    Page<LivraisonVehicule>findAllByConcessionnaireId(Pageable pageable, Long id);
    LivraisonVehicule findByRevendeurId(Long id);
    LivraisonVehicule findLivraisonVehiculeByRevendeurId(Long id);
}
