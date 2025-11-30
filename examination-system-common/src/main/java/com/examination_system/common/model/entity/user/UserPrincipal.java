package com.examination_system.common.model.entity.user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    private AuthInfo user;

    public UserPrincipal(AuthInfo user) {
        this.user = user;
    }

    @Override
    // Returns the authorities granted to the user.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = AuthInfo.AUTHORIES[user.getRole()];
        String authority = "ROLE_" + role;
        System.out.println("=== DEBUG: UserPrincipal.getAuthorities() ===");
        System.out.println("User: " + user.getUserName());
        System.out.println("User role index: " + user.getRole());
        System.out.println("Role name: " + role);
        System.out.println("Authority being created: " + authority);
        System.out.println("=============================================");
        return Collections.singleton(new SimpleGrantedAuthority(authority));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public AuthInfo getAuthInfo() {
        return user;
    }

}
