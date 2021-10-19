package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.CommandeCarnetSouche;
import com.supernettechnologie.impro.domain.CommandeVehicule;
import com.supernettechnologie.impro.domain.InfoCommandeCarnetASouche;
import com.supernettechnologie.impro.domain.InfoCommandeVehicule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the InfoCommandeVehicule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoCommandeVehiculeRepository extends JpaRepository<InfoCommandeVehicule, Long> {
    List<InfoCommandeVehicule> findByCommandeVehiculeId(Long id);
    List<InfoCommandeVehicule> findByCommandeVehicule(CommandeVehicule commandeVehicule);
}
