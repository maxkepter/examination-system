package com.SpringExaminationSystem.service.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringExaminationSystem.model.dto.common.AuthInfoDTO;
import com.SpringExaminationSystem.model.entity.user.AuthInfo;
import com.SpringExaminationSystem.model.mapper.exam.AuthInfoMapper;
import com.SpringExaminationSystem.model.mapper.exam.UserMapper;
import com.SpringExaminationSystem.repository.user.AuthInfoDao;

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
