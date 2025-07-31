package com.ausboard.ausboardbackend.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ausboard.ausboardbackend.entity.Bugs;
import com.ausboard.ausboardbackend.repository.BugsRepository;

@Service
public class BugsService {
    @Autowired
    private BugsRepository bugsRepository;

    public Bugs createBugs(Bugs bugs){
        bugs.setId(UUID.randomUUID());
        bugs.setCreatedAt(LocalDateTime.now());
        return bugsRepository.save(bugs);
    }
}
