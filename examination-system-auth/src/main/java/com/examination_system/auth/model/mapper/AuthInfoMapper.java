package com.examination_system.model.mapper;

import org.mapstruct.Mapper;

import com.examination_system.model.dto.common.AuthInfoDTO;
import com.examination_system.model.entity.user.AuthInfo;

@Mapper(componentModel = "spring")
public interface AuthInfoMapper {
    AuthInfo toEntity(AuthInfoDTO authInfoDTO);

    AuthInfoDTO toDTO(AuthInfo authInfo);
}
