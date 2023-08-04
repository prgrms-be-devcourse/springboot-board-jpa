package prgrms.board.post.application.dto.response;

import prgrms.board.post.domain.Post;

import java.time.LocalDateTime;

public record PostFindResponse(
        String createdBy,
        String title,
        String content,
        LocalDateTime createdAt
) {
    public static PostFindResponse of(Post post) {
        String postCreatedBy = post.getCreatedBy();
        String postTitle = post.getTitle();
        String postContent = post.getContent();
        LocalDateTime postCreatedAt = post.getCreatedAt();

        return new PostFindResponse(
                postCreatedBy, postTitle,
                postContent, postCreatedAt
        );
    }
}
