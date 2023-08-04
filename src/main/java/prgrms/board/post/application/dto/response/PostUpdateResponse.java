package prgrms.board.post.application.dto.response;

import prgrms.board.post.domain.Post;

import java.time.LocalDateTime;

public record PostUpdateResponse(
        Long postId,
        String title,
        String content,
        String createdBy,
        LocalDateTime updatedAt
) {
    public static PostUpdateResponse of(Post post) {
        Long postId = post.getId();
        String postTitle = post.getTitle();
        String postContent = post.getContent();
        String postCreatedBy = post.getCreatedBy();
        LocalDateTime postUpdatedAt = post.getUpdatedAt();

        return new PostUpdateResponse(
                postId, postTitle, postContent,
                postCreatedBy, postUpdatedAt
        );
    }
}
