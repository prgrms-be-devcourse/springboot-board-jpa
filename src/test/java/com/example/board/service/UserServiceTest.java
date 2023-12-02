package com.example.board.service;

import com.example.board.domain.User;
import com.example.board.dto.request.user.CreateUserRequest;
import com.example.board.dto.request.user.UpdateUserRequest;
import com.example.board.dto.response.ResponseStatus;
import com.example.board.exception.CustomException;
import com.example.board.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserServiceTest() {
        this.userRepository = Mockito.mock(UserRepository.class);
        this.userService = new UserService(userRepository);
    }

    @Test
    void 유저를_생성한다() {
        // given
        CreateUserRequest requestDto = new CreateUserRequest("빙봉", 30, "러닝");
        User user = generateUser();
        given(userRepository.save(any(User.class))).willReturn(user);

        // when
        userService.createUser(requestDto);

        // then
        verify(userRepository).save(any(User.class));
    }

    @Test
    void 중복된_이름의_유저를_생성할_수_없다() {
        // given
        CreateUserRequest requestDto = new CreateUserRequest("빙봉", 30, "러닝");
        User user = generateUser();

        given(userRepository.findByNameAndDeletedAt(any(String.class), eq(null))).willReturn(Collections.singletonList(user));

        // when & then
        assertThatThrownBy(() -> userService.createUser(requestDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseStatus.DUPLICATED_USER_NAME.getMessage());
    }

    @Test
    void 특정_유저를_조회한다() {
        // given
        User user = generateUser();
        given(userRepository.findById(eq(user.getId()))).willReturn(Optional.ofNullable(user));

        // when
        userService.getUser(user.getId());

        // then
        verify(userRepository).findById(user.getId());
    }

    @Test
    void 존재하지_않는_유저를_조회할_수_없다() {
        // given
        given(userRepository.findById(any(Long.class))).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.getUser(0L))
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseStatus.USER_NOT_FOUND.getMessage());
    }

    @Test
    void 탈퇴한_유저를_조회할_수_없다() {
        // given
        User user = User.builder()
                .id(1L)
                .name("빙봉")
                .age(20)
                .hobby("마라톤")
                .deletedAt(LocalDateTime.now())
                .build();
        given(userRepository.findById(eq(user.getId()))).willReturn(Optional.ofNullable(user));

        // when & then
        assertThatThrownBy(() -> userService.getUser(user.getId()))
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseStatus.ALREADY_DELETED_USER.getMessage());
    }

    @Test
    void 유저를_수정한다() {
        // given
        User mockUser = mock(User.class);
        UpdateUserRequest updateDto = new UpdateUserRequest("삥뽕", 30, "러닝");
        given(userRepository.findById(eq(mockUser.getId()))).willReturn(Optional.ofNullable(mockUser));

        // when
        userService.updateUser(mockUser.getId(), updateDto);

        // then
        verify(mockUser).update(updateDto.name(), updateDto.age(), updateDto.hobby());
    }

    @Test
    void 존재하지_않는_유저를_수정할_수_없다() {
        // given
        UpdateUserRequest updateDto = new UpdateUserRequest("삥뽕", 30, "러닝");
        given(userRepository.findById(any(Long.class))).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.updateUser(0L, updateDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseStatus.USER_NOT_FOUND.getMessage());
    }

    @Test
    void 탈퇴한_유저를_수정할_수_없다() {
        // given
        User user = User.builder()
                .id(1L)
                .name("빙봉")
                .age(20)
                .hobby("마라톤")
                .deletedAt(LocalDateTime.now())
                .build();
        UpdateUserRequest updateDto = new UpdateUserRequest("삥뽕", 30, "러닝");
        given(userRepository.findById(eq(user.getId()))).willReturn(Optional.ofNullable(user));

        // when & then
        assertThatThrownBy(() -> userService.updateUser(user.getId(), updateDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseStatus.ALREADY_DELETED_USER.getMessage());
    }

    @Test
    void 유저를_삭제한다() {
        // given
        User mockUser = mock(User.class);
        given(userRepository.findById(eq(mockUser.getId()))).willReturn(Optional.ofNullable(mockUser));

        // when
        userService.deleteUser(mockUser.getId());

        // then
        verify(mockUser).delete();
    }

    @Test
    void 존재하지_않는_유저를_삭제할_수_없다() {
        // given
        Long userId = 0L;
        given(userRepository.findById(eq(userId))).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.deleteUser(userId))
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseStatus.USER_NOT_FOUND.getMessage());
    }

    @Test
    void 탈퇴한_유저를_삭제할_수_없다() {
        // given
        User user = User.builder()
                .id(1L)
                .name("빙봉")
                .deletedAt(LocalDateTime.now())
                .build();
        given(userRepository.findById(eq(user.getId()))).willReturn(Optional.ofNullable(user));

        // when & then
        assertThatThrownBy(() -> userService.deleteUser(user.getId()))
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseStatus.ALREADY_DELETED_USER.getMessage());
    }

    User generateUser() {
        return User.builder()
                .id(1L)
                .name("빙봉")
                .age(20)
                .hobby("마라톤")
                .build();
    }
}
