package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.exception.ResourceNotFoundException;

public interface UserService {
    User findUserProfileByJwt(String token) throws ResourceNotFoundException;

    User findUserByEmail(String email) throws ResourceNotFoundException;

    User findUserById(Long id) throws ResourceNotFoundException;

    User updateUserByProjectSize(User user, int number );

}
