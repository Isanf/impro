package com.supernettechnologie.impro.service.mapper;


import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.service.dto.FirstloginDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Firstlogin} and its DTO {@link FirstloginDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface FirstloginMapper extends EntityMapper<FirstloginDTO, Firstlogin> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    FirstloginDTO toDto(Firstlogin firstlogin);

    @Mapping(source = "userId", target = "user")
    Firstlogin toEntity(FirstloginDTO firstloginDTO);

    default Firstlogin fromId(Long id) {
        if (id == null) {
            return null;
        }
        Firstlogin firstlogin = new Firstlogin();
        firstlogin.setId(id);
        return firstlogin;
    }
}
