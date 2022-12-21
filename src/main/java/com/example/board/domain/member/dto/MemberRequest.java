package com.example.board.domain.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class  MemberRequest {

  private String name;
  private int age;
  private String hobby;

  public MemberRequest(String name, int age, String hobby){
    this.name = name;
    this.age = age;
    this.hobby = hobby;
  }
}
