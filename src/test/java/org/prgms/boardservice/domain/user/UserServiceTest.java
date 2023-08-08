package org.prgms.boardservice.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    User user = new User("email@naver.com", "password0!", "nickname");

    @Test
    @DisplayName("유저가 성공적으로 생성된다.")
    void success_Create_User() {
        Long id = userService.create(user);

        User created = userService.getById(id);

        assertThat(created).usingRecursiveComparison()
                .isEqualTo(user);
    }
}
