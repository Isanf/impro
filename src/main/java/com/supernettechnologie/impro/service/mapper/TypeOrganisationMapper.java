package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.TypeOrganisationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeOrganisation} and its DTO {@link TypeOrganisationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategorieOrganisationMapper.class})
public interface TypeOrganisationMapper extends EntityMapper<TypeOrganisationDTO, TypeOrganisation> {

    @Mapping(source = "categorieOrganisation.id", target = "categorieOrganisationId")
    TypeOrganisationDTO toDto(TypeOrganisation typeOrganisation);

    @Mapping(target = "organisations", ignore = true)
    @Mapping(target = "removeOrganisations", ignore = true)
    @Mapping(source = "categorieOrganisationId", target = "categorieOrganisation")
    @Mapping(target = "typeActeurs", ignore = true)
    @Mapping(target = "removeTypeActeurs", ignore = true)
    TypeOrganisation toEntity(TypeOrganisationDTO typeOrganisationDTO);

    default TypeOrganisation fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeOrganisation typeOrganisation = new TypeOrganisation();
        typeOrganisation.setId(id);
        return typeOrganisation;
    }
}
