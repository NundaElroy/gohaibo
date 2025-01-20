package com.gohaibo.gohaibo.repo;

import com.gohaibo.gohaibo.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepo underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void  itShouldCheckIfUserExistsByEmail() {
        //given
        String email = "johndoe@exaple.com";
        User  user = new User();
        user.setEmail(email);

        underTest.save(user);

        //when
        boolean expected = underTest.findUserByEmail(email).isPresent();

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void  itShouldCheckIfUserDoesNotExistsByEmail() {
        //given
        String email = "johndoe@exaple.com";
        User  user = new User();
        user.setEmail(email);



        //when
        boolean expected = underTest.findUserByEmail(email).isPresent();

        //then
        assertThat(expected).isFalse();
    }
}