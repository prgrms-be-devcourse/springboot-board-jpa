package com.prgrms.jpaboard.domain.user.service;

import com.prgrms.jpaboard.domain.user.domain.User;
import com.prgrms.jpaboard.domain.user.domain.UserRepository;
import com.prgrms.jpaboard.domain.user.dto.UserRequestDto;
import com.prgrms.jpaboard.global.error.exception.WrongFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user = User.builder()
            .name("jerry")
            .age(25)
            .hobby("누워있기")
            .createdBy("jerry")
            .build();

    @Test
    @DisplayName("회원 생성 테스트")
    void testCreateUser() {
        UserRequestDto userRequestDto = new UserRequestDto("jerry", 25, "누워있기");
        when(userRepository.save(any())).thenReturn(user);

        userService.createUser(userRequestDto);

        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("회원 생성시 전달되는 값 검증 테스트")
    void testCreateUserWrongFieldException() {
        UserRequestDto userRequestDto = new UserRequestDto("", 25, "누워있기");

        try {
            userService.createUser(userRequestDto);
        }catch (WrongFieldException e) {
            assertThat(e.getFieldName()).isEqualTo("UserRequestDto.name");
        }
    }
}