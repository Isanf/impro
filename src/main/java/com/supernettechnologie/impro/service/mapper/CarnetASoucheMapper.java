package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.CarnetASoucheDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarnetASouche} and its DTO {@link CarnetASoucheDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganisationMapper.class, LivraisonCarnetSoucheMapper.class, TypeCarnetMapper.class})
public interface CarnetASoucheMapper extends EntityMapper<CarnetASoucheDTO, CarnetASouche> {

    @Mapping(source = "concessionnaire.id", target = "concessionnaireId")
    @Mapping(source = "livraisonCarnetSouche.id", target = "livraisonCarnetSoucheId")
    @Mapping(source = "typeCarnet.id", target = "typeCarnetId")
    CarnetASoucheDTO toDto(CarnetASouche carnetASouche);

    @Mapping(target = "certificatImmatriculations", ignore = true)
    @Mapping(target = "removeCertificatImmatriculation", ignore = true)
    @Mapping(source = "concessionnaireId", target = "concessionnaire")
    @Mapping(source = "livraisonCarnetSoucheId", target = "livraisonCarnetSouche")
    @Mapping(source = "typeCarnetId", target = "typeCarnet")
    CarnetASouche toEntity(CarnetASoucheDTO carnetASoucheDTO);

    default CarnetASouche fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarnetASouche carnetASouche = new CarnetASouche();
        carnetASouche.setId(id);
        return carnetASouche;
    }
}
