package com.gohaibo.gohaibo.serviceimp;

import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.repo.UserRepo;
import com.gohaibo.gohaibo.service.UserService;
import com.gohaibo.gohaibo.utility.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    private  final UserRepo userRepo;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImp(UserRepo userRepo, JwtTokenProvider jwtTokenProvider) {
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public User findUserProfileByJwt(String token) throws Exception {
        String email = jwtTokenProvider.getEmail(token);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        return  userRepo.findUserByEmail(email).orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public User findUserById(Long id) throws Exception {
        return userRepo.findUserById(id).orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public User updateUserByProjectSize(User user, int number) {
        user.setProjectSize(user.getProjectSize() + number);
        return userRepo.save(user);
    }
}
