package com.kdt.simpleboard.user.service;

import com.kdt.simpleboard.common.exception.CustomException;
import com.kdt.simpleboard.common.exception.ErrorCode;
import com.kdt.simpleboard.user.domain.User;
import com.kdt.simpleboard.user.dto.UserRequest;
import com.kdt.simpleboard.user.dto.UserResponse;
import com.kdt.simpleboard.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.kdt.simpleboard.user.UserData.*;
import static com.kdt.simpleboard.user.dto.UserRequest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원을 생성할 수 있다.")
    void createUserSuccess(){
        //given
        CreateUserReq requestDto = createUserReq();
        User user = user();

        given(userRepository.existsByName(requestDto.name())).willReturn(false);
        given(userRepository.save(ArgumentMatchers.any(User.class))).willReturn(user);

        //when
        UserResponse.CreateUserRes responseDto = userService.createUser(requestDto);

        //then
        assertEquals(user.getId(), responseDto.createdId());
    }

    @Test
    @DisplayName("이미 존재하는 회원에 대해서 생성 시 예외가 발생한다.")
    void createUserFail(){
        //given
        UserRequest.CreateUserReq requestDto = createUserReq();

        given(userRepository.existsByName(requestDto.name())).willReturn(true);

        //when
        CustomException customException = assertThrows(CustomException.class, ()
                -> userService.createUser(requestDto));
        assertEquals(ErrorCode.USER_ALREADY_EXISTS, customException.getErrorCode());
    }
}