package com.example.board.domain.member.dto;

import com.example.board.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberRequest {

  private String name;
  private int age;
  private String hobby;

  public Member toEntity(){
    return new Member(name, age, hobby);
  }
}
