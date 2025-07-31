package com.ausboard.ausboardbackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ausboard.ausboardbackend.entity.TestCase;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TestCaseRepository extends JpaRepository<TestCase,UUID>{
    List<TestCase>findAllById(UUID id); 
    Optional<TestCase>findByPriority(String priority); 
    List<TestCase>findByStatus(String status); 
    List<TestCase>findByTitle(String title);
    List<TestCase>findByProject_ProjectId(UUID projectId);
}
