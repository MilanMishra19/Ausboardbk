package com.ausboard.ausboardbackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ausboard.ausboardbackend.entity.Users;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users,UUID>{
    List<Users>findAllById(UUID id);
    List<Users>findByName(String name);
    Optional<Users>findByEmail(String email);
    Optional<Users>findByRole(String role);
} 