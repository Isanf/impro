package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.CarteWDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarteW} and its DTO {@link CarteWDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganisationMapper.class})
public interface CarteWMapper extends EntityMapper<CarteWDTO, CarteW> {

    @Mapping(source = "organisation.id", target = "organisationId")
    CarteWDTO toDto(CarteW carteW);

    @Mapping(source = "organisationId", target = "organisation")
    CarteW toEntity(CarteWDTO carteWDTO);

    default CarteW fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarteW carteW = new CarteW();
        carteW.setId(id);
        return carteW;
    }
}
