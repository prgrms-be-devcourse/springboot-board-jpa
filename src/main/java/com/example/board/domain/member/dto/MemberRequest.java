package com.example.board.domain.member.dto;

import com.example.board.domain.member.entity.Member;

public class MemberRequest {

  private MemberRequest(){}

  public record Login(String email, String password) {

  }

  public record SignUp(String name, String email, String password, int age, String hobby) {

    public Member toEntity() {
      return new Member(name, email, password, age, hobby);
    }
  }
}