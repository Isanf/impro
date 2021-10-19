package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.InfoCommandeVehiculeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InfoCommandeVehicule} and its DTO {@link InfoCommandeVehiculeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommandeVehiculeMapper.class, MarqueVehiculeMapper.class})
public interface InfoCommandeVehiculeMapper extends EntityMapper<InfoCommandeVehiculeDTO, InfoCommandeVehicule> {

    @Mapping(source = "commandeVehicule.id", target = "commandeVehiculeId")
    @Mapping(source = "marqueVehicule.id", target = "marqueVehiculeId")
    InfoCommandeVehiculeDTO toDto(InfoCommandeVehicule infoCommandeVehicule);

    @Mapping(source = "commandeVehiculeId", target = "commandeVehicule")
    @Mapping(source = "marqueVehiculeId", target = "marqueVehicule")
    InfoCommandeVehicule toEntity(InfoCommandeVehiculeDTO infoCommandeVehiculeDTO);

    default InfoCommandeVehicule fromId(Long id) {
        if (id == null) {
            return null;
        }
        InfoCommandeVehicule infoCommandeVehicule = new InfoCommandeVehicule();
        infoCommandeVehicule.setId(id);
        return infoCommandeVehicule;
    }
}
