package com.programmers.epicblues.board.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.EntityFixture;

class PostTest {

  @Test
  @DisplayName("Post는 요구사항에 맞는 필드들을 갖고있어야 한다.")
  void post_should_meet_all_requirements() {

    // Given
    String title = "제목입니다.";
    String content = "게시글 내용 입니다.";

    // When
    Post post = Post.builder()
        .title(title)
        .content(content)
        .build();

    // Then
    assertThat(post.getId()).isNull();
    assertThat(post.getTitle()).isEqualTo(title);
    assertThat(post.getContent()).isEqualTo(content);
    assertThat(post.getCreatedAt()).isNull();

  }

  @Test
  @DisplayName("Post가 User를 할당할 경우, User의 Post 목록에도 Post가 등록되어 있어야 한다.")
  void assign_user_test() {

    // Given
    Post post = EntityFixture.getFirstPost();
    User user = EntityFixture.getUser();

    // When
    post.assignUser(user);

    // Then
    assertThat(user.contains(post)).isTrue();
  }

  @Test
  @DisplayName("Post에 User를 할당하면 User의 이름을 createdBy에 할당해야 한다.")
  void test_assign_user_set_post_created_by() {

    // Given
    User user = EntityFixture.getUser();
    Post newPost = Post.builder().title("hello").content("yo").build();

    // When
    newPost.assignUser(user);

    // Then
    assertThat(newPost.getCreatedBy()).isEqualTo(user.getName());

  }

}
