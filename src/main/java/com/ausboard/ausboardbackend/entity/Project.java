package com.ausboard.ausboardbackend.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table( name = "projects")
public class Project {
    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID projectId;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn( name = "created_by")
    private Users createdBy;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<TestCase> testCases;
    private String url;
    @Column( name = "websitecategory", nullable = false)
    private String websiteCategory;
    @Column( name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public UUID getProjectId(){
        return projectId;
    }
    public void setProjectId(UUID projectId){
        this.projectId = projectId;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public Users getCreatedBy(){
        return createdBy;
    }
    public void setCreatedBy(Users createdBy){
        this.createdBy = createdBy;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
    public String getWebsiteCategory(){
        return websiteCategory;
    }
    public void setWebsiteCategory(String websiteCategory){
        this.websiteCategory = websiteCategory;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public List<TestCase> getTestCases(){
        return testCases;
    }
    public void setTestCases(List<TestCase> testCases){
        this.testCases = testCases;
    }
}
