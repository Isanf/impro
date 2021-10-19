package com.supernettechnologie.impro.repository;
import com.supernettechnologie.impro.domain.DocIdentificationPP;
import com.supernettechnologie.impro.domain.Organisation;
import com.supernettechnologie.impro.domain.PersonnePhysique;
import org.springframework.data.jpa.repository.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the PersonnePhysique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonnePhysiqueRepository extends JpaRepository<PersonnePhysique, Long> {
    Optional<PersonnePhysique> findByUserLogin(String login);
    List<PersonnePhysique> findOneByUserLogin(String login);
    Optional<PersonnePhysique> findByOrganisation(Organisation organisation);
    Optional<PersonnePhysique> findByOrganisationId(Long id);
    PersonnePhysique findByDocIdentification(DocIdentificationPP docIdentificationPP);
}
