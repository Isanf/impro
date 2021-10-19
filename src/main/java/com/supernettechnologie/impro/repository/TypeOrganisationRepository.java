package com.supernettechnologie.impro.repository;
import com.supernettechnologie.impro.domain.TypeOrganisation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;


/**
 * Spring Data  repository for the TypeOrganisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeOrganisationRepository extends JpaRepository<TypeOrganisation, Long> {
    TypeOrganisation findByCategorieOrganisationIdAndNiveau(Long categorieOrganisationId, Integer niveau);
    TypeOrganisation findByCategorieOrganisationId(Long categorieOrganisationId);
}
