package com.supernettechnologie.impro.repository;
import com.supernettechnologie.impro.domain.TypeActeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the TypeActeur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeActeurRepository extends JpaRepository<TypeActeur, Long> {
    Optional<TypeActeur> findByNom(String typ);

}
