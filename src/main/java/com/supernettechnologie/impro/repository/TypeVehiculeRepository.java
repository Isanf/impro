package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.TypeCarnet;
import com.supernettechnologie.impro.domain.TypeVehicule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the TypeVehicule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeVehiculeRepository extends JpaRepository<TypeVehicule, Long> {
    Optional<TypeVehicule> findByTypeCarnets(TypeCarnet typeCarnet);

    TypeVehicule findOneByLibelle(String moto);
}
