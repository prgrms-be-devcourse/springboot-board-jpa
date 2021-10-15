package org.prgms.board.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.board.common.exception.NotFoundException;
import org.prgms.board.domain.entity.User;
import org.prgms.board.domain.repository.UserRepository;
import org.prgms.board.user.dto.UserRequest;
import org.prgms.board.user.dto.UserResponse;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final String REQUEST_NAME = "김부희";
    private static final int REQUEST_AGE = 26;
    private static final String REQUEST_HOBBY = "만들기";
    private static final String UPDATE_HOBBY = "터프팅";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .name("김부희")
            .age(26)
            .hobby("만들기")
            .build();
    }

    @DisplayName("사용자 저장 확인")
    @Test
    void addUserTest() {
        UserRequest newUser = new UserRequest(REQUEST_NAME, REQUEST_AGE, REQUEST_HOBBY);

        when(userRepository.save(any())).thenReturn(user);
        Long userId = userService.addUser(newUser);
        assertThat(userId).isEqualTo(user.getId());
    }

    @DisplayName("사용자 수정 확인")
    @Test
    void modifyUserTest() {
        UserRequest modifyUser = new UserRequest(REQUEST_NAME, REQUEST_AGE, UPDATE_HOBBY);
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));

        userService.modifyUser(user.getId(), modifyUser);

        UserResponse retrievedUser = userService.getUser(user.getId());
        assertThat(retrievedUser.getHobby()).isEqualTo(modifyUser.getHobby());
    }

    @DisplayName("사용자 삭제 확인")
    @Test
    void removeUserTest() {
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.removeUser(user.getId()))
            .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("사용자 상세정보 확인")
    @Test
    void getOneUserTest() {
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));

        UserResponse retrievedUser = userService.getUser(user.getId());
        assertThat(retrievedUser.getName()).isEqualTo(user.getName());
        assertThat(retrievedUser.getAge()).isEqualTo(user.getAge());
        assertThat(retrievedUser.getHobby()).isEqualTo(user.getHobby());
    }
}