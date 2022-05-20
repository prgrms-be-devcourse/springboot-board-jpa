package org.programmers.springbootboardjpa.web.dto.post;

import org.programmers.springbootboardjpa.domain.Post;

import java.time.LocalDateTime;

public record PostDtoV1(Long postId, String title, String content, Long userId, String nickname,
                        LocalDateTime createdDate, LocalDateTime lastModifiedDate) {

    public static PostDtoV1 from(Post post) {
        return new PostDtoV1(post.getPostId(), post.getTitle(), post.getContent(),
                post.getUser().getUserId(), post.getUser().getNickname(),
                post.getCreatedDate(), post.getLastModifiedDate());
    }
}
