package com.programmers.springbootboardjpa.domain.user.service;

import com.programmers.springbootboardjpa.domain.user.domain.User;
import com.programmers.springbootboardjpa.domain.user.domain.UserRepository;
import com.programmers.springbootboardjpa.domain.user.dto.UserRequestDto;
import com.programmers.springbootboardjpa.domain.user.dto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceMockingTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User("김이름", 27, "캠핑");

        userRequestDto = UserRequestDto.builder()
                .name("김이름")
                .age(27)
                .hobby("캠핑")
                .build();
    }

    @DisplayName("회원을 저장한다")
    @Test
    void create() {
        //given
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(null)).thenReturn(Optional.of(user));

        //when
        UserResponseDto userResponseDto = userService.create(userRequestDto);

        UserResponseDto result = userService.findById(userResponseDto.id());

        //then
        assertThat(result.name()).isEqualTo(userRequestDto.name());
        assertThat(result.age()).isEqualTo(userRequestDto.age());
        assertThat(result.hobby()).isEqualTo(userRequestDto.hobby());
    }

    @DisplayName("id로 회원을 단건 조회한다")
    @Test
    void findById() {
        //given
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        Long requestUserId = 1L;

        //when
        UserResponseDto userResponseDto = userService.findById(requestUserId);

        //then
        assertThat(userResponseDto.name()).isEqualTo(user.getName());
        assertThat(userResponseDto.age()).isEqualTo(user.getAge());
        assertThat(userResponseDto.hobby()).isEqualTo(user.getHobby());
    }

    @DisplayName("저장된 회원들을 페이징 조회한다")
    @Test
    void findAll() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<User> users = List.of(user);
        Page<User> userPage = new PageImpl<>(users, pageRequest, users.size());

        when(userRepository.findAll(any(PageRequest.class))).thenReturn(userPage);

        //when
        Page<UserResponseDto> result = userService.findAll(pageRequest);

        //then
        assertThat(result).hasSize(1);
    }
}
