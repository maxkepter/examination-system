package com.SpringExaminationSystem.service.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringExaminationSystem.model.dto.common.UserDTO;
import com.SpringExaminationSystem.model.entity.user.AuthInfo;
import com.SpringExaminationSystem.model.entity.user.User;
import com.SpringExaminationSystem.model.mapper.exam.AuthInfoMapper;
import com.SpringExaminationSystem.model.mapper.exam.UserMapper;
import com.SpringExaminationSystem.repository.user.AuthInfoDao;
import com.SpringExaminationSystem.repository.user.UserDao;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthInfoDao authInfoDao;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthInfoMapper authInfoMapper;

    public void editUserRole(Integer userId, Integer role) {
        AuthInfo authInfo = authInfoDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        authInfo.setRole(role);
        authInfoDao.save(authInfo);
    }

    public void deleteUser(Integer userId) {
        authInfoDao.deleteById(userId);
        userDao.deleteById(userId);
    }

    public void editUser(UserDTO userDTO) {
        Integer userId = userDTO.getUserId();
        User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user = userMapper.toEntity(userDTO);
        userDao.save(user);
    }
}
