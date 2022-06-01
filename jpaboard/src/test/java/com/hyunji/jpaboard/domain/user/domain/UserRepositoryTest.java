package com.hyunji.jpaboard.domain.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("test")
@Transactional
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
        compareUser(saved, user);
    }

    @Test
    void findUserByName() {
        User user = new User("user01", 15, "hobby");
        userRepository.save(user);

        Optional<User> byName = userRepository.findUserByName(user.getName());

        assertThat(byName).isNotEmpty();
        User saved = byName.get();
        compareUser(saved, user);
    }

    private void compareUser(User actual, User expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getAge()).isEqualTo(expected.getAge());
        assertThat(actual.getHobby()).isEqualTo(expected.getHobby());
        assertThat(actual.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}