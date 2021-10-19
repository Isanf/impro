package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.DocIdentificationPPDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocIdentificationPP} and its DTO {@link DocIdentificationPPDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocIdentificationPPMapper extends EntityMapper<DocIdentificationPPDTO, DocIdentificationPP> {



    default DocIdentificationPP fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocIdentificationPP docIdentificationPP = new DocIdentificationPP();
        docIdentificationPP.setId(id);
        return docIdentificationPP;
    }
}
