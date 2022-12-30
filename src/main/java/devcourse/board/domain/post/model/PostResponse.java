package devcourse.board.domain.post.model;

import java.time.LocalDateTime;

public record PostResponse(
        Long postId,
        String title,
        String content,
        LocalDateTime createdAt,
        String createdBy
) {

    public PostResponse(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.findOutWriterName()
        );
    }
}
