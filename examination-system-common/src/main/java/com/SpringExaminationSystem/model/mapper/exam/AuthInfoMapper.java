package com.SpringExaminationSystem.model.mapper.exam;

import org.mapstruct.Mapper;

import com.SpringExaminationSystem.model.dto.common.AuthInfoDTO;
import com.SpringExaminationSystem.model.entity.user.AuthInfo;

@Mapper(componentModel = "spring")
public interface AuthInfoMapper {
    AuthInfo toEntity(AuthInfoDTO authInfoDTO);

    AuthInfoDTO toDTO(AuthInfo authInfo);
}
