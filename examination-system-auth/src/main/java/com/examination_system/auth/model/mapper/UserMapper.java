package com.examination_system.auth.model.mapper;

import org.mapstruct.Mapper;

import com.examination_system.auth.model.dto.common.UserDTO;
import com.examination_system.common.model.entity.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);

    UserDTO toDTO(User user);
}
