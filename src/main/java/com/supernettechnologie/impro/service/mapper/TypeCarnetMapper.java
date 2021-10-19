package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.TypeCarnetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeCarnet} and its DTO {@link TypeCarnetDTO}.
 */
@Mapper(componentModel = "spring", uses = {TypeVehiculeMapper.class})
public interface TypeCarnetMapper extends EntityMapper<TypeCarnetDTO, TypeCarnet> {

    @Mapping(source = "typeVehicule.id", target = "typeVehiculeId")
    TypeCarnetDTO toDto(TypeCarnet typeCarnet);

    @Mapping(target = "carnetSouches", ignore = true)
    @Mapping(target = "removeCarnetSouche", ignore = true)
    @Mapping(target = "infoCommandeCarnetASouches", ignore = true)
    @Mapping(target = "removeInfoCommandeCarnetASouche", ignore = true)
    @Mapping(source = "typeVehiculeId", target = "typeVehicule")
    TypeCarnet toEntity(TypeCarnetDTO typeCarnetDTO);

    default TypeCarnet fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeCarnet typeCarnet = new TypeCarnet();
        typeCarnet.setId(id);
        return typeCarnet;
    }
}
