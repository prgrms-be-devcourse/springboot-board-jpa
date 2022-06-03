package com.programmers.springbootboardjpa.repository;

import com.programmers.springbootboardjpa.domain.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 저장 테스트")
    void saveTest() {
        User user = new User("user1", "user1", 30L, "hobby1");
        User savedUser = userRepository.save(user);

        Optional<User> maybeUser = userRepository.findById(savedUser.getId());
        assertThat(maybeUser.isPresent(), is(true));
        assertThat(maybeUser.get(), samePropertyValuesAs(savedUser));
    }
}