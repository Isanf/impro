package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.PrixCertificatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrixCertificat} and its DTO {@link PrixCertificatDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrixCertificatMapper extends EntityMapper<PrixCertificatDTO, PrixCertificat> {



    default PrixCertificat fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrixCertificat prixCertificat = new PrixCertificat();
        prixCertificat.setId(id);
        return prixCertificat;
    }
}
