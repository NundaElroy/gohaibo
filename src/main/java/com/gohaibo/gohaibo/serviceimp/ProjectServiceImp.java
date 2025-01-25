package com.gohaibo.gohaibo.serviceimp;

import com.gohaibo.gohaibo.entity.Chat;
import com.gohaibo.gohaibo.entity.Project;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.repo.ProjectRepo;
import com.gohaibo.gohaibo.service.ChatService;
import com.gohaibo.gohaibo.service.ProjectService;
import com.gohaibo.gohaibo.service.UserService;
import com.gohaibo.gohaibo.dto.ProjectRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImp implements ProjectService {

    private final ProjectRepo projectRepo;
    private final UserService userService;
    private final ChatService chatService;

    public ProjectServiceImp(ProjectRepo projectRepo, UserService userService, ChatService chatService) {
        this.projectRepo = projectRepo;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Transactional
    @Override
    public Project createProject(ProjectRequest project, User user) throws Exception {
        Project createdProject = new Project();
        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setDescription(project.getDescription());
        createdProject.setCategory(project.getCategory());
        createdProject.getTeam().add(user);

        Project savedProject = projectRepo.save(createdProject);

        Chat chat = new Chat();
        chat.setProject(savedProject);

        Chat projectChat = chatService.createChat(chat);

        return savedProject;

    }



   /*
   *so this function is being used for basic search functionality
   * we first get the projects that the user is a part of as an owner or part of the team
   * then we filter the projects based on the category and tag
   */
    @Override
    public List<Project> getProjectByTeams(User user, String tag, String category) throws Exception {
        //for filtering purposes we can use the team or owner of the project
        List<Project> projects = projectRepo.findByTeamContainingOrOwner(user,user);

        if(category != null){
            projects = projects.stream()
                       .filter((project) -> project.getCategory().equals(category))
                       .collect(Collectors.toList());
        }

        if(tag != null){
            projects = projects.stream()
                    .filter((project) -> project.getTags().contains(tag))
                    .collect(Collectors.toList());
        }

        return projects;

    }

    @Override
    public Project getProjectById(Long id) throws Exception {
        return projectRepo.findProjectById(id).orElseThrow(() -> new Exception("Project not found"));
    }

    @Override
    public void deleteProject(Long id, Long userID) throws Exception {
        projectRepo.deleteById(id);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = projectRepo.findProjectById(id).orElseThrow(() -> new Exception("Project not found"));
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());

        return projectRepo.save(project);
    }

    @Transactional
    @Override
    public void addUserToProject(Long projectID, Long userID) throws Exception {
        Project project = projectRepo.findProjectById(projectID).orElseThrow(() -> new Exception("Project not found"));
        User user = userService.findUserById(userID);

        if(!project.getTeam().contains(user)){
            project.getTeam().add(user);
            project.getChat().getUsers().add(user);
        }

        projectRepo.save(project);

    }

    @Override
    public void removeUserToProject(Long projectID, Long userID) throws Exception {
        Project project = projectRepo.findProjectById(projectID).orElseThrow(() -> new Exception("Project not found"));
        User user = userService.findUserById(userID);

        if(project.getTeam().contains(user)){
            project.getTeam().remove(user);
            project.getChat().getUsers().remove(user);
        }

        projectRepo.save(project);

    }

    @Override
    public Chat getChatByProjectID(Long projectID) throws Exception {
        Project project = projectRepo.findProjectById(projectID).orElseThrow(() -> new Exception("Project not found"));
        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {

        //so this is the wildcard incase it contains the keyword
        String partialName = "%" + keyword + "%";
        return projectRepo.findByNameContainingAndTeamContains(partialName,user);
    }
}
