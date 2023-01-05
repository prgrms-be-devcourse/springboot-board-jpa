package com.example.board.domain.post.entity;

import static org.assertj.core.api.Assertions.*;

import com.example.board.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

  private static final String TITLE = "RBF";
  private static final String CONTENT = "RBF를 작성합니다";

  private final Member member = new Member("김환", "email123@naver.com", "password123!", 25, "게임");

  @Test
  @DisplayName("Post를 생성할 수 있습니다")
  void newPost(){
    //given & when
    Post post = new Post(TITLE, CONTENT, member);

    //then
    String actualTitle = post.getTitle();
    String actualContent = post.getContent();

    assertThat(actualTitle)
        .isEqualTo("RBF");
    assertThat(actualContent)
        .isEqualTo("RBF를 작성합니다");
  }

  @Test
  @DisplayName("Post를 생성 시 Member와의 연관관계를 설정합니다")
  void mappingPostAndMember(){
    //given & when
    Post post = new Post(TITLE, CONTENT, member);

    //then
    int actualPostSize = member.getPosts().size();
    Member actualMember = post.getMember();

    assertThat(actualPostSize).isEqualTo(1);
    assertThat(actualMember).isEqualTo(member);
  }

  @Test
  @DisplayName("Post의 제목을 변경할 수 있습니다")
  void updateTitle(){
    //given
    Post post = new Post(TITLE, CONTENT, member);

    //when
    String newTitle = "회고록";
    post.update(newTitle, CONTENT, member.getName());

    //then
    String actualTitle = post.getTitle();
    assertThat(actualTitle)
        .isEqualTo("회고록");
  }

  @Test
  @DisplayName("Post의 내용을 변경할 수 있습니다")
  void updateContent(){
    //given
    Post post = new Post(TITLE, CONTENT, member);

    //when
    String newContent = "RBF를 더 많이 작성합니다";
    post.update(TITLE, newContent, member.getName());

    //then
    String actualContent = post.getContent();
    assertThat(actualContent)
        .isEqualTo("RBF를 더 많이 작성합니다");
  }
}