package com.programmers.epicblues.jpa_board.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  @DisplayName("사용자 엔터티는 요구사항에 맞는 속성을 가져야 한다.")
  void test() {
    // id는 database에서 auto_increment이므로 생성자에서 생성하지 않는다.
    // created_at은
    // Given
    String name = "Minsung";
    int age = 30;
    String hobby = "맛집 탐방";
    String createdBy = "민성";
    LocalDateTime createdAt = LocalDateTime.now();

    // When
    User user = new User(name, age, hobby, createdBy, createdAt);

    // Then
    assertThat(user.getId()).isNull();
    assertThat(user.getName()).isEqualTo(name);
    assertThat(user.getAge()).isEqualTo(age);
    assertThat(user.getHobby()).isEqualTo(hobby);
    assertThat(user.getCreatedBy()).isEqualTo(createdBy);
    assertThat(user.getCreatedAt()).isEqualTo(createdAt);

  }

}
