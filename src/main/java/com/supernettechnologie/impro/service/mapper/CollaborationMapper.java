package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.CollaborationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Collaboration} and its DTO {@link CollaborationDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganisationMapper.class})
public interface CollaborationMapper extends EntityMapper<CollaborationDTO, Collaboration> {

    @Mapping(source = "revendeur.id", target = "revendeurId")
    @Mapping(source = "concessionnaire.id", target = "concessionnaireId")
    CollaborationDTO toDto(Collaboration collaboration);

    @Mapping(source = "revendeurId", target = "revendeur")
    @Mapping(source = "concessionnaireId", target = "concessionnaire")
    Collaboration toEntity(CollaborationDTO collaborationDTO);

    default Collaboration fromId(Long id) {
        if (id == null) {
            return null;
        }
        Collaboration collaboration = new Collaboration();
        collaboration.setId(id);
        return collaboration;
    }
}
