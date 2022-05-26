package com.example.springbootboardjpa.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeAll
    void setup() {
        userRepository.deleteAll();
        user = User.builder()
                .id(UUID.randomUUID())
                .age(29)
                .name("nnagman")
                .hobby("thinking")
                .build();
        user = userRepository.save(user);
    }

    @Test
    @DisplayName("중복된 이름의 유저는 등록 할 수 없다.")
    void DUPLICATE_USER_NAME_TEST() {
        // given
        User saveUser = User.builder()
                .id(UUID.randomUUID())
                .age(29)
                .name(user.getName())
                .hobby("thinking")
                .build();

        // when // then
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(saveUser));
    }

    @Test
    @DisplayName("유저를 아이디로 검색 할 수 있다.")
    void FIND_USER_BY_ID_TEST() {
        // given
        UUID id = user.getId();

        // when
        Optional<User> byId = userRepository.findById(id);

        // then
        assertThat(byId).contains(user);
    }

    @Test
    @DisplayName("유저를 이름으로 검색 할 수 있다.")
    void FIND_USER_BY_NAME_TEST() {
        // given
        String name = user.getName();

        // when
        List<User> byName = userRepository.findAllByName(name);

        // then
        assertThat(byName).hasSize(1);
    }

    @Test
    @DisplayName("유저 정보를 수정 할 수 있습니다.")
    void UPDATE_USER_TEST() {
        // given
        user = user.toBuilder()
                .age(30)
                .hobby("doing")
                .build();

        // when
        User updated = userRepository.save(user);

        // then
        assertThat(updated).isEqualTo(user);
    }

    @Test
    @DisplayName("유저를 삭제 할 수 있습니다.")
    void DELETE_USER_TEST() {
        // given
        UUID id = user.getId();

        // when
        userRepository.deleteById(id);

        // then
        assertThat(userRepository.findById(id)).isEmpty();
    }
}
