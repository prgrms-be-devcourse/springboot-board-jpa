package com.prgrms.board.dto.post;

import com.prgrms.board.domain.Post;
import com.prgrms.board.domain.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Builder
public class PostResponse {
    private final Long postId;
    private final String userEmail;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static PostResponse fromEntity(Post post) {
        Users user = post.getUser();
        return PostResponse.builder()
                .postId(post.getPostId())
                .userEmail(user.getEmail())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedDate())
                .modifiedAt(post.getModifiedDate())
                .build();
    }
}
