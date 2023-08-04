package com.programmers.springbootboardjpa.domain.user.service;

import com.programmers.springbootboardjpa.domain.user.dto.UserRequestDto;
import com.programmers.springbootboardjpa.domain.user.dto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setUp() {
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
        //when
        UserResponseDto userResponseDto = userService.create(userRequestDto);
        UserResponseDto result = userService.findById(userResponseDto.id());

        //then
        assertThat(result.name()).isEqualTo(userRequestDto.name());
        assertThat(result.age()).isEqualTo(userRequestDto.age());
        assertThat(result.hobby()).isEqualTo(userRequestDto.hobby());
    }

    @DisplayName("회원을 수정한다")
    @Test
    void update() {
        //given
        UserResponseDto savedUserResponseDto = userService.create(userRequestDto);

        Long requestUserId = savedUserResponseDto.id();

        UserRequestDto userRequestDto2 = UserRequestDto.builder()
                .name("수정이름")
                .age(11)
                .hobby("영화 보기")
                .build();

        //when
        UserResponseDto userResponseDto = userService.update(requestUserId, userRequestDto2);

        //then
        assertThat(userResponseDto.name()).isEqualTo(userRequestDto2.name());
        assertThat(userResponseDto.age()).isEqualTo(userRequestDto2.age());
        assertThat(userResponseDto.hobby()).isEqualTo(userRequestDto2.hobby());
    }

    @DisplayName("id로 회원을 단건 조회한다")
    @Test
    void findById() {
        //given
        UserResponseDto savedUserDto = userService.create(userRequestDto);

        Long requestUserId = savedUserDto.id();

        //when
        UserResponseDto userResponseDto = userService.findById(requestUserId);

        //then
        assertThat(userResponseDto.name()).isEqualTo(savedUserDto.name());
        assertThat(userResponseDto.age()).isEqualTo(savedUserDto.age());
        assertThat(userResponseDto.hobby()).isEqualTo(savedUserDto.hobby());
    }

    @DisplayName("저장된 회원들을 페이징 조회한다")
    @Test
    void findAll() {
        //given
        userService.create(userRequestDto);

        UserRequestDto userRequestDto2 = UserRequestDto.builder()
                .name("이이름")
                .age(29)
                .hobby("수영")
                .build();

        userService.create(userRequestDto2);

        PageRequest pageRequest = PageRequest.of(0, 5);

        //when
        Page<UserResponseDto> result = userService.findAll(pageRequest);

        //then
        assertThat(result).hasSize(2);
    }
}