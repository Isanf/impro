package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.OrganisationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organisation} and its DTO {@link OrganisationDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganisationLocaliteMapper.class, TypeOrganisationMapper.class, PersonnePhysiqueMapper.class, TypeActeurMapper.class})
public interface OrganisationMapper extends EntityMapper<OrganisationDTO, Organisation> {

    @Mapping(source = "organisationLocalite.id", target = "organisationLocaliteId")
    @Mapping(source = "pere.id", target = "pereId")
    @Mapping(source = "typeOrganisation.id", target = "typeOrganisationId")
    @Mapping(source = "gerant.id", target = "gerantId")
    @Mapping(source = "typeActeur.id", target = "typeActeurId")
    OrganisationDTO toDto(Organisation organisation);

    @Mapping(target = "carnetASouches", ignore = true)
    @Mapping(target = "removeCarnetASouche", ignore = true)
    @Mapping(target = "collaborationsRevendeurs", ignore = true)
    @Mapping(target = "removeCollaborationsRevendeur", ignore = true)
    @Mapping(target = "collaborationsConcessionnaires", ignore = true)
    @Mapping(target = "removeCollaborationsConcessionnaire", ignore = true)
    @Mapping(target = "commandeCSConcessionnaires", ignore = true)
    @Mapping(target = "removeCommandeCSConcessionnaire", ignore = true)
    @Mapping(target = "commandeCSRevendeurs", ignore = true)
    @Mapping(target = "removeCommandeCSRevendeur", ignore = true)
    @Mapping(target = "commandeVRevendeurs", ignore = true)
    @Mapping(target = "removeCommandeVRevendeur", ignore = true)
    @Mapping(target = "commandeVConcessionnaires", ignore = true)
    @Mapping(target = "removeCommandeVConcessionnaire", ignore = true)
    @Mapping(target = "immatriculations", ignore = true)
    @Mapping(target = "removeImmatriculation", ignore = true)
    @Mapping(target = "livraisonCSConcessionnaires", ignore = true)
    @Mapping(target = "removeLivraisonCSConcessionnaire", ignore = true)
    @Mapping(target = "livraisonCSSupernets", ignore = true)
    @Mapping(target = "removeLivraisonCSSupernet", ignore = true)
    @Mapping(target = "livraisonVRevendeurs", ignore = true)
    @Mapping(target = "removeLivraisonVRevendeur", ignore = true)
    @Mapping(target = "livraisonVConcessionnaires", ignore = true)
    @Mapping(target = "removeLivraisonVConcessionnaire", ignore = true)
    @Mapping(target = "fils", ignore = true)
    @Mapping(target = "removeFils", ignore = true)
    @Mapping(target = "personnePhysiques", ignore = true)
    @Mapping(target = "removePersonnePhysique", ignore = true)
    @Mapping(target = "posePlaques", ignore = true)
    @Mapping(target = "removePosePlaque", ignore = true)
    @Mapping(target = "profils", ignore = true)
    @Mapping(target = "removeProfils", ignore = true)
    @Mapping(target = "stocks", ignore = true)
    @Mapping(target = "removeStock", ignore = true)
    @Mapping(target = "ventes", ignore = true)
    @Mapping(target = "removeVente", ignore = true)
    @Mapping(source = "organisationLocaliteId", target = "organisationLocalite")
    @Mapping(source = "pereId", target = "pere")
    @Mapping(source = "typeOrganisationId", target = "typeOrganisation")
    @Mapping(source = "gerantId", target = "gerant")
    @Mapping(source = "typeActeurId", target = "typeActeur")
    Organisation toEntity(OrganisationDTO organisationDTO);

    default Organisation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Organisation organisation = new Organisation();
        organisation.setId(id);
        return organisation;
    }
}
