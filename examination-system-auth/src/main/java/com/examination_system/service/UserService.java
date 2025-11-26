package com.examination_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examination_system.model.dto.common.UserDTO;
import com.examination_system.model.entity.user.AuthInfo;
import com.examination_system.model.entity.user.User;
import com.examination_system.model.mapper.exam.AuthInfoMapper;
import com.examination_system.model.mapper.exam.UserMapper;
import com.examination_system.repository.user.AuthInfoRepository;
import com.examination_system.repository.user.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthInfoRepository authInfoRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthInfoMapper authInfoMapper;

    public void editUserRole(Long userId, Integer role) {
        AuthInfo authInfo = authInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        authInfo.setRole(role);
        authInfoRepository.save(authInfo);
    }

    public void deleteUser(Long userId) {
        authInfoRepository.deleteById(userId);
        userRepository.deleteById(userId);
    }

    public void editUser(UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user = userMapper.toEntity(userDTO);
        userRepository.save(user);
    }

}
