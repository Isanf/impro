package com.supernettechnologie.impro.repository;
import com.supernettechnologie.impro.domain.PersonneMorale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the PersonneMorale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonneMoraleRepository extends JpaRepository<PersonneMorale, Long> {
    PersonneMorale findByNumeroIFU(String ifu);
}
