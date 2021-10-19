package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.NationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nation} and its DTO {@link NationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NationMapper extends EntityMapper<NationDTO, Nation> {



    default Nation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Nation nation = new Nation();
        nation.setId(id);
        return nation;
    }
}
