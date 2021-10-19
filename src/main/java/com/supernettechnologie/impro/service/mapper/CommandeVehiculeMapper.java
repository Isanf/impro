package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.CommandeVehiculeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommandeVehicule} and its DTO {@link CommandeVehiculeDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganisationMapper.class})
public interface CommandeVehiculeMapper extends EntityMapper<CommandeVehiculeDTO, CommandeVehicule> {

    @Mapping(source = "revendeur.id", target = "revendeurId")
    @Mapping(source = "concessionnaire.id", target = "concessionnaireId")
    CommandeVehiculeDTO toDto(CommandeVehicule commandeVehicule);

    @Mapping(target = "infoCommandeVehicules", ignore = true)
    @Mapping(target = "removeInfoCommandeVehicule", ignore = true)
    @Mapping(target = "livraisonVehicules", ignore = true)
    @Mapping(target = "removeLivraisonVehicule", ignore = true)
    @Mapping(source = "revendeurId", target = "revendeur")
    @Mapping(source = "concessionnaireId", target = "concessionnaire")
    CommandeVehicule toEntity(CommandeVehiculeDTO commandeVehiculeDTO);

    default CommandeVehicule fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommandeVehicule commandeVehicule = new CommandeVehicule();
        commandeVehicule.setId(id);
        return commandeVehicule;
    }
}
