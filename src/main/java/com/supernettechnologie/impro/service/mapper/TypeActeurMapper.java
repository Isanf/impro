package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.TypeActeurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeActeur} and its DTO {@link TypeActeurDTO}.
 */
@Mapper(componentModel = "spring", uses = {TypeOrganisationMapper.class})
public interface TypeActeurMapper extends EntityMapper<TypeActeurDTO, TypeActeur> {


    @Mapping(target = "organisations", ignore = true)
    @Mapping(target = "removeOrganisations", ignore = true)
    @Mapping(target = "removeTypeOrganisations", ignore = true)
    TypeActeur toEntity(TypeActeurDTO typeActeurDTO);

    default TypeActeur fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeActeur typeActeur = new TypeActeur();
        typeActeur.setId(id);
        return typeActeur;
    }
}
