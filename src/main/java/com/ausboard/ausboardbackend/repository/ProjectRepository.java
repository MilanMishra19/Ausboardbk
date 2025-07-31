package com.ausboard.ausboardbackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ausboard.ausboardbackend.entity.Project;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<Project,UUID>{
    List<Project>findAllByProjectId(UUID projectId);
    Optional<Project>findByName(String name);
    List<Project>findByWebsiteCategory(String websiteCategory);
    List<Project>findByCreatedBy_Id(UUID id);
}
