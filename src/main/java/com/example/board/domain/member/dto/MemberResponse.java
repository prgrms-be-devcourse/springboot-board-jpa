package com.example.board.domain.member.dto;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.PostResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class MemberResponse {

  private Long id;
  private String name;
  private int age;
  private String hobby;
  private List<PostResponse> posts;

  private MemberResponse(){}

  public static MemberResponse from(Member member) {
    MemberResponse memberResponse = new MemberResponse();

    memberResponse.id = member.getId();
    memberResponse.name = member.getName();
    memberResponse.age = member.getAge();
    memberResponse.hobby = member.getHobby();
    memberResponse.posts = member.getPosts()
        .stream()
        .map(PostResponse::withoutMember)
        .collect(Collectors.toList());

    return memberResponse;
  }
}
