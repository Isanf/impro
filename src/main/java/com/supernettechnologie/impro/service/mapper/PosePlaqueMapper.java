package com.supernettechnologie.impro.service.mapper;

import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.PosePlaqueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PosePlaque} and its DTO {@link PosePlaqueDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganisationMapper.class})
public interface PosePlaqueMapper extends EntityMapper<PosePlaqueDTO, PosePlaque> {

    @Mapping(source = "revendeur.id", target = "revendeurId")
    PosePlaqueDTO toDto(PosePlaque posePlaque);

    @Mapping(source = "revendeurId", target = "revendeur")
    PosePlaque toEntity(PosePlaqueDTO posePlaqueDTO);

    default PosePlaque fromId(Long id) {
        if (id == null) {
            return null;
        }
        PosePlaque posePlaque = new PosePlaque();
        posePlaque.setId(id);
        return posePlaque;
    }
}
