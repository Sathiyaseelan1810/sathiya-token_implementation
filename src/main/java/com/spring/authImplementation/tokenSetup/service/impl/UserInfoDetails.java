package com.spring.authImplementation.tokenSetup.service.impl;

import com.spring.authImplementation.tokenSetup.daosEntity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserInfoDetails implements UserDetails {

    private String userName; // Changed from 'name' to 'username' for clarity
    private String password;
    private List<GrantedAuthority> authorities;

    //Constructor::
    public UserInfoDetails(UserInfo userInfo) {
        this.userName = userInfo.getEmail();// Assuming 'name' is used as 'username'
        this.password = userInfo.getPassword();
        this.authorities = Arrays
                .stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement your logic if you need this
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement your logic if you need this
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement your logic if you need this
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement your logic if you need this
    }

}