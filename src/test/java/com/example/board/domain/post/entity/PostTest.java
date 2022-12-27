package com.example.board.domain.post.entity;

import com.example.board.domain.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

  @Test
  @DisplayName("Post를 생성할 수 있습니다")
  void newPost(){
    //given
    Member member = new Member("김환", "email123@naver.com", "password123!", 25, "게임");

    //when
    Post post = new Post("RBF", "RBF를 작성합니다", member);
    String title = post.getTitle();
    String content = post.getContent();

    //then
    Assertions.assertThat(post.getTitle())
        .isEqualTo(title);
    Assertions.assertThat(post.getContent())
        .isEqualTo(content);
  }

  @Test
  @DisplayName("Post의 제목을 변경할 수 있습니다")
  void updateTitle(){
    //given
    Member member = new Member("김환", "email123@naver.com", "password123!", 25, "게임");
    String title = "RBF";
    String content = "RBF를 작성합니다";

    Post post = new Post(title, content, member);

    //when
    String newTitle = "회고록";
    post.update(newTitle, content, member.getName());

    //then
    Assertions.assertThat(post.getTitle())
        .isEqualTo(newTitle);
  }

  @Test
  @DisplayName("Post의 내용을 변경할 수 있습니다")
  void updateContent(){
    //given
    Member member = new Member("김환", "email123@naver.com", "password123!", 25, "게임");
    String title = "RBF";
    String content = "RBF를 작성합니다";

    Post post = new Post(title, content, member);

    //when
    String newContent = "RBF를 더 많이 작성합니다";
    post.update(title, newContent, member.getName());

    //then
    Assertions.assertThat(post.getContent())
        .isEqualTo(newContent);
  }
}