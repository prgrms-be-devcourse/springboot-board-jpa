package com.poogle.board.service.user;

import com.poogle.board.controller.user.UserRequest;
import com.poogle.board.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Autowired
    private UserService userService;

    private User user;

    @BeforeAll
    void setUp() {
        user = User.of("tester", 27, "listening music");
        userService.join(user);
    }

    @Test
    @DisplayName("사용자 추가")
    void create_user() {
        assertAll(
                () -> assertThat(user, is(notNullValue())),
                () -> assertThat(user.getId(), is(notNullValue())),
                () -> assertThat(user.getAge(), is(27)),
                () -> assertThat(user.getHobby(), is("listening music"))
        );
        log.info("[*] Inserted User: {}", user);
    }

    @Test
    @DisplayName("사용자 id로 조회")
    void find_user_by_id() {
        User foundUser = userService.findUser(1L).orElse(null);
        assertAll(
                () -> assertThat(foundUser, is(notNullValue())),
                () -> assertThat(foundUser.getId(), is(1L))
        );
        log.info("[*] Found User: {}", foundUser);
    }

    @Test
    @DisplayName("사용자 정보 수정")
    void update_user() {
        User user = userService.findUser(1L).orElse(null);
        UserRequest newUser = UserRequest.builder()
                .name("newTester")
                .age(28)
                .hobby("watching movie")
                .build();
        assertThat(user, is(notNullValue()));
        userService.modify(1L, newUser);
        assertAll(
                () -> assertThat(user, is(notNullValue())),
                () -> assertThat(user.getId(), is(1L))
        );
        log.info("[*] New User: {}", user);
    }

    @Test
    @DisplayName("사용자 전체 조회")
    void list_users() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<User> users = userService.findUsers(pageable);
        assertAll(
                () -> assertThat(users, is(notNullValue())),
                () -> assertThat(users.getSize(), is(2))
        );
    }

}
