package com.ausboard.ausboardbackend.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ausboard.ausboardbackend.dto.RegisterRequest;
import com.ausboard.ausboardbackend.entity.Users;
import com.ausboard.ausboardbackend.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @PostMapping
    public ResponseEntity<Users>createNewUser(@RequestBody RegisterRequest registerRequest){
        Users createdUsers = usersService.createUser(registerRequest);
        return ResponseEntity.ok(createdUsers);
    }
    @GetMapping("/me")
    public ResponseEntity<?>getCurrentUser(Principal principal){
        if(principal == null){
            return ResponseEntity.status(401).body("Not Logged in");
        }
        String email = principal.getName();
        Optional<Users>usersOpt = usersService.getUserByEmail(email);
        if(usersOpt.isEmpty()){
            return ResponseEntity.status(404).body("User not found");
        }
        Users users = usersOpt.get();
        return ResponseEntity.ok(Map.of(
            "id",users.getId(),
            "email",users.getEmail(),
            "name",users.getName()
        ));
    }
    @GetMapping("/")
    public ResponseEntity<List<Users>>getAllUsers(){
        return ResponseEntity.ok(usersService.getAllUsers());
    }
}
