package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.StatistiqueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Statistique} and its DTO {@link StatistiqueDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StatistiqueMapper extends EntityMapper<StatistiqueDTO, Statistique> {



    default Statistique fromId(Long id) {
        if (id == null) {
            return null;
        }
        Statistique statistique = new Statistique();
        statistique.setId(id);
        return statistique;
    }
}
