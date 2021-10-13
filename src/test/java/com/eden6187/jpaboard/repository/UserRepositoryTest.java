package com.eden6187.jpaboard.repository;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.eden6187.jpaboard.test_data.UserMockData;
import com.eden6187.jpaboard.model.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  @Transactional
  @DisplayName("새로운 User를 저장 할 수 있다.")
  void createUser() {
    //given
    User user = User.builder()
        .name(UserMockData.TEST_NAME)
        .age(UserMockData.TEST_AGE)
        .hobby(UserMockData.TEST_HOBBY)
        .build();

    //when
    User savedUser = userRepository.save(user);

    //Then
    Optional<User> foundUser = userRepository.findById(savedUser.getId());
    assertThat(foundUser.isPresent(), is(true));
    assertThat(foundUser.get().getName(), is(UserMockData.TEST_NAME));
  }

  @Test
  @Transactional
  @DisplayName("user table은 unique 제약 조건을 가진다.")
  void duplicatedUser() {

    User user1 = User.builder()
        .name(UserMockData.TEST_NAME)
        .age(UserMockData.TEST_AGE)
        .hobby(UserMockData.TEST_HOBBY)
        .build();

    User user2 = User.builder()
        .name(UserMockData.TEST_NAME)
        .age(UserMockData.TEST_AGE)
        .hobby(UserMockData.TEST_HOBBY)
        .build();

    userRepository.save(user1);

    //when + then
    assertThrows(DataIntegrityViolationException.class, () -> {
      userRepository.save(user2);
    });

  }

}