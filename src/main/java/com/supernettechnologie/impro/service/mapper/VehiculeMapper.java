package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.VehiculeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vehicule} and its DTO {@link VehiculeDTO}.
 */
@Mapper(componentModel = "spring", uses = {LivraisonVehiculeMapper.class, TypeVehiculeMapper.class, MarqueVehiculeMapper.class, VenteMapper.class, StockMapper.class})
public interface VehiculeMapper extends EntityMapper<VehiculeDTO, Vehicule> {

    @Mapping(source = "livraisonVehicule.id", target = "livraisonVehiculeId")
    @Mapping(source = "typeVehicule.id", target = "typeVehiculeId")
    @Mapping(source = "marqueVehicule.id", target = "marqueVehiculeId")
    @Mapping(source = "vente.id", target = "venteId")
    @Mapping(source = "stock.id", target = "stockId")
    VehiculeDTO toDto(Vehicule vehicule);

    @Mapping(target = "immatriculations", ignore = true)
    @Mapping(target = "removeImmatriculation", ignore = true)
    @Mapping(target = "plaqueImmatriculations", ignore = true)
    @Mapping(target = "removePlaqueImmatriculation", ignore = true)
    @Mapping(source = "livraisonVehiculeId", target = "livraisonVehicule")
    @Mapping(source = "typeVehiculeId", target = "typeVehicule")
    @Mapping(source = "marqueVehiculeId", target = "marqueVehicule")
    @Mapping(source = "venteId", target = "vente")
    @Mapping(source = "stockId", target = "stock")
    Vehicule toEntity(VehiculeDTO vehiculeDTO);

    default Vehicule fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vehicule vehicule = new Vehicule();
        vehicule.setId(id);
        return vehicule;
    }
}
