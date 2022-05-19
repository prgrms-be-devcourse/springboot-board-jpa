package com.hyunji.jpaboard.domain.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("test")
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 상태저장_테스트() {
        User user = new User("user01", 15, "hobby");
        userRepository.save(user);

        Optional<User> byId = userRepository.findById(user.getId());

        assertThat(byId).isNotEmpty();
        User saved = byId.get();
        assertThat(saved.getId()).isEqualTo(user.getId());
        assertThat(saved.getName()).isEqualTo(user.getName());
        assertThat(saved.getAge()).isEqualTo(user.getAge());
        assertThat(saved.getHobby()).isEqualTo(user.getHobby());
        assertThat(saved.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}