package com.prgrms.boardjpa.domain.post.dto;

import com.prgrms.boardjpa.domain.member.Member;
import com.prgrms.boardjpa.domain.post.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final Member member;

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getMember());
    }
}
