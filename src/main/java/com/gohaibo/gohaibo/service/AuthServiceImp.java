package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.dto.LoginDTO;
import com.gohaibo.gohaibo.dto.RegisterDTO;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.repo.UserRepo;
import com.gohaibo.gohaibo.serviceint.AuthService;
import com.gohaibo.gohaibo.utility.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
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

        if (userRepo.findUserByEmail(registerDTO.getEmail()).isPresent() ) {
            return false;
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userRepo.save(user);
        return true;
    }

    @Override
    public String login(LoginDTO loginDTO) {

        // authenticate loginDTO
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                 loginDTO.getEmail(),
                 loginDTO.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}
