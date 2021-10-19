package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.CertificatImmatriculation;
import com.supernettechnologie.impro.domain.PlaqueImmatriculation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PlaqueImmatriculation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaqueImmatriculationRepository extends JpaRepository<PlaqueImmatriculation, Long> {
    List<PlaqueImmatriculation> findByCertificatImmatriculationId(Long id);
    Boolean existsByVehiculeId(Long id);
    Boolean existsByNumeroImmatriculation(String imm);
    Boolean existsByNumeroSerie(String imm);
    Page<PlaqueImmatriculation> findAllByVehiculeNotNull(Pageable pageable);
    List<PlaqueImmatriculation> findAllByNumeroImmatriculation(String nb);
    List<PlaqueImmatriculation> findAllByCodeQR(String qr);
    List<PlaqueImmatriculation> findAllByVehiculeId(Long id);
    Boolean existsByCertificatImmatriculationId(Long id);
    List<PlaqueImmatriculation> findAllByNumeroImmatriculationContains(String indice);

    List<PlaqueImmatriculation> findByNumeroImmatriculation(String numeroImmatriculation);
    Optional<PlaqueImmatriculation> findTopByNumeroImmatriculationContainsOrderByIdDesc(String ind);
}
