package com.gohaibo.gohaibo.controller;

import com.gohaibo.gohaibo.dto.LoginDTO;
import com.gohaibo.gohaibo.dto.RegisterDTO;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.serviceint.AuthService;
import com.gohaibo.gohaibo.utility.ApiResponse;
import com.gohaibo.gohaibo.utility.JwtAuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

   private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?>  registerUser(@RequestBody RegisterDTO registerDTO){
        if(registerDTO == null){
            return ResponseEntity.badRequest().body(new ApiResponse<String>(false,
                    "unsuccessful request", "User cannot be null"));
        }

        if(!authService.registerUser(registerDTO)){
            return ResponseEntity.badRequest().body(new ApiResponse<String>(false,
                    "unsuccessful request", "registration failure"));
        }


        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse<String>(true,
                        "User registered successfully", "User registered successfully with email: " + registerDTO.getEmail()));
    }


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTO user){
        String token = authService.login(user);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}
