package com.prgrms.boardjpa.domain.post.dto;

import com.prgrms.boardjpa.domain.member.Member;
import com.prgrms.boardjpa.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostCreateRequestDto {

    private Long memberId;
    private String title;
    private String content;

    public Post toPost() {
        return Post.builder()
                .member(
                        Member.builder().id(memberId).build()
                )
                .title(title)
                .content(content)
                .build();
    }
}
