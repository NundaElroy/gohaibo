package com.gohaibo.gohaibo.repo;

import com.gohaibo.gohaibo.entity.Project;
import com.gohaibo.gohaibo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project,Long> {


//    List<Project> findByOwner(User user);

    List<Project> findByNameContainingAndTeamContains(String partialName, User user);//for search purposes

//    @Query("SELECT p FROM Project p join p.team t where t=:user" )
//    List<Project> findProjectByTeam(@Param("user")User user);//if you find that user return those projects related to user

    List<Project> findByTeamContainingOrOwner(User user, User owner);

    Optional<Project> findProjectById(Long id);
}
