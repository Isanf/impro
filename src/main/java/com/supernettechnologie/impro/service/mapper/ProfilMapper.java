package com.supernettechnologie.impro.service.mapper;

import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.ProfilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profil} and its DTO {@link ProfilDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganisationMapper.class})
public interface ProfilMapper extends EntityMapper<ProfilDTO, Profil> {

    @Mapping(source = "organisation.id", target = "organisationId")
    ProfilDTO toDto(Profil profil);

    @Mapping(target = "personnePhysiques", ignore = true)
    @Mapping(target = "removePersonnePhysique", ignore = true)
    @Mapping(source = "organisationId", target = "organisation")
    Profil toEntity(ProfilDTO profilDTO);

    default Profil fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profil profil = new Profil();
        profil.setId(id);
        return profil;
    }
}
