package com.jpaboard.domain.user.application;

import com.jpaboard.domain.user.User;
import com.jpaboard.domain.user.UserConverter;
import com.jpaboard.domain.user.dto.request.UserCreationRequest;
import com.jpaboard.domain.user.dto.request.UserUpdateRequest;
import com.jpaboard.domain.user.dto.response.UserResponse;
import com.jpaboard.domain.user.infrastructure.UserRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("유저를 생성할 수 있다.")
    void testCreateUser() {
        // given
        UserCreationRequest request = Instancio.create(UserCreationRequest.class);
        User user = createUserByCreationRequest(request);
        given(userRepository.save(any(User.class))).willReturn(user);

        // when
        Long createdUserId = userServiceImpl.createUser(request);

        // then
        assertThat(createdUserId).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("유저를 조회할 수 있다.")
    void testFindUserById() {
        // given
        User user = createUser();
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        UserResponse expected = createUserResponse(user);

        // when
        UserResponse response = userServiceImpl.findUserById(user.getId());

        // then
        assertThat(response).isEqualTo(expected);
    }

    @Test
    @DisplayName("유저를 수정할 수 있다.")
    void testUpdateUser() {
        // given
        User user = mock(User.class);
        UserUpdateRequest request = Instancio.create(UserUpdateRequest.class);
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        // when
        userServiceImpl.updateUser(user.getId(), request);

        // then
        verify(userRepository, atMostOnce()).findById(user.getId());
        verify(user, atMostOnce()).update(request.name(), request.age(), request.hobby());
    }

    @Test
    @DisplayName("유저를 삭제할 수 있다.")
    void testDeleteUser() {
        // given
        User user = mock(User.class);
        Long userId = user.getId();
        doNothing().when(userRepository).deleteById(userId);

        // when
        userServiceImpl.deleteUser(userId);

        // then
        verify(userRepository, only()).deleteById(userId);
    }

    private User createUser() {
        return Instancio.create(User.class);
    }

    private UserResponse createUserResponse(User user) {
        return Instancio.of(UserResponse.class)
                .supply(all(UserResponse.class), gen -> UserConverter.convertEntityToResponse(user))
                .create();
    }

    private User createUserByCreationRequest(UserCreationRequest request) {
        return Instancio.of(User.class)
                .supply(all(User.class), gen -> UserConverter.convertRequestToEntity(request))
                .create();
    }
}
