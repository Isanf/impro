package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.CarnetASouche;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data  repository for the CarnetASouche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarnetASoucheRepository extends JpaRepository<CarnetASouche, Long> {
    Page<CarnetASouche> findByConcessionnaireId(Pageable pageable, Long id);
    List<CarnetASouche> findByConcessionnaireId(Long id);
    Page<CarnetASouche> findAllByTypeCarnetIdAndConcessionnaireIsNull(Pageable pageable, Long id);
    Long countAllByConcessionnaireId(Long id);
    List<CarnetASouche> findAllByDateLivraisonBetweenAndConcessionnaireId(
        ZonedDateTime dateLivraison, ZonedDateTime dateLivraison2, Long concessionnaire_id);
    int countAllByDateLivraisonBetweenAndConcessionnaireId(
        ZonedDateTime dateLivraison, ZonedDateTime dateLivraison2, Long concessionnaire_id);
}
