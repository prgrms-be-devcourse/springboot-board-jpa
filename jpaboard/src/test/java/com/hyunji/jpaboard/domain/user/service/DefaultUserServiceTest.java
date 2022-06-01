package com.hyunji.jpaboard.domain.user.service;

import com.hyunji.jpaboard.domain.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DefaultUserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void user_등록() {
        User user = new User("user01", 30, "hobby");

        userService.register(user);

        assertThat(user.getId()).isNotNull();
    }
}