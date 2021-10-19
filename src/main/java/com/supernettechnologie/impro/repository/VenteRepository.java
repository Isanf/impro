package com.supernettechnologie.impro.repository;
import com.supernettechnologie.impro.domain.Organisation;
import com.supernettechnologie.impro.domain.Vente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Vente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
    Page<Vente> findAllByRevendeurId(Pageable pageable, Long id);
    List<Vente> findAllByRevendeurId(Long id);
    Boolean existsByRevendeurAndAndVehiculesId(Organisation organisation, Long id);
    Optional<Vente> findByVehiculesId(Long id);
}
