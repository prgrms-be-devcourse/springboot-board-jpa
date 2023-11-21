package kdt.jpa.board.post.service.dto.request;

public record EditPostRequest (
        long postId,
        String title,
        String content
) {
}
