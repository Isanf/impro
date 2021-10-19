package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.InfoCommandeVehicule;
import com.supernettechnologie.impro.domain.MarqueVehicule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MarqueVehicule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarqueVehiculeRepository extends JpaRepository<MarqueVehicule, Long> {

    List<MarqueVehicule> findByInfoCommandeVehicules(InfoCommandeVehicule infoCommandeVehicule);

    MarqueVehicule findOneByLibelle(String datum);
}
