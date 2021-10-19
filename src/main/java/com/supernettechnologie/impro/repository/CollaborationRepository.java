package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.Collaboration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Collaboration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollaborationRepository extends JpaRepository<Collaboration, Long> {
    List<Collaboration> findAllByRevendeurId(Long id);
    List<Collaboration> findAllByConcessionnaireId(Long id);
    Page<Collaboration> findAllByConcessionnaireId(Pageable pageable, Long id);
}
