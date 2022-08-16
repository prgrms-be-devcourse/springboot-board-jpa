package com.prgrms.springbootboardjpa.user.service;

import com.prgrms.springbootboardjpa.exception.exceptions.*;
import com.prgrms.springbootboardjpa.user.dto.UserDto;
import com.prgrms.springbootboardjpa.user.dto.UserResponse;
import com.prgrms.springbootboardjpa.user.entity.Name;
import com.prgrms.springbootboardjpa.user.entity.User;
import com.prgrms.springbootboardjpa.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    User user;
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

        userResponse = userService.save(userDto);

        user = userRepository.findById(userResponse.getId()).get();
    }

    @AfterEach
    void clearUp(){
        userRepository.deleteAll();
    }

    @Test
    void save() {
        //Given
        UserDto newUserDto = UserDto.builder()
                .nickName("Nickname1")
                .age(20)
                .hobby("Sleep")
                .firstName("Ella")
                .lastName("Ma")
                .password("Password123")
                .email("test1@gmail.com")
                .build();

        UserResponse newUserResponse = UserResponse.builder()
                .nickName(newUserDto.getNickName())
                .age(newUserDto.getAge())
                .hobby(newUserDto.getHobby())
                .firstName(newUserDto.getFirstName())
                .lastName(newUserDto.getLastName())
                .email(newUserDto.getEmail())
                .build();

        //When
        UserResponse response = userService.save(newUserDto);

        //Then
        assertThat(response).usingRecursiveComparison().ignoringFields("id").isEqualTo(newUserResponse);

    }

    @Test
    void checkUserDuplicateExist() {
        //When, Then
        assertThatThrownBy(() -> {
            userService.checkUserDuplicate(user);
        }).usingRecursiveComparison().isEqualTo(new CustomRuntimeException(CustomExceptionCode.DUPLICATE_EXCEPTION, "Email이 중복됩니다."));

    }

    @Test
    void checkUserDuplicationNotExist(){
        //Given
        User givenUser = new User.UserBuilder()
                    .nickName("Nickname2")
                    .age(20)
                    .hobby("Sleep")
                    .name(Name.builder()
                            .firstName("Ella")
                            .lastName("Ma")
                            .build())
                    .password("Password123")
                    .email("test2@gmail.com")
                    .createdBy(Name.builder()
                            .firstName("Ella")
                            .lastName("Ma")
                            .build().toString())
                    .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                    .build();

        //When, Then
        assertThatNoException().isThrownBy(() -> {
                    userService.checkUserDuplicate(givenUser);
                }
        );

    }


    @Test
    void login() {
        //When
        User loginResult = userService.login(userDto.getEmail(), userDto.getPassword());

        //Then
        assertThat(loginResult).usingRecursiveComparison().ignoringFields("posts").isEqualTo(user);
    }

    @Test
    void loginFailWithNotExistUser(){
        //Given
        UserDto givenUserDto = UserDto.builder()
                .nickName("Nickname2")
                .age(20)
                .hobby("Sleep")
                .firstName("Ella")
                .lastName("Ma")
                .password("Password123")
                .email("test2@gmail.com")
                .build();

        //When,Then
        assertThatThrownBy(() -> {
                    userService.login(givenUserDto.getEmail(), givenUserDto.getPassword());
                }
        ).usingRecursiveComparison().isEqualTo(new CustomRuntimeException(CustomExceptionCode.NO_SUCH_RESOURCE_EXCEPTION, "해당하는 User 정보가 없습니다."));
    }

    @Test
    void loginFailWithWrongPassword(){
        //Given
        UserDto givenUserDto = UserDto.builder()
                .nickName(userDto.getNickName())
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password("WrongPassword123")
                .email(userDto.getEmail())
                .build();

        //When,Then
        assertThatThrownBy(() -> {
                    userService.login(givenUserDto.getEmail(), givenUserDto.getPassword());
                }
        ).usingRecursiveComparison().isEqualTo(new CustomRuntimeException(CustomExceptionCode.WRONG_PASSWORD_EXCEPTION));
    }


    @Test
    void findAllWithoutPage() {
        //Given
        UserDto givenUserDto = UserDto.builder()
                .nickName("Nickname2")
                .age(20)
                .hobby("Sleep")
                .firstName("Ella")
                .lastName("Ma")
                .password("Password123")
                .email("test2@gmail.com")
                .build();
        UserResponse givenUserResponse = userService.save(givenUserDto);
        List<UserResponse> userResponseList = new ArrayList<>();
        userResponseList.add(userResponse);
        userResponseList.add(givenUserResponse);

        //When
        Page<UserResponse> userResponsePage = userService.findAll(Pageable.unpaged());

        //Then
        assertThat(userResponsePage.stream().toList()).usingRecursiveFieldByFieldElementComparator().isEqualTo(userResponseList);
    }

    @Test
    void findAllWithPage() {
        //Given
        UserDto givenUserDto = UserDto.builder()
                .nickName("Nickname2")
                .age(20)
                .hobby("Sleep")
                .firstName("Ella")
                .lastName("Ma")
                .password("Password123")
                .email("test2@gmail.com")
                .build();
        UserResponse givenUserResponse = userService.save(givenUserDto);
        List<UserResponse> userResponseList = new ArrayList<>();
        userResponseList.add(givenUserResponse);

        //When
        Page<UserResponse> userResponsePage = userService.findAll(Pageable.ofSize(1).withPage(1));

        //Then
        assertThat(userResponsePage.stream().toList()).usingRecursiveFieldByFieldElementComparator().isEqualTo(userResponseList);
    }
}