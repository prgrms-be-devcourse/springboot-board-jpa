package com.example.board.domain.post.dto;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.entity.Post;

public record PostRequest(String title, String content, Long memberId) {

  public Post toEntity(Member member){
    return new Post(title(), content(), member);
  }
}
