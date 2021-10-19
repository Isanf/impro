package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.VehiculeTraversantDTO;

import com.supernettechnologie.impro.service.dto.VenteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehiculeTraversant} and its DTO {@link VehiculeTraversantDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonnePhysiqueMapper.class, PersonneMoraleMapper.class})
public interface VehiculeTraversantMapper extends EntityMapper<VehiculeTraversantDTO, VehiculeTraversant> {


    @Mapping(source = "personnePhysique.id", target = "personnePhysiqueId")
    @Mapping(source = "personneMorale.id", target = "personneMoraleId")
    @Mapping(source = "personnePhysique", target = "personnePhysiqueDTO")
    @Mapping(source = "personneMorale", target = "personneMoraleDTO")
    VehiculeTraversantDTO toDto(VehiculeTraversant vehiculeTraversant);
    @Mapping(source = "personnePhysiqueId", target = "personnePhysique")
    @Mapping(source = "personneMoraleId", target = "personneMorale")
    VehiculeTraversant toEntity(VehiculeTraversantDTO vehiculeTraversantDTO);

    default VehiculeTraversant fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehiculeTraversant vehiculeTraversant = new VehiculeTraversant();
        vehiculeTraversant.setId(id);
        return vehiculeTraversant;
    }
}
