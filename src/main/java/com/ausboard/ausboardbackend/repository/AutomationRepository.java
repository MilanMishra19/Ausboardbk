package com.ausboard.ausboardbackend.repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ausboard.ausboardbackend.entity.Automation;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface AutomationRepository extends JpaRepository<Automation,UUID>{
    List<Automation>findBySchedule(String schedule);
    List<Automation>findByLastRun(Date lastRun);
    List<Automation>findByProject_projectId(UUID projectId);
    List<Automation>findByStatus(String status);
}
