package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.entity.User;

public interface UserService {
    User findUserProfileByJwt(String token) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(Long id) throws Exception;

    User updateUserByProjectSize(User user, int number );

}
