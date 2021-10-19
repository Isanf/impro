package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.MarqueVehiculeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MarqueVehicule} and its DTO {@link MarqueVehiculeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MarqueVehiculeMapper extends EntityMapper<MarqueVehiculeDTO, MarqueVehicule> {


    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "removeVehicule", ignore = true)
    @Mapping(target = "infoCommandeVehicules", ignore = true)
    @Mapping(target = "removeInfoCommandeVehicule", ignore = true)
    MarqueVehicule toEntity(MarqueVehiculeDTO marqueVehiculeDTO);

    default MarqueVehicule fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarqueVehicule marqueVehicule = new MarqueVehicule();
        marqueVehicule.setId(id);
        return marqueVehicule;
    }
}
