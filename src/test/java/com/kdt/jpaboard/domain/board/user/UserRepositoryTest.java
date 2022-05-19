package com.kdt.jpaboard.domain.board.user;

import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("user CRUD 테스트")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .name("beomseok")
                .age(26)
                .hobby("soccer")
                .build();
    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("save 테스트")
    void testSave() {
        // Given

        // When
        User save = userRepository.save(user);

        // Then
        assertThat(userRepository.existsById(save.getId()), is(true));
    }

    @Test
    @DisplayName("특정 아이디를 가진 user 찾는 테스트")
    void testFindOne() {
        // Given

        // When
        User save = userRepository.save(user);

        // Then
        Optional<User> findUser = userRepository.findById(save.getId());
        assertThat(findUser.isEmpty(), is(false));

    }

    @Test
    @DisplayName("모든 user 찾는 테스트")
    void testFindAll() {
        // Given
        User test = User.builder()
                .name("test")
                .age(10)
                .hobby("study")
                .build();

        // When
        userRepository.save(user);
        userRepository.save(test);

        // Then
        List<User> all = userRepository.findAll();
        assertThat(all, hasSize(2));
    }

    @Test
    @DisplayName("특정 user 수정 테스트")
    void testUpdate() {
        // Given

        // When
        userRepository.save(user);
        user.changeUserInfo("beomsic", 26, "sleep");
        User save = userRepository.save(user);

        // Then
        Optional<User> user = userRepository.findById(save.getId());
        assertThat(user.isEmpty(), is(false));
        assertThat(user.get().getName().equals("beomsic"), is(true));
    }

    @Test
    @DisplayName("특정 user 삭제 테스트")
    void testDelete() {
        // Given

        // When
        User save = userRepository.save(user);
        userRepository.deleteById(save.getId());

        // Then
        Optional<User> deleteUser = userRepository.findById(save.getId());
        assertThat(deleteUser.isEmpty(), is(true));
    }

}