package com.eden6187.jpaboard.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eden6187.jpaboard.test_data.UserMockData;
import com.eden6187.jpaboard.controller.UserController.AddUserRequestDto;
import com.eden6187.jpaboard.service.converter.UserConverter;
import com.eden6187.jpaboard.exception.DuplicatedUserNameException;
import com.eden6187.jpaboard.model.User;
import com.eden6187.jpaboard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest(classes = UserService.class)
class UserServiceUnitTest {

  @Autowired
  UserService userService; // UnitTest 할 대상 Mock 객체에 의존한다.

  @MockBean
  UserConverter userConverter;

  @MockBean
  UserRepository userRepository;

  AddUserRequestDto saveUserDto;
  User user;

  @BeforeEach
  void setUp() {
    saveUserDto = AddUserRequestDto.builder()
        .age(UserMockData.TEST_AGE)
        .name(UserMockData.TEST_NAME)
        .hobby(UserMockData.TEST_HOBBY)
        .build();

    user = User.builder()
        .age(UserMockData.TEST_AGE)
        .name(UserMockData.TEST_NAME)
        .hobby(UserMockData.TEST_HOBBY)
        .build();
  }

  @Test
  @DisplayName("새로운 유저를 저장할 수 있다.")
  void createUserTest() {
    when(userConverter.convertUser(saveUserDto)).thenReturn(user);
    when(userRepository.save(user)).thenReturn(user);

    userService.addUser(saveUserDto);

    verify(userConverter).convertUser(saveUserDto);
    verify(userRepository).save(user);
  }

  @Test
  @DisplayName("UserRepository에서 DataIntegrityViolationException이 발생하면 DuplicatedUserNameException으로 포장하여 런타임 예외를 던진다.")
  void addUser() {
    when(userConverter.convertUser(any())).thenReturn(user);
    when(userRepository.save(any())).thenThrow(new DataIntegrityViolationException("some message"));
    // stubbing 할 때 반환값에는 matcher를 사용 할 수 없다.

    assertThrows(DuplicatedUserNameException.class, () -> {
      userService.addUser(saveUserDto);
    });
  }
}