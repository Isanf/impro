package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.CommandeCarnetSoucheDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommandeCarnetSouche} and its DTO {@link CommandeCarnetSoucheDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganisationMapper.class})
public interface CommandeCarnetSoucheMapper extends EntityMapper<CommandeCarnetSoucheDTO, CommandeCarnetSouche> {

    @Mapping(source = "concessionnaire.id", target = "concessionnaireId")
    @Mapping(source = "supernet.id", target = "supernetId")
    CommandeCarnetSoucheDTO toDto(CommandeCarnetSouche commandeCarnetSouche);

    @Mapping(target = "livraisonCarnetSouches", ignore = true)
    @Mapping(target = "removeLivraisonCarnetSouche", ignore = true)
    @Mapping(target = "infoCommandeCarnetASouches", ignore = true)
    @Mapping(target = "removeInfoCommandeCarnetASouche", ignore = true)
    @Mapping(source = "concessionnaireId", target = "concessionnaire")
    @Mapping(source = "supernetId", target = "supernet")
    CommandeCarnetSouche toEntity(CommandeCarnetSoucheDTO commandeCarnetSoucheDTO);

    default CommandeCarnetSouche fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommandeCarnetSouche commandeCarnetSouche = new CommandeCarnetSouche();
        commandeCarnetSouche.setId(id);
        return commandeCarnetSouche;
    }
}
