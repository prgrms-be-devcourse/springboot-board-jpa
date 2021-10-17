package com.programmers.iyj.springbootboard.domain.user.service;

import com.programmers.iyj.springbootboard.domain.user.domain.Hobby;
import com.programmers.iyj.springbootboard.domain.user.domain.User;
import com.programmers.iyj.springbootboard.domain.user.dto.UserDto;
import com.programmers.iyj.springbootboard.domain.user.repository.UserRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static User user;
    private static UserDto userDto;

    @BeforeAll
    public static void setUp() {
        user = User.builder()
                .id(1L)
                .name("john")
                .age(25)
                .hobby(Hobby.NETFLIX)
                .build();

        userDto = UserDto.builder()
                .id(1L)
                .name("john")
                .age(25)
                .hobby(Hobby.NETFLIX)
                .build();
    }

    @Test
    @DisplayName("User can be selected by user_id")
    void selectUserById() throws NotFoundException {
        // Given
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        // When
        UserDto userDto = userService.findOneById(1L);

        // Then
        assertThat(userDto.getName()).isEqualTo("john");
        then(userRepository)
                .should()
                .findById(1L);
    }

    @Test
    @DisplayName("All users can be selected")
    void selectAll() {
        // Given
        PageRequest page = PageRequest.of(0, 10);
        List<User> users = new ArrayList<>();
        users.add(user);
        Page<User> userPage = new PageImpl<>(users);
        given(userRepository.findAll(page)).willReturn(userPage);

        // When
        Page<UserDto> all = userService.findAll(page);

        // Then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("User should be created")
    void createUser() {
        // Given
        given(userRepository.save(any())).willReturn(user);

        // When
        UserDto savedUserDto = userService.save(userDto);

        // Then
        then(userRepository)
                .should()
                .save(user);

        assertThat(savedUserDto).isEqualTo(userDto);
    }
}