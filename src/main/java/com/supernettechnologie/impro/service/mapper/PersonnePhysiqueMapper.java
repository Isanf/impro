package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.PersonnePhysiqueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonnePhysique} and its DTO {@link PersonnePhysiqueDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocIdentificationPPMapper.class, UserMapper.class, OrganisationMapper.class, ProfilMapper.class})
public interface PersonnePhysiqueMapper extends EntityMapper<PersonnePhysiqueDTO, PersonnePhysique> {

    @Mapping(source = "docIdentification.id", target = "docIdentificationId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "organisation.id", target = "organisationId")
    @Mapping(source = "profil.id", target = "profilId")
    PersonnePhysiqueDTO toDto(PersonnePhysique personnePhysique);

    @Mapping(source = "docIdentificationId", target = "docIdentification")
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "immatriculations", ignore = true)
    @Mapping(target = "removeImmatriculation", ignore = true)
    @Mapping(target = "organisations", ignore = true)
    @Mapping(target = "removeOrganisation", ignore = true)
    @Mapping(target = "ventes", ignore = true)
    @Mapping(target = "removeVente", ignore = true)
    @Mapping(source = "organisationId", target = "organisation")
    @Mapping(source = "profilId", target = "profil")
    PersonnePhysique toEntity(PersonnePhysiqueDTO personnePhysiqueDTO);

    default PersonnePhysique fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonnePhysique personnePhysique = new PersonnePhysique();
        personnePhysique.setId(id);
        return personnePhysique;
    }
}
