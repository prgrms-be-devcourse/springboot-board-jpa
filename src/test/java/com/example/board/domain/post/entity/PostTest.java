package com.example.board.domain.post.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

  @Test
  @DisplayName("Post를 생성할 수 있습니다")
  void newPost(){
    //given
    String title = "RBF";
    String content = "RBF를 작성합니다";

    //when
    Post post = new Post(title, content);

    //then
    Assertions.assertThat(post.getTitle()).isEqualTo(title);
    Assertions.assertThat(post.getContent()).isEqualTo(content);
  }

  @Test
  @DisplayName("Post의 제목을 변경할 수 있습니다")
  void updateTitle(){
    //given
    String title = "RBF";
    String content = "RBF를 작성합니다";

    Post post = new Post(title, content);

    //when
    String newTitle = "회고록";
    post.changeTitle(newTitle);

    //then
    Assertions.assertThat(post.getTitle()).isEqualTo(newTitle);
  }

  @Test
  @DisplayName("Post의 내용을 변경할 수 있습니다")
  void updateContent(){
    //given
    String title = "RBF";
    String content = "RBF를 작성합니다";

    Post post = new Post(title, content);

    //when
    String newContent = "RBF를 더 많이 작성합니다";
    post.changeContent(newContent);

    //then
    Assertions.assertThat(post.getContent()).isEqualTo(newContent);
  }
}