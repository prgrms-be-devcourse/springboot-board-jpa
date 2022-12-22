package com.example.board.domain.user.service;

import com.example.board.domain.user.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.board.domain.hobby.entity.HobbyType.GAME;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @DisplayName("회원 등록에 성공한다.")
    @Test
    void enroll_user_success() {
        // given
        UserDto.CreateUserRequest createUserRequest = new UserDto.CreateUserRequest("박현서", 3, GAME);

        // when
        UserDto.SingleUserDetailResponse savedUserResponse = userService.enroll(createUserRequest);

        // then
        assertThat(savedUserResponse)
                .hasFieldOrPropertyWithValue("name", "박현서")
                .hasFieldOrPropertyWithValue("age", 3)
                .hasFieldOrPropertyWithValue("hobbies", List.of(GAME));
    }
}