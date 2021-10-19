package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.CertificatImmatriculationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CertificatImmatriculation} and its DTO {@link CertificatImmatriculationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CarnetASoucheMapper.class})
public interface CertificatImmatriculationMapper extends EntityMapper<CertificatImmatriculationDTO, CertificatImmatriculation> {

    @Mapping(source = "carnetASouche.id", target = "carnetASoucheId")
    CertificatImmatriculationDTO toDto(CertificatImmatriculation certificatImmatriculation);

    @Mapping(target = "plaqueImmatriculations", ignore = true)
    @Mapping(target = "removePlaqueImmatriculation", ignore = true)
    @Mapping(source = "carnetASoucheId", target = "carnetASouche")
    CertificatImmatriculation toEntity(CertificatImmatriculationDTO certificatImmatriculationDTO);

    default CertificatImmatriculation fromId(Long id) {
        if (id == null) {
            return null;
        }
        CertificatImmatriculation certificatImmatriculation = new CertificatImmatriculation();
        certificatImmatriculation.setId(id);
        return certificatImmatriculation;
    }
}
