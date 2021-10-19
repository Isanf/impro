package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.PlaqueImmatriculationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlaqueImmatriculation} and its DTO {@link PlaqueImmatriculationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CertificatImmatriculationMapper.class, VehiculeMapper.class})
public interface PlaqueImmatriculationMapper extends EntityMapper<PlaqueImmatriculationDTO, PlaqueImmatriculation> {

    @Mapping(source = "certificatImmatriculation.id", target = "certificatImmatriculationId")
    @Mapping(source = "vehicule.id", target = "vehiculeId")
    PlaqueImmatriculationDTO toDto(PlaqueImmatriculation plaqueImmatriculation);

    @Mapping(source = "certificatImmatriculationId", target = "certificatImmatriculation")
    @Mapping(source = "vehiculeId", target = "vehicule")
    PlaqueImmatriculation toEntity(PlaqueImmatriculationDTO plaqueImmatriculationDTO);

    default PlaqueImmatriculation fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlaqueImmatriculation plaqueImmatriculation = new PlaqueImmatriculation();
        plaqueImmatriculation.setId(id);
        return plaqueImmatriculation;
    }
}
