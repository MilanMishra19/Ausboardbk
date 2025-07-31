package com.ausboard.ausboardbackend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ausboard.ausboardbackend.dto.ProjectRequest;
import com.ausboard.ausboardbackend.entity.Project;
import com.ausboard.ausboardbackend.entity.Users;
import com.ausboard.ausboardbackend.repository.ProjectRepository;
import com.ausboard.ausboardbackend.repository.UsersRepository;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UsersRepository usersRepository;
    public Project createProject(ProjectRequest request){
        Project project = new Project();
        project.setName(request.getName());
        project.setUrl(request.getUrl());
        project.setDescription(request.getDescription());
        project.setWebsiteCategory(request.getWebsiteCategory());
        project.setCreatedAt(LocalDateTime.now());

        Users users = usersRepository.findById(request.getCreatedBy())
        .orElseThrow(()->new RuntimeException("User not found"));
        project.setCreatedBy(users);
        return projectRepository.save(project);
    }
    public List<Project>getProjectByUser(UUID id){
        return projectRepository.findByCreatedBy_Id(id);
    }
    public Optional<Project> getProjectById(UUID projectId){
        return projectRepository.findById(projectId);
    }
}
