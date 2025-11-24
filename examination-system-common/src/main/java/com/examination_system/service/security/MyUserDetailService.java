package com.examination_system.service.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.examination_system.model.entity.user.AuthInfo;
import com.examination_system.model.entity.user.UserPrincipal;
import com.examination_system.repository.user.AuthInfoDao;

@Service

public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private AuthInfoDao authInfoDao;

    @Override
    // This method is used by Spring Security to load user details by username.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by username using the AuthInfoDao
        Optional<AuthInfo> user = Optional.ofNullable(authInfoDao.findByUserName(username));

        return new UserPrincipal(user.orElseThrow(() -> new UsernameNotFoundException(username + " not found")));
    }

}
