package com.examination_system.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.examination_system.common.model.entity.user.AuthInfo;
import com.examination_system.common.model.entity.user.UserPrincipal;
import com.examination_system.common.repository.user.AuthInfoRepository;

/**
 * User Details Service for authentication
 */
@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private AuthInfoRepository authInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthInfo> user = Optional.ofNullable(authInfoRepository.findByUserName(username));
        return new UserPrincipal(user.orElseThrow(() -> new UsernameNotFoundException(username + " not found")));
    }
}
