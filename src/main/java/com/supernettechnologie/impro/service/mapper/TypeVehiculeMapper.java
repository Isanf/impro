package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.TypeVehiculeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeVehicule} and its DTO {@link TypeVehiculeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeVehiculeMapper extends EntityMapper<TypeVehiculeDTO, TypeVehicule> {


    @Mapping(target = "typeCarnets", ignore = true)
    @Mapping(target = "removeTypeCarnet", ignore = true)
    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "removeVehicule", ignore = true)
    TypeVehicule toEntity(TypeVehiculeDTO typeVehiculeDTO);

    default TypeVehicule fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeVehicule typeVehicule = new TypeVehicule();
        typeVehicule.setId(id);
        return typeVehicule;
    }
}
