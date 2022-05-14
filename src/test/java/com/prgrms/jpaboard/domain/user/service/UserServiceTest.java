package com.prgrms.jpaboard.domain.user.service;

import com.prgrms.jpaboard.domain.user.domain.User;
import com.prgrms.jpaboard.domain.user.domain.UserRepository;
import com.prgrms.jpaboard.domain.user.dto.UserRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserRequestDto userRequestDto =
            new UserRequestDto("jerry", 25, "누워있기");

    private User user = User.builder()
            .id(1L)
            .age(25)
            .hobby("누워있기")
            .build();

    @Test
    @DisplayName("회원 생성 테스트")
    void testCreateUser() {
        when(userRepository.save(any())).thenReturn(user);

        userService.createUser(userRequestDto);

        verify(userRepository).save(any());
    }
}