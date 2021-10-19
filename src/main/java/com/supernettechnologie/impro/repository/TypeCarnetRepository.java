package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.InfoCommandeCarnetASouche;
import com.supernettechnologie.impro.domain.TypeCarnet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TypeCarnet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeCarnetRepository extends JpaRepository<TypeCarnet, Long> {
    Optional<TypeCarnet> findByInfoCommandeCarnetASouches(InfoCommandeCarnetASouche infoCommandeCarnetASouche);
}
