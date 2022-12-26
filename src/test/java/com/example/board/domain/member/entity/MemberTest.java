package com.example.board.domain.member.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

  @Test
  @DisplayName("Member를 생성합니다")
  public void createMember(){
    //given
    String name = "김환";
    int age = 25;
    String hobby = "게임";

    // when
    Member member = new Member(name, age, hobby);

    //then
    assertThat(member.getAge())
        .isEqualTo(age);
    assertThat(member.getName())
        .isEqualTo(name);
    assertThat(member.getHobby())
        .isEqualTo(hobby);
  }

  @Test
  @DisplayName("Member의 나이를 변경합니다")
  public void updateMemberAge(){
    //given
    String name = "김환";
    int age = 25;
    String hobby = "게임";
    Member member = new Member(name, age, hobby);

    // when
    int newAge = 10;
    member.update(name, newAge, hobby);

    //then
    assertThat(member.getAge())
        .isEqualTo(newAge);
  }

  @Test
  @DisplayName("Member의 이름을 변경합니다")
  public void updateMemberName(){
    //given
    String name = "김환";
    int age = 25;
    String hobby = "게임";
    Member member = new Member(name, age, hobby);

    // when
    String newName = "김환";
    member.update(newName, age, hobby);

    //then
    assertThat(member.getName())
        .isEqualTo(newName);
  }

  @Test
  @DisplayName("Member의 취미를 변경합니다")
  public void updateMemberHobby(){
    //given
    String name = "김환";
    int age = 25;
    String hobby = "게임";
    Member member = new Member(name, age, hobby);

    // when
    String newHobby= "코딩";
    member.update(name, age, newHobby);

    //then
    assertThat(member.getHobby())
        .isEqualTo(newHobby);
  }
}