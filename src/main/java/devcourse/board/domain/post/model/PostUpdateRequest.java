package devcourse.board.domain.post.model;

public record PostUpdateRequest(
        String title,
        String content
) {
}
