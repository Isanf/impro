package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.DocIdentificationPM;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the DocIdentificationPM entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocIdentificationPMRepository extends JpaRepository<DocIdentificationPM, Long> {
    DocIdentificationPM findByOrganisationId(Long id);
    Optional<DocIdentificationPM> findByPersonneMoraleId(Long id);
    DocIdentificationPM findByNumeroIFUOrNumeroRCCM(String ifu, String rccm);
    DocIdentificationPM findByNumeroIFU(String ifu);

}
