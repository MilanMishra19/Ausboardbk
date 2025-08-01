package com.ausboard.ausboardbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ausboard.ausboardbackend.entity.CustomUserDetails;
import com.ausboard.ausboardbackend.entity.Users;
import com.ausboard.ausboardbackend.repository.UsersRepository;



@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new CustomUserDetails(users);
    }
}


