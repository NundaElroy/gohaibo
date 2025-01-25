package com.gohaibo.gohaibo.controller;


import com.gohaibo.gohaibo.entity.Issue;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.exception.ResourceNotFoundException;
import com.gohaibo.gohaibo.service.IssueService;
import com.gohaibo.gohaibo.service.UserService;
import com.gohaibo.gohaibo.utility.ApiResponse;
import com.gohaibo.gohaibo.dto.IssueDTO;
import com.gohaibo.gohaibo.utility.IssueRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    private final IssueService issueService;
    private final UserService userService;


    public IssueController(IssueService issueService, UserService userService) {
        this.issueService = issueService;
        this.userService = userService;
    }


    @GetMapping("/{issueID}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueID) throws ResourceNotFoundException {
        Issue issue = issueService.getIssueById(issueID);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    @GetMapping("/project/{projectID}")
    public ResponseEntity<List<Issue>> getIssueByProjectID(@PathVariable Long projectID) throws ResourceNotFoundException {
        List<Issue> issue = issueService.getIssueByProjectID(projectID);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<IssueDTO>> createIssue(@RequestBody IssueRequest issueRequest, @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);


        Issue issue = issueService.createIssue(issueRequest, user);

        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(issue.getId());
        issueDTO.setTitle(issue.getTitle());
        issueDTO.setDescription(issue.getDescription());
        issueDTO.setStatus(issue.getStatus());
        issueDTO.setProjectID(issue.getProjectID());
        issueDTO.setPriority(issue.getPriority());
        issueDTO.setDueDate(issue.getDueDate());
        issueDTO.setProject(issue.getProject());
        issueDTO.setAssignee(issue.getAssignee());
        issueDTO.setTags(issue.getTags());

        return new ResponseEntity<>(new ApiResponse<>(true,"issue created",issueDTO), HttpStatus.CREATED);





    }

    @DeleteMapping("/{issueID}")
    public ResponseEntity<ApiResponse<Object>> deleteIssue(@PathVariable Long issueID, @RequestHeader("Authorization") String token) throws ResourceNotFoundException{
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueID, user.getId());
        return new ResponseEntity<>(new ApiResponse<>(true, "issue deleted", "Successful operation"), HttpStatus.OK);
    }

    @PatchMapping("/{issueID}/assignee/{userID}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueID, @PathVariable Long userID) throws ResourceNotFoundException {
        Issue issue = issueService.addUserToIssue(issueID, userID);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    @PatchMapping("/{issueID}/status/{status}")
    public ResponseEntity<Issue> updateStatus(@PathVariable Long issueID, @PathVariable String status) throws ResourceNotFoundException {
        Issue issue = issueService.updateStatus(issueID, status);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }






}
