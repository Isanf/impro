package com.supernettechnologie.impro.service.mapper;

import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.PersonneMoraleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonneMorale} and its DTO {@link PersonneMoraleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonneMoraleMapper extends EntityMapper<PersonneMoraleDTO, PersonneMorale> {


    @Mapping(target = "immatriculations", ignore = true)
    @Mapping(target = "removeImmatriculation", ignore = true)
    @Mapping(target = "ventes", ignore = true)
    @Mapping(target = "removeVente", ignore = true)
    PersonneMorale toEntity(PersonneMoraleDTO personneMoraleDTO);

    default PersonneMorale fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonneMorale personneMorale = new PersonneMorale();
        personneMorale.setId(id);
        return personneMorale;
    }
}
