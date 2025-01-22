package com.gohaibo.gohaibo.serviceimp;

import com.gohaibo.gohaibo.dto.LoginDTO;
import com.gohaibo.gohaibo.dto.RegisterDTO;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.exception.AuthenticationException;
import com.gohaibo.gohaibo.repo.UserRepo;
import com.gohaibo.gohaibo.utility.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceImpTest {

    private  AuthServiceImp authService;
    @Mock
    private UserRepo userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImp(userRepo, passwordEncoder, authenticationManager, jwtTokenProvider);
    }

    @Test
    void registerUser_successfulRegistration() {
        // Arrange: Set up the RegisterDTO object
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setFullName("John Doe");
        registerDTO.setEmail("johndoe@example.com");
        registerDTO.setPassword("password");

        // Mock behavior
        when(userRepo.findUserByEmail(registerDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerDTO.getPassword())).thenReturn("encodedPassword");

//

        // Act: Call the method to be tested
        boolean result = authService.registerUser(registerDTO);

        // Assert: Verify the result
        assertThat(result).isTrue();

        // Capture the User object passed to the repository's save method
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(userArgumentCaptor.capture());

        // Verify the User object is correctly constructed
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser.getEmail()).isEqualTo(registerDTO.getEmail());
        assertThat(capturedUser.getPassword()).isEqualTo("encodedPassword");
    }

    @Test
    void registerUser_unsuccessfulRegistrationDueToNullDTO(){

        //given
        RegisterDTO registerDTO = null;

        //assertion
        assertThatThrownBy(() -> authService.registerUser(registerDTO))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("Invalid registration data");

        verifyNoInteractions(userRepo); //verify that the userRepo was not interacted with

    }

    @Test
    void registerUser_unsuccessfulRegistrationDueToNullEmail(){

        //given
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setFullName("John Doe");
        registerDTO.setEmail(null);
        registerDTO.setPassword("password");

        //assertion
        assertThatThrownBy(() -> authService.registerUser(registerDTO))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("Invalid registration data");

        verifyNoInteractions(userRepo); //verify that the userRepo was not interacted with

    }

    @Test
    void registerUser_unsuccessfulRegistrationDueToNullPassword(){

        //given
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setFullName("John Doe");
        registerDTO.setEmail("johndoe@example.com");
        registerDTO.setPassword(null);

        //assertion
        assertThatThrownBy(() -> authService.registerUser(registerDTO))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("Invalid registration data");

        verifyNoInteractions(userRepo); //verify that the userRepo was not interacted with

    }


    @Test
    void registerUser_unsuccessfulRegistrationDueToNullFullName(){

        //given
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setFullName("John Doe");
        registerDTO.setEmail("johndoe@example.com");
        registerDTO.setPassword(null);

        //assertion
        assertThatThrownBy(() -> authService.registerUser(registerDTO))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("Invalid registration data");

        verifyNoInteractions(userRepo); //verify that the userRepo was not interacted with

    }


    @Test
    void registerUser_unsuccessfulRegistrationDueToExistingEmail() {
        // given
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("johndoe@example.com");
        registerDTO.setFullName("John Doe");
        registerDTO.setPassword("password");

        // when
        when(userRepo.findUserByEmail(registerDTO.getEmail())).thenReturn(Optional.of(new User()));

        // then
        assertThatThrownBy(() -> authService.registerUser(registerDTO))
                .isInstanceOf(AuthenticationException.class)
                .hasMessageContaining("User already exists");

        // Verify findUserByEmail was called, but save was never called
        verify(userRepo).findUserByEmail(registerDTO.getEmail());
        verify(userRepo, never()).save(any(User.class));
    }



///LOGIN METHOD TESTS

    @Test
    void successfulLogin() {
        //given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("johndoe@example.com");
        loginDTO.setPassword("encodedpassword");

        //when
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        )).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("mockedJwtToken");



        String token = authService.login(loginDTO);

        // then
        assertThat(token).isEqualTo("mockedJwtToken");
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        verify(jwtTokenProvider).generateToken(authentication);
    }


    @Test
    void login_throwsExceptionWhenInvalidCredentials() {
        // given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("johndoe@example.com");
        loginDTO.setPassword("wrongPassword");

        //when
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        )).thenThrow(new BadCredentialsException("Invalid credentials"));

        //then
        assertThatThrownBy(() -> authService.login(loginDTO))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid credentials");
    }


    @Test
    void login_throwsExceptionWhenDTOIsNull() {
        // Act & Assert
        assertThatThrownBy(() -> authService.login(null))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("Invalid credentials");

    }





}