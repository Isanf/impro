package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.PrixCertificat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PrixCertificat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrixCertificatRepository extends JpaRepository<PrixCertificat, Long> {
}
