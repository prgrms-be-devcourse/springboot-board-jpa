package com.example.springbootboard.service;

import com.example.springbootboard.converter.DtoConverter;
import com.example.springbootboard.dto.PostResponseDto;
import com.example.springbootboard.dto.UserRequestDto;
import com.example.springbootboard.dto.UserResponseDto;
import com.example.springbootboard.entity.Post;
import com.example.springbootboard.entity.Title;
import com.example.springbootboard.entity.User;
import com.example.springbootboard.exception.error.NotFoundException;
import com.example.springbootboard.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    UserRepository userRepository = mock(UserRepository.class);

    DtoConverter dtoConverter = mock(DtoConverter.class);

    UserService userService = new UserService(userRepository, dtoConverter);

    User user;

    UserRequestDto userRequestDto;

    UserResponseDto userResponseDto;

    @BeforeAll
     void setUp() {
        user = User.builder()
                .name("testName")
                .age(21)
                .hobby("testHobby")
                .build();

        user.addPost(Post.builder()
                .title(new Title("testTitle"))
                .content("testContent")
                .build());

        userRequestDto = UserRequestDto.builder()
                .age(26)
                .name("userRequestDto")
                .hobby("testHobby")
                .build();

        userResponseDto = UserResponseDto.builder()
                .age(26)
                .name("userResponseDto")
                .hobby("testHobby")
                .postDtos(List.of(PostResponseDto.builder()
                        .title("testTitle")
                        .content("testContent")
                        .build())
                )
                .build();
    }

    @Test
    void insert() {
        // given
        when(dtoConverter.convertUser(userRequestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        // when
        userService.insert(userRequestDto);
        // then
        verify(dtoConverter).convertUser(userRequestDto);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findById() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dtoConverter.convertUserResponseDto(user)).thenReturn(userResponseDto);
        // when
        userService.findById(1L);
        // then
        verify(userRepository).findById(1L);
        assertThrows(NotFoundException.class, () -> userService.findById(2L));
    }

    @Test
    void findAll() {
        // given
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(dtoConverter.convertUserResponseDto(user)).thenReturn(userResponseDto);
        // when
        List<UserResponseDto> testUsers = userService.findAll();
        // then
        verify(userRepository).findAll();
        verify(dtoConverter).convertUserResponseDto(user);
    }
}