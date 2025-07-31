package com.ausboard.ausboardbackend.controller;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ausboard.ausboardbackend.dto.TestRunRequest;
import com.ausboard.ausboardbackend.entity.TestRun;
import com.ausboard.ausboardbackend.service.TestRunService;

@RestController
@RequestMapping("/api/testrun")
public class TestRunController {
    @Autowired
    private TestRunService testRunService;

    @PostMapping
    public ResponseEntity<TestRun>createNewTestRun(@RequestBody TestRun testRun){
        TestRun created = testRunService.createTestRun(testRun);
        return ResponseEntity.ok(created);
    }
    @GetMapping("/project/testrun/{projectId}")
    public ResponseEntity<List<TestRunRequest>> findTestRunByProject(@PathVariable UUID projectId){
        List<TestRunRequest> find = testRunService.findTestRunByProject(projectId);
        return ResponseEntity.ok(find);
    }
    @GetMapping("/testcase/{testCaseId}")
    public ResponseEntity<List<TestRun>> findTestRunByTestcase(@RequestParam UUID testCaseId){
        List<TestRun> runs = testRunService.findTestRunByTestCase(testCaseId);
        return ResponseEntity.ok(runs);
    }
}
