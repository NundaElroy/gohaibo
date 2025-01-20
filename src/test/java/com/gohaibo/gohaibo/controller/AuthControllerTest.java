package com.gohaibo.gohaibo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gohaibo.gohaibo.dto.LoginDTO;
import com.gohaibo.gohaibo.dto.RegisterDTO;
import com.gohaibo.gohaibo.exception.AuthenticationException;
import com.gohaibo.gohaibo.serviceint.AuthService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;


import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    private MockMvc mockMvc;

    private AuthController authController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        authController = new AuthController(authService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void successful_registerUser() throws Exception {
        //given
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setFullName("John Doe");
        registerDTO.setEmail("johndoe@example.com");
        registerDTO.setPassword("password");

        //when
        when(authService.registerUser(registerDTO)).thenReturn(true);

        //then
        mockMvc.perform(post("/auth/register")
                .contentType("application/json")
                .accept("application/json")
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Registration successful")))
                .andExpect(jsonPath("$.data", is("User registered successfully with email: " + registerDTO.getEmail())));



    }


    @Test
    void registerUser_Failure() throws Exception {

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setFullName(null);
        registerDTO.setEmail(null);
        registerDTO.setPassword(null);

        //when
        when(authService.registerUser(registerDTO)).thenReturn(false);

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Registration failed")))
                .andExpect(jsonPath("$.data", is("Unable to complete registration")));
    }


    @Test
    void login_Success() throws Exception {
        // Given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("johndoe@example.com");
        loginDTO.setPassword("password");
        String mockToken = "mock-jwt-token";
        when(authService.login(loginDTO)).thenReturn(mockToken);

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", is(mockToken)));
    }

    @Test
    @Disabled
    void login_Failure() throws Exception {
        // Given
        LoginDTO loginDTO = new LoginDTO();

        when(authService.login(loginDTO))
                .thenThrow(new AuthenticationException("Invalid credentials"));

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Invalid credentials")))
                .andExpect(jsonPath("$.data", nullValue()));
    }


}