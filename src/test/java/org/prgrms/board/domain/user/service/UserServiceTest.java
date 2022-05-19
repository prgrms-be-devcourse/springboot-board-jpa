package org.prgrms.board.domain.user.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.board.domain.user.domain.Email;
import org.prgrms.board.domain.user.domain.Name;
import org.prgrms.board.domain.user.domain.Password;
import org.prgrms.board.domain.user.domain.User;
import org.prgrms.board.domain.user.exception.UserException;
import org.prgrms.board.domain.user.mapper.UserMapper;
import org.prgrms.board.domain.user.repository.UserRepository;
import org.prgrms.board.domain.user.requset.UserCreateRequest;
import org.prgrms.board.domain.user.requset.UserLoginRequest;
import org.prgrms.board.domain.user.response.UserSearchResponse;
import org.prgrms.board.global.exception.ErrorCode;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Spy
    UserMapper userMapper;

    @Nested
    class ID로_조회 {
        @Test
        void 유저ID가_존재할경우_조회성공() {
            //given
            long userId = 1L;
            User user = User.builder()
                    .id(userId)
                    .name(Name.builder()
                            .firstName("우진")
                            .lastName("박")
                            .build())
                    .password(new Password("kkkk13804943@"))
                    .email(new Email("dbslzld15@naver.com"))
                    .age(27)
                    .build();
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            //when
            UserSearchResponse searchResponse = userService.findById(userId);
            //then
            assertThat(searchResponse).usingRecursiveComparison()
                    .isEqualTo(userMapper.toSearchResponse(user));
        }

        @Test
        void 유저ID가_존재하지않을경우_조회실패() {
            //given
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
            //when
            //then
            assertThatThrownBy(() -> userService.findById(1L))
                    .isInstanceOf(UserException.class)
                    .hasMessage(ErrorCode.USER_NOT_EXIST.getMessage());
        }
    }

    @Test
    void 유저를_저장할수있다() {
        //given
        long userId = 1L;
        User user = User.builder()
                .id(userId)
                .name(Name.builder()
                        .firstName("우진")
                        .lastName("박")
                        .build())
                .password(new Password("kkkk13804943@"))
                .email(new Email("dbslzld15@naver.com"))
                .age(27)
                .build();
        when(userRepository.save(any())).thenReturn(user);
        //when
        UserCreateRequest createRequest = UserCreateRequest.builder()
                .age(27)
                .email("dbslzld15@naver.com")
                .firstName("우진")
                .lastName("박")
                .password("kkkk13804943@")
                .build();
        long savedId = userService.save(createRequest);
        //then
        assertThat(savedId).isEqualTo(userId);
    }

    @Nested
    class 로그인 {
        @Test
        void 아이디와_패스워드가_일치할경우_로그인성공() {
            //given
            long userId = 1L;
            String passwordInput = "kkkk13804943@";
            String emailInput = "dbslzld15@naver.com";
            User user = User.builder()
                    .id(userId)
                    .name(Name.builder()
                            .firstName("우진")
                            .lastName("박")
                            .build())
                    .password(new Password(passwordInput))
                    .email(new Email(emailInput))
                    .age(27)
                    .build();
            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            when(userRepository.findByPassword(any())).thenReturn(Optional.of(user));
            //when
            UserLoginRequest loginRequest = new UserLoginRequest(emailInput, passwordInput);
            long loginId = userService.login(loginRequest);
            //then
            assertThat(loginId).isEqualTo(userId);
        }

        @Test
        void 아이디가_존재하지않을경우_로그인실패() {
            //given
            String passwordInput = "kkkk13804943@";
            String emailInput = "dbslzld15@naver.com";
            when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
            //when
            UserLoginRequest loginRequest = new UserLoginRequest(emailInput, passwordInput);
            //then
            assertThatThrownBy(() -> userService.login(loginRequest))
                    .isInstanceOf(UserException.class)
                    .hasMessage(ErrorCode.EMAIL_NOT_EXIST.getMessage());
        }

        @Test
        void 패스워드가_일치하지않을경우_로그인실패() {
            //given
            long userId = 1L;
            String passwordInput = "kkkk13804943@";
            String emailInput = "dbslzld15@naver.com";
            User user = User.builder()
                    .id(userId)
                    .name(Name.builder()
                            .firstName("우진")
                            .lastName("박")
                            .build())
                    .password(new Password(passwordInput))
                    .email(new Email(emailInput))
                    .age(27)
                    .build();
            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            when(userRepository.findByPassword(any())).thenReturn(Optional.empty());
            //when
            UserLoginRequest loginRequest = new UserLoginRequest(emailInput, passwordInput);
            //then
            assertThatThrownBy(() -> userService.login(loginRequest))
                    .isInstanceOf(UserException.class)
                    .hasMessage(ErrorCode.PASSWORD_INCORRECT.getMessage());
        }
    }

    @Nested
    class 유저_삭제{

        @Test
        void 유저ID가_존재할경우_삭제성공(){
            //given
            long userId = 1L;
            String passwordInput = "kkkk13804943@";
            String emailInput = "dbslzld15@naver.com";
            User user = User.builder()
                    .id(userId)
                    .name(Name.builder()
                            .firstName("우진")
                            .lastName("박")
                            .build())
                    .password(new Password(passwordInput))
                    .email(new Email(emailInput))
                    .age(27)
                    .build();
            when(userRepository.findById(any())).thenReturn(Optional.of(user));
            //when
            userService.delete(1L);
            //then
            verify(userRepository, times(1)).findById(any());
            verify(userRepository, times(1)).deleteById(any());
        }

        @Test
        void 유저ID가_존재하지않을경우_삭제실패(){
            //given
            long userId = 1L;
            when(userRepository.findById(any())).thenReturn(Optional.empty());
            //when
            //then
            assertThatThrownBy(() -> userService.delete(1L))
                    .isInstanceOf(UserException.class)
                    .hasMessage(ErrorCode.USER_NOT_EXIST.getMessage());
        }
    }

}