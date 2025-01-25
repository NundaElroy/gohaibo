package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.entity.Issue;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.exception.ResourceNotFoundException;
import com.gohaibo.gohaibo.utility.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue getIssueById(Long issueID) throws ResourceNotFoundException;

    List<Issue> getIssueByProjectID(Long projectID) throws ResourceNotFoundException;

//    Issue createIssue(IssueRequest issueRequest,Long userID) throws Exception;


    Issue createIssue(IssueRequest issueRequest, User user) throws Exception;

    void   deleteIssue(Long issueID, Long userID) throws ResourceNotFoundException;


//    List<Issue>  searchIssues(String title ,String status , String priority , Long assigneeID) throws IssueException;
//
//    List<Issue>  getAssigneeForIssue(Long issueID) throws IssueException;

    Issue addUserToIssue(Long issueID, Long userID) throws ResourceNotFoundException;

    Issue updateStatus(Long issueID,String status ) throws ResourceNotFoundException;
}
