package com.example.board.domain.post.dto;

import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.post.entity.Post;
import java.time.LocalDateTime;
public class PostResponse {

  private PostResponse(){}

  public record Shortcut(Long id, String title){

    public static Shortcut from(Post post) {
      return new Shortcut(
          post.getId(),
          post.getTitle()
      );
    }
  }

  public record Detail(Long id, String title, String content, MemberResponse.OnlyAuthor author,
                             LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy){

    public static Detail from(Post post) {
      return new Detail(
          post.getId(),
          post.getTitle(),
          post.getContent(),
          MemberResponse.OnlyAuthor
              .from(post.getMember()),
          post.getCreatedAt(),
          post.getUpdatedAt(),
          post.getCreatedBy(),
          post.getUpdatedBy()
      );
    }
  }
}
