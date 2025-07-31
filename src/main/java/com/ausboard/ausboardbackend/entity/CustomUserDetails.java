package com.ausboard.ausboardbackend.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    private final Users users;
    public CustomUserDetails(Users users) {
        this.users = users;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_tester"));
    }
    @Override
    public String getPassword() {
        return users.getPassword();
    }
    @Override
    public String getUsername() {
        return users.getEmail();
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
        return "tester".equalsIgnoreCase(users.getRole());
    }
    public Users getUsers () {
        return users;
    }

}
