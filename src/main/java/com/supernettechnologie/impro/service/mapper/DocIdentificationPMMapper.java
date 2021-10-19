package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.DocIdentificationPMDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocIdentificationPM} and its DTO {@link DocIdentificationPMDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganisationMapper.class, PersonneMoraleMapper.class})
public interface DocIdentificationPMMapper extends EntityMapper<DocIdentificationPMDTO, DocIdentificationPM> {

    @Mapping(source = "organisation.id", target = "organisationId")
    @Mapping(source = "personneMorale.id", target = "personneMoraleId")
    DocIdentificationPMDTO toDto(DocIdentificationPM docIdentificationPM);

    @Mapping(source = "organisationId", target = "organisation")
    @Mapping(source = "personneMoraleId", target = "personneMorale")
    DocIdentificationPM toEntity(DocIdentificationPMDTO docIdentificationPMDTO);

    default DocIdentificationPM fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocIdentificationPM docIdentificationPM = new DocIdentificationPM();
        docIdentificationPM.setId(id);
        return docIdentificationPM;
    }
}
