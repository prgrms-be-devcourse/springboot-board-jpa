package com.example.springbootboard.repository;

import com.example.springbootboard.domain.Hobby;
import com.example.springbootboard.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


@Slf4j
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveTest() {
        // given
        User newUser = User.of(UUID.randomUUID().toString(), "sezi", 29, Hobby.FOOD);

        // when
        userRepository.save(newUser);

        // then
        User retrievedUser = userRepository.findUserByName("sezi").get();
        assertThat(retrievedUser, samePropertyValuesAs(newUser));
    }


}
