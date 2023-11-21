package kdt.jpa.board.post.service.dto.request;

public record CreatePostRequest(
        String title,
        String content,
        long userId
) {
}
