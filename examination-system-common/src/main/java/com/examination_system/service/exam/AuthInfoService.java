package com.examination_system.service.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examination_system.model.dto.common.AuthInfoDTO;
import com.examination_system.model.entity.user.AuthInfo;
import com.examination_system.model.mapper.exam.AuthInfoMapper;
import com.examination_system.model.mapper.exam.UserMapper;
import com.examination_system.repository.user.AuthInfoDao;

@Service
public class AuthInfoService {

    @Autowired
    AuthInfoDao authInfoDao;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AuthInfoMapper authInfoMapper;

    public void changePassword(AuthInfoDTO authInfoDTO) {
        AuthInfo authInfo = authInfoMapper.toEntity(authInfoDTO);
        authInfoDao.save(authInfo);
    }
}
