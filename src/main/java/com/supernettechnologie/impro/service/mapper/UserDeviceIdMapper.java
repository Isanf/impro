package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.UserDeviceIdDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserDeviceId} and its DTO {@link UserDeviceIdDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserDeviceIdMapper extends EntityMapper<UserDeviceIdDTO, UserDeviceId> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    UserDeviceIdDTO toDto(UserDeviceId userDeviceId);

    @Mapping(source = "userId", target = "user")
    UserDeviceId toEntity(UserDeviceIdDTO userDeviceIdDTO);

    default UserDeviceId fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDeviceId userDeviceId = new UserDeviceId();
        userDeviceId.setId(id);
        return userDeviceId;
    }
}
