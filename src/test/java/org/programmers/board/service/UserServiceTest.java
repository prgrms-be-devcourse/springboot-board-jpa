package org.programmers.board.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.board.entity.User;
import org.programmers.board.entity.vo.Name;
import org.programmers.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    User user;

    @BeforeEach
    void setup() {
        user = new User(new Name("김지웅"), 27, "독서");
    }

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 생성 서비스")
    void createUserTest() {
        Long user1 = userService.createUser(user);

        assertThat(user1).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("유저 단건 조회")
    void getUserTest() {
        User savedUser = userRepository.save(user);

        Optional<User> findUser = userService.getUser(savedUser.getId());

        assertAll(
                () -> assertThat(savedUser.getId()).isEqualTo(findUser.get().getId()),
                () -> assertThat(savedUser.getName().getName()).isEqualTo(findUser.get().getName().getName()),
                () -> assertThat(savedUser.getAge()).isEqualTo(findUser.get().getAge()),
                () -> assertThat(savedUser.getHobby()).isEqualTo(findUser.get().getHobby())
        );
    }

    @Test
    @DisplayName("유저 전체 조회")
    void getAllUserTest() {
        userRepository.save(user);

        List<User> allUser = userService.getAllUser();

        assertThat(allUser).isNotEmpty();
    }

    @Test
    @DisplayName("유저 삭제 서비스")
    void deleteUser() {
        userRepository.save(user);

        userService.deleteUser(user.getId());

        List<User> all = userRepository.findAll();

        assertThat(all.isEmpty()).isTrue();
    }
}