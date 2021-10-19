package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.CategorieOrganisationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategorieOrganisation} and its DTO {@link CategorieOrganisationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategorieOrganisationMapper extends EntityMapper<CategorieOrganisationDTO, CategorieOrganisation> {


    @Mapping(target = "types", ignore = true)
    @Mapping(target = "removeType", ignore = true)
    CategorieOrganisation toEntity(CategorieOrganisationDTO categorieOrganisationDTO);

    default CategorieOrganisation fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategorieOrganisation categorieOrganisation = new CategorieOrganisation();
        categorieOrganisation.setId(id);
        return categorieOrganisation;
    }
}
