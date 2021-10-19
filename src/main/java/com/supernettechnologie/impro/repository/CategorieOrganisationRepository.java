package com.supernettechnologie.impro.repository;
import com.supernettechnologie.impro.domain.CategorieOrganisation;
import com.supernettechnologie.impro.domain.enumeration.TypeCategorieOrganisation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the CategorieOrganisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieOrganisationRepository extends JpaRepository<CategorieOrganisation, Long> {
    CategorieOrganisation findByTypeCategorieOrganisation(TypeCategorieOrganisation typeCategorieOrganisation);
}
