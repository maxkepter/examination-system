package com.examination_system.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examination_system.auth.model.dto.common.AuthInfoDTO;
import com.examination_system.common.model.entity.user.AuthInfo;
import com.examination_system.auth.model.mapper.AuthInfoMapper;
import com.examination_system.auth.model.mapper.UserMapper;
import com.examination_system.common.repository.user.AuthInfoRepository;

@Service
public class AuthInfoService {

    @Autowired
    AuthInfoRepository authInfoRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AuthInfoMapper authInfoMapper;

    public void changePassword(AuthInfoDTO authInfoDTO) {
        AuthInfo authInfo = authInfoMapper.toEntity(authInfoDTO);
        authInfoRepository.save(authInfo);
    }
}
