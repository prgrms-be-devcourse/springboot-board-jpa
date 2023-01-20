package com.example.springbootboardjpa.domain.post.dto.request;

import com.example.springbootboardjpa.domain.member.entity.Member;
import com.example.springbootboardjpa.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSaveRequestDto {

  private String title;
  private String content;
  private long memberId;

  public static Post toPost(PostSaveRequestDto postSaveRequestDto, Member member) {
    return Post.builder()
        .title(postSaveRequestDto.getTitle())
        .content(postSaveRequestDto.getContent())
        .member(member)
        .build();
  }
}
