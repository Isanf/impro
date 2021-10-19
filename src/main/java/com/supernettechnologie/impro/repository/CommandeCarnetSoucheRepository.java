package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.CommandeCarnetSouche;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

/**
 * Spring Data  repository for the CommandeCarnetSouche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeCarnetSoucheRepository extends JpaRepository<CommandeCarnetSouche, Long> {
    List<CommandeCarnetSouche> findAllByEstValideFalse(Pageable pageable);
    Page<CommandeCarnetSouche> findAllByConcessionnaireId(Pageable pageable, Long id);
    Optional<CommandeCarnetSouche> findByLivraisonCarnetSouchesId(Long id);
    Long countAllByConcessionnaireId(Long id);
}
