package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.entity.Chat;
import com.gohaibo.gohaibo.entity.Project;
import com.gohaibo.gohaibo.entity.User;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project, User user) throws Exception;

    List<Project> getProjectByTeams(User user, String tag , String category) throws Exception;

    Project getProjectById(Long id) throws Exception;

    void deleteProject(Long id ,Long userID) throws Exception;

//    Project updateProject(Project updatedProject, Long id, Long userID) throws Exception;

    Project updateProject(Project updatedProject, Long id) throws Exception;

    void addUserToProject(Long projectID, Long userID) throws Exception;

    void removeUserToProject(Long projectID, Long userID) throws Exception;

    Chat getChatByProjectID(Long projectID) throws Exception;

    List<Project> searchProjects(String keyword, User user) throws Exception;
}
