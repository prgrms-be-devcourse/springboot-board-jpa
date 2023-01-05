package com.example.board.domain.member.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {
  private static final String NAME = "김환";
  private static final String EMAIL = "email123@naver.com";
  private static final String PASSWORD = "password123!";
  private static final int AGE = 25;
  private static final String HOBBY = "게임";

  @Test
  @DisplayName("Member를 생성합니다")
  void createMember(){
    //given & when
    Member member = new Member(NAME, EMAIL, PASSWORD, AGE, HOBBY);

    //then
    String actualName = member.getName();
    String actualEmail = member.getEmail();
    String actualPassword = member.getPassword();
    int actualAge = member.getAge();
    String actualHobby = member.getHobby();

    assertThat(actualName).isEqualTo("김환");
    assertThat(actualEmail).isEqualTo("email123@naver.com");
    assertThat(actualPassword).isEqualTo("password123!");
    assertThat(actualAge).isEqualTo(25);
    assertThat(actualHobby).isEqualTo("게임");
  }

  @Test
  @DisplayName("Member의 나이를 변경합니다")
  void updateMemberAge(){
    //given
    Member member = new Member(NAME, EMAIL, PASSWORD, AGE, HOBBY);

    //when
    int newAge = 10;
    member.update(NAME, newAge, HOBBY);

    //then
    int actualAge = member.getAge();
    assertThat(actualAge).isEqualTo(10);
  }

  @Test
  @DisplayName("Member의 이름을 변경합니다")
  void updateMemberName(){
    //given
    Member member = new Member(NAME, EMAIL, PASSWORD, AGE, HOBBY);

    // when
    String newName = "홍길동";
    member.update(newName, AGE, HOBBY);

    //then
    String actualName = member.getName();
    assertThat(actualName).isEqualTo("홍길동");
  }

  @Test
  @DisplayName("Member의 취미를 변경합니다")
  void updateMemberHobby(){
    //given
    Member member = new Member(NAME, EMAIL, PASSWORD, AGE, HOBBY);

    // when
    String newHobby= "코딩";
    member.update(NAME, AGE, newHobby);

    //then
    String actualHobby = member.getHobby();
    assertThat(actualHobby).isEqualTo("코딩");
  }
}