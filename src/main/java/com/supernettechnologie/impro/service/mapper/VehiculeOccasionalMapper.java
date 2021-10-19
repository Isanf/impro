package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.VehiculeOccasionalDTO;

import com.supernettechnologie.impro.service.dto.VehiculeTraversantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehiculeOccasional} and its DTO {@link VehiculeOccasionalDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonnePhysiqueMapper.class, PersonneMoraleMapper.class, CarteWMapper.class})
public interface VehiculeOccasionalMapper extends EntityMapper<VehiculeOccasionalDTO, VehiculeOccasional> {

    @Mapping(source = "personnePhysique.id", target = "personnePhysiqueId")
    @Mapping(source = "personneMorale.id", target = "personneMoraleId")
    @Mapping(source = "carteW.id", target = "carteWId")
    @Mapping(source = "personnePhysique", target = "personnePhysiqueDTO")
    @Mapping(source = "personneMorale", target = "personneMoraleDTO")
    @Mapping(source = "carteW", target = "carteWDTO")
    VehiculeOccasionalDTO toDto(VehiculeOccasional vehiculeOccasional);
    @Mapping(source = "personnePhysiqueId", target = "personnePhysique")
    @Mapping(source = "personneMoraleId", target = "personneMorale")
    @Mapping(source = "carteWId", target = "carteW.id")
    VehiculeOccasional toEntity(VehiculeOccasionalDTO vehiculeOccasionalDTO);

    default VehiculeOccasional fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehiculeOccasional vehiculeOccasional = new VehiculeOccasional();
        vehiculeOccasional.setId(id);
        return vehiculeOccasional;
    }
}
