package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.CertificatImmatriculation;

import com.supernettechnologie.impro.domain.PlaqueImmatriculation;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CertificatImmatriculation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CertificatImmatriculationRepository extends JpaRepository<CertificatImmatriculation, Long> {
    @Modifying
    @Query("update CertificatImmatriculation cert set cert.codeQr = :code where cert.id = :id")
    int updateCodeQr(@Param("code") String code,@Param("id") Long id);

    Optional<CertificatImmatriculation> findByCodeQr(String qr);
    Optional<CertificatImmatriculation> findByPlaqueImmatriculations(PlaqueImmatriculation pm);
    Boolean existsByNumero(String numb);
    Boolean existsByCarnetASoucheId(Long id);
    Long countAllByCarnetASoucheId(Long id);
    List<CertificatImmatriculation> findAllByCarnetASoucheId(Long id);
    CertificatImmatriculation findByCarnetASoucheId(Long id);
}
