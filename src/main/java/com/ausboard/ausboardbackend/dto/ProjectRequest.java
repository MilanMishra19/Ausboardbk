package com.ausboard.ausboardbackend.dto;

import java.util.UUID;

public class ProjectRequest {
    private String url;
    private String name;
    private String websiteCategory;
    private UUID createdBy;
    private String description;

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public UUID getCreatedBy(){
        return createdBy;
    }
    public void setCreatedBy(UUID createdBy){
        this.createdBy = createdBy;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getWebsiteCategory(){
        return websiteCategory;
    }
    public void setWebsiteCategory(String websiteCategory){
        this.websiteCategory = websiteCategory;
    }
}
