package com.example.boardbackend.service;

import com.example.boardbackend.domain.User;
import com.example.boardbackend.dto.UserDto;
import com.example.boardbackend.common.converter.DtoConverter;
import com.example.boardbackend.repository.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DtoConverter dtoConverter;

    UserDto userDto = UserDto.builder()
            .id(1L)
            .email("test@mail.com")
            .password("1234")
            .name("test")
            .age(20)
            .hobby("코딩")
            .createdAt(LocalDateTime.now())
            .build();

    @BeforeEach
    void setUp(){
        userRepository.save(dtoConverter.convertToUserEntity(userDto));
    }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

//    ------------------------------------------------------------------------------------

    @Test
    @DisplayName("회원 정보를 저장할 수 있다")
    void saveUser_test() {
        // Given
        UserDto newUserDto = UserDto.builder()
                .id(2L)
                .email("test2@mail.com")
                .password("1234")
                .name("test2")
                .age(30)
                .hobby("개발")
                .createdAt(LocalDateTime.now())
                .build();

        // When
        userService.saveUser(newUserDto);

        // Then
        long count = userRepository.count();
        assertThat(count, is(2L));
        Optional<User> byId = userRepository.findById(newUserDto.getId());
        assertThat(byId.isPresent(), is(true));
    }

    @Test
    @DisplayName("모든 회원의 정보를 조회할 수 있다")
    void findUsersAll_test() {
        // Given
        // prepared userDto

        // When
        List<UserDto> usersAll = userService.findUsersAll();

        // Then
        assertThat(usersAll, hasSize(1));
        assertThat(usersAll, iterableWithSize(1));
    }

    @Test
    @DisplayName("Email로 회원을 조회할 수 있다")
    void findUserByEmail_test() {
        // Given
        // prepared userDto

        // When
        Optional<UserDto> userByEmail = userService.findUserByEmail(userDto.getEmail());

        // Then
        assertThat(userByEmail.isPresent(), is(true));
        assertThat(userByEmail.get().getEmail(), is(userDto.getEmail()));
    }

    @Test
    @DisplayName("ID로 회원을 조회할 수 있다")
    void findUserById_test() throws NotFoundException {
        // Given
        // prepared userDto

        // When
        UserDto userById = userService.findUserById(userDto.getId());

        // Then
//        assertThat(userById.isPresent(), is(true));
//        assertThat(userById.getId(), userDto.getId());
    }

    @Test
    @DisplayName("ID로 회원을 삭제할 수 있다.")
    void deleteUserById() {
        // Given

        // When

        // Then
    }
}