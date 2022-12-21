package com.example.board.domain.post.dto;

import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.dto.MemberResponse.OnlyAuthor;
import com.example.board.domain.post.entity.Post;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponse {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Shortcut{

    private Long id;
    private String title;

    public static Shortcut from(Post post) {
      Shortcut shortcut = new Shortcut();

      shortcut.id = post.getId();
      shortcut.title = post.getTitle();

      return shortcut;
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Detail{

    private Long id;
    private String title;
    private String content;
    private OnlyAuthor author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public static Detail from(Post post){
      Detail detail = new Detail();

      detail.id = post.getId();
      detail.title = post.getTitle();
      detail.content = post.getContent();
      detail.author = MemberResponse.OnlyAuthor
          .from(post.getMember());
      detail.createdAt = post.getCreatedAt();
      detail.updatedAt = post.getUpdatedAt();
      detail.createdBy = post.getCreatedBy();
      detail.updatedBy = post.getUpdatedBy();

      return detail;
    }
  }
}
