package com.examination_system.auth.service;

import com.examination_system.auth.model.dto.response.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examination_system.auth.model.dto.common.UserDTO;
import com.examination_system.common.model.entity.user.AuthInfo;
import com.examination_system.common.model.entity.user.User;
import com.examination_system.auth.model.mapper.AuthInfoMapper;
import com.examination_system.auth.model.mapper.UserMapper;
import com.examination_system.common.repository.user.AuthInfoRepository;
import com.examination_system.common.repository.user.UserRepository;

import java.util.List;

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

    public List<ProfileResponse> findAll() {
        List<AuthInfo> authInfos = authInfoRepository.findAll();
        return authInfos.stream()
                .map(authInfo -> {
                    User user = authInfo.getUser();
                    ProfileResponse profileResponse = new ProfileResponse();
                    profileResponse.setUserId(user.getUserId());
                    profileResponse.setFirstName(user.getFirstName());
                    profileResponse.setLastName(user.getLastName());
                    profileResponse.setEmail(user.getEmail());
                    profileResponse.setUsername(authInfo.getUserName());
                    profileResponse.setRole(authInfo.getRole());
                    return profileResponse;
                })
                .toList();
    }

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

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateProfile(Long userId, String firstName, String lastName, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepository.save(user);
    }

    public User getUserByUsername(String userName) {
        AuthInfo authInfo = authInfoRepository.findByUserName(userName);
        if (authInfo == null) {
            throw new RuntimeException("User not found");
        }
        return authInfo.getUser();
    }

    public void updateProfileByUsername(String userName, String firstName, String lastName, String email) {
        AuthInfo authInfo = authInfoRepository.findByUserName(userName);
        if (authInfo == null) {
            throw new RuntimeException("User not found");
        }
        User user = authInfo.getUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepository.save(user);
    }

}
