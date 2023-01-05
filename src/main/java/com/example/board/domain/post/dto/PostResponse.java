package com.example.board.domain.post.dto;

import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.post.entity.Post;
import java.time.LocalDateTime;

public class PostResponse {

  private PostResponse(){}

  public record Shortcut(Long id, String title){

    public Shortcut(Post post) {
      this(
          post.getId(),
          post.getTitle());
    }
  }

  public record Detail(Long id, String title, String content, MemberResponse.OnlyAuthor author,
                             LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy){

    public Detail(Post post){
      this(
          post.getId(),
          post.getTitle(),
          post.getContent(),
          new MemberResponse.OnlyAuthor(
              post.getMember()),
          post.getCreatedAt(),
          post.getUpdatedAt(),
          post.getCreatedBy(),
          post.getUpdatedBy());
    }
  }
}
