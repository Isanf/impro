package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.OrganisationLocalite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrganisationLocalite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganisationLocaliteRepository extends JpaRepository<OrganisationLocalite, Long> {

}
