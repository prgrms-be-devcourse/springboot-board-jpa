package com.prgrms.dlfdyd96.board.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgrms.dlfdyd96.board.domain.User;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @AfterEach
  void tearDown() {
    this.userRepository.deleteAll();
  }

  private User originUser;

  // @Test
  // @DisplayName("User를 저장할 수 있다.")
  @BeforeEach
  void saveTest() {
    User user = User.builder()
        .name("ilyong")
        .age(26)
        .hobby("개발이취미엌ㅋㅋ말해놓고도웃기네")
        .build();
    originUser = userRepository.save(user);
  }

  @Test
  @DisplayName("잘못 된 값을 입력할 때")
  void saveExceptionTest() {
    // GIVEN
    User user = User.builder()
        // .name("ilyong")
        .age(26)
        .hobby("개발이취미엌ㅋㅋ말해놓고도웃기네")
        .build();
    // THEN
    assertThatThrownBy(() -> {
      // WHEN
      userRepository.save(user);
    });
  }

  @Test
  @DisplayName("id로 User 데이터를 찾을 수 있다.")
  void findByIdTest() {
    // GIVEN
    long id = 1;

    // WHEN
    Optional<User> userFindById = userRepository.findById(id);

    // THEN
    userFindById.ifPresentOrElse(
        (item) -> assertThat(item.getId()).isEqualTo(id),
        () -> {
          throw new IllegalArgumentException("없음");
        }
    );
  }
}