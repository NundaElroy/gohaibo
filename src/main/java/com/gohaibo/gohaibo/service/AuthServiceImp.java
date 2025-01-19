package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.dto.LoginDTO;
import com.gohaibo.gohaibo.dto.RegisterDTO;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.repo.UserRepo;
import com.gohaibo.gohaibo.serviceint.AuthService;
import com.gohaibo.gohaibo.utility.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.gohaibo.gohaibo.exception.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class  AuthServiceImp implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImp(UserRepo userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean registerUser(RegisterDTO registerDTO) {


        if (registerDTO == null || registerDTO.getEmail() == null || registerDTO.getPassword() == null || registerDTO.getFullName() == null) {
            throw new AuthenticationException("Invalid registration data");
        }

        if (userRepo.findUserByEmail(registerDTO.getEmail()).isPresent()) {
            throw new AuthenticationException("User already exists");
        }

        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setFullName(registerDTO.getFullName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userRepo.save(user);
        return true;
    }


    @Override
    public String login(LoginDTO loginDTO) {

        if (loginDTO == null) {
            throw new AuthenticationException("Invalid data") {
            };
        }
        // authenticate loginDTO
        Authentication authentication = authenticate(loginDTO.getEmail(), loginDTO.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }


    private Authentication authenticate(String email, String password) {
          return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
