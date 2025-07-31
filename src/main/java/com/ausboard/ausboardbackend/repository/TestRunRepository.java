package com.ausboard.ausboardbackend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ausboard.ausboardbackend.entity.TestRun;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TestRunRepository extends JpaRepository<TestRun,UUID>{
    List<TestRun>findByTestCase_Id(UUID testCaseId);
    List<TestRun>findByStatus(String status); 
    List<TestRun>findByRunAtBetween(LocalDateTime start,LocalDateTime end);
    @Query("SELECT tr FROM TestRun tr "+"JOIN tr.testCase tc "+"JOIN tc.project p "+"WHERE p.id = :projectId")
    List<TestRun> findTestRunByProjectId(@Param("projectId") UUID projectId);
}
