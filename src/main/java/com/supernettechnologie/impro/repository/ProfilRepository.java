package com.supernettechnologie.impro.repository;
import com.supernettechnologie.impro.domain.Organisation;
import com.supernettechnologie.impro.domain.Profil;
import com.supernettechnologie.impro.service.dto.OrganisationDTO;
import com.supernettechnologie.impro.service.dto.ProfilDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Profil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {
    Page<Profil> findAllByOrganisationId(Long i, Pageable pageable);
    Page<Profil> findAllByOrganisationIn(List<OrganisationDTO> organisationDTOS, Pageable pageable);
    List<Profil> findAllByCreatedby(String login);
}
