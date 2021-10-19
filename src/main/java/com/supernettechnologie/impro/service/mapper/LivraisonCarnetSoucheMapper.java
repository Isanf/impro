package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.LivraisonCarnetSoucheDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LivraisonCarnetSouche} and its DTO {@link LivraisonCarnetSoucheDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganisationMapper.class, CommandeCarnetSoucheMapper.class})
public interface LivraisonCarnetSoucheMapper extends EntityMapper<LivraisonCarnetSoucheDTO, LivraisonCarnetSouche> {

    @Mapping(source = "concessionnaire.id", target = "concessionnaireId")
    @Mapping(source = "supernet.id", target = "supernetId")
    @Mapping(source = "commandeCarnetSouche.id", target = "commandeCarnetSoucheId")
    LivraisonCarnetSoucheDTO toDto(LivraisonCarnetSouche livraisonCarnetSouche);

    @Mapping(target = "carnetASouches", ignore = true)
    @Mapping(target = "removeCarnetASouche", ignore = true)
    @Mapping(source = "concessionnaireId", target = "concessionnaire")
    @Mapping(source = "supernetId", target = "supernet")
    @Mapping(source = "commandeCarnetSoucheId", target = "commandeCarnetSouche")
    LivraisonCarnetSouche toEntity(LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO);

    default LivraisonCarnetSouche fromId(Long id) {
        if (id == null) {
            return null;
        }
        LivraisonCarnetSouche livraisonCarnetSouche = new LivraisonCarnetSouche();
        livraisonCarnetSouche.setId(id);
        return livraisonCarnetSouche;
    }
}
