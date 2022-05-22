package com.kdt.board.user.service;

import static org.assertj.core.api.Assertions.*;

import com.kdt.board.user.dto.request.UserCreateRequestDto;
import com.kdt.board.user.dto.response.UserResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("UserDto User Entity로 전환후 저장")
    void saveTest() {
        // Given
        UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto("CHOI", 23, "RUNNING");

        // When
        Long saveId = userService.save(userCreateRequestDto);

        // Then
        UserResponseDto findUser = userService.findOne(saveId);
        assertThat(findUser.getId()).isEqualTo(saveId);
    }
}