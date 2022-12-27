package com.example.board.domain.member.dto;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.dto.PostResponse;
import java.time.LocalDateTime;
import java.util.List;

public class MemberResponse {

  private MemberResponse(){}

  public record Detail(Long id, String name, String email, String password, int age, String hobby, List<PostResponse.Shortcut> posts,
                              LocalDateTime createdAt, LocalDateTime updatedAt){

    public static Detail from(Member member) {
      return new Detail(
          member.getId(),
          member.getName(),
          member.getEmail(),
          member.getPassword(),
          member.getAge(),
          member.getHobby(),
          member.getPosts()
              .stream()
              .map(PostResponse.Shortcut::from)
              .toList(),
          member.getCreatedAt(),
          member.getUpdatedAt()
      );
    }
  }

  public record OnlyAuthor(Long id, String name) {

    public static OnlyAuthor from(Member member) {
      return new OnlyAuthor(
          member.getId(),
          member.getName()
      );
    }
  }
}
