package com.gohaibo.gohaibo.serviceimp;

import com.gohaibo.gohaibo.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import com.gohaibo.gohaibo.entity.User;
import static org.mockito.Mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;




@ExtendWith(MockitoExtension.class)
class CustomUserDetailTest {

    @Mock
    private UserRepo userRepo;
    private CustomUserDetail customUserDetail;

    @BeforeEach
    void setUp() {
        customUserDetail = new CustomUserDetail(userRepo);
    }

    @Test
    void loadUserByUsername_userExists() {
        // given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");

        //when
        when(userRepo.findUserByEmail(email)).thenReturn(Optional.of(user));
        UserDetails userDetails = customUserDetail.loadUserByUsername(email);

        // Assert
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(email);
        assertThat(userDetails.getPassword()).isEqualTo("encodedPassword");
        assertThat(userDetails.getAuthorities()).hasSize(1);
        assertThat(userDetails.getAuthorities().iterator().next().getAuthority()).isEqualTo("ROLE_USER");

        // Verify interaction with the repository
        verify(userRepo).findUserByEmail(email);
    }

    @Test
    void loadUserByUsername_userDoesNotExist() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepo.findUserByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customUserDetail.loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");

        // Verify interaction with the repository
        verify(userRepo).findUserByEmail(email);
    }
}
