package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.UserOtpDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserOtp} and its DTO {@link UserOtpDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserOtpMapper extends EntityMapper<UserOtpDTO, UserOtp> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    UserOtpDTO toDto(UserOtp userOtp);

    @Mapping(source = "userId", target = "user")
    UserOtp toEntity(UserOtpDTO userOtpDTO);

    default UserOtp fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserOtp userOtp = new UserOtp();
        userOtp.setId(id);
        return userOtp;
    }
}
