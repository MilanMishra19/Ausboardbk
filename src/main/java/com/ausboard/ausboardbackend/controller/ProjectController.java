package com.ausboard.ausboardbackend.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ausboard.ausboardbackend.dto.ProjectRequest;
import com.ausboard.ausboardbackend.entity.Project;
import com.ausboard.ausboardbackend.entity.Users;
import com.ausboard.ausboardbackend.repository.ProjectRepository;
import com.ausboard.ausboardbackend.repository.UsersRepository;
import com.ausboard.ausboardbackend.service.ProjectService;

@RestController
@RequestMapping("/api/project")

public class ProjectController {

    private final ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UsersRepository usersRepository;

    ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @PostMapping
    public ResponseEntity<Project>createNewProject(@RequestBody ProjectRequest request){
        Project created = projectService.createProject(request);
        return ResponseEntity.ok(created);
    }
    @GetMapping("/my-projects")
    public ResponseEntity<List<Project>>getMyProject(Principal principal){
        String email = principal.getName();
        Users user = usersRepository.findByEmail(email)
        .orElseThrow(()->new RuntimeException("User not found or not logged in"));
        List<Project> projects = projectService.getProjectByUser(user.getId());
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable UUID projectId){
        Optional<Project> project = projectService.getProjectById(projectId);
        if(project.isPresent()) return ResponseEntity.ok(project.get());
        else return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable UUID projectId){
        if(!projectRepository.existsById(projectId)){
            return ResponseEntity.notFound().build();
        }
        projectRepository.deleteById(projectId);
        return ResponseEntity.ok("Project successfully deleted");

    }

}
