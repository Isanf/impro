package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.LivraisonVehiculeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LivraisonVehicule} and its DTO {@link LivraisonVehiculeDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganisationMapper.class, CommandeVehiculeMapper.class})
public interface LivraisonVehiculeMapper extends EntityMapper<LivraisonVehiculeDTO, LivraisonVehicule> {

    @Mapping(source = "revendeur.id", target = "revendeurId")
    @Mapping(source = "concessionnaire.id", target = "concessionnaireId")
    @Mapping(source = "commandeVehicule.id", target = "commandeVehiculeId")
    LivraisonVehiculeDTO toDto(LivraisonVehicule livraisonVehicule);

    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "removeVehicule", ignore = true)
    @Mapping(source = "revendeurId", target = "revendeur")
    @Mapping(source = "concessionnaireId", target = "concessionnaire")
    @Mapping(source = "commandeVehiculeId", target = "commandeVehicule")
    LivraisonVehicule toEntity(LivraisonVehiculeDTO livraisonVehiculeDTO);

    default LivraisonVehicule fromId(Long id) {
        if (id == null) {
            return null;
        }
        LivraisonVehicule livraisonVehicule = new LivraisonVehicule();
        livraisonVehicule.setId(id);
        return livraisonVehicule;
    }
}
