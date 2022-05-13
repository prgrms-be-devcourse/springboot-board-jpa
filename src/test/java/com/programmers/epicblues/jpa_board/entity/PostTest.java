package com.programmers.epicblues.jpa_board.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.epicblues.jpa_board.EntityFixture;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

  @Test
  @DisplayName("Post는 요구사항에 맞는 필드들을 갖고있어야 한다.")
  void post_should_meet_all_requirements() {

    // Given
    String title = "제목입니다.";
    String content = "게시글 내용 입니다.";
    LocalDateTime createdAt = LocalDateTime.now();
    String createdBy = "김민성";

    // When
    Post post = Post.builder()
        .title(title)
        .content(content)
        .createdBy(createdBy)
        .build();

    // Then
    assertThat(post.getId()).isNull();
    assertThat(post.getTitle()).isEqualTo(title);
    assertThat(post.getContent()).isEqualTo(content);
    assertThat(post.getCreatedAt()).isNull();
    assertThat(post.getCreatedBy()).isEqualTo(createdBy);

  }

  @Test
  @DisplayName("Post가 User를 할당할 경우, User의 Post 목록에도 Post가 등록되어 있어야 한다.")
  void assign_user_test() {

    // Given
    var post = EntityFixture.getFirstPost();
    var user = EntityFixture.getUser();

    // When
    post.assignUser(user);

    // Then
    assertThat(user.contains(post)).isTrue();
  }

}
