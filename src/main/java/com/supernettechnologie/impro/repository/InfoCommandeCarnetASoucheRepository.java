package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.InfoCommandeCarnetASouche;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the InfoCommandeCarnetASouche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoCommandeCarnetASoucheRepository extends JpaRepository<InfoCommandeCarnetASouche, Long> {

    List<InfoCommandeCarnetASouche> findByCommandeCarnetSoucheIdAndEstDeliverFalse(Long id);

    List<InfoCommandeCarnetASouche> findByCommandeCarnetSoucheId(Long id);
}
