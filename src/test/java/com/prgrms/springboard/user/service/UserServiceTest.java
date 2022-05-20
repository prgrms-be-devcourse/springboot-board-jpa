package com.prgrms.springboard.user.service;

import static com.prgrms.springboard.TestUtils.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrms.springboard.user.domain.User;
import com.prgrms.springboard.user.domain.UserRepository;
import com.prgrms.springboard.user.dto.CreateUserRequest;
import com.prgrms.springboard.user.dto.UserDto;
import com.prgrms.springboard.user.exception.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @DisplayName("회원을 저장한다.")
    @Test
    void join() {
        // given
        CreateUserRequest userRequest = new CreateUserRequest("유민환", 26, "낚시");
        given(userRepository.save(any(User.class))).willReturn(createUser());

        // when
        Long id = userService.join(userRequest);

        // then
        assertThat(id).isEqualTo(1L);
        then(userRepository).should(times(1)).save(any(User.class));
    }

    @DisplayName("회원을 조회한다.")
    @Test
    void findOne() {
        // given
        Long id = 1L;
        given(userRepository.findById(id)).willReturn(Optional.of(createUser()));

        // when
        UserDto user = userService.findOne(id);

        // then
        assertAll(
            () -> assertThat(user.getId()).isEqualTo(1L),
            () -> assertThat(user.getName()).isEqualTo("유민환"),
            () -> assertThat(user.getAge()).isEqualTo(26),
            () -> assertThat(user.getHobby()).isEqualTo("낚시")
        );
    }

    @DisplayName("저장된 id가 아닐 경우 예외가 발생한다.")
    @Test
    void findOne_NotSavedId() {
        // given
        Long id = 10L;
        given(userRepository.findById(id)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> userService.findOne(id))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("ID가 10인 회원은 없습니다.");
    }

}
