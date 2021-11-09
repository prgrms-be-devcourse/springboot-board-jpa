package com.devcourse.bbs;

import com.devcourse.bbs.controller.bind.UserCreateRequest;
import com.devcourse.bbs.domain.user.User;
import com.devcourse.bbs.repository.user.UserRepository;
import com.devcourse.bbs.service.user.BasicUserService;
import com.devcourse.bbs.service.user.UserService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

class UserServiceTest {
    @Test
    void createUserTest() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(any(User.class))).thenReturn(User.builder()
                .id(1L)
                .age(25)
                .name("NAME")
                .hobby("HOBBY")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build());
        UserService userService = new BasicUserService(userRepository);
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setAge(25);
        userCreateRequest.setName("NAME");
        userCreateRequest.setHobby("HOBBY");
        userService.createUser(userCreateRequest);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void findUserByNameTest() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByName("NAME")).thenReturn(Optional.of(User.builder()
                .id(1L)
                .age(25)
                .name("NAME")
                .hobby("HOBBY")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build()));
        UserService userService = new BasicUserService(userRepository);
        userService.findUserByName("NAME");
        verify(userRepository).findByName("NAME");
    }

    @Test
    void deleteUserTest() {
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new BasicUserService(userRepository);
        userService.deleteUser("NAME");
        verify(userRepository).deleteByName("NAME");
    }
}
