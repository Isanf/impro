package com.supernettechnologie.impro.repository;
import com.supernettechnologie.impro.domain.Organisation;
import com.supernettechnologie.impro.domain.PersonnePhysique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Organisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
    Organisation findByCommandeCSConcessionnairesId(Long id);
    Organisation findOrganisationById(Long id);
    Page<Organisation> findByTypeOrganisationId(Long id, Pageable pageable);
    List<Organisation> findAllByTypeOrganisationId(Long id);
    List<Organisation> findAllByTypeActeurId(Long id);
    List<Organisation> findAllByTypeActeur_Nom(String n);
    List<Organisation> findAllByFilsIs(Organisation organisation);
    Page<Organisation> findAllById(Long id, Pageable pageable);
    List<Organisation> findAllById(Long id);
    Page<Organisation> findAllByPereId(Long id, Pageable pageable);
    /*Optional<Organisation> findByNamesign(String namesign);*/
}
