package com.gohaibo.gohaibo.repo;

import com.gohaibo.gohaibo.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepo extends JpaRepository<Issue, Long> {


    List<Issue> findByProjectID(Long projectID);
}
