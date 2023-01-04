package devcourse.board.domain.post.model;

public record PostCreationRequest(
        String title,
        String content
) {
}
