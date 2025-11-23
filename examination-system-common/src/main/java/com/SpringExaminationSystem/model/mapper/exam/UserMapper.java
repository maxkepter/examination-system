package com.SpringExaminationSystem.model.mapper.exam;

import org.mapstruct.Mapper;

import com.SpringExaminationSystem.model.dto.common.UserDTO;
import com.SpringExaminationSystem.model.entity.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);

    UserDTO toDTO(User user);
}
