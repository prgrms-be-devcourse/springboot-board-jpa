package prgrms.board.post.application.dto.response;

import prgrms.board.post.domain.Post;

import java.time.LocalDateTime;

public record PostSaveResponse(
        Long postId,
        LocalDateTime createdAt
) {
    public static PostSaveResponse of(Post newPost) {
        long postId = newPost.getId();
        LocalDateTime postCreatedAt = newPost.getCreatedAt();

        return new PostSaveResponse(postId, postCreatedAt);
    }
}
