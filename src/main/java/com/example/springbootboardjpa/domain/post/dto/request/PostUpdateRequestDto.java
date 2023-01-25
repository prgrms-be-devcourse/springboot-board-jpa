package com.example.springbootboardjpa.domain.post.dto.request;

import com.example.springbootboardjpa.domain.member.entity.Member;
import com.example.springbootboardjpa.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class PostUpdateRequestDto {

  private String title;
  private String content;
  private long memberId;

  public Post toPost(PostUpdateRequestDto postUpdateRequestDto) {
    return Post.builder()
        .title(postUpdateRequestDto.getTitle())
        .content(postUpdateRequestDto.getContent())
        .build();
  }
}
