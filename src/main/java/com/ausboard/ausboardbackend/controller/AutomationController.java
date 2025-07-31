package com.ausboard.ausboardbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ausboard.ausboardbackend.entity.Automation;
import com.ausboard.ausboardbackend.service.AutomationService;

@RestController
@RequestMapping("/api/automation")
public class AutomationController {
    @Autowired
    private AutomationService automationService;

    @PostMapping
    public ResponseEntity<Automation>createNewAutomation(@RequestBody Automation automation){
        Automation created = automationService.createAutomation(automation);
        return ResponseEntity.ok(created);
    }
}

