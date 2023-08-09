package org.prgms.boardservice.domain.post.dto;

import org.prgms.boardservice.domain.post.Content;
import org.prgms.boardservice.domain.post.Post;
import org.prgms.boardservice.domain.post.Title;

public record PostCreateRequestDto(String title, String content, Long userId) {

    public Post toEntity() {
        return Post.builder()
                .title(new Title(title))
                .content(new Content(content))
                .userId(userId)
                .build();
    }
}
