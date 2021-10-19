package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.LivraisonCarnetSouche;

import com.supernettechnologie.impro.domain.Organisation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data  repository for the LivraisonCarnetSouche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivraisonCarnetSoucheRepository extends JpaRepository<LivraisonCarnetSouche, Long> {
    List<LivraisonCarnetSouche> findByConcessionnaire(Organisation organisation);
    int countAllByDateLivraisonBetweenAndConcessionnaireId(
        ZonedDateTime dateImmatriculation,
        ZonedDateTime dateImmatriculation2,
        Long organisation_id);
}
