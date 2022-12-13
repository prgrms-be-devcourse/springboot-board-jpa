package com.ys.board.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.ys.board.domain.user.DuplicateNameException;
import com.ys.board.domain.user.User;
import com.ys.board.domain.user.api.UserCreateRequest;
import com.ys.board.domain.user.api.UserCreateResponse;
import com.ys.board.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceSliceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @DisplayName("createUser 성공 테스트 - 유저 네임이 중복이 아니라면 유저가 생성된다")
    @Test
    void createUserSuccess() {
        //given
        String name = "ys";
        int age = 28;
        UserCreateRequest userCreateRequest = new UserCreateRequest(name, age, "");
        User user = User.create(userCreateRequest.getName(), userCreateRequest.getAge(),
            userCreateRequest.getHobby());
        given(userRepository.existsByName(name))
            .willReturn(false);

        given(userRepository.save(user)).willReturn(user);

        try (MockedStatic<User> userMockedStatic = Mockito.mockStatic(User.class)) {
            given(User.create(name, age, ""))
                .willReturn(user);

            //when
            UserCreateResponse userCreateResponse = userService.createUser(userCreateRequest);

            //then
            assertEquals(user.getId(), userCreateResponse.getUserId());
            verify(userRepository).existsByName(name);
            verify(userRepository).save(user);
            userMockedStatic.verify(() -> User.create(name, age, ""));
        }
    }

    @DisplayName("createUser 실패 테스트 - 유저 네임이 중복이 중복이라면 예외가 발생한다")
    @Test
    void createUserFailDuplicateName() {
        //given
        String name = "ys";
        int age = 28;
        UserCreateRequest userCreateRequest = new UserCreateRequest(name, age, "");

        given(userRepository.existsByName(name))
            .willReturn(true);

        //when
        assertThrows(DuplicateNameException.class, () -> userService.createUser(userCreateRequest));
    }


}