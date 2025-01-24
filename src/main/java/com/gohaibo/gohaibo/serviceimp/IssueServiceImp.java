package com.gohaibo.gohaibo.serviceimp;

import com.gohaibo.gohaibo.entity.Issue;
import com.gohaibo.gohaibo.entity.Project;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.repo.IssueRepo;
import com.gohaibo.gohaibo.service.IssueService;
import com.gohaibo.gohaibo.service.ProjectService;
import com.gohaibo.gohaibo.service.UserService;
import com.gohaibo.gohaibo.utility.IssueRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImp implements IssueService {


    private final IssueRepo issueRepo;
    private final ProjectService  projectService;
    private final UserService userService;

    public IssueServiceImp(IssueRepo issueRepo, ProjectService projectService, UserService userService) {
        this.issueRepo = issueRepo;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public Issue getIssueById(Long issueID) throws Exception {
        return  issueRepo.findById(issueID).orElseThrow(()-> new Exception("Issue not found"));
    }

    @Override
    public List<Issue> getIssueByProjectID(Long projectID) throws Exception {
        return issueRepo.findByProjectID(projectID);
    }

    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issueRequest.getProjectID());
        Issue issue = new Issue();
        issue.setProjectID(issueRequest.getProjectID());
        issue.setDescription(issueRequest.getDescription());
        issue.setPriority(issueRequest.getPriority());
        issue.setStatus(issueRequest.getStatus());
        issue.setTitle(issueRequest.getTitle());
        issue.setDueDate(issueRequest.getDueDate());

        issue.setProject(project);

        return issueRepo.save(issue);


    }

    @Override
    public void deleteIssue(Long issueID, Long userID) throws Exception {
        getIssueById(issueID);
        issueRepo.deleteById(issueID);
    }

    @Override
    public Issue addUserToIssue(Long issueID, Long userID) throws Exception {
       User user = userService.findUserById(userID);
       Issue issue = getIssueById(issueID);
         issue.setAssignee(user);

       return  issueRepo.save(issue);

    }

    @Override
    public Issue updateStatus(Long issueID, String status) throws Exception {
        Issue issue = getIssueById(issueID);
        issue.setStatus(status);
        return issueRepo.save(issue);
    }
}
