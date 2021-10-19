package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.InfoCommandeCarnetASoucheDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InfoCommandeCarnetASouche} and its DTO {@link InfoCommandeCarnetASoucheDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommandeCarnetSoucheMapper.class, TypeCarnetMapper.class})
public interface InfoCommandeCarnetASoucheMapper extends EntityMapper<InfoCommandeCarnetASoucheDTO, InfoCommandeCarnetASouche> {

    @Mapping(source = "commandeCarnetSouche.id", target = "commandeCarnetSoucheId")
    @Mapping(source = "typeCarnet.id", target = "typeCarnetId")
    InfoCommandeCarnetASoucheDTO toDto(InfoCommandeCarnetASouche infoCommandeCarnetASouche);

    @Mapping(source = "commandeCarnetSoucheId", target = "commandeCarnetSouche")
    @Mapping(source = "typeCarnetId", target = "typeCarnet")
    InfoCommandeCarnetASouche toEntity(InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO);

    default InfoCommandeCarnetASouche fromId(Long id) {
        if (id == null) {
            return null;
        }
        InfoCommandeCarnetASouche infoCommandeCarnetASouche = new InfoCommandeCarnetASouche();
        infoCommandeCarnetASouche.setId(id);
        return infoCommandeCarnetASouche;
    }
}
