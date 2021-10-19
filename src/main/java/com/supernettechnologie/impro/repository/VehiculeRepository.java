package com.supernettechnologie.impro.repository;
import com.supernettechnologie.impro.domain.Vehicule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Vehicule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    List<Vehicule> findByMarqueVehiculeIdAndLivraisonVehiculeIsNotNull(Long id);
    List<Vehicule> findAllByLivraisonVehiculeId(Long id);
    List<Vehicule> findAllByVenteId(Long id);
    Optional<Vehicule> findByVenteId(Long id);
    Optional<Vehicule> findByNumeroChassis(String chassis);
    List<Vehicule> findAllByVenteIdAndStockIsNull(Long id);
    Page<Vehicule> findAllByLivraisonVehiculeIsNotNull(Pageable pageable);
    Page<Vehicule> findAllByVenteIsNotNull(Pageable pageable);
    Page<Vehicule> findAllByLivraisonVehiculeIsNullAndVenteIsNull(Pageable pageable);
    Vehicule findByPlaqueImmatriculationsId(Long id);
    List<Vehicule> findAllByStockId(Long id);
    Vehicule findOneByNumeroChassis(String chassis);
}
