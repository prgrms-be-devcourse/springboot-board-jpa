package com.programmers.epicblues.board.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.EntityFixture;

class UserTest {

  @Test
  @DisplayName("User는 요구사항에 맞는 속성을 가져야 한다.")
  void user_should_meet_all_requirements() {
    // Given
    String name = "Minsung";
    int age = 30;
    String hobby = "맛집 탐방";
    String createdBy = "민성";

    // When
    User user = new User(name, age, hobby, createdBy);

    // Then
    assertThat(user.getId()).isNull();
    assertThat(user.getName()).isEqualTo(name);
    assertThat(user.getAge()).isEqualTo(age);
    assertThat(user.getHobby()).isEqualTo(hobby);
    assertThat(user.getCreatedAt()).isNull();
    assertThat(user.getCreatedBy()).isEqualTo(createdBy);

  }

  @Test
  @DisplayName("User가 post를 추가할 경우, post도 user를 참조할 수 있어야 한다. ")
  void test() {

    // Given
    var user = EntityFixture.getUser();
    var post = EntityFixture.getFirstPost();

    // When
    user.addPost(post);

    // Then
    assertThat(post.getUser()).isEqualTo(user);
  }

  @Test
  @DisplayName("여러 post를 한 번에 추가하면, post에도 user가 등록된다.")
  void add_posts_test() {

    // Given
    var user = EntityFixture.getUser();
    var posts = EntityFixture.getPostList();
    // When
    user.addPosts(posts);

    // Then
    assertThat(posts).allMatch(post -> post.getUser().equals(user));
  }

}
