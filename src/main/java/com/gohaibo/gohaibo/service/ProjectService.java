package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.entity.Chat;
import com.gohaibo.gohaibo.entity.Project;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.dto.ProjectRequest;
import com.gohaibo.gohaibo.exception.ResourceCannotBeCreatedException;
import com.gohaibo.gohaibo.exception.ResourceNotFoundException;

import java.util.List;

public interface ProjectService {

    Project createProject(ProjectRequest project, User user) throws ResourceCannotBeCreatedException;

    List<Project> getProjectByTeams(User user, String tag , String category) throws ResourceNotFoundException;

    Project getProjectById(Long id) throws ResourceNotFoundException;

    void deleteProject(Long id ,Long userID) throws Exception;

//    Project updateProject(Project updatedProject, Long id, Long userID) throws Exception;

    Project updateProject(ProjectRequest updatedProject, Long id) throws ResourceNotFoundException;

    void addUserToProject(Long projectID, Long userID) throws ResourceNotFoundException;

    void removeUserToProject(Long projectID, Long userID) throws ResourceNotFoundException;

    Chat getChatByProjectID(Long projectID) throws ResourceNotFoundException;

    List<Project> searchProjects(String keyword, User user) throws ResourceNotFoundException;
}
