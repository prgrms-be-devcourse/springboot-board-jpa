package com.example.board.domain.post.dto;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.entity.Post;

public record PostCreateRequest(
    String title,
    String content
) {

    public Post toEntity(Member member) {
        return Post.builder()
            .title(title)
            .content(content)
            .member(member)
            .build();
    }
}
