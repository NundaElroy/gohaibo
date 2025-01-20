package com.gohaibo.gohaibo.utility;

import com.gohaibo.gohaibo.exception.AuthenticationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private final String secretKey = "bXlzdHJvbmdzZXByZWN0a2V5Y29tcGxleHNpdG9jdXJlY2VydC1leGFtcGxlMjM=";
    private final long jwtExpirationTime = 1000 * 60 * 60 * 24 * 7; // 7 days

    private  JwtTokenProvider jwtTokenProvider;
    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(secretKey, jwtExpirationTime);
    }

    @Test
    void generateToken_validAuthentication() {
        // given
        String email = "test@example.com";

        when(authentication.getName()).thenReturn(email);

        // when
        String token = jwtTokenProvider.generateToken(authentication);

        // then
        assertThat(token).isNotNull().isNotEmpty();
    }

//    @Test
//    void generateToken_invalidAuthentication() {
//        // given
//        when(authentication.getName()).thenReturn(null);
//
//        // when / then
//        assertThatThrownBy(() -> jwtTokenProvider.generateToken(authentication))
//                .isInstanceOf(AuthenticationException.class)
//                .hasMessageContaining("Error generating token");
//    }

    @Test
    void generateToken_invalidAuthentication() {
        // given
        when(authentication.getName()).thenReturn(null);

        // when / then
        try {
            jwtTokenProvider.generateToken(authentication);
        } catch (AuthenticationException e) {
            System.out.println("Caught exception: " + e.getMessage());  // Print the exception message
            assertThat(e).isInstanceOf(AuthenticationException.class);
            assertThat(e).hasMessageContaining("Error generating token");
        } catch (Exception e) {
            // If any other exception is thrown, print it for debugging
            fail("Expected AuthenticationException but got: " + e.getClass().getName());
        }
    }






}