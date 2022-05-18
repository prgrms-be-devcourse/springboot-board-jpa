package com.prgrms.board.user.repository;

import com.prgrms.board.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(
                User.builder()
                        .name("wansu")
                        .age(27L)
                        .hobby("soccer")
                        .build());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void user_저장() {
        Assertions.assertThat(userRepository.findById(user.getUserId())).isPresent();
    }

    @Test
    void 중복된_name으로_user_저장_실패() {
        User user = User.builder()
                .name("wansu")
                .age(27L)
                .hobby("soccer")
                .build();
        Assertions.assertThatThrownBy(() -> userRepository.save(user))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void user_삭제() {
        userRepository.deleteById(user.getUserId());
        Assertions.assertThat(userRepository.findById(user.getUserId())).isNotPresent();
    }

}