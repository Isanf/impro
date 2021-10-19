package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.LogActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LogActivity} and its DTO {@link LogActivityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LogActivityMapper extends EntityMapper<LogActivityDTO, LogActivity> {



    default LogActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        LogActivity logActivity = new LogActivity();
        logActivity.setId(id);
        return logActivity;
    }
}
