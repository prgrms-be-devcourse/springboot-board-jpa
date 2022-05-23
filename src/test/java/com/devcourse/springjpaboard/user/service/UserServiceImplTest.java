package com.devcourse.springjpaboard.user.service;

import com.devcourse.springjpaboard.exception.NotFoundException;
import com.devcourse.springjpaboard.user.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.devcourse.springjpaboard.exception.ExceptionMessage.NOT_FOUND_USER;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Disabled
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("존재하지 않는 회원 ID로 조회시 예외 발생 테스트")
    void getUserByIdFailTest() throws Exception {
        // given
        Long request = 3L;

        // when
        when(userService.findById(request)).thenThrow(new NotFoundException(NOT_FOUND_USER));

        // then
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userService.findById(request));
    }
}