package org.prgms.board.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.board.domain.entity.User;
import org.prgms.board.domain.repository.UserRepository;
import org.prgms.board.exception.NotFoundException;
import org.prgms.board.user.dto.UserRequest;
import org.prgms.board.user.dto.UserResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .name("buhee")
            .age(26)
            .hobby("making")
            .build();
    }

    @DisplayName("사용자 저장 확인")
    @Test
    void addUserTest() {
        UserRequest newUser = new UserRequest("buhee", 26, "making");

        when(userRepository.save(any())).thenReturn(user);
        Long userId = userService.addUser(newUser);
        assertThat(userId).isEqualTo(user.getId());
    }

    @DisplayName("사용자 수정 확인")
    @Test
    void modifyUserTest() {
        UserRequest modifyUser = new UserRequest("buri", 26, "tufting");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.modifyUser(user.getId(), modifyUser);

        UserResponse retrievedUser = userService.getOneUser(user.getId());
        assertThat(retrievedUser.getName()).isEqualTo("buri");
        assertThat(retrievedUser.getHobby()).isEqualTo("tufting");
    }

    @DisplayName("사용자 삭제 확인")
    @Test
    void removeUserTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.removeUser(1L))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining("해당 사용자를 찾을 수 없습니다.");
    }

    @DisplayName("사용자 상세정보 확인")
    @Test
    void getOneUserTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserResponse retrievedUser = userService.getOneUser(user.getId());
        assertThat(retrievedUser.getName()).isEqualTo("buhee");
        assertThat(retrievedUser.getAge()).isEqualTo(26);
        assertThat(retrievedUser.getHobby()).isEqualTo("making");
    }
}