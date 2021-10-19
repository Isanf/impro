package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.VenteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vente} and its DTO {@link VenteDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganisationMapper.class, PersonnePhysiqueMapper.class, PersonneMoraleMapper.class})
public interface VenteMapper extends EntityMapper<VenteDTO, Vente> {

    @Mapping(source = "revendeur.id", target = "revendeurId")
    @Mapping(source = "personnePhysique.id", target = "personnePhysiqueId")
    @Mapping(source = "personneMorale.id", target = "personneMoraleId")
    VenteDTO toDto(Vente vente);

    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "removeVehicules", ignore = true)
    @Mapping(source = "revendeurId", target = "revendeur")
    @Mapping(source = "personnePhysiqueId", target = "personnePhysique")
    @Mapping(source = "personneMoraleId", target = "personneMorale")
    Vente toEntity(VenteDTO venteDTO);

    default Vente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vente vente = new Vente();
        vente.setId(id);
        return vente;
    }
}
