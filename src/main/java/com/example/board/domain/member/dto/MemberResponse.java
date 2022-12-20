package com.example.board.domain.member.dto;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.dto.PostResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Detail{

    private Long id;
    private String name;
    private int age;
    private String hobby;
    private List<PostResponse.Shortcut> posts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Detail from(Member member) {
      Detail detail = new Detail();

      detail.id = member.getId();
      detail.name = member.getName();
      detail.age = member.getAge();
      detail.hobby = member.getHobby();
      detail.posts = member.getPosts()
          .stream()
          .map(PostResponse.Shortcut::withoutMember)
          .collect(Collectors.toList());
      detail.createdAt = member.getCreatedAt();
      detail.updatedAt = member.getUpdatedAt();

      return detail;
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class OnlyAuthor {

    private Long id;
    private String name;

    public static OnlyAuthor from(Member member) {
      OnlyAuthor onlyAuthor = new OnlyAuthor();

      onlyAuthor.id = member.getId();
      onlyAuthor.name = member.getName();

      return onlyAuthor;
    }
  }
}
