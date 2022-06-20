package com.programmers.board.core.user.domain.repository;

import com.programmers.board.core.user.domain.Hobby;
import com.programmers.board.core.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .name("jung")
                .age(145)
                .hobby(Hobby.COOKING)
                .build();

        userRepository.save(user);
    }

    @Test
    @DisplayName("user 생성 조회 테스트")
    void test1(){
        Optional<User> maybeUser = userRepository.findById(user.getId());

        assertThat(maybeUser).isNotEmpty();
    }

}