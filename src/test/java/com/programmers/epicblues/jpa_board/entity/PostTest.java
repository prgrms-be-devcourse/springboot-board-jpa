package com.programmers.epicblues.jpa_board.entity;

import static org.assertj.core.api.Assertions.assertThat;

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
        .createdAt(createdAt)
        .createdBy(createdBy)
        .build();

    // Then
    assertThat(post.getId()).isNull();
    assertThat(post.getTitle()).isEqualTo(title);
    assertThat(post.getContent()).isEqualTo(content);
    assertThat(post.getCreatedAt()).isEqualTo(createdAt);
    assertThat(post.getCreatedBy()).isEqualTo(createdBy);

  }

}
