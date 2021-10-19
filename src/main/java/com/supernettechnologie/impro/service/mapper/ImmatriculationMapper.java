package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.ImmatriculationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Immatriculation} and its DTO {@link ImmatriculationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CertificatImmatriculationMapper.class, OrganisationMapper.class, PersonnePhysiqueMapper.class, PersonneMoraleMapper.class, VehiculeMapper.class})
public interface ImmatriculationMapper extends EntityMapper<ImmatriculationDTO, Immatriculation> {

    @Mapping(source = "certificatImmatriculation.id", target = "certificatImmatriculationId")
    @Mapping(source = "organisation.id", target = "organisationId")
    @Mapping(source = "personnePhysique.id", target = "personnePhysiqueId")
    @Mapping(source = "personneMorale.id", target = "personneMoraleId")
    @Mapping(source = "vehicule.id", target = "vehiculeId")
    ImmatriculationDTO toDto(Immatriculation immatriculation);

    @Mapping(source = "certificatImmatriculationId", target = "certificatImmatriculation")
    @Mapping(source = "organisationId", target = "organisation")
    @Mapping(source = "personnePhysiqueId", target = "personnePhysique")
    @Mapping(source = "personneMoraleId", target = "personneMorale")
    @Mapping(source = "vehiculeId", target = "vehicule")
    Immatriculation toEntity(ImmatriculationDTO immatriculationDTO);

    default Immatriculation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Immatriculation immatriculation = new Immatriculation();
        immatriculation.setId(id);
        return immatriculation;
    }
}
