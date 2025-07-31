package com.ausboard.ausboardbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ausboard.ausboardbackend.entity.Bugs;
import com.ausboard.ausboardbackend.service.BugsService;

@RestController
@RequestMapping("/api/bugs")
public class BugsController {
    @Autowired
    private BugsService bugsService;

    @PostMapping
    public ResponseEntity<Bugs>createNewBug(@RequestBody Bugs bugs){
        Bugs created = bugsService.createBugs(bugs);
        return ResponseEntity.ok(created);
    }
}