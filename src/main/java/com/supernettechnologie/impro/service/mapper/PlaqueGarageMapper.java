package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.PlaqueGarageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlaqueGarage} and its DTO {@link PlaqueGarageDTO}.
 */
@Mapper(componentModel = "spring", uses = {CarteWMapper.class})
public interface PlaqueGarageMapper extends EntityMapper<PlaqueGarageDTO, PlaqueGarage> {

    @Mapping(source = "carteW.id", target = "carteWId")
    PlaqueGarageDTO toDto(PlaqueGarage plaqueGarage);

    @Mapping(source = "carteWId", target = "carteW")
    PlaqueGarage toEntity(PlaqueGarageDTO plaqueGarageDTO);

    default PlaqueGarage fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlaqueGarage plaqueGarage = new PlaqueGarage();
        plaqueGarage.setId(id);
        return plaqueGarage;
    }
}
