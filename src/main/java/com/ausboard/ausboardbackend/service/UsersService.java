package com.ausboard.ausboardbackend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ausboard.ausboardbackend.dto.RegisterRequest;
import com.ausboard.ausboardbackend.entity.Users;
import com.ausboard.ausboardbackend.repository.UsersRepository;

@Service
public class UsersService {
    @Autowired
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public  UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Users createUser(RegisterRequest registerRequest){
        Users users = new Users();
        users.setName(registerRequest.getName());
        users.setEmail(registerRequest.getEmail());
        users.setRole("tester");
        users.setCreatedAt(LocalDateTime.now());

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        users.setPassword(hashedPassword);
        return usersRepository.save(users);
    }
    public List<Users>getAllUsers(){
        return usersRepository.findAll();
    }
    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
