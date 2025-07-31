package com.ausboard.ausboardbackend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ausboard.ausboardbackend.entity.Bugs;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface BugsRepository extends JpaRepository<Bugs,UUID>{
    List<Bugs>findByStatus(String status);
    List<Bugs>findBySeverity(String severity); 
    List<Bugs>findByTitle(String title);
}

