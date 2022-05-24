package com.prgrms.springbootboardjpa.user.service;

import com.prgrms.springbootboardjpa.user.dto.UserDto;
import com.prgrms.springbootboardjpa.user.dto.UserResponse;
import com.prgrms.springbootboardjpa.user.entity.Email;
import com.prgrms.springbootboardjpa.user.entity.Name;
import com.prgrms.springbootboardjpa.user.entity.Password;
import com.prgrms.springbootboardjpa.user.entity.User;
import com.prgrms.springbootboardjpa.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    UserDto userDto;
    UserResponse userResponse;

    @BeforeEach
    void setUp(){
        userDto = UserDto.builder()
                .nickName("Nickname")
                .age(20)
                .hobby("Sleep")
                .firstName("Ella")
                .lastName("Ma")
                .password("Password123")
                .email("test@gmail.com")
                .build();

        userResponse = UserResponse.builder()
                .nickName(userDto.getNickName())
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .firstName(userDto.getFirstName())
                .lastname(userDto.getLastName())
                .email(userDto.getEmail())
                .build();

        userRepository.save(UserDto.convertToUser(userDto));
    }

    @Test
    void save() {
        //Given
        UserDto newUserDto = UserDto.builder()
                .nickName("Nickname2")
                .age(20)
                .hobby("Sleep")
                .firstName("Ella")
                .lastName("Ma")
                .password("Password123")
                .email("test2@gmail.com")
                .build();

        UserResponse newUserResponse = UserResponse.builder()
                .nickName(newUserDto.getNickName())
                .age(newUserDto.getAge())
                .hobby(newUserDto.getHobby())
                .firstName(newUserDto.getFirstName())
                .lastname(newUserDto.getLastName())
                .email(newUserDto.getEmail())
                .build();

        //When
        UserResponse response = userService.save(newUserDto);

        //Then
        assertThat(response).usingRecursiveComparison().ignoringFields("id").isEqualTo(newUserResponse);

    }

    @Test
    void checkUserDuplicate() {

    }

    @Test
    void encodePassword() {
    }

    @Test
    void login() {
    }

    @Test
    void checkPassword() {
    }

    @Test
    void findAll() {
    }
}