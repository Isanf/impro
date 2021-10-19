package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.DocIdentificationPP;

import com.supernettechnologie.impro.service.dto.DocIdentificationPPDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DocIdentificationPP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocIdentificationPPRepository extends JpaRepository<DocIdentificationPP, Long> {
    DocIdentificationPP findByNip(String nip);
    List<DocIdentificationPP> findAllByNip(String nip);
    Boolean existsByNip(String nip);
}
