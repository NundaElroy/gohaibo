package com.gohaibo.gohaibo.controller;


import com.gohaibo.gohaibo.entity.Chat;
import com.gohaibo.gohaibo.entity.Project;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.service.ProjectService;
import com.gohaibo.gohaibo.service.UserService;
import com.gohaibo.gohaibo.utility.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;



    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(@RequestParam(required = false)String category,
                                                     @RequestParam(required = false)String tag,
                                                     @RequestHeader("Authorization")String token) throws Exception {


        User user = userService.findUserProfileByJwt(token);
        List<Project> projects = projectService.getProjectByTeams(user, category, tag);

        return new  ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectID}")
    public ResponseEntity<Project> getProject(@PathVariable Long projectID) throws Exception {
        Project project = projectService.getProjectById(projectID);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project, @RequestHeader("Authorization")String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        Project newProject = projectService.createProject(project, user);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @PatchMapping("/{projectID}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectID ,
                                                 @RequestBody Project project
                                                 ) throws Exception {

        Project updatedProject = projectService.updateProject( project , projectID);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectID}")
    public ResponseEntity<ApiResponse<Object>> deleteProject(@PathVariable Long projectID,
                                                             @RequestHeader("Authorization")String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        projectService.deleteProject(projectID, user.getId());

        return new ResponseEntity<>(new ApiResponse<>(true,"project "+ projectID + "deleted",
                "operation success"),
                HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Project>> searchProjects(@RequestParam(required = false)String keyword,
                                                     @RequestHeader("Authorization")String token) throws Exception {


        User user = userService.findUserProfileByJwt(token);
        List<Project> projects = projectService.searchProjects(keyword, user);

        return new  ResponseEntity<>(projects, HttpStatus.OK);
    }


    @GetMapping("/{projectID}")
    public ResponseEntity<Chat> getChatByProjectID(@PathVariable Long projectID) throws Exception {
        Chat chat = projectService.getChatByProjectID(projectID);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }


}
