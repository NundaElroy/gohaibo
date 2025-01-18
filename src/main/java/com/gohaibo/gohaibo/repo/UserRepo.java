package com.gohaibo.gohaibo.repo;

import com.gohaibo.gohaibo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

}
