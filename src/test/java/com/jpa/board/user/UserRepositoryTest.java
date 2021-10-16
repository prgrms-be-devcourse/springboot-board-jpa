package com.jpa.board.user;


import com.jpa.board.user.User;
import com.jpa.board.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldAddUser(){
        userRepository.save(User.builder()
                .name("test")
                .age(11)
                .hobby("test")
                .build());

        List<User> usersList = userRepository.findAll();

        User user = usersList.get(0);
        assertThat(user.getHobby(), is("test"));
        assertThat(user.getName(), is("test"));
    }



}