package com.prgms.jpaBoard.domain.user.application;

import com.prgms.jpaBoard.domain.user.HobbyType;
import com.prgms.jpaBoard.domain.user.application.dto.UserResponse;
import com.prgms.jpaBoard.domain.user.presentation.dto.UserSaveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("유저를 저장 할 수 있다.")
    void saveUser() {
        // Given
        UserSaveRequest userSaveRequest = new UserSaveRequest("재웅", 28, HobbyType.EXERCISE);

        // When
        Long savedId = userService.save(userSaveRequest);

        UserResponse userResponse = userService.findOne(savedId);

        // Then
        assertThat(userResponse.id()).isEqualTo(savedId);
    }

    @Test
    @DisplayName("유저를 조회 할 수 있다.")
    void readUser() {
        // Given
        UserSaveRequest userSaveRequest = new UserSaveRequest("재웅", 28, HobbyType.EXERCISE);

        // When
        Long savedId = userService.save(userSaveRequest);

        UserResponse userResponse = userService.findOne(savedId);

        // Then
        assertThat(userResponse.id()).isEqualTo(savedId);
    }

}