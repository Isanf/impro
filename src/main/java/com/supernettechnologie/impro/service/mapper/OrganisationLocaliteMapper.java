package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.OrganisationLocaliteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganisationLocalite} and its DTO {@link OrganisationLocaliteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganisationLocaliteMapper extends EntityMapper<OrganisationLocaliteDTO, OrganisationLocalite> {


    @Mapping(target = "organisations", ignore = true)
    @Mapping(target = "removeOrganisations", ignore = true)
    OrganisationLocalite toEntity(OrganisationLocaliteDTO organisationLocaliteDTO);

    default OrganisationLocalite fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrganisationLocalite organisationLocalite = new OrganisationLocalite();
        organisationLocalite.setId(id);
        return organisationLocalite;
    }
}
