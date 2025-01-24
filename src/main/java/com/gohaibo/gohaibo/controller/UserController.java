package com.gohaibo.gohaibo.controller;

import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User>   getUserProfile(@RequestHeader("Authorization")String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        return ResponseEntity.ok(user);
    }
}
