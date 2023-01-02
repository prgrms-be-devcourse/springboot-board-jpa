package com.programmers.kwonjoosung.board.service;

import com.programmers.kwonjoosung.board.model.User;
import com.programmers.kwonjoosung.board.model.dto.CreateUserRequest;
import com.programmers.kwonjoosung.board.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;
    @Mock
    private CreateUserRequest request;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    @DisplayName("[성공] save 메소드 테스트")
    void saveUserTest() {
        // given
        given(request.toEntity()).willReturn(user);
        given(userRepository.save(user)).willReturn(user);
        given(user.getId()).willReturn(1L);
        // when
        userService.saveUser(request);
        // then
        then(userRepository).should().save(user);
    }

}